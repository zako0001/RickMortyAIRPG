package zj.rickmortyairpg.controller;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;
import zj.rickmortyairpg.model.Character;
import zj.rickmortyairpg.model.Location;
import zj.rickmortyairpg.service.RickMortyApiService;

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
