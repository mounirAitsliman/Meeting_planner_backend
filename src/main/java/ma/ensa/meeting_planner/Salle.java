package ma.ensa.meeting_planner;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;

@Entity
@Data
@AllArgsConstructor
@Table

public class Salle {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nom;
    private int capacite;
    private String typeOutils;
    private int plage_reservation;


    public Salle() {

    }

    public Salle(String salleA, int i, String vc) {
    }
}
