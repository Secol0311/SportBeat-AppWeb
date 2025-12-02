package com.sportbeat.gateway.controller;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import reactor.core.publisher.Mono;


@Controller
public class IndexController {

     @GetMapping("/")
    public Mono<String> index() {
        // En WebFlux, se devuelve un Mono que emite el nombre de la vista.
        return Mono.just("index"); // Carga templates/index.html
    }
    @GetMapping("/quienes-somos")
    public Mono<String> quienesSomos() {
        return Mono.just("pages/quienes-somos"); // Apunta a templates/pages/quienes-somos.html
    }

    @GetMapping("/deportes")
    public Mono<String> deportes() {
        return Mono.just("pages/deportes"); // Apunta a templates/pages/deportes.html
    }

    @GetMapping("/contacto")
    public Mono<String> contacto() {
        return Mono.just("pages/contacto"); // Apunta a templates/pages/contacto.html
    }
   
}
