package zj.rickmortyairpg.webapp;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import zj.rickmortyairpg.persistance.GameCharacter;
import zj.rickmortyairpg.persistance.GameCharacterRepository;
import zj.rickmortyairpg.persistance.LocationRepository;
import zj.rickmortyairpg.rickandmortyapi.SimpleLocation;
import zj.rickmortyairpg.rickandmortyapi.Location;
import zj.rickmortyairpg.rickandmortyapi.RickAndMortyApiService;

import java.util.List;

@Component
public class InitData implements CommandLineRunner {

    @Override
    public void run(String... args) {
        locationRepository.saveAll(rickAndMortyApiService.fetchLocationsByIds("1,12,20").block());
        locationRepository.save(new Location((short) 127, "Earth", "Planet", "Parmesan Dimension"));
        List<GameCharacter> characters = rickAndMortyApiService.fetchCharactersByIds("1,2,3,4,5").block();
        characters.forEach(character -> character.setLocation(new SimpleLocation("Earth (Home)", (short) 127)));
        characters.get(1).setOrigin(new SimpleLocation("Earth (C-131)", (short) 1));
        gameCharacterRepository.saveAll(characters);
    }

    private final GameCharacterRepository gameCharacterRepository;
    private final LocationRepository locationRepository;
    private final RickAndMortyApiService rickAndMortyApiService;

    public InitData(GameCharacterRepository gameCharacterRepository, LocationRepository locationRepository, RickAndMortyApiService rickAndMortyApiService) {
        this.gameCharacterRepository = gameCharacterRepository;
        this.locationRepository = locationRepository;
        this.rickAndMortyApiService = rickAndMortyApiService;
    }

}