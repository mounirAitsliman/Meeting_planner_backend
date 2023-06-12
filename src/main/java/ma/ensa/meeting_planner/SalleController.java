package ma.ensa.meeting_planner;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
@RestController
@RequestMapping("/salles")
public class SalleController {


        private final SalleService salleService;

        @Autowired
        public SalleController(SalleService salleService) {
            this.salleService = salleService;
        }

        @PostMapping
        public Salle ajouterSalle(@RequestBody Salle salle) {
            return salleService.ajouterSalle(salle);
        }

        // Define other endpoints for reservations, availability, etc.
    }

