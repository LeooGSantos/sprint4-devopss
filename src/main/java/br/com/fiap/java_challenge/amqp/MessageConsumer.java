package br.com.fiap.java_challenge.amqp;


import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Service
public class MessageConsumer {

    @RabbitListener(queues = "myQueue") // Escuta a fila "myQueue"
    public void receiveMessage(String message) {
        System.out.println("Mensagem recebida: " + message);
        // Aqui você processa a mensagem recebida
    }
}