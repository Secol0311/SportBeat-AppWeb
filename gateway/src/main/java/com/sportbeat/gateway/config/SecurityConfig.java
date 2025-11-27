package com.sportbeat.gateway.config;

import com.sportbeat.gateway.security.JwtAuthenticationFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.config.web.server.SecurityWebFiltersOrder;
import org.springframework.security.web.server.SecurityWebFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityWebFilterChain springSecurityFilterChain(ServerHttpSecurity http,
                                                            JwtAuthenticationFilter jwtFilter) {

        return http
                .csrf(ServerHttpSecurity.CsrfSpec::disable)
                .authorizeExchange(auth -> auth
                        .pathMatchers("/login", "/register", "/css/**", "/js/**", "/images/**").permitAll()
                        .pathMatchers("/api/auth/**").permitAll()
                        .pathMatchers("/dashboard/admin/**").hasRole("ADMINISTRADOR")
                        .pathMatchers("/dashboard/entrenador/**").hasRole("ENTRENADOR")
                        .pathMatchers("/dashboard/jugador/**").hasRole("JUGADOR")
                        .anyExchange().authenticated()
                )
                .addFilterAt(jwtFilter, SecurityWebFiltersOrder.AUTHENTICATION)
                .exceptionHandling(ex -> ex
                        .authenticationEntryPoint((swe, e) -> {
                            swe.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
                            return swe.getResponse().setComplete();
                        })
                )
                .build();
    }
}
