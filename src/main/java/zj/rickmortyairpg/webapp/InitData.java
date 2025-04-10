package zj.rickmortyairpg.webapp;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;
import zj.rickmortyairpg.persistance.CharacterInfo;
import zj.rickmortyairpg.rickandmortyapi.RickAndMortyApiService;

import java.util.Optional;

@Component
public class InitData implements CommandLineRunner {

    @Override
    public void run(String... args) {

    }

    private final RickAndMortyApiService rickAndMortyApiService;

    public InitData(RickAndMortyApiService rickAndMortyApiService) {
        this.rickAndMortyApiService = rickAndMortyApiService;
    }

    private void loadCharacterInfo(short characterId) {
        rickAndMortyApiService.fetchCharacter(characterId).
    }
}