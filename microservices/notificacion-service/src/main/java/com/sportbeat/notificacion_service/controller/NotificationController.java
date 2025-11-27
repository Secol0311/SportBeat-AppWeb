package com.sportbeat.notificacion_service.controller;

import com.sportbeat.notificacion_service.model.Notification;
import com.sportbeat.notificacion_service.model.NotificationQueue;
import com.sportbeat.notificacion_service.service.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.sportbeat.notificacion_service.dto.CrearNotificacionRequest;
import jakarta.validation.Valid;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/notifications")
public class NotificationController {

    @Autowired
    private NotificationService notificationService;

    @PostMapping
    public ResponseEntity<NotificationQueue> crearNotificacion(@Valid @RequestBody CrearNotificacionRequest request) {
        NotificationQueue nuevaNotificacion = notificationService.crearNotificacion(request);
        return new ResponseEntity<>(nuevaNotificacion, HttpStatus.ACCEPTED); // 202 Accepted es ideal para procesos asíncronos
    }

    @GetMapping("/usuario/{usuarioId}")
    public ResponseEntity<List<Notification>> obtenerHistorial(@PathVariable UUID usuarioId) {
        List<Notification> historial = notificationService.obtenerHistorialPorUsuario(usuarioId);
        return ResponseEntity.ok(historial);
    }

    @PutMapping("/{id}/read")
    public ResponseEntity<Void> marcarComoLeida(@PathVariable UUID id) {
        notificationService.marcarComoLeida(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/status")
    public ResponseEntity<String> status() {
        return ResponseEntity.ok("Notificaciones-Service is running!");
    }
}