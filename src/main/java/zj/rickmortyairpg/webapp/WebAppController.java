package zj.rickmortyairpg.webapp;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@CrossOrigin
public class WebAppController {

    private final WebAppService webAppService;

    public WebAppController(WebAppService webAppService) {
        this.webAppService = webAppService;
    }

    @PostMapping("/user/{username}")
    public ResponseEntity<Mono<UserDTO>> createUser(@PathVariable String username, @RequestParam short characterId) {
        return webAppService.createUser(username, characterId);
    }

    @GetMapping("/user/{username}")
    public ResponseEntity<Mono<UserDTO>> login(@PathVariable String username) {
        return webAppService.login(username);
    }

}
