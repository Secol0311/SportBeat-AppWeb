package com.sportbeat.gateway.serviceclient;

import com.sportbeat.gateway.dto.*;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class UsuarioEquipoServiceClient {

    private final WebClient webClient;
    private final String baseUrl;

    public UsuarioEquipoServiceClient(WebClient.Builder builder,
                                      @Value("${urls.servicios.equipo-jugador}") String baseUrl) {
        this.webClient = builder.build();
        this.baseUrl = baseUrl;
    }

    // ============================
    // USUARIOS
    // ============================

    public Flux<UsuarioDTO> findAllUsuarios() {
        return webClient.get()
                .uri(baseUrl + "/api/usuarios")
                .retrieve()
                .bodyToFlux(UsuarioDTO.class);
    }

    public Mono<UsuarioDTO> findUsuarioById(UUID id) {
        return webClient.get()
                .uri(baseUrl + "/api/usuarios/" + id)
                .retrieve()
                .bodyToMono(UsuarioDTO.class);
    }

    public Mono<UsuarioDTO> findByUsername(String username) {
        return webClient.get()
                .uri(baseUrl + "/api/usuarios/public/" + username)
                .retrieve()
                .bodyToMono(UsuarioDTO.class);
    }

    public Mono<Long> countUsuarios() {
        return webClient.get()
                .uri(baseUrl + "/api/usuarios/count")
                .retrieve()
                .bodyToMono(Long.class);
    }

    // **FALTANTE → crear usuario**
    public Mono<UsuarioDTO> crearUsuario(CrearUsuarioRequest request) {
        return webClient.post()
                .uri(baseUrl + "/api/usuarios")
                .bodyValue(request)
                .retrieve()
                .bodyToMono(UsuarioDTO.class);
    }

    // ============================
    // LIGAS
    // ============================

    public Flux<LigaDTO> findAllLigas() {
        return webClient.get()
                .uri(baseUrl + "/api/ligas")
                .retrieve()
                .bodyToFlux(LigaDTO.class);
    }

    public Mono<Long> countLigas() {
        return webClient.get()
                .uri(baseUrl + "/api/ligas/count")
                .retrieve()
                .bodyToMono(Long.class);
    }

    // **FALTANTE → crear liga**
    public Mono<LigaDTO> crearLiga(CrearLigaRequest request) {
        return webClient.post()
                .uri(baseUrl + "/api/ligas")
                .bodyValue(request)
                .retrieve()
                .bodyToMono(LigaDTO.class);
    }

    // **FALTANTE → obtener liga por ID**
    public Mono<LigaDTO> findLigaById(UUID id) {
        return webClient.get()
                .uri(baseUrl + "/api/ligas/" + id)
                .retrieve()
                .bodyToMono(LigaDTO.class);
    }

    // **FALTANTE → eliminar liga**
    public Mono<Void> eliminarLiga(UUID id) {
        return webClient.delete()
                .uri(baseUrl + "/api/ligas/" + id)
                .retrieve()
                .bodyToMono(Void.class);
    }

    // ============================
    // EQUIPOS
    // ============================

    public Flux<EquipoDTO> findAllEquipos() {
        return webClient.get()
                .uri(baseUrl + "/api/equipos")
                .retrieve()
                .bodyToFlux(EquipoDTO.class);
    }

    public Mono<EquipoDTO> findEquipoById(UUID id) {
        return webClient.get()
                .uri(baseUrl + "/api/equipos/" + id)
                .retrieve()
                .bodyToMono(EquipoDTO.class);
    }

    public Mono<Long> countEquipos() {
        return webClient.get()
                .uri(baseUrl + "/api/equipos/count")
                .retrieve()
                .bodyToMono(Long.class);
    }

    public Flux<EquipoDTO> findEquiposByCoachId(UUID coachId) {
        return webClient.get()
                .uri(baseUrl + "/api/equipos/coach/" + coachId)
                .retrieve()
                .bodyToFlux(EquipoDTO.class);
    }

    // ============================
    // JUGADORES
    // ============================

    public Flux<JugadorDTO> findAllJugadores() {
        return webClient.get()
                .uri(baseUrl + "/api/jugadores")
                .retrieve()
                .bodyToFlux(JugadorDTO.class);
    }

    public Mono<JugadorDTO> findJugadorById(UUID id) {
        return webClient.get()
                .uri(baseUrl + "/api/jugadores/" + id)
                .retrieve()
                .bodyToMono(JugadorDTO.class);
    }

    public Flux<JugadorDTO> findJugadoresByEquipo(UUID equipoId) {
        return webClient.get()
                .uri(baseUrl + "/api/jugadores/equipo/" + equipoId)
                .retrieve()
                .bodyToFlux(JugadorDTO.class);
    }

    public Mono<JugadorDTO> findJugadorByUsuarioId(UUID usuarioId) {
        return webClient.get()
                .uri(baseUrl + "/api/jugadores/usuario/" + usuarioId)
                .retrieve()
                .bodyToMono(JugadorDTO.class);
    }
}
