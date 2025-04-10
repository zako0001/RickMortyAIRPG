package zj.rickmortyairpg.persistance;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Data
@Entity
public class User {
    @Id
    private String username;
    @OneToOne(optional = false)
    private CharacterInfo characterInfo;
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Incident> incidents;
}
