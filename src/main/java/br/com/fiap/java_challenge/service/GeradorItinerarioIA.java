package br.com.fiap.java_challenge.service;

import br.com.fiap.java_challenge.dto.request.PreferenciaViagemRequest;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.logging.Level;
import java.util.logging.Logger;

@Service
public class GeradorItinerarioIA {


    private static final Logger LOG = Logger.getLogger(GeradorItinerarioIA.class.getName());
    @Autowired
    private RestTemplate restTemplate;

    private final String API_URL_TEMPLATE = "https://generativelanguage.googleapis.com/v1beta/models/gemini-1.5-flash:generateContent?key=%s";

    @Value("${google.api.key}")
    private String googleApiKey;


    public String gerarItinerario(PreferenciaViagemRequest preferencias) {
        String prompt = construirPrompt(preferencias);
        return this.chamadaApi(prompt, googleApiKey);
    }




    private String construirPrompt(PreferenciaViagemRequest preferencias) {
        // Construa um prompt detalhado para a IA com as preferências
        return "Crie um itinerário de viagem detalhado para a cidade de São Paulo, e dependendo do idioma que foi usada para responder como ingles, espanhol ou portugues gere o itinerario de acordo com o idioma usado " +
                "com base nas seguintes preferências:\n" +
                "Tipo de Culinária: " + preferencias.tipo_culinaria() + "\n" +
                "Restrições Alimentares: " + preferencias.restricoes_alimentares() + "\n" +
                "Tipo de Transporte: " + preferencias.tipo_transporte() + "\n" +
                "Tipo de Hospedagem: " + preferencias.tipo_hospedagem() + "\n" +
                "Viaja Sozinho: " + preferencias.viaja_sozinho() + "\n";
    }


    public String chamadaApi(final String prompt, final String apiKey) {
        String apiUrl = String.format(API_URL_TEMPLATE, apiKey);
        HttpHeaders headers = new HttpHeaders();
        headers.set("Content-Type", "Application/json");

        String requestBody = requestJSONCreator(prompt);
        HttpEntity<String> request = new HttpEntity<>(requestBody, headers);

        try {
            ResponseEntity<String> response = restTemplate.exchange(apiUrl, HttpMethod.POST, request, String.class);
            if (response.getStatusCode().is2xxSuccessful()) {
                System.out.println("JSON Completo da Resposta: " + response.getBody());

                ObjectMapper mapper = new ObjectMapper();

                try {
                    ObjectNode jsonNodes = mapper.readValue(response.getBody(), ObjectNode.class);
                    ArrayNode candidates = (ArrayNode) jsonNodes.get("candidates");
                    if (candidates != null && candidates.size() > 0) {
                        ObjectNode candidate = (ObjectNode) candidates.get(0);
                        if (candidate != null) {
                            ObjectNode content = (ObjectNode) candidate.get("content"); // Acessa "content"
                            if (content != null) {
                                ArrayNode parts = (ArrayNode) content.get("parts"); // Acessa "parts"
                                if (parts != null && parts.size() > 0) {
                                    ObjectNode part = (ObjectNode) parts.get(0); // Acessa o primeiro elemento de "parts"
                                    if (part != null && part.has("text")) {       // Verifica se "text" existe
                                        String output = part.get("text").asText(); // Extrai o texto de "text"
                                        return output;
                                    } else {
                                        System.err.println("Campo 'text' não encontrado ou parts vazio na resposta do Gemini.");
                                    }
                                } else {
                                    System.err.println("Campo 'parts' não encontrado ou nulo na resposta do Gemini.");
                                }
                            } else {
                                System.err.println("Campo 'content' não encontrado ou nulo na resposta do Gemini.");
                            }
                        }
                    } else {
                        System.err.println("Campo 'candidates' não encontrado ou vazio na resposta do Gemini.");
                    }
                }catch (Exception e){
                    e.printStackTrace();
                }
            } else {
                LOG.log(Level.SEVERE, "Erro na chamada à API Gemini: " + response.getStatusCodeValue() + " - " + response.getBody());
                return null;
            }
        } catch (Exception e) {
            LOG.log(Level.SEVERE, "Erro na chamada à API Gemini: " + e.getMessage());
            e.printStackTrace(); // Imprime o stack trace para mais detalhes
            return null;
        }
        return null;
    }


    private String requestJSONCreator(final String prompt) throws RuntimeException {
        ObjectMapper objectMapper = new ObjectMapper();
        ObjectNode contentNode = objectMapper.createObjectNode();
        ObjectNode partsNode = objectMapper.createObjectNode();
        partsNode.put("text", prompt);
        contentNode.set("parts", objectMapper.createArrayNode().add(partsNode));
        ObjectNode requestBodyNote = objectMapper.createObjectNode();
        requestBodyNote.set("contents", objectMapper.createArrayNode().add(contentNode));
        String requestBody = "";
        try {
            requestBody = objectMapper.writeValueAsString(requestBodyNote);
        } catch (JsonProcessingException ex) {
            LOG.log(Level.SEVERE, "Erro ao construir o corpo da requisição JSON: " + ex.getMessage(), ex);

            throw new RuntimeException("Falha ao construir o corpo da requisição JSON", ex);
        }
        return requestBody;
    }
}