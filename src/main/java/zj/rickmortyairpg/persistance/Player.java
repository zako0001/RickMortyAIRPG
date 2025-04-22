package zj.rickmortyairpg.persistance;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import zj.rickmortyairpg.openaiplatformapi.Message;
import zj.rickmortyairpg.rickandmortyapi.Location;

import java.util.*;

@Data
@NoArgsConstructor
@Entity
public class Player {
    @Id
    @Column(columnDefinition = "VARCHAR(15)")
    private String username;
    private Short characterId;
    @EqualsAndHashCode.Exclude
    @OneToMany(mappedBy = "player", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<PlayerMessage> playerMessages;
    @EqualsAndHashCode.Exclude
    @OneToMany(mappedBy = "player", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Set<PlayerGameCharacter> playerGameCharacters;
    @EqualsAndHashCode.Exclude
    @ManyToMany
    private Set<Location> locations;

    public Player(String username, short characterId) {
        this.username = username;
        this.characterId = characterId;
    }

    public void addCharacters(Collection<GameCharacter> gameCharacters) {
        if (getPlayerGameCharacters() == null) {
            setPlayerGameCharacters(new HashSet<>());
        }
        List<PlayerGameCharacter> characters = gameCharacters
                .stream()
                .map(gc -> new PlayerGameCharacter(this, gc))
                .toList();
        getPlayerGameCharacters().addAll(characters);
    }

    public PlayerMessage addMessage(String role, String content) {
        if (getPlayerMessages() == null) {
            setPlayerMessages(new ArrayList<>());
        }
        PlayerMessage playerMessage = new PlayerMessage();
        playerMessage.setPlayer(this);
        playerMessage.setRole(role);
        playerMessage.setContent(content);
        getPlayerMessages().add(playerMessage);
        return playerMessage;
    }

    public List<Message> getMessages() {
        return playerMessages
                .stream()
                .filter(message -> List.of("developer", "user", "assistant").contains(message.getRole()))
                .map(message -> new Message(message.getRole(), message.getContent()))
                .toList();
    }

}
