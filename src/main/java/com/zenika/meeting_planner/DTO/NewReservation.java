package com.zenika.meeting_planner.DTO;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class NewReservation {
    private int startingHour;
    private ReservationTypeEnum reservationTypeEnum;
    private int numOfPeople;
    public int getStartingHour() {
        return startingHour;
    }

    public void setStartingHour(int startingHour) {
        this.startingHour = startingHour;
    }

    public ReservationTypeEnum getReservationTypeEnum() {
        return reservationTypeEnum;
    }

    public void setReservationTypeEnum(ReservationTypeEnum reservationTypeEnum) {
        this.reservationTypeEnum = reservationTypeEnum;
    }

    public int getNumOfPeople() {
        return numOfPeople;
    }

    public void setNumOfPeople(int numOfPeople) {
        this.numOfPeople = numOfPeople;
    }


}