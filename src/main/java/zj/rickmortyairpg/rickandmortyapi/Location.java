package zj.rickmortyairpg.rickandmortyapi;

import com.fasterxml.jackson.annotation.JsonSetter;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Location {

    @Id
    private Short id;
    private String name;
    private String type;
    private String dimension;

    @JsonSetter("name")
    public void setShortName(String name) {
        if (name.startsWith("Earth (") || name.endsWith(" Earth")) {
            this.name = "Earth";
        } else {
            this.name = name;
        }
    }

    @JsonSetter("dimension")
    public void setRealityDimension(String dimension) {
        if (dimension.equals("Replacement Dimension")) {
            this.dimension = "C-131";
        } else if (dimension.startsWith("Dimension ")) {
            this.dimension = dimension.substring(10);
        } else {
            this.dimension = dimension;
        }
    }

}
