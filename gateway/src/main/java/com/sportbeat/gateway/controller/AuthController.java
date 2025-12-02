// En: gateway/src/main/java/com/sportbeat/gateway/controller/AuthController.java
package com.sportbeat.gateway.controller;

import com.sportbeat.gateway.dto.CrearUsuarioRequest;
import com.sportbeat.gateway.serviceclient.UsuarioEquipoServiceClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseCookie;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.Map;

@Controller
public class AuthController {

    @Autowired private UsuarioEquipoServiceClient usuarioEquipoServiceClient;
    @Autowired private WebClient.Builder webClientBuilder;

    @GetMapping("/register-jugador")
    public Mono<String> mostrarRegistroJugador(Model model) {
        model.addAttribute("usuario", new CrearUsuarioRequest());
        model.addAttribute("titulo", "Registro de Jugador");
        return Mono.just("auth/registro-jugador");
    }

    @GetMapping("/register-entrenador")
    public Mono<String> mostrarRegistroEntrenador(Model model) {
        model.addAttribute("usuario", new CrearUsuarioRequest());
        model.addAttribute("titulo", "Registro de Entrenador");
        return Mono.just("auth/registro-entrenador");
    }

    @PostMapping("/register")
    public Mono<String> procesarRegistro(@ModelAttribute CrearUsuarioRequest request, Model model) {
        // Usamos el cliente reactivo
        return usuarioEquipoServiceClient.crearUsuario(request)
                .then(Mono.just("redirect:/login?registered=true")) // Si tiene éxito, redirige
                .onErrorResume(e -> { // Si hay un error, recarga la página con un mensaje
                    model.addAttribute("error", "No se pudo registrar el usuario. El usuario o email ya podrían estar en uso.");
                    model.addAttribute("usuario", request);
                    String view = "COACH".equals(request.getRole()) ? "auth/registro-entrenador" : "auth/registro-jugador";
                    return Mono.just(view);
                });
    }

    @GetMapping("/login")
    public Mono<String> loginPage() {
        return Mono.just("auth/login");
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
                        ResponseCookie cookie = ResponseCookie.from("JWT_TOKEN", token)
                                .httpOnly(true)
                                .secure(false) // cámbialo a true en HTTPS
                                .path("/")
                                .maxAge(24 * 60 * 60)
                                .build();
                        exchange.getResponse().addCookie(cookie);
                        return Mono.just("redirect:/"); // Redirige al index después del login
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