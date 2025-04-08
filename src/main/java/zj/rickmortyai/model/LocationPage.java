package zj.rickmortyai.model;

import lombok.Data;

import java.util.List;

@Data
public class LocationPage {
    private List<Location> results;
}
