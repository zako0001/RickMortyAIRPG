package zj.rickmortyairpg.rickandmortyfandom;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import zj.rickmortyairpg.rickandmortyapi.SimpleLocation;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
@Entity
@NoArgsConstructor
public class Item {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Short id;
    private String name;
    private String image;
    private String type;
    private String purpose;
    @Embedded
    @AttributeOverrides({
            @AttributeOverride( name = "name", column = @Column(name = "location_name")),
            @AttributeOverride( name = "id", column = @Column(name = "location_id"))
    })
    private SimpleLocation origin;

    public Item(String name, String image) {
        this.name = name;
        this.image = image;
    }
}
