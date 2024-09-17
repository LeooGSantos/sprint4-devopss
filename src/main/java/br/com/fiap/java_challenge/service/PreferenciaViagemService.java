package br.com.fiap.java_challenge.service;

import br.com.fiap.java_challenge.dto.request.PreferenciaViagemRequest;
import br.com.fiap.java_challenge.dto.response.PreferenciaViagemResponse;
import br.com.fiap.java_challenge.entity.PreferenciaViagem;
import br.com.fiap.java_challenge.repository.PreferenciaViagemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
public class PreferenciaViagemService implements ServiceDTO<PreferenciaViagem, PreferenciaViagemRequest, PreferenciaViagemResponse> {

    @Autowired
    private PreferenciaViagemRepository repo;

    @Override
    public Collection<PreferenciaViagem> findAll(Example<PreferenciaViagem> example) {
        return repo.findAll(example);
    }

    @Override
    public PreferenciaViagem findById(Long id) {
        return repo.findById(id).orElse(null);
    }

    @Override
    public PreferenciaViagem save(PreferenciaViagem e) {
        return repo.save(e);
    }

    @Override
    public PreferenciaViagem toEntity(PreferenciaViagemRequest dto) {
        return PreferenciaViagem.builder()
                .tipo_culinaria(dto.tipo_culinaria())
                .restricoes_alimentares(dto.restricoes_alimentares())
                .tipo_transporte(dto.tipo_transporte())
                .tipo_hospedagem(dto.tipo_hospedagem())
                .viaja_sozinho(dto.viaja_sozinho())
                .build();
    }

    @Override
    public PreferenciaViagemResponse toResponse(PreferenciaViagem e) {
        return PreferenciaViagemResponse.builder()
                .id(e.getId())
                .tipo_culinaria(e.getTipo_culinaria())
                .restricoes_alimentares(e.getRestricoes_alimentares())
                .tipo_transporte(e.getTipo_transporte())
                .tipo_hospedagem(e.getTipo_hospedagem())
                .viaja_sozinho(e.getViaja_sozinho())
                .build();
    }
}