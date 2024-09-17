package br.com.fiap.java_challenge.dto.response;

import lombok.Builder;

import java.time.LocalDate;

@Builder
public record PessoaResponse(
        Long id,
        String nome,
        String sobrenome,
        LocalDate dataNascimento,
        String email
) {
}