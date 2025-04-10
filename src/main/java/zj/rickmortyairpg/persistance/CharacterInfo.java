package zj.rickmortyairpg.persistance;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class CharacterInfo {
    @Id
    private Short id;
    private Short maxHealth;
    private Short currentHealth;
    private Short currentLocationId;
}
