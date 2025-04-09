package zj.rickmortyairpg.config;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import zj.rickmortyairpg.openaimodel.Chat;
import zj.rickmortyairpg.openaimodel.Message;
import zj.rickmortyairpg.repository.ChatRepository;
import zj.rickmortyairpg.repository.MessageRepository;
import zj.rickmortyairpg.service.OpenAIService;
import zj.rickmortyairpg.service.RickMortyApiService;

import java.util.ArrayList;
import java.util.List;

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
        Message message = new Message(0L,"system", "You are Rick Sanchez from Rick and Morty. You are talking to Morty");
        Message message2 = new Message(0L, "user", "Hi Rick, what are you up to?");
        message = messageRepository.save(message);
        message2 = messageRepository.save(message2);
        Chat chat = new Chat(0L, new ArrayList<>(List.of(message, message2)));
        chat = chatRepository.save(chat);
    }

    private final RickMortyApiService rickMortyApiService;
    private final OpenAIService openAIService;
    private final MessageRepository messageRepository;
    private final ChatRepository chatRepository;

    public ApiDataPrinter(RickMortyApiService rickMortyApiService, OpenAIService openAIService, MessageRepository messageRepository, ChatRepository chatRepository) {
        this.rickMortyApiService = rickMortyApiService;
        this.openAIService = openAIService;
        this.messageRepository = messageRepository;
        this.chatRepository = chatRepository;
    }
}
