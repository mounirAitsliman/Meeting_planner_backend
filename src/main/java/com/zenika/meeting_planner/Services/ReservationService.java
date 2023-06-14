package com.zenika.meeting_planner.Services;

import com.zenika.meeting_planner.DTO.NewReservation;
import com.zenika.meeting_planner.Entities.Reservation;
import com.zenika.meeting_planner.Entities.Salle;
import com.zenika.meeting_planner.Repositories.ReservationRepository;
import com.zenika.meeting_planner.Repositories.SalleRepository;
import com.zenika.meeting_planner.ReservationTypes.ReservationTypeBuilder;
import com.zenika.meeting_planner.Response.Response;
import com.zenika.meeting_planner.Enclosures.ReservationEnclosure;
import com.zenika.meeting_planner.Enclosures.SalleEnclosure;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ReservationService {
    private final ReservationRepository reservationRepository;
    private final SalleRepository salleRepository;

    @Autowired
    public ReservationService(ReservationRepository reservationRepository, SalleRepository salleRepository) {
        this.reservationRepository = reservationRepository;
        this.salleRepository = salleRepository;
    }
    public List<Reservation> getReservations(){
        return this.reservationRepository.findAll();
    }


    public Response<Reservation> reserveSalle(NewReservation newReservation) {
        // Find an available salle for the reservation
        Salle availableSalle = findSalleForReservation(newReservation);
        if (availableSalle == null) {
            return new Response<>(
                    true,
                    "Sorry, there are no available salles for " + newReservation.getReservationTypeEnum()
                            + " reservations at the moment. Please try again later."
            );
        }
        //Create the reservation
        Reservation reservation = Reservation.builder()
                .salle(availableSalle)
                .startingHour(newReservation.getStartingHour())
                .numberOfPeople(newReservation.getNumOfPeople())
                .reservationTypeEnum(newReservation.getReservationTypeEnum())
                .build();

        // Save the reservation
        reservationRepository.save(reservation);

        return new Response<>(
                "Your reservation for salle " + availableSalle.getName() +
                        " has been successfully made. We look forward to hosting you!",
                reservation
        );
    }

    // we loop through each salle and each reservation in the selected salle and we check if the ratio-capacity
    //is adequate with the number of ppl in the reservation and we select the salle with the minimGap value to optimize 
    //our resources
    private Salle findSalleForReservation(NewReservation reservation) {
        Salle selectedSalle = null;
        int minimumGap = Integer.MAX_VALUE;

        List<Salle> allSalles = salleRepository.findAll();
        List<Reservation> allReservations = reservationRepository.findAll();

        for (Salle salle : allSalles) {
            List<ReservationEnclosure> currentReservations = new ArrayList<>();

            for (Reservation existingReservation : allReservations) {
                if (existingReservation.getSalle().getId() == salle.getId()) {
                    currentReservations.add(new ReservationEnclosure(
                            existingReservation.getStartingHour(),
                            ReservationTypeBuilder.getReservationTypeFromReservationTypeEnum(existingReservation.getReservationTypeEnum()),
                            existingReservation.getNumberOfPeople()
                    ));
                }
            }

            SalleEnclosure salleEnclosure = new SalleEnclosure(
                    salle.getCapacity(),
                    salle.getAvailableEquipements(),
                    currentReservations
            );

            ReservationEnclosure newReservation = new ReservationEnclosure(
                    reservation.getStartingHour(),
                    ReservationTypeBuilder.getReservationTypeFromReservationTypeEnum(reservation.getReservationTypeEnum()),
                    reservation.getNumOfPeople()
            );

            if (salleEnclosure.isReservationValid(newReservation)) {
                int currentGap = (int) (salleEnclosure.getFullRatio() * salle.getCapacity()) - reservation.getNumOfPeople();
                if (currentGap < minimumGap) {
                    minimumGap = currentGap;
                    selectedSalle = salle;
                }
            }
        }

        return selectedSalle;
    }

}
