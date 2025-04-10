package zj.rickmortyairpg.persistance;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity
public class Incident {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne(optional = false)
    private User user;
    @OneToOne
    private CharacterInfo characterInfo;
    private String role;
    private String content;

    public Incident(String role, String content) {
        this.role = role;
        this.content = content;
    }
}
