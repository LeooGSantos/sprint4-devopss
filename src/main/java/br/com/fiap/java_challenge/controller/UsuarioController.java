package br.com.fiap.java_challenge.controller;

import br.com.fiap.java_challenge.dto.request.PessoaRequest;
import br.com.fiap.java_challenge.dto.request.PreferenciaViagemRequest;
import br.com.fiap.java_challenge.dto.request.UsuarioRequest;
import br.com.fiap.java_challenge.entity.Estabelecimento;
import br.com.fiap.java_challenge.entity.TipoEstabelecimento;
import br.com.fiap.java_challenge.entity.Usuario;
import br.com.fiap.java_challenge.service.EstabelecimentoService;
import br.com.fiap.java_challenge.service.UsuarioService;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/usuarios")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private EstabelecimentoService estabelecimentoService;

    @GetMapping
    public ModelAndView listarUsuarios(Usuario usuario) {
        ModelAndView mv = new ModelAndView("usuario/listar");
        ExampleMatcher matcher = ExampleMatcher
                .matching()
                .withIgnoreCase()
                .withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING);
        Example<Usuario> example = Example.of(usuario, matcher);

        List<Usuario> listaUsuarios = usuarioService.findAll(example).stream().toList();
        mv.addObject("listaUsuarios", listaUsuarios);
        return mv;
    }

    @GetMapping("/novo")
    public ModelAndView novoUsuario() {
        ModelAndView mv = new ModelAndView("usuario/criar");
        mv.addObject("usuarioRequest",
                UsuarioRequest.builder()
                        .pessoa(new PessoaRequest(null, null, null, null)) // Passa null para todos os campos
                        .preferenciaViagem(new PreferenciaViagemRequest(null, null, null, null, null)) // Passa null para todos os campos
                        .build());
        return mv;
    }

    @PostMapping("/novo")
    @Transactional
    public ModelAndView criarUsuario(@Valid UsuarioRequest usuarioRequest, BindingResult result) {
        if (result.hasErrors()) {
            return new ModelAndView("usuario/criar");
        }
        var entity = usuarioService.toEntity(usuarioRequest);
        var saved = usuarioService.save(entity);

        return new ModelAndView("redirect:/usuarios");
    }

    @GetMapping("/{id}/editar")
    public ModelAndView editarUsuario(@PathVariable("id") Long id, Model model) {
        Optional<Usuario> usuario = Optional.ofNullable(usuarioService.findById(id));

        if (usuario.isPresent()) {
            UsuarioRequest usuarioRequest = usuarioService.toRequest(usuario.get());
            ModelAndView mv = new ModelAndView("usuario/editar");
            mv.addObject("usuarioRequest", usuarioRequest);
            mv.addObject("usuarioId", id);
            return mv;
        } else {
            return new ModelAndView("redirect:/usuarios");
        }
    }

    @PostMapping("/{id}/editar")
    @Transactional
    public ModelAndView atualizarUsuario(@PathVariable("id") Long id, @Valid UsuarioRequest usuarioRequest, BindingResult result) {
        if (result.hasErrors()) {
            ModelAndView mv = new ModelAndView("usuario/editar");
            mv.addObject("usuarioId", id);
            return mv;
        }

        Usuario usuarioExistente = usuarioService.findById(id);

        if (usuarioExistente != null) {
            usuarioService.update(usuarioExistente, usuarioRequest);
            return new ModelAndView("redirect:/usuarios");
        } else {
            return new ModelAndView("redirect:/usuarios");
        }
    }


    @GetMapping("/{id}/excluir")
    public ModelAndView excluirUsuario(@PathVariable("id") Long id) {
        usuarioService.delete(id);
        return new ModelAndView("redirect:/usuarios");
    }

    @Transactional
    @PostMapping("/{id}/estabelecimentos")
    public String adicionarEstabelecimento(@PathVariable Long id, @RequestParam("nome") String nome,
                                           @RequestParam("cep") String cep,
                                           @RequestParam("tipo") TipoEstabelecimento tipo, Model model) {
        var usuario = usuarioService.findById(id);
        if (usuario == null) return "error"; // Lidar com caso de usuário não encontrado

        var estabelecimento = Estabelecimento.builder()
                .nome(nome)
                .cep(cep)
                .tipo(tipo)
                .build();
        var estabelecimentos = usuario.getEstabelecimentos();
        estabelecimentos.add(estabelecimento);
        usuario.setEstabelecimentos(estabelecimentos);

        usuarioService.save(usuario);

        return "redirect:/usuarios/{id}/estabelecimentos"; // Redireciona para a página de estabelecimentos
    }


    @GetMapping("/{id}/estabelecimentos")
    public String findEstabelecimentos(@PathVariable Long id, Model model) {
        var usuario = usuarioService.findById(id);
        if (usuario == null) {
            return "error";
        }

        var estabelecimentos = usuario.getEstabelecimentos();
        var resposta = estabelecimentos.stream()
                .map(estabelecimentoService::toResponse)
                .toList();

        model.addAttribute("estabelecimentos", resposta);
        model.addAttribute("usuarioId", id);
        return "usuario/estabelecimento";
    }

    @GetMapping("/{usuarioId}/estabelecimentos/{estabelecimentoId}/editar")
    public String editarEstabelecimento(@PathVariable Long usuarioId, @PathVariable Long estabelecimentoId, Model model) {
        Usuario usuario = usuarioService.findById(usuarioId);
        if (usuario == null) {
            return "error";
        }
        Estabelecimento estabelecimento = usuario.getEstabelecimentos().stream()
                .filter(e -> e.getId().equals(estabelecimentoId))
                .findFirst()
                .orElse(null);

        if (estabelecimento == null) {
            return "error";
        }
        model.addAttribute("usuarioId", usuarioId);
        model.addAttribute("estabelecimento", estabelecimento);
        model.addAttribute("tiposEstabelecimento", TipoEstabelecimento.values());
        return "usuario/editar-estabelecimento";
    }

    @PostMapping("/{usuarioId}/estabelecimentos/{estabelecimentoId}/editar")
    @Transactional
    public String atualizarEstabelecimento(@PathVariable Long usuarioId,
                                           @PathVariable Long estabelecimentoId,
                                           @RequestParam("nome") String nome,
                                           @RequestParam("cep") String cep,
                                           @RequestParam("tipo") TipoEstabelecimento tipo) {
        Usuario usuario = usuarioService.findById(usuarioId);
        if (usuario == null) {
            return "error"; // Ou página de erro adequada
        }

        Estabelecimento estabelecimento = usuario.getEstabelecimentos().stream()
                .filter(e -> e.getId().equals(estabelecimentoId))
                .findFirst()
                .orElse(null);

        if (estabelecimento == null) {
            return "error"; // Ou página de erro adequada
        }

        // Atualiza o estabelecimento
        estabelecimento.setNome(nome);
        estabelecimento.setCep(cep);
        estabelecimento.setTipo(tipo);

        usuarioService.save(usuario); // Salva o usuário, persistindo as alterações no estabelecimento

        return "redirect:/usuarios/" + usuarioId + "/estabelecimentos";
    }


    @GetMapping("/{usuarioId}/estabelecimentos/{estabelecimentoId}/excluir")
    @Transactional
    public String excluirEstabelecimento(@PathVariable Long usuarioId, @PathVariable Long estabelecimentoId) {
        Usuario usuario = usuarioService.findById(usuarioId);
        if (usuario == null) {
            return "error"; // Ou página de erro adequada
        }

        usuario.getEstabelecimentos().removeIf(e -> e.getId().equals(estabelecimentoId));
        usuarioService.save(usuario);
        return "redirect:/usuarios/" + usuarioId + "/estabelecimentos";
    }

}