package zj.rickmortyairpg.webapp;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import zj.rickmortyairpg.persistance.PlayerMessage;

import java.util.List;

@RestController
@CrossOrigin
public class WebAppRestController {

    private final WebAppService webAppService;

    public WebAppRestController(WebAppService webAppService) {
        this.webAppService = webAppService;
    }

    @PostMapping("/player/{username}")
    public ResponseEntity<String> createPlayer(@PathVariable String username, @RequestParam short characterId) {
        return webAppService.createPlayer(username, characterId);
    }

    @GetMapping("/player/{username}")
    public ResponseEntity<GameInfo> login(@PathVariable String username) {
        return webAppService.login(username);
    }

    @PostMapping("/player/{username}/messages")
    public ResponseEntity<List<PlayerMessage>> prompt(@PathVariable String username, @RequestBody PlayerMessage message) throws InterruptedException {
        return webAppService.prompt(username, message);
    }

}
