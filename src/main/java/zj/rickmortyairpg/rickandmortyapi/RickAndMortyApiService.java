package zj.rickmortyairpg.rickandmortyapi;

import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.List;

@Service
public class RickAndMortyApiService {

    private final WebClient webClient;

    public RickAndMortyApiService() {
        this.webClient = WebClient.create("https://rickandmortyapi.com/api");
    }

    public Mono<List<Character>> fetchCharacters(String filter) {
        return webClient
                .get()
                .uri("/character/" + filter)
                .retrieve()
                .bodyToMono(CharacterPage.class)
                .map(CharacterPage::getResults);
    }

    public Mono<List<Location>> fetchLocations(String filter) {
        return webClient
                .get()
                .uri("/location/" + filter)
                .retrieve()
                .bodyToMono(LocationPage.class)
                .map(LocationPage::getResults);
    }

    public Mono<Character> fetchCharacter(short id) {
        return webClient
                .get()
                .uri("/character/" + id)
                .retrieve()
                .bodyToMono(Character.class);
    }

    public Mono<Location> fetchLocation(short id) {
        return webClient
                .get()
                .uri("/location/" + id)
                .retrieve()
                .bodyToMono(Location.class);
    }
}
