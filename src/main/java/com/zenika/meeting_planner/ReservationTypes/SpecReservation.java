package com.zenika.meeting_planner.ReservationTypes;

import com.zenika.meeting_planner.Equipements.Equipement;

import java.util.ArrayList;

public class SpecReservation implements ReservationType{
    private static ArrayList<Equipement> mustHaveEquipements=new ArrayList<>();
    {
        mustHaveEquipements.add(Equipement.TABLEAU);
    }

    public ArrayList<Equipement> getMustHaveEquipements() {
        return mustHaveEquipements;
    }
}
