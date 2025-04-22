package zj.rickmortyairpg.webapp;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import zj.rickmortyairpg.openaiplatformapi.OpenAiPlatformApiService;
import zj.rickmortyairpg.persistance.*;
import zj.rickmortyairpg.rickandmortyapi.RickAndMortyApiService;

import java.util.*;

@Service
public class WebAppService {

    private final GameCharacterRepository gameCharacterRepository;
    private final PlayerMessageRepository playerMessageRepository;
    private final PlayerRepository playerRepository;
    private final RickAndMortyApiService rickAndMortyApiService;
    private final OpenAiPlatformApiService openAiPlatformApiService;
    private final PlayerGameCharacterRepository playerGameCharacterRepository;
    private final LocationRepository locationRepository;

    public WebAppService(GameCharacterRepository gameCharacterRepository, PlayerMessageRepository playerMessageRepository, PlayerRepository playerRepository, RickAndMortyApiService rickAndMortyApiService, OpenAiPlatformApiService openAiPlatformApiService, PlayerGameCharacterRepository playerGameCharacterRepository, LocationRepository locationRepository) {
        this.gameCharacterRepository = gameCharacterRepository;
        this.playerMessageRepository = playerMessageRepository;
        this.playerRepository = playerRepository;
        this.rickAndMortyApiService = rickAndMortyApiService;
        this.openAiPlatformApiService = openAiPlatformApiService;
        this.playerGameCharacterRepository = playerGameCharacterRepository;
        this.locationRepository = locationRepository;
    }

    private String getStoryMessage(int characterId) {
        List<String> characters = new ArrayList<>(List.of("{2$Morty}", "{3$Summer}", "{4$Beth}", "{5$Jerry}"));
        characters.remove(characterId - 2);
        return "{" + characterId + "$You} enter the Smith house living room, and see " + String.join(", ", characters) + ", and {1$Rick}.";
    }

    private String getDeveloperMessage(int characterId) {
        List<String> characters = List.of("{2$Morty}", "{3$Summer}", "{4$Beth}", "{5$Jerry}");
        List<String> options = new ArrayList<>(characters);
        options.remove(characterId - 2);
        return "You deliver responses in a text-based Rick and Morty RPG." +
                "The user role-plays as " + characters.get(characterId - 2) +
                ", and you role-play as " + String.join(", ", options) + ", and {1$Rick}." +
                "The story starts as the user enters the Smith house living room." +
                "Each response starts with the character you choose for that response and a colon." +
                "The response is formatted with \"quote\", *action*, and {id$name}." +
                "You deliver one response at a time without giving options.\n\n" +
                "Example:\n{1$Rick}: *Burps.* \"No " + characters.get(characterId -2) + ", you can't burrow the Space Cruiser.\"";
    }

    public ResponseEntity<String> createPlayer(String username, short characterId) {
        if (playerRepository.existsById(username)) return new ResponseEntity<>("Username taken.", HttpStatus.CONFLICT);
        Player player = new Player(username, characterId);
        player.addCharacters(gameCharacterRepository.findAllById(List.of(new Short[]{1, 2, 3, 4, 5})));
        player.setLocations(new HashSet<>(locationRepository.findAllById(List.of(new Short[]{127, 1, 12, 20}))));
        player.addMessage("developer", getDeveloperMessage(characterId));
        player.addMessage("story", getStoryMessage(characterId));
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
        PlayerMessage assistantMessage = player.addMessage("assistant", openAiPlatformApiService
                .makeRequest(player.getMessages())
                .map(chatResponse -> chatResponse.getChoices().getFirst().getMessage().getContent())
                .block());
        playerRepository.save(player);
        return ResponseEntity.ok(List.of(userMessage, assistantMessage));
    }

}
