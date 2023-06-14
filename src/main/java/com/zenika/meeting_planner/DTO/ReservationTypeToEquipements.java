package com.zenika.meeting_planner.DTO;

import com.zenika.meeting_planner.Equipements.Equipement;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ReservationTypeToEquipements {
    public static Map<ReservationTypeEnum, List<Equipement>> reservationTypeToEquipements(){
        Map<ReservationTypeEnum, List<Equipement>> typeTypeToEquipements=new HashMap<>();
        typeTypeToEquipements.put(ReservationTypeEnum.VC, List.of(Equipement.PIEUVRE, Equipement.ECRAN, Equipement.WEBCAM));
        typeTypeToEquipements.put(ReservationTypeEnum.SPEC, List.of(Equipement.TABLEAU));
        typeTypeToEquipements.put(ReservationTypeEnum.RC, List.of(Equipement.PIEUVRE, Equipement.ECRAN, Equipement.TABLEAU));
        typeTypeToEquipements.put(ReservationTypeEnum.RS, List.of());
        return typeTypeToEquipements;
    }
}
