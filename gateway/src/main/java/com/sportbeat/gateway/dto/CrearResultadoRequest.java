package com.sportbeat.gateway.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.UUID;



@Data
@NoArgsConstructor
@AllArgsConstructor
public class CrearResultadoRequest {
    private UUID partidoId;
    private int golesLocal;
    private int golesVisitante;

    // Getters y Setters
    public UUID getPartidoId() {
        return partidoId;
    }

    public void setPartidoId(UUID partidoId) {
        this.partidoId = partidoId;
    }

    public int getGolesLocal() {
        return golesLocal;
    }

    public void setGolesLocal(int golesLocal) {
        this.golesLocal = golesLocal;
    }

    public int getGolesVisitante() {
        return golesVisitante;
    }

    public void setGolesVisitante(int golesVisitante) {
        this.golesVisitante = golesVisitante;
    }
}