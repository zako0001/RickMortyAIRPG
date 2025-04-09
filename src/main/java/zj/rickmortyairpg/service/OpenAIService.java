package zj.rickmortyairpg.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import zj.rickmortyairpg.openaimodel.ChatCompletionRequest;
import zj.rickmortyairpg.openaimodel.ChatCompletionResponse;
import zj.rickmortyairpg.openaimodel.Message;

import java.util.ArrayList;
import java.util.List;

@Service
public class OpenAIService {

    @Value("${app.api-key}")
    private String API_KEY;

    private final WebClient webClient;

    public OpenAIService(WebClient webClient) {
        this.webClient = webClient;
    }

    public ChatCompletionResponse makeRequest(String userPrompt) throws JsonProcessingException {

        List<Message> messages = new ArrayList<>();
        Message systemMessage = new Message();
        Message userMessage = new Message();
        messages.add(systemMessage);
        messages.add(userMessage);
        systemMessage.setContent("You are Rick Sanchez from Rick and Morty. You are talking to Morty");
        systemMessage.setRole("system");
        userMessage.setContent(userPrompt);
        userMessage.setRole("user");

        ChatCompletionRequest chatCompletionRequest = new ChatCompletionRequest();
        chatCompletionRequest.setModel("gpt-4o-mini");
        chatCompletionRequest.setMax_completion_tokens(5);
        chatCompletionRequest.setMessages(messages);

        return webClient
                .post()
                .uri("https://api.openai.com/v1/chat/completions")
                .header("Authorization", "Bearer " + API_KEY)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromValue(new ObjectMapper().writeValueAsString(chatCompletionRequest)))
                .retrieve()
                .bodyToMono(ChatCompletionResponse.class)
                .block();
    }
}
