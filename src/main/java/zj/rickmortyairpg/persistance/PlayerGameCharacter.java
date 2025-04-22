package zj.rickmortyairpg.persistance;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonUnwrapped;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import zj.rickmortyairpg.rickandmortyapi.SimpleLocation;

@Data
@NoArgsConstructor
@Entity
public class PlayerGameCharacter {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long updateId;
    @JsonIgnore
    @ManyToOne(optional = false)
    private Player player;
    @JsonUnwrapped
    @ManyToOne(optional = false)
    private GameCharacter gameCharacter;
    private Short currentHealth;
    @JsonProperty("location")
    @Embedded
    @AttributeOverrides({
            @AttributeOverride( name = "name", column = @Column(name = "current_location_name")),
            @AttributeOverride( name = "id", column = @Column(name = "current_location_id"))
    })
    private SimpleLocation currentLocation;

    public PlayerGameCharacter(Player player, GameCharacter gameCharacter) {
        setPlayer(player);
        setGameCharacter(gameCharacter);
        setCurrentHealth(gameCharacter.getMaxHealth());
        setCurrentLocation(gameCharacter.getLocation());
    }

}

























