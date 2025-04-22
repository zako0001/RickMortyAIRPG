package zj.rickmortyairpg.rickandmortyapi;

import com.fasterxml.jackson.annotation.JsonSetter;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Embeddable
public class SimpleLocation {

    private String name;
    private Short id;

    @JsonSetter("url")
    public void setUrl(String url) {
        if (url.isBlank()) {
            this.id = null;
        } else {
            this.id = Short.parseShort(url.substring(url.lastIndexOf('/') + 1));
        }
    }

    @JsonSetter("name")
    public void setShortName(String name) {
        if (name.equals("Earth (Replacement Dimension)")) {
            this.name = "Earth (C-131)";
        } else {
            this.name = name;
        }
    }

}
