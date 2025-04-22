package zj.rickmortyairpg.rickandmortyfandom;

import lombok.Data;
import zj.rickmortyairpg.rickandmortyapi.SimpleLocation;

@Data
public class Item {
    private String name;
    private String type;
    private String purpose;
    private SimpleLocation origin;
}
