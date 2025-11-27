package com.sportbeat.gateway.config;

import com.sportbeat.gateway.security.JwtAuthenticationFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.security.config.web.server.SecurityWebFiltersOrder;

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
                        .pathMatchers("/login", "/register", "/css/**", "/js/**", "/images/**").permitAll()
                        .pathMatchers("/api/auth/**").permitAll()
                        .pathMatchers("/dashboard/administrador/**").hasRole("ADMINISTRADOR")
                        .pathMatchers("/dashboard/entrenador/**").hasRole("ENTRENADOR")
                        .pathMatchers("/dashboard/jugador/**").hasRole("JUGADOR")
                        .anyExchange().authenticated()
                )
                .addFilterAt(jwtFilter, SecurityWebFiltersOrder.AUTHENTICATION)
                .build();
    }
}
