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

        // Use commented out code as alternative for testing UI without using tokens.

        /*try {
            Thread.sleep(1000);
        } catch (InterruptedException ignored) {}
        Message message = new Message("assistant", "{1$Rick}: *Burps.* \"No {2$Morty}, you can't borrow the Space Cruiser.\"");
        Choice choice = new Choice();
        choice.setMessage(message);
        ChatCompletionResponse response = new ChatCompletionResponse();
        response.setChoices(List.of(choice));
        return Mono.just(response);*/

        ChatCompletionRequest request = new ChatCompletionRequest("gpt-4o-mini", messages, 1000);
        return webClient
                .post()
                .uri("https://api.openai.com/v1/chat/completions")
                .header("Authorization", "Bearer " + API_KEY)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .bodyValue(request)
                .retrieve()
                .bodyToMono(ChatCompletionResponse.class);
    }

}
