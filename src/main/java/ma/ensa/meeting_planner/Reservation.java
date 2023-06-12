package ma.ensa.meeting_planner;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class Reservation {
    private int Id;
    private int creneau;
    private String typeReunion;
    private Salle salle;

}
