package com.sportbeat.auth_service.controller;

import com.sportbeat.auth_service.dto.AuthResponse;
import com.sportbeat.auth_service.dto.LoginRequest;
import com.sportbeat.auth_service.dto.RegistroRequest;
import com.sportbeat.auth_service.model.Usuario;
import com.sportbeat.auth_service.service.AuthService;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@Valid @RequestBody LoginRequest request) {
        AuthResponse response = authService.login(request);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/register")
    public ResponseEntity<Usuario> register(@Valid @RequestBody RegistroRequest request) {
        Usuario nuevoUsuario = authService.registrar(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(nuevoUsuario);
    }

    @GetMapping("/status")
    public ResponseEntity<String> status() {
        return ResponseEntity.ok("Auth-Service is running!");
    }
}
