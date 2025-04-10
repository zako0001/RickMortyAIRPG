package zj.rickmortyairpg.rickandmortyapi;

import lombok.Data;

@Data
public class Character {
    private short id;
    private String name;
    private String species;
    private String gender;
    private Location origin;
    private Location location;
}
