package ma.ensa.meeting_planner;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
@Service
public class SalleService {

        private final SalleRepository salleRepository;

        @Autowired
        public SalleService(SalleRepository salleRepository) {
            this.salleRepository = salleRepository;
        }

        public Salle ajouterSalle(Salle salle) {
            // Implement the logic to add a salle and save it using salleRepository
            // You can add additional business logic here
            return salleRepository.save(salle);
        }

        // Implement other methods as required
    }

