package com.zenika.meeting_planner.Wrappers;

import com.zenika.meeting_planner.ReservationTypes.ReservationType;

public class ReservationWrapper {
    private static long generatedReservations=0; // ids are generated automatically
    private long id;
    //The starting hour of the Reservation for simplicity it's an int(Full Hours Only)
    private int startingHour;
    private ReservationType ReservationType;
    private int numberOfPeople;

    public ReservationWrapper(int startAt, ReservationType ReservationType, int numberOfPeople) {
        this.id=++generatedReservations; //we'll go with 1 based indexing
        this.startingHour = startAt;
        this.ReservationType = ReservationType;
        this.numberOfPeople = numberOfPeople;
    }



    public static long getGeneratedReservations() {
        return generatedReservations;
    }

    public static void setGeneratedReservations(long generatedReservations) {
        ReservationWrapper.generatedReservations = generatedReservations;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int getStartingHour() {
        return startingHour;
    }

    public void setStartingHour(int startingHour) {
        this.startingHour = startingHour;
    }

    public ReservationType getReservationType() {
        return ReservationType;
    }

    public void setReservationType(ReservationType ReservationType) {
        this.ReservationType = ReservationType;
    }

    public int getNumberOfPeople() {
        return numberOfPeople;
    }

    public void setNumberOfPeople(int numberOfPeople) {
        this.numberOfPeople = numberOfPeople;
    }
}
