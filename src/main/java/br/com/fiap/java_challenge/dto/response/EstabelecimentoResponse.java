package br.com.fiap.java_challenge.dto.response;

import br.com.fiap.java_challenge.entity.TipoEstabelecimento;
import lombok.Builder;

@Builder
public record EstabelecimentoResponse(
        Long id,
        String nome,
        String cep,
        TipoEstabelecimento tipo
) {
}