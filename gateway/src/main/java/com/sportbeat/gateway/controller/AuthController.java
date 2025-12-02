package com.sportbeat.gateway.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import com.sportbeat.gateway.dto.UsuarioRequest;
import com.sportbeat.gateway.dto.CrearUsuarioRequest;
import com.sportbeat.gateway.dto.LigaDTO;
import com.sportbeat.gateway.serviceclient.UsuarioEquipoServiceClient;
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

    @Autowired private UsuarioEquipoServiceClient usuarioEquipoServiceClient;
    

    @Autowired
    private WebClient.Builder webClientBuilder;


 @GetMapping("/register-jugador")
public String mostrarRegistroJugador(Model model) {
    model.addAttribute("usuario", new CrearUsuarioRequest());
    model.addAttribute("titulo", "Registro de Jugador");
    return "auth/registro-jugador";
}

@GetMapping("/register-entrenador")
public String mostrarRegistroEntrenador(Model model) {
    model.addAttribute("usuario", new CrearUsuarioRequest());
    model.addAttribute("titulo", "Registro de Entrenador");
    return "auth/registro-entrenador";
}

  @PostMapping("/register")
public String procesarRegistro(@ModelAttribute CrearUsuarioRequest request, Model model) {
    try {
        usuarioEquipoServiceClient.crearUsuario(request);
        return "redirect:/login?registered=true";
    } catch (Exception e) {
        model.addAttribute("error", "No se pudo registrar el usuario. El usuario o el Email podrian estar en uso");
        model.addAttribute("usuario", request);
        return "COACH".equals(request.getRole())
                ? "auth/registro-entrenador"
                : "auth/registro-jugador";
    }
}

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
