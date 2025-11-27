package com.sportbeat.gateway.dto;

import lombok.Data;

@Data // Lombok: genera getters, setters, toString, etc.
public class LigaDTO {
    private Long id;
    private String nombre;
    private String deporte;
    private String temporada;
}