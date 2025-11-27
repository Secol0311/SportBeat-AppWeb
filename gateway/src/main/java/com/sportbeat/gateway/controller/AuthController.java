package com.sportbeat.gateway.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.Map;

@Controller
public class AuthController {

    @Autowired
    private WebClient.Builder webClientBuilder;

    @GetMapping("/login")
    public String loginPage() {
        return "auth/login"; // Thymeleaf, JSP, etc.
    }

    @PostMapping("/login")
    public Mono<String> processLogin(@RequestParam String username,
                                     @RequestParam String password,
                                     ServerWebExchange exchange) {

        WebClient webClient = webClientBuilder.build();

        return webClient.post()
                .uri("http://localhost:8081/api/auth/login")
                .bodyValue(Map.of("username", username, "password", password))
                .retrieve()
                .toEntity(Map.class)
                .flatMap(authResponse -> {

                    if (authResponse.getStatusCode().is2xxSuccessful() && authResponse.getBody() != null) {

                        String token = (String) authResponse.getBody().get("token");

                        // Creamos cookie HttpOnly para WebFlux
                        ResponseCookie cookie = ResponseCookie.from("JWT_TOKEN", token)
                                .httpOnly(true)
                                .secure(false) // cámbialo a true en HTTPS
                                .path("/")
                                .maxAge(24 * 60 * 60)
                                .build();

                        exchange.getResponse().addCookie(cookie);

                        return Mono.just("redirect:/dashboard/jugador");
                    }

                    return Mono.just("redirect:/login?error=true");
                })
                .onErrorReturn("redirect:/login?error=true");
    }

    @GetMapping("/logout")
    public Mono<String> logout(ServerWebExchange exchange) {

        ResponseCookie deleteCookie = ResponseCookie.from("JWT_TOKEN", "")
                .path("/")
                .maxAge(0)
                .build();

        exchange.getResponse().addCookie(deleteCookie);

        return Mono.just("redirect:/login");
    }
}
