package com.zenika.meeting_planner.UnitTests.WrapperTests;

import com.zenika.meeting_planner.Equipements.Equipement;
import com.zenika.meeting_planner.ReservationTypes.RcReservation;
import com.zenika.meeting_planner.ReservationTypes.RsReservation;
import com.zenika.meeting_planner.ReservationTypes.SpecReservation;
import com.zenika.meeting_planner.ReservationTypes.VcReservation;
import com.zenika.meeting_planner.Wrappers.ReservationWrapper;
import com.zenika.meeting_planner.Wrappers.SalleWrapper;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class SalleWrapperTest {
    @Test
    void itShouldAssignAppropriateNumberOfPeopleToSalle() {
        // 70% of 10(7) is <= than to 7
        SalleWrapper salleWrapper=new SalleWrapper(10, List.of(Equipement.TABLEAU, Equipement.ECRAN));
        ReservationWrapper reservationWrapper=new ReservationWrapper(9, new RsReservation(), 7);
        assertThat(salleWrapper.hasSufficientCapacityAndEquipments(reservationWrapper)).isTrue();
    }

    @Test
    void itShouldNotAssignInappropriateNumberOfPeopleToSalle() {
        // 8 is not less <= 70% of 10(7)
        SalleWrapper salleWrapper=new SalleWrapper(10, List.of(Equipement.TABLEAU, Equipement.ECRAN));
        ReservationWrapper reservationWrapper=new ReservationWrapper(9, new RsReservation(), 8);
        assertThat(salleWrapper.hasSufficientCapacityAndEquipments(reservationWrapper)).isFalse();
    }

    // Rs Reservations tests
    @Test
    void itShouldNotAssignRsReservationToSalle() {
        // 3 < 4
        SalleWrapper salleWrapper=new SalleWrapper(3, List.of(Equipement.TABLEAU, Equipement.ECRAN));
        ReservationWrapper reservationWrapper=new ReservationWrapper(9, new RsReservation(), 3);
        assertThat(salleWrapper.hasSufficientCapacityAndEquipments(reservationWrapper)).isFalse();
    }

    @Test
    void itShouldAssignRsReservationToSalle() {
        SalleWrapper salleWrapper=new SalleWrapper(10, List.of(Equipement.TABLEAU, Equipement.ECRAN));
        ReservationWrapper reservationWrapper=new ReservationWrapper(9, new RsReservation(), 3);
        assertThat(salleWrapper.hasSufficientCapacityAndEquipments(reservationWrapper)).isTrue();
    }

    // Rc Reservations tests
    @Test
    void itShouldAssignRcReservationToSalle() { // All Equipements and the num of people with capacity are good
        SalleWrapper salleWrapper=new SalleWrapper(10, List.of(Equipement.TABLEAU, Equipement.ECRAN, Equipement.PIEUVRE));
        ReservationWrapper reservationWrapper=new ReservationWrapper(9, new RcReservation(), 3);
        assertThat(salleWrapper.hasSufficientCapacityAndEquipments(reservationWrapper)).isTrue();
    }

    @Test
    void itShouldNotAssignRcReservationToSalle() { // No Board but num of people with capacity are good
        SalleWrapper salleWrapper=new SalleWrapper(10, List.of(Equipement.ECRAN, Equipement.PIEUVRE));
        ReservationWrapper reservationWrapper=new ReservationWrapper(9, new RcReservation(), 3);
        assertThat(salleWrapper.hasSufficientCapacityAndEquipments(reservationWrapper)).isFalse();
    }

    // Spec Reservations tests
    @Test
    void itShouldAssignSpecReservationToSalle() { // All Equipements(Only Needs A Board) and the num of people with capacity are good
        SalleWrapper salleWrapper=new SalleWrapper(10, List.of(Equipement.TABLEAU, Equipement.ECRAN));
        ReservationWrapper reservationWrapper=new ReservationWrapper(9, new SpecReservation(), 3);
        assertThat(salleWrapper.hasSufficientCapacityAndEquipments(reservationWrapper)).isTrue();
    }

    @Test
    void itShouldNotAssignSpecReservationToSalle() { // No Board but num of people with capacity are good
        SalleWrapper salleWrapper=new SalleWrapper(10, List.of(Equipement.ECRAN, Equipement.PIEUVRE));
        ReservationWrapper reservationWrapper=new ReservationWrapper(9, new SpecReservation(), 3);
        assertThat(salleWrapper.hasSufficientCapacityAndEquipments(reservationWrapper)).isFalse();
    }

    // Vc tests
    @Test
    void itShouldAssignVcReservationToSalle() { // All Equipements and the num of people with capacity are good
        SalleWrapper salleWrapper=new SalleWrapper(10, List.of(Equipement.WEBCAM, Equipement.ECRAN, Equipement.PIEUVRE));
        ReservationWrapper reservationWrapper=new ReservationWrapper(9, new VcReservation(), 3);
        assertThat(salleWrapper.hasSufficientCapacityAndEquipments(reservationWrapper)).isTrue();
    }

    @Test
    void itShouldNotAssignVcReservationToSalle() {
        // No Board but num of people with capacity are good
        SalleWrapper salleWrapper=new SalleWrapper(10, List.of(Equipement.ECRAN, Equipement.PIEUVRE));
        ReservationWrapper reservationWrapper=new ReservationWrapper(9, new VcReservation(), 3);
        assertThat(salleWrapper.hasSufficientCapacityAndEquipments(reservationWrapper)).isFalse();
    }

    // Now let's test the isReservationValid method
    @Test
    void itShouldNotAssignVcReservationToSalleOutOfRange_8h_20h() {
        // All Equipements and the num of people with capacity are good but 5AM in the morning is NOT in [8h-20h]
        SalleWrapper salleWrapper=new SalleWrapper(10, List.of(Equipement.WEBCAM, Equipement.ECRAN, Equipement.PIEUVRE));
        ReservationWrapper reservationWrapper=new ReservationWrapper(5, new VcReservation(), 3);
        assertThat(salleWrapper.isReservationValid(reservationWrapper)).isFalse();
    }

    @Test
    void itShouldAssignVcReservationToSalleInRange_8h_20h() {
        // All Equipements and the num of people with capacity are good but 5AM in the morning is NOT in [8h-20h]
        SalleWrapper salleWrapper=new SalleWrapper(10, List.of(Equipement.WEBCAM, Equipement.ECRAN, Equipement.PIEUVRE));
        ReservationWrapper reservationWrapper=new ReservationWrapper(8, new VcReservation(), 3);
        assertThat(salleWrapper.isReservationValid(reservationWrapper)).isTrue();
    }

    @Test
    void itShouldNotAssignVcReservationToSalleIfTheSalleIfOccupied() {
        // The Salle is already reserved
        SalleWrapper salleWrapper=new SalleWrapper(10, List.of(Equipement.WEBCAM, Equipement.ECRAN, Equipement.PIEUVRE));
        ReservationWrapper reservationWrapper=new ReservationWrapper(8, new VcReservation(), 3);
        salleWrapper.addReservation(reservationWrapper);
        ReservationWrapper reservationWrapper2=new ReservationWrapper(8, new VcReservation(), 3);
        assertThat(salleWrapper.isReservationValid(reservationWrapper2)).isFalse();
    }

    @Test
    void itShouldNotAssignVcReservationToSalleIfTheSalleIfWeAreCleaningIt() {
        // The Salle should be empty till 10 so it's not available at 9
        SalleWrapper salleWrapper=new SalleWrapper(10, List.of(Equipement.WEBCAM, Equipement.ECRAN, Equipement.PIEUVRE));
        ReservationWrapper reservationWrapper=new ReservationWrapper(8, new VcReservation(), 3);
        salleWrapper.addReservation(reservationWrapper);
        ReservationWrapper reservationWrapper2=new ReservationWrapper(9, new VcReservation(), 3);
        assertThat(salleWrapper.isReservationValid(reservationWrapper2)).isFalse();
    }

    @Test
    void itShouldAssignVcReservationToSalleIfNoOverlappingInTime() {
        // No overlapping in time of reservations
        SalleWrapper salleWrapper=new SalleWrapper(10, List.of(Equipement.WEBCAM, Equipement.ECRAN, Equipement.PIEUVRE));
        ReservationWrapper reservationWrapper=new ReservationWrapper(8, new VcReservation(), 3);
        salleWrapper.addReservation(reservationWrapper);
        ReservationWrapper reservationWrapper2=new ReservationWrapper(10, new VcReservation(), 3);
        assertThat(salleWrapper.isReservationValid(reservationWrapper2)).isTrue();
    }

    @Test
    void itShouldNotAssignVcReservationToSalleInEdgeHour() {
        // Reservation from 18h-19h so Salle would be empty till 20h
        SalleWrapper salleWrapper=new SalleWrapper(10, List.of(Equipement.WEBCAM, Equipement.ECRAN, Equipement.PIEUVRE));
        ReservationWrapper reservationWrapper=new ReservationWrapper(18, new VcReservation(), 3);
        salleWrapper.addReservation(reservationWrapper);
        ReservationWrapper reservationWrapper2=new ReservationWrapper(19, new VcReservation(), 3);
        assertThat(salleWrapper.isReservationValid(reservationWrapper2)).isFalse();
    }

    @Test
    void itShouldAssignVcReservationToSalleAtLastHour() {
        //Reservation in the last hour
        SalleWrapper salleWrapper=new SalleWrapper(10, List.of(Equipement.WEBCAM, Equipement.ECRAN, Equipement.PIEUVRE));
        ReservationWrapper reservationWrapper=new ReservationWrapper(19, new VcReservation(), 3);
        assertThat(salleWrapper.isReservationValid(reservationWrapper)).isTrue();
    }

    @Test
    void itShouldNotAssignVcReservationToSalleAtClosingTime_20h() {
        // Reservation when closing at 20h
        SalleWrapper salleWrapper=new SalleWrapper(10, List.of(Equipement.WEBCAM, Equipement.ECRAN, Equipement.PIEUVRE));
        ReservationWrapper reservationWrapper=new ReservationWrapper(20, new VcReservation(), 3);
        assertThat(salleWrapper.isReservationValid(reservationWrapper)).isFalse();
    }
}