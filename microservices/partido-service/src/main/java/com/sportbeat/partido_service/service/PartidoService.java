package com.sportbeat.partido_service.service;

import com.sportbeat.partido_service.dto.CrearPartidoRequest;
import com.sportbeat.partido_service.dto.PartidoDTO;
import com.sportbeat.partido_service.model.Partido;
import com.sportbeat.partido_service.repository.PartidoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class PartidoService {

    @Autowired
    private PartidoRepository partidoRepository;

    public PartidoDTO crearPartido(CrearPartidoRequest request) {
        Partido nuevoPartido = Partido.builder()
                .ligaId(request.getLigaId())
                .equipoLocalId(request.getEquipoLocalId())
                .equipoVisitanteId(request.getEquipoVisitanteId())
                .fecha(request.getFecha())
                .hora(request.getHora())
                .venue(request.getVenue())
                .estado(com.sportbeat.partido_service.model.EstadoPartido.PROGRAMADO) // Siempre empieza programado
                .build();

        Partido guardado = partidoRepository.save(nuevoPartido);
        return mapToDTO(guardado);
    }

    public List<PartidoDTO> obtenerCalendarioPorLiga(UUID ligaId) {
        return partidoRepository.findByLigaIdOrderByFechaAscHoraAsc(ligaId)
                .stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    public PartidoDTO obtenerPartidoPorId(UUID id) {
        return partidoRepository.findById(id)
                .map(this::mapToDTO)
                .orElse(null); // O lanzar una excepción personalizada
    }

    public void eliminarPartido(UUID id) {
        if (partidoRepository.existsById(id)) {
            partidoRepository.deleteById(id);
        } else {
            // Lanzar excepción si no se encuentra
            throw new RuntimeException("Partido no encontrado con id: " + id);
        }
    }

    // Método auxiliar para convertir Entidad a DTO
    private PartidoDTO mapToDTO(Partido partido) {
        return new PartidoDTO(
                partido.getId(),
                partido.getLigaId(),
                partido.getEquipoLocalId(),
                partido.getEquipoVisitanteId(),
                partido.getFecha(),
                partido.getHora(),
                partido.getVenue(),
                partido.getEstado(),
                partido.getCreatedAt(),
                partido.getUpdatedAt()
        );
    }
}