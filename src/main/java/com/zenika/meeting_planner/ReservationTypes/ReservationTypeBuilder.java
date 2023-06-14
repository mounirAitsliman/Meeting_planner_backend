package com.zenika.meeting_planner.ReservationTypes;

import com.zenika.meeting_planner.DTO.ReservationTypeEnum;

public class ReservationTypeBuilder {
    public static ReservationType getReservationTypeFromReservationTypeEnum(ReservationTypeEnum reservationTypeEnum){
        if(reservationTypeEnum== ReservationTypeEnum.VC)return new VcReservation();
        if(reservationTypeEnum==ReservationTypeEnum.RS)return new RsReservation();
        if(reservationTypeEnum==ReservationTypeEnum.SPEC)return new SpecReservation();
        return new RcReservation();
    }
}
