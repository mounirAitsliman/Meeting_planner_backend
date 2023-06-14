package com.zenika.meeting_planner.Enclosures;

import com.zenika.meeting_planner.ReservationTypes.ReservationType;
import lombok.Data;

@Data
public class ReservationEnclosure {
    private static long generatedReservations=0;
    // ids are generated and incremented automatically
    private long id;
    //for simplicity concerns we considered the startingHour as an int
    // 8,9...
    private int startingHour;
    private ReservationType ReservationType;
    private int numberOfPeople;

    public ReservationEnclosure(int startAt, ReservationType ReservationType, int numberOfPeople) {
        this.id=++generatedReservations;
        //starting index 1
        this.startingHour = startAt;
        this.ReservationType = ReservationType;
        this.numberOfPeople = numberOfPeople;
    }
}
