package br.com.fiap.java_challenge.dto.response;

import lombok.Builder;

import java.util.List;

@Builder
public record UsuarioResponse(
        Long id,
        String username,
        String password,
        PessoaResponse pessoa,
        List<EstabelecimentoResponse> estabelecimentos,
        PreferenciaViagemResponse preferenciaViagem
) {
}