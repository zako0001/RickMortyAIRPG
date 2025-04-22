package zj.rickmortyairpg.persistance;

import org.springframework.data.jpa.repository.JpaRepository;

public interface GameCharacterRepository extends JpaRepository<GameCharacter, Short> {
}
