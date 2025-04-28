package zj.rickmortyairpg.webapp;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import zj.rickmortyairpg.persistance.GameCharacter;
import zj.rickmortyairpg.persistance.GameCharacterRepository;
import zj.rickmortyairpg.persistance.ItemRepository;
import zj.rickmortyairpg.persistance.LocationRepository;
import zj.rickmortyairpg.rickandmortyapi.SimpleLocation;
import zj.rickmortyairpg.rickandmortyapi.Location;
import zj.rickmortyairpg.rickandmortyapi.RickAndMortyApiService;
import zj.rickmortyairpg.rickandmortyfandom.Item;

import java.util.*;

@Component
public class InitData implements CommandLineRunner {

    @Override
    public void run(String... args) {
        //loadInfo();
    }

    private final GameCharacterRepository gameCharacterRepository;
    private final ItemRepository itemRepository;
    private final LocationRepository locationRepository;
    private final RickAndMortyApiService rickAndMortyApiService;

    public InitData(GameCharacterRepository gameCharacterRepository, ItemRepository itemRepository, LocationRepository locationRepository, RickAndMortyApiService rickAndMortyApiService) {
        this.gameCharacterRepository = gameCharacterRepository;
        this.itemRepository = itemRepository;
        this.locationRepository = locationRepository;
        this.rickAndMortyApiService = rickAndMortyApiService;
    }

    public void loadInfo() {
        locationRepository.saveAll(rickAndMortyApiService.fetchLocationsByIds("1,12,20").block());
        locationRepository.save(new Location((short) 127, "Earth", "Planet", "Parmesan Dimension"));
        List<GameCharacter> characters = rickAndMortyApiService.fetchCharactersByIds("1,2,3,4,5").block();
        characters.forEach(character -> character.setLocation(new SimpleLocation("Earth", "Parmesan Dimension", (short) 127)));
        characters.get(1).setOrigin(new SimpleLocation("Earth", "C-131", (short) 1));
        gameCharacterRepository.saveAll(characters);
        Item portalGun = new Item("Portal Gun", "https://static.wikia.nocookie.net/rickandmorty/images/5/55/Portal_gun.png");
        portalGun.setType("Gadget");
        portalGun.setPurpose("Open portals to other universes");
        itemRepository.save(portalGun);
    }

}