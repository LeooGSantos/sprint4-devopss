package br.com.fiap.java_challenge.controller;

import br.com.fiap.java_challenge.amqp.MessageProducer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/mensagens")
public class MensagemController {


    @Autowired
    private MessageProducer messageProducer;

    @GetMapping("/enviar")
    public String enviarMensagem(@RequestParam("mensagem") String message) {
        messageProducer.sendMessage(message);
        return "Mensagem enviada: " + message;
    }



}