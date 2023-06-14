package com.zenika.meeting_planner.DTO;

import lombok.*;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class NewReservation {
    private int startingHour;
    private ReservationTypeEnum reservationTypeEnum;
    private int numOfPeople;
}