package zj.rickmortyairpg.rickandmortyapi;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import zj.rickmortyairpg.persistance.GameCharacter;

import java.util.List;

@Service
public class RickAndMortyApiService {

    private final WebClient webClient;

    public RickAndMortyApiService() {
        this.webClient = WebClient.create("https://rickandmortyapi.com/api");
    }

    public Mono<List<GameCharacter>> fetchCharactersByIds(String commaSeparatedIds) {
        return webClient
                .get()
                .uri("/character/" + commaSeparatedIds)
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<>() {});
    }

    public Mono<List<Location>> fetchLocationsByIds(String commaSeparatedIds) {
        return webClient
                .get()
                .uri("/location/" + commaSeparatedIds)
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<>() {});
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
