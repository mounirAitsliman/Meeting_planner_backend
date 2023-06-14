package com.zenika.meeting_planner.ReservationTypes;

import com.zenika.meeting_planner.Equipements.Equipement;

import java.util.ArrayList;

public class RcReservation implements ReservationType{
    private static ArrayList<Equipement> mustHaveEquipements=new ArrayList<>();
    {
        mustHaveEquipements.add(Equipement.TABLEAU);
        mustHaveEquipements.add(Equipement.ECRAN);
        mustHaveEquipements.add(Equipement.PIEUVRE);
    }

    public ArrayList<Equipement> getMustHaveEquipements() {
        return mustHaveEquipements;
    }
}
