package br.com.fiap.java_challenge.controller;


import br.com.fiap.java_challenge.dto.request.AvaliacaoRequest;
import br.com.fiap.java_challenge.entity.Avaliacao;
import br.com.fiap.java_challenge.entity.Estabelecimento;
import br.com.fiap.java_challenge.service.AvaliacaoService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import br.com.fiap.java_challenge.service.EstabelecimentoService;

import jakarta.validation.Valid;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/avaliacoes")
public class AvaliacaoController {

    @Autowired
    private AvaliacaoService avaliacaoService;

    @Autowired
    private EstabelecimentoService estabelecimentoService;

    @GetMapping
    public ModelAndView listarAvaliacoes(AvaliacaoRequest avaliacaoRequest) {
        ModelAndView mv = new ModelAndView("avaliacao/listar");

        if (avaliacaoRequest == null) {
            avaliacaoRequest = new AvaliacaoRequest("", null, null, null);
        }

        Estabelecimento estabelecimento = Estabelecimento.builder()
                .build();

        Avaliacao avaliacao = Avaliacao.builder()
                .comentario(avaliacaoRequest.comentario())
                .nota(avaliacaoRequest.nota())
                .dataAvaliacao(avaliacaoRequest.dataAvaliacao())
                .estabelecimento(estabelecimento)
                .build();

        ExampleMatcher matcher = ExampleMatcher
                .matchingAll()
                .withIgnoreNullValues()
                .withIgnoreCase();

        Example<Avaliacao> example = Example.of(avaliacao, matcher);

        List<Avaliacao> listaAvaliacoes = avaliacaoService.findAll(example)
                .stream().toList();
        mv.addObject("listaAvaliacoes", listaAvaliacoes);

        mv.addObject("avaliacaoRequest", new AvaliacaoRequest(null, null, null, null));
        return mv;
    }


    @GetMapping("/novo")
    public ModelAndView novaAvaliacao(AvaliacaoRequest avaliacaoRequest) {
        ModelAndView mv = new ModelAndView("avaliacao/criar");
        mv.addObject("avaliacaoRequest", avaliacaoRequest);
        return mv;
    }

    @PostMapping("/novo")
    @Transactional
    public ModelAndView criarAvaliacao(@Valid AvaliacaoRequest avaliacaoRequest, BindingResult result) {
        if (result.hasErrors()) {
            return new ModelAndView("avaliacao/criar");
        }

        // Verifica se o estabelecimento existe
        Estabelecimento estabelecimento = estabelecimentoService.findById(avaliacaoRequest.estabelecimentoId());
        if (estabelecimento == null) {
            // Adiciona um erro ao BindingResult
            result.rejectValue("estabelecimentoId", "error.estabelecimentoId", "Estabelecimento não encontrado.");
            return new ModelAndView("avaliacao/criar"); // Retorna para o formulário com o erro
        }

        Avaliacao entity = avaliacaoService.toEntity(avaliacaoRequest);
        Avaliacao saved = avaliacaoService.save(entity);

        return new ModelAndView("redirect:/avaliacoes");
    }

    @GetMapping("/{id}/editar")
    public ModelAndView editarAvaliacao(@PathVariable("id") Long id, Model model) {
        Optional<Avaliacao> avaliacao = Optional.ofNullable(avaliacaoService.findById(id));

        if (avaliacao.isPresent()) {
            AvaliacaoRequest avaliacaoRequest = avaliacaoService.toRequest(avaliacao.get());
            ModelAndView mv = new ModelAndView("avaliacao/editar");
            mv.addObject("avaliacaoRequest", avaliacaoRequest);
            mv.addObject("avaliacaoId", id);
            return mv;
        } else {
            return new ModelAndView("redirect:/avaliacoes");
        }
    }

    @PostMapping("/{id}/editar")
    @Transactional
    public ModelAndView atualizarAvaliacao(@PathVariable("id") Long id, @Valid AvaliacaoRequest avaliacaoRequest, BindingResult result) {
        if (result.hasErrors()) {
            ModelAndView mv = new ModelAndView("avaliacao/editar");
            mv.addObject("avaliacaoId", id);
            return mv;
        }

        Avaliacao avaliacaoExistente = avaliacaoService.findById(id);

        if (avaliacaoExistente != null) {
            avaliacaoService.update(avaliacaoExistente, avaliacaoRequest);
            return new ModelAndView("redirect:/avaliacoes");
        } else {
            return new ModelAndView("redirect:/avaliacoes");
        }
    }


    @GetMapping("/{id}/excluir")
    public ModelAndView excluirAvaliacao(@PathVariable("id") Long id) {
        avaliacaoService.delete(id);
        return new ModelAndView("redirect:/avaliacoes");
    }
}