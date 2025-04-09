package zj.rickmortyairpg.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import zj.rickmortyairpg.openaimodel.Message;

public interface MessageRepository extends JpaRepository<Message, Long> {


}
