package com.zenika.meeting_planner.Services;

import com.zenika.meeting_planner.Entities.Salle;
import com.zenika.meeting_planner.Equipements.Equipement;
import com.zenika.meeting_planner.Repositories.SalleRepository;
import com.zenika.meeting_planner.Enclosures.SalleEnclosure;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
@Service
public class SalleService {
    private final SalleRepository salleRepository;

    @Autowired
    public SalleService(SalleRepository salleRepository) {
        this.salleRepository = salleRepository;
    }

    public List<Salle> generateSalles() {
        //Generating Rooms
        List<SalleEnclosure> salles=Arrays.asList(
                new SalleEnclosure("E1001", 23, Arrays.asList(Equipement.NEANT)),
                new SalleEnclosure("E1002", 10, Arrays.asList(Equipement.ECRAN)),
                new SalleEnclosure("E1003", 8, Arrays.asList(Equipement.PIEUVRE)),
                new SalleEnclosure("E1004", 4, Arrays.asList(Equipement.TABLEAU)),

                new SalleEnclosure("E2001", 4, Arrays.asList(Equipement.NEANT)),
                new SalleEnclosure("E2002", 15, Arrays.asList(Equipement.ECRAN, Equipement.WEBCAM)),
                new SalleEnclosure("E2003", 7, Arrays.asList(Equipement.NEANT)),
                new SalleEnclosure("E2004", 9, Arrays.asList(Equipement.TABLEAU)),

                new SalleEnclosure("E3001", 13, Arrays.asList(Equipement.ECRAN, Equipement.WEBCAM, Equipement.PIEUVRE)),
                new SalleEnclosure("E3002", 8, Arrays.asList(Equipement.NEANT)),
                new SalleEnclosure("E3003", 9, Arrays.asList(Equipement.ECRAN, Equipement.PIEUVRE)),
                new SalleEnclosure("E3004", 4, Arrays.asList(Equipement.NEANT))
        );

        for (SalleEnclosure salle: salles) {
            Salle newSalle= Salle.builder().
                    name(salle.getName())
                    .capacity(salle.getRealCapacity())
                    .availableEquipements(salle.getAvailableEquipements())
                    .build();
            this.salleRepository.save(newSalle);
        };
        return this.salleRepository.findAll();
    }

    public List<Salle> getSalles() {
        return this.salleRepository.findAll();
    }
}
