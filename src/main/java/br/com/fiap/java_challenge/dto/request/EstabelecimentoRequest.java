package br.com.fiap.java_challenge.dto.request;

import br.com.fiap.java_challenge.entity.TipoEstabelecimento;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

public record EstabelecimentoRequest(

        @NotBlank(message = "O nome do estabelecimento não pode ser nulo ou em branco")
        String nome,

        @Pattern(regexp = "\\d{5}-?\\d{3}", message = "CEP inválido")
        @NotBlank(message = "O CEP do estabelecimento deve ser informado")
        String cep,

        @NotNull(message = "O tipo de estabelecimento é obrigatório")
        TipoEstabelecimento tipo
) {
}