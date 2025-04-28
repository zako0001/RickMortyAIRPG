package zj.rickmortyairpg.webapp;

import lombok.Data;
import zj.rickmortyairpg.persistance.Player;
import zj.rickmortyairpg.persistance.PlayerGameCharacter;
import zj.rickmortyairpg.persistance.PlayerMessage;
import zj.rickmortyairpg.rickandmortyapi.Location;
import zj.rickmortyairpg.rickandmortyfandom.Item;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Data
public class GameInfo {

    private String username;
    private Short characterId;
    private Map<String, PlayerGameCharacter> characters;
    private Map<String, Location> locations;
    private List<PlayerMessage> messages;
    private Map<String, Item> inventory;

    public GameInfo(Player player) {
        this.username = player.getUsername();
        this.characterId = player.getCharacterId();
        this.characters = player
                .getPlayerGameCharacters()
                .stream()
                .collect(Collectors.toMap(pgc -> "id" + pgc.getGameCharacter().getId(), pgc -> pgc));
        this.locations = player
                .getLocations()
                .stream()
                .collect(Collectors.toMap(location -> "id" + location.getId(), location -> location));
        this.messages = player
                .getPlayerMessages()
                .stream()
                .filter(pm -> !pm.getRole().equals("developer"))
                .sorted(Comparator.comparing(PlayerMessage::getId))
                .toList();
        this.inventory = player
                .getInventory()
                .stream()
                .collect(Collectors.toMap(item -> "id" + item.getId(), item -> item));
    }

}
