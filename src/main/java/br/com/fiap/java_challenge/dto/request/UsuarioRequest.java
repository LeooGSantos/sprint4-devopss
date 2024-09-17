package br.com.fiap.java_challenge.dto.request;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Builder;

@Builder
public record UsuarioRequest(

        @Email(message = "Username deve ser um email válido")
        @NotBlank(message = "Username não pode ser nulo ou em branco")
        String username,

        @Pattern(regexp = "^(?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[$*&@#])[0-9a-zA-Z$*&@#]{8,16}$",
                message = "A senha deve conter entre 8 e 16 caracteres, incluindo pelo menos uma letra minúscula, uma letra maiúscula, um número e um caractere especial ($*&@#)")
        @NotBlank(message = "O password não pode ser nulo ou em branco")
        String password,

        @Valid
        @NotNull(message = "É necessário informar os dados da pessoa")
        PessoaRequest pessoa,

        @Valid
        @NotNull(message = "É necessário informar as preferências de viagem")
        PreferenciaViagemRequest preferenciaViagem
) {

}