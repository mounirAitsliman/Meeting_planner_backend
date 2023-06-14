package com.zenika.meeting_planner.Services;

import com.zenika.meeting_planner.Entities.Salle;
import com.zenika.meeting_planner.Equipements.Equipement;
import com.zenika.meeting_planner.Repositories.SalleRepository;
import com.zenika.meeting_planner.Wrappers.SalleWrapper;
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
        List<SalleWrapper> salles=Arrays.asList(
                new SalleWrapper("E1001", 23, Arrays.asList(Equipement.NEANT)),
                new SalleWrapper("E1002", 10, Arrays.asList(Equipement.ECRAN)),
                new SalleWrapper("E1003", 8, Arrays.asList(Equipement.PIEUVRE)),
                new SalleWrapper("E1004", 4, Arrays.asList(Equipement.TABLEAU)),

                new SalleWrapper("E2001", 4, Arrays.asList(Equipement.NEANT)),
                new SalleWrapper("E2002", 15, Arrays.asList(Equipement.ECRAN, Equipement.WEBCAM)),
                new SalleWrapper("E2003", 7, Arrays.asList(Equipement.NEANT)),
                new SalleWrapper("E2004", 9, Arrays.asList(Equipement.TABLEAU)),

                new SalleWrapper("E3001", 13, Arrays.asList(Equipement.ECRAN, Equipement.WEBCAM, Equipement.PIEUVRE)),
                new SalleWrapper("E3002", 8, Arrays.asList(Equipement.NEANT)),
                new SalleWrapper("E3003", 9, Arrays.asList(Equipement.ECRAN, Equipement.PIEUVRE)),
                new SalleWrapper("E3004", 4, Arrays.asList(Equipement.NEANT))
        );

        for (SalleWrapper salle: salles) {
            Salle newSalle= Salle.builder().
                    name(salle.getName())
                    .capacity(salle.getRealCapacity()).availableEquipements(salle.getAvailableEquipements())
                    .build();
            this.salleRepository.save(newSalle);
        };
        return this.salleRepository.findAll();
    }

    public List<Salle> getSalles() {
        return this.salleRepository.findAll();
    }
}
