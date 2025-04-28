package zj.rickmortyairpg.persistance;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
public class PlayerMessage {
    @JsonIgnore
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @JsonIgnore
    @ManyToOne(optional = false)
    private Player player;
    @JsonIgnore
    @Column(nullable = false)
    private String role;
    @Column(nullable = false, columnDefinition = "VARCHAR(2048)")
    private String content;
}
