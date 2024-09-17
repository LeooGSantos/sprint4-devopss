package br.com.fiap.java_challenge.service;

import br.com.fiap.java_challenge.dto.request.EstabelecimentoRequest;
import br.com.fiap.java_challenge.dto.response.EstabelecimentoResponse;
import br.com.fiap.java_challenge.entity.Estabelecimento;
import br.com.fiap.java_challenge.repository.EstabelecimentoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
public class EstabelecimentoService implements ServiceDTO<Estabelecimento, EstabelecimentoRequest, EstabelecimentoResponse> {

    @Autowired
    private EstabelecimentoRepository repo;

    @Override
    public Collection<Estabelecimento> findAll(Example<Estabelecimento> example) {
        return repo.findAll(example);
    }

    @Override
    public Estabelecimento findById(Long id) {
        return repo.findById(id).orElse(null);
    }

    @Override
    public Estabelecimento save(Estabelecimento e) {
        return repo.save(e);
    }

    @Override
    public Estabelecimento toEntity(EstabelecimentoRequest dto) {
        return Estabelecimento.builder()
                .nome(dto.nome())
                .cep(dto.cep())
                .tipo(dto.tipo())
                .build();
    }

    @Override
    public EstabelecimentoResponse toResponse(Estabelecimento e) {
        return EstabelecimentoResponse.builder()
                .id(e.getId())
                .nome(e.getNome())
                .cep(e.getCep())
                .tipo(e.getTipo())
                .build();
    }
}