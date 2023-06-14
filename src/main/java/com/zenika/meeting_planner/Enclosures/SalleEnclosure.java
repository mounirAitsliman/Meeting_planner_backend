package com.zenika.meeting_planner.Enclosures;

import com.zenika.meeting_planner.Equipements.Equipement;
import lombok.Data;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;
@Data
@Getter
public class SalleEnclosure {
    // The room capacity is set to 70% due to covid
    private static double fullRatio=0.7;
    // The room should be empty (to get cleaned) for one hour before its next use
    private static double cleaningDuration=1.0;
    // Edge hours are 8h (minReservationHour)
    private static int minReservationHour=8;
    //and 20h (maxReservationHour)
    private static int maxReservationHour=20;
    // A reservation has a duration of 1h
    private static int reservationDuration=1;
    private static long generatedSalles=0;
    // ids are generated and incremented automatically
    private long id;
    private String name;
    private int realCapacity;
    List<Equipement> availableEquipements;
    List<ReservationEnclosure> reservations=new ArrayList<>();
    public SalleEnclosure(String name, int realCapacity, List<Equipement> availableEquipements) {
        this.name = name;
        this.id=++generatedSalles;
        this.realCapacity = realCapacity;
        this.availableEquipements = availableEquipements;
        this.reservations= new ArrayList<>();
    }
    public SalleEnclosure(int realCapacity, List<Equipement> availableEquipements, List<ReservationEnclosure> reservations) {
        this.realCapacity = realCapacity;
        this.availableEquipements = availableEquipements;
        this.reservations = reservations;
    }
    public SalleEnclosure(int realCapacity, List<Equipement> availableEquipements) {
        this.realCapacity = realCapacity;
        this.availableEquipements = availableEquipements;
    }
    // Check if we can assign a room to a reservation or not(considering capacity and equipement)
    // This method should be private to demonstrate encapsulation but for the tests it's public
    //following the best practices a method must be declared with the minimum modificator possible
    public boolean hasSufficientCapacityAndEquipments(ReservationEnclosure reservation) {
        boolean hasSufficientCapacity = reservation.getNumberOfPeople() <= (fullRatio * this.realCapacity);

        if (hasSufficientCapacity) {
            //in order to reduce the complexity of our method we break down the no equipement salle special case first
            if (reservation.getReservationType().getMustHaveEquipements().isEmpty()) {
                // RS reservation requires a salle with 4 or more people
                return fullRatio * this.realCapacity >= 4;
                                                                                    }

            for (Equipement equipement : reservation.getReservationType().getMustHaveEquipements()) {
                if (!this.availableEquipements.contains(equipement)) {
                    return false;
                    // Required equipment is not available
                                                                    }
                                    }

            return true;
            // All required equipment is available
        }

        return false;
        // Room does not have sufficient capacity
    }



 //this methode checks if there is a an empty room to get booked or not (it return true or false)
    public boolean isReservationValid(ReservationEnclosure reservation) {
        int reservationDuration = SalleEnclosure.reservationDuration;

        // Check if the salle has sufficient capacity and required equipments for the reservation
        if (!hasSufficientCapacityAndEquipments(reservation)) {
            return false;
        }

        int reservationStartingHour = reservation.getStartingHour();
        int reservationEndingHour = reservationStartingHour + reservationDuration;

        // Check if the reservation is in range
        if (reservationStartingHour < minReservationHour || reservationEndingHour > maxReservationHour) {
            return false;
        }

        for (ReservationEnclosure existingReservation : reservations) {
            int existingReservationStartingHour = existingReservation.getStartingHour();
            int existingReservationEndingHour = existingReservationStartingHour + reservationDuration;

            //break down each case scenario to diminish complexity
            // Edge case: Not enough time before the first reservation
            if (existingReservationStartingHour == minReservationHour && reservationStartingHour < minReservationHour + reservationDuration + cleaningDuration) {
                return false;
            }

            // Edge case: Not enough time after the last reservation
            if (existingReservationEndingHour == maxReservationHour && reservationEndingHour > existingReservationStartingHour - cleaningDuration) {
                return false;
            }

            // Check for overlap with existing reservations
            boolean isNoOverlap = reservationEndingHour <= existingReservationStartingHour - cleaningDuration ||
                    reservationStartingHour >= existingReservationEndingHour + cleaningDuration;
            if (!isNoOverlap) {
                return false; // Overlapping reservation found
            }
        }
        return true; // No conflicts found, reservation is valid
    }
    public void addReservation(ReservationEnclosure reservationEnclosure){
        this.reservations.add(reservationEnclosure);
    }
}
