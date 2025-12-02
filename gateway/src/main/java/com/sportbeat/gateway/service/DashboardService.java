package com.sportbeat.gateway.service;

import com.sportbeat.gateway.dto.*;
import com.sportbeat.gateway.serviceclient.PartidoServiceClient;
import com.sportbeat.gateway.serviceclient.ResultadoServiceClient;
import com.sportbeat.gateway.serviceclient.UsuarioEquipoServiceClient;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.*;

@Service
public class DashboardService {

    @Autowired
    private UsuarioEquipoServiceClient usuarioEquipoClient;

    @Autowired
    private PartidoServiceClient partidoClient;

    @Autowired
    private ResultadoServiceClient resultadoClient;

    @Autowired
    private PartidoServiceClient partidoServiceClient;

    // -------------------------------------------------------------
    // ADMIN
    // -------------------------------------------------------------
     public Mono<Map<String, Object>> getAdminDashboardData() {
        // Los clientes ya devuelven Mono/Flux, así que solo los juntamos
        Mono<List<LigaDTO>> ligasMono = usuarioEquipoClient.findAllLigas().collectList();
        Mono<List<EquipoDTO>> equiposMono = usuarioEquipoClient.findAllEquipos().collectList();
        Mono<List<PartidoDTO>> partidosMono = partidoClient.findTodosPartidos().collectList();

        return Mono.zip(ligasMono, equiposMono, partidosMono)
                .map(tuple -> {
                    Map<String, Object> data = new HashMap<>();
                    data.put("ligas", tuple.getT1());
                    data.put("equipos", tuple.getT2());
                    data.put("partidos", tuple.getT3());
                    // ... resto del código para añadir objetos vacíos ...
                    return data;
                });
    }

    // -------------------------------------------------------------
    // ENTRENADOR
    // -------------------------------------------------------------
    public Mono<Map<String, Object>> getEntrenadorDashboardData(UUID entrenadorId) {

        return usuarioEquipoClient.findEquiposByCoachId(entrenadorId)
                .collectList()
                .flatMap(equipos -> {

                    if (equipos.isEmpty()) {
                        return Mono.just(Map.of("mensaje", "Aún no tienes ningún equipo asignado."));
                    }

                    EquipoDTO equipo = equipos.get(0);
                    Map<String, Object> data = new HashMap<>();
                    data.put("equipo", equipo);

                    return partidoClient.findCalendarioByLiga(equipo.getLeagueId())
                            .collectList()
                            .doOnNext(lista -> data.put("proximosPartidos", lista))
                            .then(usuarioEquipoClient.findJugadoresByEquipo(equipo.getId()).collectList())
                            .doOnNext(jugadores -> data.put("jugadores", jugadores))
                            .then(Mono.just(data));
                });
    }

    // -------------------------------------------------------------
    // JUGADOR
    // -------------------------------------------------------------
    public Mono<Map<String, Object>> getJugadorDashboardData(UUID jugadorId) {

        Map<String, Object> data = new HashMap<>();

        return usuarioEquipoClient.findJugadorByUsuarioId(jugadorId)
                .doOnNext(jugador -> data.put("jugador", jugador))
                .thenMany(partidoClient.findProximosPartidosDeJugador(jugadorId))
                .collectList()
                .doOnNext(lista -> data.put("proximosPartidos", lista))
                .thenMany(resultadoClient.findEstadisticasByJugador(jugadorId))
                .collectList()
                .doOnNext(lista -> data.put("estadisticas", lista))
                .then(Mono.just(data));
    }

    public Mono<PartidoDTO> getPartidoById(UUID partidoId) {
    return partidoServiceClient.findPartidoById(partidoId);
}

}
