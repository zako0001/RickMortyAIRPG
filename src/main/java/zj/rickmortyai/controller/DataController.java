package zj.rickmortyai.controller;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;
import zj.rickmortyai.model.Character;
import zj.rickmortyai.model.Location;
import zj.rickmortyai.service.RickMortyApiService;

@RestController
@CrossOrigin
public class DataController {

    private final RickMortyApiService rickMortyApiService;

    public DataController(RickMortyApiService rickMortyApiService) {
        this.rickMortyApiService = rickMortyApiService;
    }

    @GetMapping("/character")
    public Mono<Character> character() {
        return rickMortyApiService.fetchCharacter();
    }

    @GetMapping("/location")
    public Mono<Location> location() {
        return rickMortyApiService.fetchLocation();
    }

}
