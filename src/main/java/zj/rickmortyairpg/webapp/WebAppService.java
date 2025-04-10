package zj.rickmortyairpg.webapp;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import zj.rickmortyairpg.openaiplatformapi.OpenAiPlatformApiService;
import zj.rickmortyairpg.persistance.*;
import zj.rickmortyairpg.rickandmortyapi.RickAndMortyApiService;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class WebAppService {

    private final CharacterInfoRepository characterInfoRepository;
    private final IncidentRepository incidentRepository;
    private final UserRepository userRepository;
    private final RickAndMortyApiService rickAndMortyApiService;
    private final OpenAiPlatformApiService openAiPlatformApiService;

    public WebAppService(CharacterInfoRepository characterInfoRepository, IncidentRepository incidentRepository, UserRepository userRepository, RickAndMortyApiService rickAndMortyApiService, OpenAiPlatformApiService openAiPlatformApiService) {
        this.characterInfoRepository = characterInfoRepository;
        this.incidentRepository = incidentRepository;
        this.userRepository = userRepository;
        this.rickAndMortyApiService = rickAndMortyApiService;
        this.openAiPlatformApiService = openAiPlatformApiService;
    }

    private Mono<UserDTO> loadUserDTO(User user) {
        List<Incident> incidents = incidentRepository.findByUser_Username(username);
    }

    private Mono<CharacterInfo> loadCharacterInfo(short characterId) {
        Optional<CharacterInfo> characterInfo = characterInfoRepository.findById(characterId);
        if (characterInfo.isPresent()) return Mono.just(characterInfo.get());
        return rickAndMortyApiService.fetchCharacter(characterId)
                .map(character -> new CharacterInfo(
                        character.getId(),
                        (short) 100,
                        (short) 100,
                        character.getLocation().getIdFromUrl()
                ));
    }

    public ResponseEntity<Mono<UserDTO>> createUser(String username, short characterId) {
        if (userRepository.existsById(username)) return ResponseEntity.status(HttpStatus.CONFLICT).build();
        List<Incident> incidents = List.of(
                new Incident("developer", "You are Rick Sanchez from Rick and Morty. Correspondence begin with 'Name: ' and then '*action*' or '\"quote\"' or a combination."),
                new Incident("user", "Morty: *Enters the living room.* \"Hello Rick.\""));
        User user = new User();
        user.setUsername(username);
        user.setIncidents(incidents);
        Mono<UserDTO> userDTO = loadCharacterInfo(characterId).map(characterInfo -> {
            user.setCharacterInfo(characterInfo);
        });
        return ResponseEntity.status(HttpStatus.CREATED).body(userDTO);
    }

    public ResponseEntity<Mono<UserDTO>> login(String username) {
        Optional<User> user = userRepository.findById(username);
        if (user.isEmpty()) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(loadUserDTO(user.get()));
    }

}
