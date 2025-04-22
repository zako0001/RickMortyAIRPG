package zj.rickmortyairpg.persistance;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PlayerMessageRepository extends JpaRepository<PlayerMessage, Long> {
}
