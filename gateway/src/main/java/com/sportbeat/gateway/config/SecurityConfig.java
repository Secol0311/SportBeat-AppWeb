package com.sportbeat.gateway.config;

import com.sportbeat.gateway.security.JwtAuthenticationFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.web.server.SecurityWebFiltersOrder;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.security.web.server.context.NoOpServerSecurityContextRepository;



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

                .formLogin(ServerHttpSecurity.FormLoginSpec::disable)
                // Deshabilita el logout HTTP básico
                .httpBasic(ServerHttpSecurity.HttpBasicSpec::disable)
                // Deshabilita el logout por defecto de Spring Security
                .logout(logout -> logout.disable())
                

                .authorizeExchange(ex -> ex
                        .pathMatchers("/", "/css/**", "/js/**", "/images/**", "/favicon.ico").permitAll()
                        .pathMatchers("/", "/login", "/register-jugador", "/register-entrenador",
                                      "/contacto", "/quienes-somos", "/deportes", "/test-page").permitAll()
                        .pathMatchers("/api/auth/**").permitAll()
                        .pathMatchers("/ligas", "/equipos", "/partidos/calendario").permitAll()

                        .pathMatchers("/dashboard/administrador/**").hasRole("ADMINISTRADOR")
                        .pathMatchers("/dashboard/entrenador/**").hasRole("ENTRENADOR")
                        .pathMatchers("/dashboard/jugador/**").hasRole("JUGADOR")

                        .anyExchange().authenticated()
                )

                .addFilterAt(jwtFilter, SecurityWebFiltersOrder.AUTHENTICATION)

                .httpBasic(ServerHttpSecurity.HttpBasicSpec::disable)
                .formLogin(ServerHttpSecurity.FormLoginSpec::disable)

                .build();
    }
}