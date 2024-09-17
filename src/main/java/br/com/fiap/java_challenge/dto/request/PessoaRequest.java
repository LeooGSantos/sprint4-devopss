package br.com.fiap.java_challenge.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import lombok.Builder;

import java.time.LocalDate;

@Builder
public record PessoaRequest(

        @NotBlank(message = "O nome é campo obrigatório")
        String nome,

        @NotBlank(message = "O sobrenome é campo obrigatório")
        String sobrenome,

        @PastOrPresent(message = "Não aceitamos data no futuro")
        @NotNull(message = "A data de nascimento é obrigatória")
        LocalDate dataNascimento,

        @Email(message = "Email é inválido")
        @NotBlank(message = "Email é campo obrigatório")
        String email
) {

}