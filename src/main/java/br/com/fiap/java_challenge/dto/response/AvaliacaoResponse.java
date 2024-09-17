package br.com.fiap.java_challenge.dto.response;

import lombok.Builder;

import java.time.LocalDate;

@Builder
public record AvaliacaoResponse(
        Long id,
        String comentario,
        Long nota,
        LocalDate dataAvaliacao,
        EstabelecimentoResponse estabelecimento
) {
}