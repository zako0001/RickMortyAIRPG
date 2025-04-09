package zj.rickmortyairpg.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import zj.rickmortyairpg.openaimodel.Chat;

public interface ChatRepository extends JpaRepository<Chat, Long> {
}
