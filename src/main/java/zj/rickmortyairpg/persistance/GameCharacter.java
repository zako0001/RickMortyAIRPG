package zj.rickmortyairpg.persistance;

import jakarta.persistence.Entity;
import jakarta.persistence.PrePersist;
import lombok.Data;
import lombok.EqualsAndHashCode;
import zj.rickmortyairpg.rickandmortyapi.Character;

@EqualsAndHashCode(callSuper = true)
@Data
@Entity
public class GameCharacter extends Character {

    private Short maxHealth;

    @PrePersist
    public void setHealth() {
        if (getName().contains("Rick")) {
            maxHealth = (short) 1000;
        } else if (getSpecies().equals("Human") && getType().equals("")) {
            maxHealth = (short) 100;
        } else {
            maxHealth = (short) 200;
        }
    }

}
