package zj.rickmortyairpg.rickandmortyapi;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import reactor.core.publisher.Mono;
import zj.rickmortyairpg.persistance.GameCharacter;

import java.net.URI;
import java.util.List;

@Service
public class RickAndMortyApiService {

    private final WebClient webClient;

    public RickAndMortyApiService() {
        this.webClient = WebClient.create();
    }

    public Mono<List<GameCharacter>> fetchCharactersByIds(String commaSeparatedIds) {
        return webClient
                .get()
                .uri("https://rickandmortyapi.com/api/character/" + commaSeparatedIds)
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<>() {});
    }

    public Mono<List<Location>> fetchLocationsByIds(String commaSeparatedIds) {
        return webClient
                .get()
                .uri("https://rickandmortyapi.com/api/location/" + commaSeparatedIds)
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<>() {});
    }

    public Location fetchLocation(short id) {
        return webClient
                .get()
                .uri("https://rickandmortyapi.com/api/location/" + id)
                .retrieve()
                .bodyToMono(Location.class)
                .block();
    }

    public ResponseEntity<LocationPage> fetchLocationsByFilter(String filter) {
        try {
            return webClient
                    .get()
                    .uri("https://rickandmortyapi.com/api/location/" + filter.replace(" ", "%20"))
                    .retrieve()
                    .toEntity(LocationPage.class)
                    .block();
        } catch (WebClientResponseException e) {
            return ResponseEntity.notFound().build();
        }
    }

    public LocationPage fetchLocationsByUrl(String url) {
        return webClient
                .get()
                .uri(url)
                .retrieve()
                .bodyToMono(LocationPage.class)
                .block();
    }

}
