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

    // Usamos AntPathMatcher para manejar patrones de URL como "/css/**"
    private final AntPathMatcher pathMatcher = new AntPathMatcher();

    // Lista de rutas públicas que no requieren autenticación
    private static final List<String> PUBLIC_PATHS = List.of(
            "/", "/login", "/register-jugador", "/register-entrenador", "/contacto",
            "/css/**", "/js/**", "/images/**", "/favicon.ico",
            "/api/auth/**", "/ligas", "/equipos", "/partidos/calendario"
    );

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
        ServerHttpRequest request = exchange.getRequest();
        String path = request.getPath().value();

        // 1. VERIFICAR SI LA RUTA ES PÚBLICA
        // Si la ruta actual coincide con algún patrón de la lista pública, continuamos sin validar el token.
        if (isPublicPath(path)) {
            return chain.filter(exchange);
        }

        // 2. OBTENER EL TOKEN JWT DE LA CABECERA
        String authHeader = request.getHeaders().getFirst(HttpHeaders.AUTHORIZATION);

        // Si no hay cabecera o no empieza con "Bearer ", continuamos sin autenticación.
        // La petición será bloqueada más tarde por la regla .anyExchange().authenticated() en SecurityConfig.
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            return chain.filter(exchange);
        }

        String token = authHeader.substring(7);

        // 3. VALIDAR EL TOKEN
        if (!jwtUtil.validateToken(token)) {
            // Si el token no es válido, continuamos sin autenticación.
            // La petición será bloqueada por la configuración de seguridad.
            return chain.filter(exchange);
        }

        // 4. EXTRAER INFORMACIÓN Y CREAR EL CONTEXTO DE SEGURIDAD
        String username = jwtUtil.getUsernameFromToken(token);
        String role = jwtUtil.getClaimsFromToken(token).get("role", String.class);

        // Creamos la lista de autoridades (roles) para Spring Security
        List<SimpleGrantedAuthority> authorities = List.of(new SimpleGrantedAuthority("ROLE_" + role));

        // Creamos el objeto de autenticación de Spring Security
        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                username,
                null, // Las credenciales (password) no son necesarias ya que el token es válido
                authorities
        );

        // 5. AÑADIR EL CONTEXTO DE SEGURIDAD AL FLUJO REACTIVO
        // Esto hace que la autenticación esté disponible para los controladores y la configuración de seguridad.
        return chain.filter(exchange)
                .contextWrite(ReactiveSecurityContextHolder.withAuthentication(authentication));
    }

    /**
     * Método de ayuda para verificar si una ruta coincide con la lista de rutas públicas.
     * @param path La ruta de la petición actual.
     * @return true si la ruta es pública, false en caso contrario.
     */
    private boolean isPublicPath(String path) {
        return PUBLIC_PATHS.stream().anyMatch(publicPath -> pathMatcher.match(publicPath, path));
    }
}