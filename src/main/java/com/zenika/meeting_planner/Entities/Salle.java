package com.zenika.meeting_planner.Entities;

import com.zenika.meeting_planner.Equipements.Equipement;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table
public class Salle {
    @Id
    @SequenceGenerator(
            name="salle_sequence",
            sequenceName = "salle_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "salle_sequence"
    )
    private Long id;
    private String name;
    private int capacity;

    @ElementCollection(targetClass = Equipement.class)
    @Enumerated(EnumType.STRING)
    private List<Equipement> availableEquipements= new ArrayList<>();
}
