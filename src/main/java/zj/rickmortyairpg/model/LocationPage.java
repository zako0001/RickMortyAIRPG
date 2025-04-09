package zj.rickmortyairpg.model;

import lombok.Data;

import java.util.List;

@Data
public class LocationPage {
    private List<Location> results;
}
