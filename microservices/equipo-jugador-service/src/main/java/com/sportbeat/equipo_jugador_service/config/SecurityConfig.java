package com.sportbeat.equipo_jugador_service.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http
            .csrf(csrf -> csrf.disable())  // Microservicios → desactivar CSRF
            .authorizeHttpRequests(auth -> auth
                // Endpoints PUBLICOS
                .requestMatchers(
                        "/api/usuarios/public/**" // Para validar usuario desde auth-service
                ).permitAll()

                // Aquí puedes agregar más rutas públicas si deseas
                .requestMatchers("/api/ligas/**").permitAll()
                .requestMatchers("/api/equipos/**").permitAll()
                .requestMatchers("/api/jugadores/**").permitAll()

                // Todo lo demás requiere autenticación
                .anyRequest().authenticated()
            )
            .httpBasic(Customizer.withDefaults()); // o quitar si no lo necesitas

        return http.build();
    }
}
