package zj.rickmortyairpg.persistance;

import org.springframework.data.jpa.repository.JpaRepository;
import zj.rickmortyairpg.rickandmortyapi.Location;

public interface LocationRepository extends JpaRepository<Location, Short> {
}
