package br.com.fiap.java_challenge.dto.request;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;

import java.time.LocalDate;

public record AvaliacaoRequest(

        @NotBlank(message = "O comentário não pode ser nulo ou em branco")
        String comentario,

        @Min(value = 1, message = "A nota deve ser no mínimo 1")
        @Max(value = 5, message = "A nota deve ser no máximo 5")
        @NotNull(message = "A nota não pode ser nula, e tem que ser de 1 a 5")
        Long nota,

        @PastOrPresent(message = "Não aceitamos data no futuro")
        LocalDate dataAvaliacao,

        @NotNull(message = "É necessário informar o ID do estabelecimento")
        @Min(value = 1, message = "O ID do estabelecimento deve ser positivo") // Impede valores negativos
        Long estabelecimentoId
) {
}