package br.com.fiap.java_challenge.dto.response;

import lombok.Builder;

@Builder
public record PreferenciaViagemResponse(
        Long id,
        String tipo_culinaria,
        String restricoes_alimentares,
        String tipo_transporte,
        String tipo_hospedagem,
        String viaja_sozinho
) {
}