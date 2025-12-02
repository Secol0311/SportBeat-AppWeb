package com.sportbeat.gateway.config;

import com.sportbeat.gateway.security.JwtAuthenticationFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.web.server.SecurityWebFiltersOrder;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;



@Configuration
public class SecurityConfig {

    private final JwtAuthenticationFilter jwtFilter;

    public SecurityConfig(JwtAuthenticationFilter jwtFilter) {
        this.jwtFilter = jwtFilter;
    }

    @Bean
    public SecurityWebFilterChain securityWebFilterChain(ServerHttpSecurity http) {

        return http
                .csrf(ServerHttpSecurity.CsrfSpec::disable)
                .authorizeExchange(ex -> ex
                        // Públicas
                        .pathMatchers("/", "/css/**", "/js/**", "/images/**", "/favicon.ico").permitAll()
                        .pathMatchers("/login", "/register-jugador", "/register-entrenador").permitAll()
                        .pathMatchers("/api/auth/**").permitAll()

                        // Público de lectura
                        .pathMatchers("/ligas", "/equipos", "/partidos/calendario").permitAll()

                        // Roles
                        .pathMatchers("/dashboard/administrador/**").hasRole("ADMINISTRADOR")
                        .pathMatchers("/dashboard/entrenador/**").hasRole("ENTRENADOR")
                        .pathMatchers("/dashboard/jugador/**").hasRole("JUGADOR")

                        // Admin CRUD
                        .pathMatchers("/ligas/nueva", "/ligas/*/editar", "/ligas/*/eliminar").hasRole("ADMINISTRADOR")
                        .pathMatchers("/partidos/nuevo", "/partidos/*/editar", "/partidos/*/resultado")
                        .hasAnyRole("ADMINISTRADOR", "ENTRENADOR")

                        .anyExchange().authenticated()
                )
                // AGREGAR FILTRO JWT
                .addFilterAt(jwtFilter, SecurityWebFiltersOrder.AUTHENTICATION)

                .httpBasic(ServerHttpSecurity.HttpBasicSpec::disable)
                .formLogin(ServerHttpSecurity.FormLoginSpec::disable)
                .build();
    }
}