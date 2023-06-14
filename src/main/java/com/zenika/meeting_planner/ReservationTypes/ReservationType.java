package com.zenika.meeting_planner.ReservationTypes;

import com.zenika.meeting_planner.Equipements.Equipement;

import java.util.ArrayList;

public interface ReservationType {
    public ArrayList<Equipement> getMustHaveEquipements();
}
