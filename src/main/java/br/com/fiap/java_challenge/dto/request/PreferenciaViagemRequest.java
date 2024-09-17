package br.com.fiap.java_challenge.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;

@Builder
public record PreferenciaViagemRequest(

        @NotBlank(message = "O tipo de culinária que o usuário gosta é um campo obrigatório")
        String tipo_culinaria,

        @NotBlank(message = "Responder se o usuário possui ou não restrições alimentares é obrigatório")
        String restricoes_alimentares,

        @NotBlank(message = "O tipo de transporte que o usuário quer usar é um campo obrigatório")
        String tipo_transporte,

        @NotBlank(message = "O tipo de hospedagem que o usuário quer é um campo obrigatório")
        String tipo_hospedagem,

        @NotBlank(message = "É obrigatório este campo se o usuário irá viajar sozinho ou não")
        String viaja_sozinho
) {

}