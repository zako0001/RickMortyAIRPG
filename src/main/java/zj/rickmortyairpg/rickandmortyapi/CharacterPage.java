package zj.rickmortyairpg.rickandmortyapi;

import lombok.Data;

import java.util.List;

@Data
public class CharacterPage {
    private List<Character> results;
}
