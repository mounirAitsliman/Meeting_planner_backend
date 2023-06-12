package ma.ensa.meeting_planner;

import jakarta.persistence.Id;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SalleRepository extends JpaRepository<Salle, Id> {
}
