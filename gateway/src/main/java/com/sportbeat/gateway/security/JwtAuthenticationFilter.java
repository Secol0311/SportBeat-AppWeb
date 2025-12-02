package com.sportbeat.gateway.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.ReactiveSecurityContextHolder;
import org.springframework.security.core.context.SecurityContextImpl;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;

import java.util.List;
@Component
public class JwtAuthenticationFilter implements WebFilter {

    @Autowired
    private JwtUtil jwtUtil;

    private final AntPathMatcher pathMatcher = new AntPathMatcher();

    // Lista de rutas públicas que no requieren autenticación
    private static final List<String> PUBLIC_PATHS = List.of(
            "/", "/login", "/register-jugador", "/register-entrenador", "/contacto", "/quienes-somos", "/deportes",
            "/css/**", "/js/**", "/images/**", "/favicon.ico",
            "/api/auth/**",
            "/ligas", "/equipos", "/partidos/calendario"
    );

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
        String path = exchange.getRequest().getPath().value();

        // ========================================================================
        // PASO 1: Si la ruta es PÚBLICA, LIMPIAR el contexto de seguridad.
        // Esto es CRÍTICO. Asegura que no haya residuos de una sesión anterior.
        // ========================================================================
        if (isPublicPath(path)) {
            System.out.println("DEBUG: Ruta pública '" + path + "'. Limpiando contexto de seguridad.");
            return chain.filter(exchange)
                    .contextWrite(ReactiveSecurityContextHolder.clearContext());
        }

        // ========================================================================
        // PASO 2: Si la ruta es PRIVADA, procesar el token.
        // ========================================================================
        String authHeader = exchange.getRequest().getHeaders().getFirst(HttpHeaders.AUTHORIZATION);

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            System.out.println("DEBUG: Ruta privada '" + path + "' sin token. Continuando sin autenticación.");
            return chain.filter(exchange);
        }

        String token = authHeader.substring(7);

        if (!jwtUtil.validateToken(token)) {
            System.out.println("DEBUG: Token inválido. Continuando sin autenticación.");
            return chain.filter(exchange);
        }

        String username = jwtUtil.getUsernameFromToken(token);
        String role = jwtUtil.getClaimsFromToken(token).get("role", String.class);
        List<SimpleGrantedAuthority> authorities = List.of(new SimpleGrantedAuthority("ROLE_" + role));

        UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(username, null, authorities);

        return chain.filter(exchange)
                .contextWrite(ReactiveSecurityContextHolder.withAuthentication(auth));
    }

    /**
     * Método de ayuda para verificar si una ruta es pública.
     */
    private boolean isPublicPath(String path) {
        return PUBLIC_PATHS.stream().anyMatch(publicPath -> pathMatcher.match(publicPath, path));
    }
}