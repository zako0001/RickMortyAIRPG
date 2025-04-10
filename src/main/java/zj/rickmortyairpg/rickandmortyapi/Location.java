package zj.rickmortyairpg.rickandmortyapi;

import lombok.Data;

@Data
public class Location {

    private Short id;
    private String name;
    private String type;
    private String dimension;
    private String url;

    public Short getIdFromUrl() {
        return Short.parseShort(url.substring("https://rickandmortyapi.com/api/location/".length()));
    }
}
