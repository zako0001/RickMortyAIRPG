package zj.rickmortyai.service;

import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import zj.rickmortyai.model.Character;
import zj.rickmortyai.model.Location;
import zj.rickmortyai.model.CharacterPage;
import zj.rickmortyai.model.LocationPage;

import java.util.List;

@Service
public class RickMortyApiService {

    private final WebClient webClient;

    public RickMortyApiService(WebClient webClient) {
        this.webClient = webClient;
    }

    public Mono<List<Character>> fetchCharacters() {
        return webClient
                .get()
                .uri("https://rickandmortyapi.com/api/character/?name=summer&status=alive")
                .retrieve()
                .bodyToMono(CharacterPage.class)
                .map(CharacterPage::getResults);
    }

    public Mono<List<Location>> fetchLocations() {
        return webClient
                .get()
                .uri("https://rickandmortyapi.com/api/location/?name=earth")
                .retrieve()
                .bodyToMono(LocationPage.class)
                .map(LocationPage::getResults);
    }

    public Mono<Character> fetchCharacter() {
        return webClient
                .get()
                .uri("https://rickandmortyapi.com/api/character/3")
                .retrieve()
                .bodyToMono(Character.class);
    }

    public Mono<Location> fetchLocation() {
        return webClient
                .get()
                .uri("https://rickandmortyapi.com/api/location/20")
                .retrieve()
                .bodyToMono(Location.class);
    }
}
