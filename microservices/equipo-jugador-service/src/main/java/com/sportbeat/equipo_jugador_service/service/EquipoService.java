package com.sportbeat.equipo_jugador_service.service;

import com.sportbeat.equipo_jugador_service.dto.EquipoRequest;
import com.sportbeat.equipo_jugador_service.model.Equipo;
import com.sportbeat.equipo_jugador_service.model.Usuario;
import com.sportbeat.equipo_jugador_service.repository.EquipoRepository;
import com.sportbeat.equipo_jugador_service.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class EquipoService {

    @Autowired
    private EquipoRepository equipoRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;  // Para buscar coach

    public Equipo crearEquipo(EquipoRequest request) {
        Usuario coach = usuarioRepository.findById(request.getCoachId())
                .orElseThrow(() -> new RuntimeException("Coach no encontrado"));

        Equipo equipo = Equipo.builder()
                .name(request.getName())
                .district(request.getDistrict())
                .department(request.getDepartment())
                .leagueId(request.getLeagueId())
                .coach(coach)  // Asignamos el objeto Usuario
                .foundedYear(request.getFoundedYear())
                .build();

        return equipoRepository.save(equipo);
    }

    public List<Equipo> obtenerTodos() {
        return equipoRepository.findAll();
    }

    public Equipo obtenerPorId(UUID id) {
        return equipoRepository.findById(id).orElse(null);
    }

    public List<Equipo> obtenerPorLeagueId(UUID leagueId) {
        return equipoRepository.findByLeagueId(leagueId);
    }

    public List<Equipo> obtenerPorCoachId(UUID coachId) {
        return equipoRepository.findByCoach_Id(coachId); // ajustado al nuevo repo
    }

    public Equipo actualizarEquipo(UUID id, EquipoRequest request) {
        Usuario coach = usuarioRepository.findById(request.getCoachId())
                .orElseThrow(() -> new RuntimeException("Coach no encontrado"));

        return equipoRepository.findById(id).map(equipo -> {
            equipo.setName(request.getName());
            equipo.setDistrict(request.getDistrict());
            equipo.setDepartment(request.getDepartment());
            equipo.setLeagueId(request.getLeagueId());
            equipo.setCoach(coach);  // Asignamos el objeto Usuario
            equipo.setFoundedYear(request.getFoundedYear());
            return equipoRepository.save(equipo);
        }).orElseThrow(() -> new RuntimeException("Equipo no encontrado"));
    }

    public void eliminarEquipo(UUID id) {
        equipoRepository.deleteById(id);
    }
}
