package zj.rickmortyairpg.persistance;

import org.springframework.data.jpa.repository.JpaRepository;

public interface PlayerGameCharacterRepository extends JpaRepository<PlayerGameCharacter, Long> {
}
