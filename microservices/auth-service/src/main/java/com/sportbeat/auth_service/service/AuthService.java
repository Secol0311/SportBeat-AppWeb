package com.sportbeat.auth_service.service;

import com.sportbeat.auth_service.dto.AuthResponse;
import com.sportbeat.auth_service.dto.LoginRequest;
import com.sportbeat.auth_service.dto.RegistroRequest;
import com.sportbeat.auth_service.model.Usuario;
import com.sportbeat.auth_service.repository.UsuarioRepository;
import com.sportbeat.auth_service.security.JwtUtil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
public class AuthService {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Value("${usuario-equipo.service.url}")
    private String usuarioEquipoServiceUrl;

    /**
     * LOGIN
     */
    public AuthResponse login(LoginRequest request) {

        WebClient webClient = WebClient.create(usuarioEquipoServiceUrl);

        Mono<Usuario> usuarioMono = webClient.get()
                .uri("/api/public/usuarios/{username}", request.getUsername())
                .retrieve()
                .bodyToMono(Usuario.class);

        Usuario usuario;

        try {
            usuario = usuarioMono.block();
        } catch (Exception e) {
            throw new RuntimeException("Error al comunicarse con usuario-equipo-service", e);
        }

        if (usuario == null) {
            throw new BadCredentialsException("Usuario no encontrado");
        }

        if (!passwordEncoder.matches(request.getPassword(), usuario.getPassword())) {
            throw new BadCredentialsException("Contraseña incorrecta");
        }

        // Generar token
        String token = jwtUtil.generateToken(usuario.getUsername(), usuario.getRol().name());

        return new AuthResponse(
                token,
                usuario.getUsername(),
                usuario.getRol()
        );
    }

    /**
     * REGISTRO (devuelve Usuario, como exige tu AuthController)
     */
    public Usuario registrar(RegistroRequest request) {

        // Verificar si el username ya existe
        if (usuarioRepository.findByUsername(request.getUsername()).isPresent()) {
            throw new RuntimeException("El usuario ya existe");
        }

        // Crear usuario nuevo
        Usuario usuario = Usuario.builder()
                .username(request.getUsername())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .rol(request.getRol())
                .build();

        // Guardar y retornar
        return usuarioRepository.save(usuario);
    }
}
