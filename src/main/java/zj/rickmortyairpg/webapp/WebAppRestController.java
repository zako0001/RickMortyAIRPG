package zj.rickmortyairpg.webapp;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;
import zj.rickmortyairpg.persistance.PlayerMessage;
import zj.rickmortyairpg.rickandmortyapi.Location;

import java.util.List;

@RestController
@CrossOrigin
public class WebAppRestController {

    private final WebAppService webAppService;

    public WebAppRestController(WebAppService webAppService) {
        this.webAppService = webAppService;
    }

    @GetMapping("/error/message")
    public ResponseEntity<String> getErrorMessage(@SessionAttribute String status) {
        return ResponseEntity.ok(status);
    }

    @PostMapping("/players/{username}")
    public ResponseEntity<String> createPlayer(@PathVariable String username, @RequestParam short characterId) {
        return webAppService.createPlayer(username, characterId);
    }

    @GetMapping("/players/{username}")
    public ResponseEntity<GameInfo> login(@PathVariable String username) {
        return webAppService.login(username);
    }

    @PostMapping("/players/{username}/messages")
    public ResponseEntity<List<PlayerMessage>> prompt(@PathVariable String username, @RequestBody PlayerMessage message) throws InterruptedException {
        return webAppService.prompt(username, message);
    }

    @GetMapping("/locations")
    public ResponseEntity<List<Location>> getLocations(@RequestParam String name, @RequestParam String type, @RequestParam String dimension) {
        return webAppService.searchLocations(name, type, dimension);
    }

    @PutMapping("/players/{username}/location")
    public ResponseEntity<String> changeLocation(@PathVariable String username, @RequestParam short id, @RequestBody List<Long> updateIds) {
        return webAppService.changeLocation(username, id, updateIds);
    }

}
