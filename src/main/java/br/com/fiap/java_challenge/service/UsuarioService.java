package br.com.fiap.java_challenge.service;

import br.com.fiap.java_challenge.dto.request.UsuarioRequest;
import br.com.fiap.java_challenge.dto.response.UsuarioResponse;
import br.com.fiap.java_challenge.entity.Pessoa;
import br.com.fiap.java_challenge.entity.Usuario;
import br.com.fiap.java_challenge.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;
import br.com.fiap.java_challenge.dto.request.PessoaRequest;
import br.com.fiap.java_challenge.dto.request.PreferenciaViagemRequest;

import java.util.Collection;
import java.util.List;
import java.util.Objects;

@Service
public class UsuarioService implements ServiceDTO<Usuario, UsuarioRequest, UsuarioResponse> {

    @Autowired
    private UsuarioRepository repo;

    @Autowired
    private PessoaService pessoaService;

    @Autowired
    private EstabelecimentoService estabelecimentoService;

    @Autowired
    private PreferenciaViagemService preferenciaViagemService;

    @Override
    public Collection<Usuario> findAll(Example<Usuario> example) {
        return repo.findAll(example);
    }

    @Override
    public Usuario findById(Long id) {
        return repo.findById(id).orElse(null);
    }

    @Override
    public Usuario save(Usuario e) {
        return repo.save(e);
    }

    @Override
    public Usuario toEntity(UsuarioRequest dto) {
        var pessoa = pessoaService.toEntity(dto.pessoa());
        var preferenciaViagem = preferenciaViagemService.toEntity(dto.preferenciaViagem());

        return Usuario.builder()
                .username(dto.username())
                .password(dto.password())
                .pessoa(pessoa)
                .preferenciaViagem(preferenciaViagem)
                .build();
    }

    @Override
    public UsuarioResponse toResponse(Usuario e) {
        var pessoa = pessoaService.toResponse(e.getPessoa());
        var preferenciaViagem = preferenciaViagemService.toResponse(e.getPreferenciaViagem());

        List<br.com.fiap.java_challenge.dto.response.EstabelecimentoResponse> estabelecimentos = null;
        if (Objects.nonNull(e.getEstabelecimentos()) && !e.getEstabelecimentos().isEmpty()) {
            estabelecimentos = e.getEstabelecimentos().stream()
                    .map(estabelecimentoService::toResponse)
                    .toList();
        }

        return UsuarioResponse.builder()
                .id(e.getId())
                .username(e.getUsername())
                .password(e.getPassword())
                .pessoa(pessoa)
                .estabelecimentos(estabelecimentos)
                .preferenciaViagem(preferenciaViagem)
                .build();
    }


    public void update(Usuario usuarioExistente, UsuarioRequest usuarioRequest) {
        // Atualiza os campos do usuário com base no DTO
        usuarioExistente.setUsername(usuarioRequest.username());
        usuarioExistente.setPassword(usuarioRequest.password());

        // Atualiza os dados da Pessoa
        PessoaRequest pessoaRequest = usuarioRequest.pessoa();
        if (pessoaRequest != null) {
            Pessoa pessoa = usuarioExistente.getPessoa();
            pessoa.setNome(pessoaRequest.nome());
            pessoa.setSobrenome(pessoaRequest.sobrenome());
            pessoa.setDataNascimento(pessoaRequest.dataNascimento());
            pessoa.setEmail(pessoaRequest.email());
        }

        // Atualiza as preferências de viagem (se necessário)
        // ...

        repo.save(usuarioExistente);
    }

    public void delete(Long id) {
        repo.deleteById(id);
    }

    public UsuarioRequest toRequest(Usuario usuario) {
        PessoaRequest pessoaRequest = PessoaRequest.builder()
                .nome(usuario.getPessoa().getNome())
                .sobrenome(usuario.getPessoa().getSobrenome())
                .dataNascimento(usuario.getPessoa().getDataNascimento())
                .email(usuario.getPessoa().getEmail())
                .build();

        PreferenciaViagemRequest preferenciaRequest = PreferenciaViagemRequest.builder()
                .tipo_culinaria(usuario.getPreferenciaViagem().getTipo_culinaria())
                .restricoes_alimentares(usuario.getPreferenciaViagem().getRestricoes_alimentares())
                .tipo_transporte(usuario.getPreferenciaViagem().getTipo_transporte())
                .tipo_hospedagem(usuario.getPreferenciaViagem().getTipo_hospedagem())
                .viaja_sozinho(usuario.getPreferenciaViagem().getViaja_sozinho())
                .build();

        return UsuarioRequest.builder()
                .username(usuario.getUsername())
                .password(usuario.getPassword())
                .pessoa(pessoaRequest)
                .preferenciaViagem(preferenciaRequest)
                .build();
    }
}