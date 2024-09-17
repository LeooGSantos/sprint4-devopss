package br.com.fiap.java_challenge.service;

import br.com.fiap.java_challenge.dto.request.PessoaRequest;
import br.com.fiap.java_challenge.dto.response.PessoaResponse;
import br.com.fiap.java_challenge.entity.Pessoa;
import br.com.fiap.java_challenge.repository.PessoaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
public class PessoaService implements ServiceDTO<Pessoa, PessoaRequest, PessoaResponse> {

    @Autowired
    private PessoaRepository repo;

    @Override
    public Collection<Pessoa> findAll(Example<Pessoa> example) {
        return repo.findAll(example);
    }

    @Override
    public Pessoa findById(Long id) {
        return repo.findById(id).orElse(null);
    }

    @Override
    public Pessoa save(Pessoa e) {
        return repo.save(e);
    }

    @Override
    public Pessoa toEntity(PessoaRequest dto) {
        return Pessoa.builder()
                .nome(dto.nome())
                .sobrenome(dto.sobrenome())
                .dataNascimento(dto.dataNascimento())
                .email(dto.email())
                .build();
    }

    @Override
    public PessoaResponse toResponse(Pessoa e) {
        return PessoaResponse.builder()
                .id(e.getId())
                .nome(e.getNome())
                .sobrenome(e.getSobrenome())
                .dataNascimento(e.getDataNascimento())
                .email(e.getEmail())
                .build();
    }
}