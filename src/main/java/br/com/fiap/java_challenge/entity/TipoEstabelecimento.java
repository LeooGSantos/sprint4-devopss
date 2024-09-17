package br.com.fiap.java_challenge.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum TipoEstabelecimento {

    RESTAURANTE("R", "Restaurante"),
    PONTO_TURISTICO("PT", "Ponto Turístico"),
    PARQUE("P", "Parque"),
    MUSEU("M", "Museu"),
    TEATRO("T", "Teatro");

    private String codigo;
    private String tipo;
}