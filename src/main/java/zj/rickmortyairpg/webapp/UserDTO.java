package zj.rickmortyairpg.webapp;

import lombok.AllArgsConstructor;
import lombok.Data;
import zj.rickmortyairpg.persistance.CharacterInfo;
import zj.rickmortyairpg.rickandmortyapi.Location;

import java.util.Map;

@Data
@AllArgsConstructor
public class UserDTO {
    private String username;
    private Short characterId;
    private Map<Short, String> incidents; // characterId, content
    private Map<Short, Character> characters;
    private Map<Short, CharacterInfo> characterInfos;
    private Map<Short, Location> locations;
}
