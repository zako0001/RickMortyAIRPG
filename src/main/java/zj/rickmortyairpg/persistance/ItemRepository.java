package zj.rickmortyairpg.persistance;

import org.springframework.data.jpa.repository.JpaRepository;
import zj.rickmortyairpg.rickandmortyfandom.Item;

public interface ItemRepository extends JpaRepository<Item, Short> {
}
