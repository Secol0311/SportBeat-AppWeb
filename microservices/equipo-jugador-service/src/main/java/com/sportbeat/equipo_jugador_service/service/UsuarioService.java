package com.sportbeat.equipo_jugador_service.service;

import com.sportbeat.equipo_jugador_service.dto.UsuarioRequest;
import com.sportbeat.equipo_jugador_service.model.Usuario;
import com.sportbeat.equipo_jugador_service.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.UUID;

@Service
public class UsuarioService {

    @Autowired private UsuarioRepository usuarioRepository;
    @Autowired private PasswordEncoder passwordEncoder; // Inyecta el encoder

    public Usuario crearUsuario(UsuarioRequest request) {
        if (usuarioRepository.existsByUsername(request.getUsername()) || usuarioRepository.existsByEmail(request.getEmail())) {
            throw new IllegalArgumentException("El username o email ya están en uso.");
        }
        Usuario nuevoUsuario = Usuario.builder()
                .username(request.getUsername())
                .passwordHash(passwordEncoder.encode(request.getPassword())) // Encripta la contraseña
                .email(request.getEmail())
                .telefono(request.getTelefono())
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .role(request.getRole())
                .build();
        return usuarioRepository.save(nuevoUsuario);
    }

    public List<Usuario> obtenerTodos() { return usuarioRepository.findAll(); }
    public Usuario obtenerPorId(UUID id) { return usuarioRepository.findById(id).orElse(null); }
    public Usuario findByUsername(String username) { return usuarioRepository.findByUsername(username).orElse(null); }

    public Usuario actualizarUsuario(UUID id, UsuarioRequest request) {
        return usuarioRepository.findById(id).map(usuario -> {
            usuario.setEmail(request.getEmail());
            usuario.setTelefono(request.getTelefono());
            usuario.setFirstName(request.getFirstName());
            usuario.setLastName(request.getLastName());
            usuario.setRole(request.getRole());
            if (request.getPassword() != null && !request.getPassword().isEmpty()) {
                usuario.setPasswordHash(passwordEncoder.encode(request.getPassword()));
            }
            return usuarioRepository.save(usuario);
        }).orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
    }

    public void eliminarUsuario(UUID id) { usuarioRepository.deleteById(id); }
}