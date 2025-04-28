package zj.rickmortyairpg.rickandmortyapi;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Data
@MappedSuperclass
public class Character {

    @Id
    private Short id;
    private String name;
    private String status;
    private String species;
    private String type;
    private String gender;
    @Embedded
    @AttributeOverrides({
            @AttributeOverride( name = "name", column = @Column(name = "origin_name")),
            @AttributeOverride( name = "id", column = @Column(name = "origin_id"))
    })
    private SimpleLocation origin;
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @Embedded
    @AttributeOverrides({
            @AttributeOverride( name = "name", column = @Column(name = "location_name")),
            @AttributeOverride( name = "id", column = @Column(name = "location_id"))
    })
    private SimpleLocation location;
    private String image;

    public String getReference() {
        if (List.of((short) 1, (short) 2, (short) 3, (short) 4, (short) 5).contains(this.id)) {
            return "{" + this.id + "$" + this.name.substring(0, name.indexOf(" ")) + "}";
        } else {
            return "{" + this.id + "$" + this.name + "}";
        }
    }
}
