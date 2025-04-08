package zj.rickmortyai.config;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import zj.rickmortyai.openaimodel.ChatCompletionResponse;
import zj.rickmortyai.service.OpenAIService;
import zj.rickmortyai.service.RickMortyApiService;

@Component
public class ApiDataPrinter implements CommandLineRunner {

    @Override
    public void run(String... args) throws JsonProcessingException {
        /*
        rickMortyApiService
                .fetchCharacters()
                .block()
                .forEach(System.out::println);
        rickMortyApiService
                .fetchLocations()
                .block()
                .forEach(System.out::println);


        ChatCompletionResponse r = openAIService.makeRequest("Hi Rick, what are you up to?");
        System.out.println(r.getChoices()
                .getFirst()
                .getMessage()
                .getContent());

         */
    }

    private final RickMortyApiService rickMortyApiService;
    private final OpenAIService openAIService;

    public ApiDataPrinter(RickMortyApiService rickMortyApiService, OpenAIService openAIService) {
        this.rickMortyApiService = rickMortyApiService;
        this.openAIService = openAIService;
    }
}
