package zj.rickmortyairpg.rickandmortyapi;

import lombok.Data;

import java.util.List;

@Data
public class LocationPage {
    private Info info;
    private List<Location> results;
}
