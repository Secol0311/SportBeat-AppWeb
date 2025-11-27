package com.sportbeat.resultado_service.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "resultados")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Resultado {

    @Id
    private UUID id;

    @Column(name = "partido_id", nullable = false, unique = true)
    private UUID partidoId;

    @Column(name = "goles_local", nullable = false)
    private Integer golesLocal;

    @Column(name = "goles_visitante", nullable = false)
    private Integer golesVisitante;

    @Column(name = "registrado_por_usuario_id")
    private UUID registradoPorUsuarioId;

    @Column(name = "fecha_registro", updatable = false)
    private LocalDateTime fechaRegistro;

    @PrePersist
    protected void onCreate() {
        fechaRegistro = LocalDateTime.now();
    }
}
