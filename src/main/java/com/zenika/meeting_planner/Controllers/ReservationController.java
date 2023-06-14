package com.zenika.meeting_planner.Controllers;

import com.zenika.meeting_planner.DTO.NewReservation;
import com.zenika.meeting_planner.Entities.Reservation;
import com.zenika.meeting_planner.Response.Response;
import com.zenika.meeting_planner.Services.ReservationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("api/v1/reservations")
@RestController
public class ReservationController {
    private final ReservationService reservationService;

    @Autowired
    public ReservationController(ReservationService reservationService) {
        this.reservationService = reservationService;
    }

    @PostMapping
    public Response<Reservation> reserveSalle(@RequestBody NewReservation newReservation){
        return this.reservationService.reserveSalle(newReservation);
    }
    @GetMapping
    public List<Reservation> getReservations(){
        return this.reservationService.getReservations();
    }
}
