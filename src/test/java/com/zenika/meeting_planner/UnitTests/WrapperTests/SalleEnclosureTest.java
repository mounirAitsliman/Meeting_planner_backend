package com.zenika.meeting_planner.UnitTests.WrapperTests;

import com.zenika.meeting_planner.Equipements.Equipement;
import com.zenika.meeting_planner.ReservationTypes.RcReservation;
import com.zenika.meeting_planner.ReservationTypes.RsReservation;
import com.zenika.meeting_planner.ReservationTypes.SpecReservation;
import com.zenika.meeting_planner.ReservationTypes.VcReservation;
import com.zenika.meeting_planner.Enclosures.ReservationEnclosure;
import com.zenika.meeting_planner.Enclosures.SalleEnclosure;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class SalleEnclosureTest {
    @Test
    @DisplayName("Must Assign Adequate Number Of People To Salle")
    void mustAssignAdequateNumberOfPeopleToSalle() {
        // 70% of 10(7) is <= than to 7
        SalleEnclosure salleEnclosure =new SalleEnclosure(10, List.of(Equipement.TABLEAU, Equipement.ECRAN));
        ReservationEnclosure reservationEnclosure =new ReservationEnclosure(9, new RsReservation(), 7);
        assertThat(salleEnclosure.hasSufficientCapacityAndEquipments(reservationEnclosure)).isTrue();
    }

    @Test
    @DisplayName("Must Assign Inadequate Number Of People To Salle")
    void mustNotAssignInadequateNumberOfPeopleToSalle() {
        // 8 is not less <= 70% of 10(7)
        SalleEnclosure salleEnclosure =new SalleEnclosure(10, List.of(Equipement.TABLEAU, Equipement.ECRAN));
        ReservationEnclosure reservationEnclosure =new ReservationEnclosure(9, new RsReservation(), 8);
        assertThat(salleEnclosure.hasSufficientCapacityAndEquipments(reservationEnclosure)).isFalse();
    }

    // Rs Reservations tests
    @Test
    @DisplayName("Must Not Assign RsReservation To Salle due to capacity")
    void mustNotAssignRsReservationToSalle() {
        SalleEnclosure salleEnclosure =new SalleEnclosure(3, List.of(Equipement.TABLEAU, Equipement.ECRAN));
        ReservationEnclosure reservationEnclosure =new ReservationEnclosure(9, new RsReservation(), 3);
        assertThat(salleEnclosure.hasSufficientCapacityAndEquipments(reservationEnclosure)).isFalse();
        // 3 < 4
    }

    @Test
    @DisplayName("Must Assign RsReservation To Salle due to adequate capacity")
    void mustAssignRsReservationToSalle() {
        SalleEnclosure salleEnclosure =new SalleEnclosure(10, List.of(Equipement.TABLEAU, Equipement.ECRAN));
        ReservationEnclosure reservationEnclosure =new ReservationEnclosure(9, new RsReservation(), 3);
        assertThat(salleEnclosure.hasSufficientCapacityAndEquipments(reservationEnclosure)).isTrue();
    }

    // Rc Reservations tests
    @Test
    @DisplayName("Must Assign RcReservation To Salle  ")
    void mustAssignRcReservationToSalle() {
        // All requirements are met (equipement and capacity)
        SalleEnclosure salleEnclosure =new SalleEnclosure(10, List.of(Equipement.TABLEAU, Equipement.ECRAN, Equipement.PIEUVRE));
        ReservationEnclosure reservationEnclosure =new ReservationEnclosure(9, new RcReservation(), 3);
        assertThat(salleEnclosure.hasSufficientCapacityAndEquipments(reservationEnclosure)).isTrue();
    }

    @Test
    @DisplayName("must Not Assign RcReservation To Salle due to inadequate equipement ")
    void mustNotAssignRcReservationToSalle() {
        // capacity requirement met but no it lacks board in equipement rooms
        SalleEnclosure salleEnclosure =new SalleEnclosure(10, List.of(Equipement.ECRAN, Equipement.PIEUVRE));
        ReservationEnclosure reservationEnclosure =new ReservationEnclosure(9, new RcReservation(), 3);
        assertThat(salleEnclosure.hasSufficientCapacityAndEquipments(reservationEnclosure)).isFalse();
    }

    // Spec Reservations tests
    @Test
    @DisplayName("must Assign SpecReservation To Salle")
    void mustAssignSpecReservationToSalle() {
        // All Equipements(Only Needs A Board) and the num of people with capacity are good
        SalleEnclosure salleEnclosure =new SalleEnclosure(10, List.of(Equipement.TABLEAU, Equipement.ECRAN));
        ReservationEnclosure reservationEnclosure =new ReservationEnclosure(9, new SpecReservation(), 3);
        assertThat(salleEnclosure.hasSufficientCapacityAndEquipments(reservationEnclosure)).isTrue();
    }

    @Test
    @DisplayName("must Not Assign SpecReservation To Salle")
    void mustNotAssignSpecReservationToSalle() { // No Board but num of people with capacity are good
        SalleEnclosure salleEnclosure =new SalleEnclosure(10, List.of(Equipement.ECRAN, Equipement.PIEUVRE));
        ReservationEnclosure reservationEnclosure =new ReservationEnclosure(9, new SpecReservation(), 3);
        assertThat(salleEnclosure.hasSufficientCapacityAndEquipments(reservationEnclosure)).isFalse();
    }

    // Vc tests
    @Test
    @DisplayName("must Assign VcReservation To Salle")
    void mustAssignVcReservationToSalle() { // All Equipements and the num of people with capacity are good
        SalleEnclosure salleEnclosure =new SalleEnclosure(10, List.of(Equipement.WEBCAM, Equipement.ECRAN, Equipement.PIEUVRE));
        ReservationEnclosure reservationEnclosure =new ReservationEnclosure(9, new VcReservation(), 3);
        assertThat(salleEnclosure.hasSufficientCapacityAndEquipments(reservationEnclosure)).isTrue();
    }

    @Test
    @DisplayName("must Not Assign VcReservation To Salle")
    void mustNotAssignVcReservationToSalle() {
        // No Board but num of people with capacity are good
        SalleEnclosure salleEnclosure =new SalleEnclosure(10, List.of(Equipement.ECRAN, Equipement.PIEUVRE));
        ReservationEnclosure reservationEnclosure =new ReservationEnclosure(9, new VcReservation(), 3);
        assertThat(salleEnclosure.hasSufficientCapacityAndEquipments(reservationEnclosure)).isFalse();
    }

    // Now let's test the isReservationValid method
    @Test
    @DisplayName("must Not Assign VcReservation To Salle OutOfRange_8h_20h")
    void mustNotAssignVcReservationToSalleOutOfRange_8h_20h() {
        // All Equipements and the num of people with capacity are good but 5AM in the morning is NOT in [8h-20h]
        SalleEnclosure salleEnclosure =new SalleEnclosure(10, List.of(Equipement.WEBCAM, Equipement.ECRAN, Equipement.PIEUVRE));
        ReservationEnclosure reservationEnclosure =new ReservationEnclosure(5, new VcReservation(), 3);
        assertThat(salleEnclosure.isReservationValid(reservationEnclosure)).isFalse();
    }

    @Test
    @DisplayName("must Assign VcReservation To Salle In Range _8h_20h")
    void mustAssignVcReservationToSalleInRange_8h_20h() {
        // All Equipements and the num of people with capacity are good but 5AM in the morning is NOT in [8h-20h]
        SalleEnclosure salleEnclosure =new SalleEnclosure(10, List.of(Equipement.WEBCAM, Equipement.ECRAN, Equipement.PIEUVRE));
        ReservationEnclosure reservationEnclosure =new ReservationEnclosure(8, new VcReservation(), 3);
        assertThat(salleEnclosure.isReservationValid(reservationEnclosure)).isTrue();
    }

    @Test
    @DisplayName("must Not Assign VcReservation To Salle If The Salle Is Occupied")
    void itShouldNotAssignVcReservationToSalleIfTheSalleIsOccupied() {
        // The Salle is already reserved
        SalleEnclosure salleEnclosure =new SalleEnclosure(10, List.of(Equipement.WEBCAM, Equipement.ECRAN, Equipement.PIEUVRE));
        ReservationEnclosure reservationEnclosure =new ReservationEnclosure(8, new VcReservation(), 3);
        salleEnclosure.addReservation(reservationEnclosure);
        ReservationEnclosure reservationEnclosure2 =new ReservationEnclosure(8, new VcReservation(), 3);
        assertThat(salleEnclosure.isReservationValid(reservationEnclosure2)).isFalse();
    }

    @Test
    @DisplayName("must Not Assign VcReservation T oSalle If The Salle If We Are Cleaning It")
    void mustNotAssignVcReservationToSalleIfTheSalleIfWeAreCleaningIt() {
        // The Salle should be empty till 10 so it's not available at 9
        SalleEnclosure salleEnclosure =new SalleEnclosure(10, List.of(Equipement.WEBCAM, Equipement.ECRAN, Equipement.PIEUVRE));
        ReservationEnclosure reservationEnclosure =new ReservationEnclosure(8, new VcReservation(), 3);
        salleEnclosure.addReservation(reservationEnclosure);
        ReservationEnclosure reservationEnclosure2 =new ReservationEnclosure(9, new VcReservation(), 3);
        assertThat(salleEnclosure.isReservationValid(reservationEnclosure2)).isFalse();
    }

    @Test
    @DisplayName("must Assign VcReservation To Salle If No Overlapping In Time")
    void mustAssignVcReservationToSalleIfNoOverlappingInTime() {
        // No overlapping in time of reservations
        SalleEnclosure salleEnclosure =new SalleEnclosure(10, List.of(Equipement.WEBCAM, Equipement.ECRAN, Equipement.PIEUVRE));
        ReservationEnclosure reservationEnclosure =new ReservationEnclosure(8, new VcReservation(), 3);
        salleEnclosure.addReservation(reservationEnclosure);
        ReservationEnclosure reservationEnclosure2 =new ReservationEnclosure(10, new VcReservation(), 3);
        assertThat(salleEnclosure.isReservationValid(reservationEnclosure2)).isTrue();
    }

    @Test
    @DisplayName("must Not Assign VcReservation To Salle In Edge Hour")
    void mustNotAssignVcReservationToSalleInEdgeHour() {
        // Reservation from 18h-19h so Salle would be empty till 20h
        SalleEnclosure salleEnclosure =new SalleEnclosure(10, List.of(Equipement.WEBCAM, Equipement.ECRAN, Equipement.PIEUVRE));
        ReservationEnclosure reservationEnclosure =new ReservationEnclosure(18, new VcReservation(), 3);
        salleEnclosure.addReservation(reservationEnclosure);
        ReservationEnclosure reservationEnclosure2 =new ReservationEnclosure(19, new VcReservation(), 3);
        assertThat(salleEnclosure.isReservationValid(reservationEnclosure2)).isFalse();
    }

    @Test
    @DisplayName("must Assign VcReservation To Salle At LastHour")
    void mustAssignVcReservationToSalleAtLastHour() {
        //Reservation in the last hour
        SalleEnclosure salleEnclosure =new SalleEnclosure(10, List.of(Equipement.WEBCAM, Equipement.ECRAN, Equipement.PIEUVRE));
        ReservationEnclosure reservationEnclosure =new ReservationEnclosure(19, new VcReservation(), 3);
        assertThat(salleEnclosure.isReservationValid(reservationEnclosure)).isTrue();
    }

    @Test
    @DisplayName("must Not Assign VcReservation To Salle At ClosingTime_20h")
    void mustNotAssignVcReservationToSalleAtClosingTime_20h() {
        // Reservation when closing at 20h
        SalleEnclosure salleEnclosure =new SalleEnclosure(10, List.of(Equipement.WEBCAM, Equipement.ECRAN, Equipement.PIEUVRE));
        ReservationEnclosure reservationEnclosure =new ReservationEnclosure(20, new VcReservation(), 3);
        assertThat(salleEnclosure.isReservationValid(reservationEnclosure)).isFalse();
    }
}