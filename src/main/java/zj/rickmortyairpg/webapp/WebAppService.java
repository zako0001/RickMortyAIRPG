package zj.rickmortyairpg.webapp;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import zj.rickmortyairpg.openaiplatformapi.OpenAiPlatformApiService;
import zj.rickmortyairpg.persistance.*;
import zj.rickmortyairpg.rickandmortyapi.Location;
import zj.rickmortyairpg.rickandmortyapi.LocationPage;
import zj.rickmortyairpg.rickandmortyapi.RickAndMortyApiService;
import zj.rickmortyairpg.rickandmortyapi.SimpleLocation;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class WebAppService {

    private final GameCharacterRepository gameCharacterRepository;
    private final ItemRepository itemRepository;
    private final PlayerMessageRepository playerMessageRepository;
    private final PlayerRepository playerRepository;
    private final RickAndMortyApiService rickAndMortyApiService;
    private final OpenAiPlatformApiService openAiPlatformApiService;
    private final PlayerGameCharacterRepository playerGameCharacterRepository;
    private final LocationRepository locationRepository;

    public WebAppService(GameCharacterRepository gameCharacterRepository, ItemRepository itemRepository, PlayerMessageRepository playerMessageRepository, PlayerRepository playerRepository, RickAndMortyApiService rickAndMortyApiService, OpenAiPlatformApiService openAiPlatformApiService, PlayerGameCharacterRepository playerGameCharacterRepository, LocationRepository locationRepository) {
        this.gameCharacterRepository = gameCharacterRepository;
        this.itemRepository = itemRepository;
        this.playerMessageRepository = playerMessageRepository;
        this.playerRepository = playerRepository;
        this.rickAndMortyApiService = rickAndMortyApiService;
        this.openAiPlatformApiService = openAiPlatformApiService;
        this.playerGameCharacterRepository = playerGameCharacterRepository;
        this.locationRepository = locationRepository;
    }

    private String getStoryMessage(int characterId) {
        List<String> characters = new ArrayList<>(List.of("{2$Morty}", "{3$Summer}", "{4$Beth}", "{5$Jerry}"));
        String self = characters.remove(characterId - 2);
        return self + ": *Enters the Smith house living room on {127@Earth (Home)}, and sees " + String.join(", ", characters) + ", and {1$Rick}.*";
    }

    private String getDeveloperMessage(int characterId) {
        List<String> characters = new ArrayList<>(List.of("{2$Morty}", "{3$Summer}", "{4$Beth}", "{5$Jerry}"));
        List<String> options = new ArrayList<>(characters);
        options.remove(characterId - 2);
        return "You deliver responses in a text-based Rick and Morty RPG." +
                "The user role-plays as " + characters.get(characterId - 2) +
                ", and you role-play as " + String.join(", ", options) + ", and {1$Rick}." +
                "Each response starts with the character you choose for that response and a colon." +
                "The response is formatted with \"quote\", *action*, and {id$name}." +
                "You deliver one response at a time without giving options.\n\n" +
                "Example:\n{1$Rick}: *Burps.* \"No " + characters.get(characterId -2) + ", you can't borrow the Space Cruiser.\"";
    }

    public PlayerMessage addAssistantMessage(Player player) {
        return player.addMessage("assistant", openAiPlatformApiService
                .makeRequest(player.getMessages())
                .map(chatResponse -> chatResponse.getChoices().getFirst().getMessage().getContent())
                .block());
    }

    public ResponseEntity<String> createPlayer(String username, short characterId) {
        if (playerRepository.existsById(username)) return new ResponseEntity<>("Username taken.", HttpStatus.CONFLICT);
        Player player = new Player(username, characterId);
        player.addCharacters(gameCharacterRepository.findAllById(List.of(new Short[]{1, 2, 3, 4, 5})));
        player.setLocations(new HashSet<>(locationRepository.findAllById(List.of(new Short[]{127, 1, 12, 20}))));
        player.addMessage("developer", getDeveloperMessage(characterId));
        player.addMessage("user", getStoryMessage(characterId));
        player.setInventory(new HashSet<>(List.of(itemRepository.findById((short) 1).get())));
        return new ResponseEntity<>(playerRepository.save(player).getUsername(), HttpStatus.CREATED);
    }

    public ResponseEntity<GameInfo> login(String username) {
        Optional<Player> user = playerRepository.findById(username);
        if (user.isEmpty()) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(new GameInfo(user.get()));
    }

    public ResponseEntity<List<PlayerMessage>> prompt(String username, PlayerMessage message) {
        Player player = playerRepository.findById(username).get();
        List<String> characters = List.of("{2$Morty}", "{3$Summer}", "{4$Beth}", "{5$Jerry}");
        String content = characters.get(player.getCharacterId() - 2) + ": \"" + message.getContent() + "\"";
        PlayerMessage userMessage = player.addMessage("user", content);
        PlayerMessage assistantMessage = addAssistantMessage(player);
        playerRepository.save(player);
        return ResponseEntity.ok(List.of(userMessage, assistantMessage));
    }

    public ResponseEntity<List<Location>> searchLocations(String name, String type, String dimension1) {
        StringJoiner sj = new StringJoiner("&");
        String dimension = dimension1.equals("C-131") ? "Replacement Dimension" : dimension1;
        if (!name.isBlank()) sj.add("name=" + name);
        if (!type.isBlank()) sj.add("type=" + type);
        if (!dimension.isBlank()) sj.add("dimension=" + dimension);
        ResponseEntity<LocationPage> response = rickAndMortyApiService.fetchLocationsByFilter("?" + sj);
        if (response.getStatusCode() == HttpStatus.NOT_FOUND) return ResponseEntity.notFound().build();
        LocationPage page = response.getBody();
        List<Location> locations = new ArrayList<>(page.getResults());
        boolean nameFits = name.isBlank();
        boolean typeFits = type.isBlank();
        boolean dimensionFits = dimension.isBlank();
        if (!name.isBlank()) nameFits = "Earth".contains(name);
        if (!type.isBlank()) typeFits = "Planet".contains(type);
        if (!dimension.isBlank()) dimensionFits = "Parmesan Dimension".contains(dimension);
        if (nameFits && typeFits && dimensionFits) {
            locations.add(locationRepository.findById((short) 127).get());
        }
        if (page.getInfo().getPages() == 1) return ResponseEntity.ok(locations);
        for (int i = 2; i <= page.getInfo().getPages(); i++) {
            locations.addAll(rickAndMortyApiService.fetchLocationsByUrl(page.getInfo().getNext()).getResults());
        }
        return ResponseEntity.ok(locations);
    }

    public ResponseEntity<String> changeLocation(String username, short locationId, List<Long> updateIds) {

        // Change location of Player Game Characters
        Location location = locationRepository.findById(locationId)
                .orElseGet(() -> locationRepository.save(rickAndMortyApiService.fetchLocation(locationId)));
        SimpleLocation simpleLocation = new SimpleLocation(location.getName(), location.getDimension(), location.getId());
        List<PlayerGameCharacter> playerGameCharacters = playerGameCharacterRepository.findAllById(updateIds);
        playerGameCharacters.forEach(playerGameCharacter -> playerGameCharacter.setCurrentLocation(simpleLocation));
        playerGameCharacterRepository.saveAll(playerGameCharacters);

        // Add messages
        Player player = playerRepository.findById(username).get();
        List<String> references = playerGameCharacters
                .stream()
                .map(playerGameCharacter -> playerGameCharacter.getGameCharacter().getReference())
                .collect(Collectors.toList());
        String self = references.remove(0);
        String message = self + ": *Fires the {1£Portal Gun} and goes through the portal to {" + simpleLocation.getId() + "@" + simpleLocation.getName() + "}.*";
        for (String reference : references) {
            message += "\n" + reference + ": *Goes through the portal.*";
        }
        player.addMessage("user", message);
        addAssistantMessage(player);
        playerRepository.save(player);

        return ResponseEntity.ok("Teleport complete.");
    }

}
