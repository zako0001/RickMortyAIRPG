package zj.rickmortyairpg.openaiplatformapi;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.List;

@Service
public class OpenAiPlatformApiService {

    @Value("${app.api-key}")
    private String API_KEY;
    private final WebClient webClient;

    public OpenAiPlatformApiService() {
        this.webClient = WebClient.create();
    }

    public Mono<ChatCompletionResponse> makeRequest(List<Message> messages) {
        ChatCompletionRequest request = new ChatCompletionRequest("gpt-4o-mini", messages, 5);
        return webClient
                .post()
                .uri("https://api.openai.com/v1/chat/completions")
                .header("Authorization", "Bearer " + API_KEY)
                .contentType(MediaType.APPLICATION_JSON) // Is this necessary?
                .accept(MediaType.APPLICATION_JSON) // Is this necessary?
                .bodyValue(request)
                .retrieve()
                .bodyToMono(ChatCompletionResponse.class);
    }

}
