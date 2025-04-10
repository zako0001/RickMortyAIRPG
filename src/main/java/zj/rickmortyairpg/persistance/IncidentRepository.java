package zj.rickmortyairpg.persistance;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface IncidentRepository extends JpaRepository<Incident, Long> {
    List<Incident> findByUser_Username(String username);
}
