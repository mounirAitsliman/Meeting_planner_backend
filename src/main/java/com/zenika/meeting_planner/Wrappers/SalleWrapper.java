package com.zenika.meeting_planner.Wrappers;

import com.zenika.meeting_planner.Equipements.Equipement;

import java.util.ArrayList;
import java.util.List;

public class SalleWrapper {
    // In covid the room supports 70%(fullRation=0.7) of its real capacity
    // but now that covid is over we may want to change this ratio to 0.9(90%) or even 1(100%)
    private static double fullRatio=0.7;
    // The room should be empty for one hour before its next use
    // the value of cleaningDuration is 0.5 because if we have 0.5h before and after a reservation
    // we would have a total of an hour between two consecutive reservations
    private static double cleaningDuration=0.5;
    // A reservation could happen only between 8h-20h
    private static int minReservationHour=8;
    private static int maxReservationHour=20;
    // A reservation has a duration of 1h but in normal days(no covid) we may change this,
    // the code would still work
    private static int reservationDuration=1;
    private static long generatedSalles=0; // ids are generated automatically
    private long id;
    private String name;
    private int realCapacity;
    List<Equipement> availableEquipements;
    List<ReservationWrapper> reservations=new ArrayList<>();

    public SalleWrapper(String name, int realCapacity, List<Equipement> availableEquipements) {
        this.name = name;
        this.id=++generatedSalles;
        this.realCapacity = realCapacity;
        this.availableEquipements = availableEquipements;
        this.reservations= new ArrayList<>();
    }

    public SalleWrapper(int realCapacity, List<Equipement> availableEquipements, List<ReservationWrapper> reservations) {
        this.realCapacity = realCapacity;
        this.availableEquipements = availableEquipements;
        this.reservations = reservations;
    }

    public SalleWrapper(int realCapacity, List<Equipement> availableEquipements) {
        this.realCapacity = realCapacity;
        this.availableEquipements = availableEquipements;
    }

    // Check if we can assign a room to a reservation or not
    // This method should be private to demonstrate encapsulation but for the tests it's public


    public boolean hasSufficientCapacityAndEquipments(ReservationWrapper reservation) {
        boolean hasSufficientCapacity = reservation.getNumberOfPeople() <= (fullRatio * this.realCapacity);

        if (hasSufficientCapacity) {
            if (reservation.getReservationType().getMustHaveEquipements().isEmpty()) {
                // RS reservation requires a salle with 4 or more people
                return fullRatio * this.realCapacity >= 4;
            }

            for (Equipement equipement : reservation.getReservationType().getMustHaveEquipements()) {
                if (!this.availableEquipements.contains(equipement)) {
                    return false; // Required equipment is not available
                }
            }

            return true; // All required equipment is available
        }

        return false; // Room does not have sufficient capacity
    }



    // This method verifies if we can we make a reservation to a room by checking if the room is already
    // booked between [t1, t2], it returns true if the room is already booked for [t1, t2] or the
    // booking timeframe is out of the range [8h-20h] and returns false otherwise
    public boolean isReservationValid(ReservationWrapper reservation) {
        int reservationDuration = SalleWrapper.reservationDuration;

        // Check if the salle has sufficient capacity and required equipments for the reservation
        if (!hasSufficientCapacityAndEquipments(reservation)) {
            return false;
        }

        int reservationStartingHour = reservation.getStartingHour();
        int reservationEndingHour = reservationStartingHour + reservationDuration;

        // Check if the reservation falls within the valid range of working hours
        if (reservationStartingHour < minReservationHour || reservationEndingHour > maxReservationHour) {
            return false;
        }

        for (ReservationWrapper existingReservation : reservations) {
            int existingReservationStartingHour = existingReservation.getStartingHour();
            int existingReservationEndingHour = existingReservationStartingHour + reservationDuration;

            // Edge case: Not enough time before the first reservation
            if (existingReservationStartingHour == minReservationHour && reservationStartingHour < minReservationHour + reservationDuration + cleaningDuration) {
                return false;
            }

            // Edge case: Not enough time after the last reservation
            if (existingReservationEndingHour == maxReservationHour && reservationEndingHour > existingReservationStartingHour - cleaningDuration) {
                return false;
            }

            // Check for overlap with existing reservations
            boolean isNoOverlap = reservationEndingHour < existingReservationStartingHour - cleaningDuration ||
                    reservationStartingHour > existingReservationEndingHour + cleaningDuration;

            if (!isNoOverlap) {
                return false; // Overlapping reservation found
            }
        }

        return true; // No conflicts found, reservation is valid
    }




    public void addReservation(ReservationWrapper reservationWrapper){
        this.reservations.add(reservationWrapper);
    }

    public long getId() {
        return id;
    }

    public int getRealCapacity() {
        return realCapacity;
    }

    public List<Equipement> getAvailableEquipements() {
        return availableEquipements;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setRealCapacity(int realCapacity) {
        this.realCapacity = realCapacity;
    }

    public void setAvailableEquipements(List<Equipement> availableEquipements) {
        this.availableEquipements = availableEquipements;
    }

    public List<ReservationWrapper> getReservations() {
        return reservations;
    }

    public void setReservations(List<ReservationWrapper> reservations) {
        this.reservations = reservations;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public static double getFullRatio() {
        return fullRatio;
    }

    public static void setFullRatio(double fullRatio) {
        SalleWrapper.fullRatio = fullRatio;
    }
}
