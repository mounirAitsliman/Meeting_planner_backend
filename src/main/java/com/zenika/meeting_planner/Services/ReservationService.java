package com.zenika.meeting_planner.Services;

import com.zenika.meeting_planner.DTO.NewReservation;
import com.zenika.meeting_planner.Entities.Reservation;
import com.zenika.meeting_planner.Entities.Salle;
import com.zenika.meeting_planner.Repositories.ReservationRepository;
import com.zenika.meeting_planner.Repositories.SalleRepository;
import com.zenika.meeting_planner.ReservationTypes.ReservationTypeBuilder;
import com.zenika.meeting_planner.Response.Response;
import com.zenika.meeting_planner.Wrappers.ReservationWrapper;
import com.zenika.meeting_planner.Wrappers.SalleWrapper;
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

        // Create the reservation
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

    // To find the appropriate salle we must minimize the gap between the actual number of people in a reservation
    // of the type X(VC, SPEC, RS, RC) and the real capacity of the salle that supports that exact reservation type
    private Salle findSalleForReservation(NewReservation reservation) {
        Salle selectedSalle = null;
        int minimumGap = Integer.MAX_VALUE;

        List<Salle> allSalles = salleRepository.findAll();
        List<Reservation> allReservations = reservationRepository.findAll();

        for (Salle salle : allSalles) {
            List<ReservationWrapper> currentReservations = new ArrayList<>();

            for (Reservation existingReservation : allReservations) {
                if (existingReservation.getSalle().getId() == salle.getId()) {
                    currentReservations.add(new ReservationWrapper(
                            existingReservation.getStartingHour(),
                            ReservationTypeBuilder.getReservationTypeFromReservationTypeEnum(existingReservation.getReservationTypeEnum()),
                            existingReservation.getNumberOfPeople()
                    ));
                }
            }

            SalleWrapper salleWrapper = new SalleWrapper(
                    salle.getCapacity(),
                    salle.getAvailableEquipements(),
                    currentReservations
            );

            ReservationWrapper newReservation = new ReservationWrapper(
                    reservation.getStartingHour(),
                    ReservationTypeBuilder.getReservationTypeFromReservationTypeEnum(reservation.getReservationTypeEnum()),
                    reservation.getNumOfPeople()
            );

            if (salleWrapper.isReservationValid(newReservation)) {
                int currentGap = (int) (salleWrapper.getFullRatio() * salle.getCapacity()) - reservation.getNumOfPeople();
                if (currentGap < minimumGap) {
                    minimumGap = currentGap;
                    selectedSalle = salle;
                }
            }
        }

        return selectedSalle;
    }

}
