package zj.rickmortyairpg.model;

import lombok.Data;

import java.util.List;

@Data
public class CharacterPage {
    private List<Character> results;
}
