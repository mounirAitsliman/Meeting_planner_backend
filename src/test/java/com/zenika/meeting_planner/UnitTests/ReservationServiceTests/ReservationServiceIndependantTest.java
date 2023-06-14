package com.zenika.meeting_planner.UnitTests.ReservationServiceTests;

import com.zenika.meeting_planner.DTO.NewReservation;
import com.zenika.meeting_planner.DTO.ReservationTypeEnum;
import com.zenika.meeting_planner.Entities.Reservation;
import com.zenika.meeting_planner.Entities.Salle;
import com.zenika.meeting_planner.Entities.Reservation;
import com.zenika.meeting_planner.Equipements.Equipement;
import com.zenika.meeting_planner.Repositories.ReservationRepository;
import com.zenika.meeting_planner.Repositories.SalleRepository;
import com.zenika.meeting_planner.Response.Response;
import com.zenika.meeting_planner.Services.ReservationService;
import com.zenika.meeting_planner.Wrappers.SalleWrapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ReservationServiceIndependantTest {
    private static List<Salle> sallesList;
    private List<Reservation> reservationList;
    @Mock
    private ReservationRepository reservationRepository;
    @Mock
    private SalleRepository roomRepository;
    @InjectMocks
    private ReservationService underTestService;

    @BeforeEach
    public void setUp() {
        List<SalleWrapper> rooms = Arrays.asList(
                new SalleWrapper("E1001", 23, Arrays.asList(Equipement.NEANT)),
                new SalleWrapper("E1002", 10, Arrays.asList(Equipement.ECRAN)),
                new SalleWrapper("E1003", 8, Arrays.asList(Equipement.PIEUVRE)),
                new SalleWrapper("E1004", 4, Arrays.asList(Equipement.TABLEAU)),
                new SalleWrapper("E2001", 4, Arrays.asList(Equipement.NEANT)),
                new SalleWrapper("E2002", 15, Arrays.asList(Equipement.ECRAN, Equipement.WEBCAM)),
                new SalleWrapper("E2003", 7, Arrays.asList(Equipement.NEANT)),
                new SalleWrapper("E2004", 9, Arrays.asList(Equipement.TABLEAU)),
                new SalleWrapper("E3001", 13, Arrays.asList(Equipement.ECRAN, Equipement.WEBCAM, Equipement.PIEUVRE)),
                new SalleWrapper("E3002", 8, Arrays.asList(Equipement.NEANT)),
                new SalleWrapper("E3003", 9, Arrays.asList(Equipement.ECRAN, Equipement.PIEUVRE)),
                new SalleWrapper("E3004", 4, Arrays.asList(Equipement.NEANT))
        );
        sallesList = new ArrayList<>();
        for (SalleWrapper room : rooms) {
            Salle newRoom = Salle.builder()
                    .name(room.getName())
                    .capacity(room.getRealCapacity())
                    .availableEquipements(room.getAvailableEquipements())
                    .build();
            sallesList.add(newRoom);
        }
        reservationList = new ArrayList<>();
        when(roomRepository.findAll()).thenReturn(sallesList);
        when(reservationRepository.findAll()).thenReturn(reservationList);
    }

    // Making a reservation at 2 in the morning
    @Test
    void makeLeftOutRangeReservation() {
        // Given
        NewReservation newReservation=new NewReservation(2, ReservationTypeEnum.RS, 8);
        // When
        Response<Reservation> result=this.underTestService.reserveSalle(newReservation);
        // Then
        assertThat(result.isError()).isTrue();
    }

    @Test
    void makeRightOutRangeReservation() {
        // Given
        NewReservation newReservation=new NewReservation(21, ReservationTypeEnum.RS, 8);
        // When
        Response<Reservation> result=this.underTestService.reserveSalle(newReservation);
        // Then
        assertThat(result.isError()).isTrue();
    }

    @Test
    void makeInRangeReservation() {
        // Given
        NewReservation newReservation=new NewReservation(8, ReservationTypeEnum.RS, 8);
        // When
        Response<Reservation> result=this.underTestService.reserveSalle(newReservation);
        // Then
        assertThat(result.isError()).isFalse();
    }

    // Now let's test the meetings we have in the use case(Monday Morning Reservations)

    @Test
    void makeMeeting1Reservation() {
        NewReservation newReservation1 = new NewReservation(9, ReservationTypeEnum.VC, 8);
        Response<Reservation> result1 = this.underTestService.reserveSalle(newReservation1);
        assertThat(result1.isError()).isFalse();
    }

    @Test
    void makeMeeting2Reservation() {
        NewReservation newReservation2 = new NewReservation(9, ReservationTypeEnum.VC, 6);
        Response<Reservation> result2 = this.underTestService.reserveSalle(newReservation2);
        assertThat(result2.isError()).isFalse();
    }

    @Test
    void makeMeeting3Reservation() {
        NewReservation newReservation3=new NewReservation(11, ReservationTypeEnum.RC, 4);
        Response<Reservation> result3=this.underTestService.reserveSalle(newReservation3);
        assertThat(result3.isError()).isTrue(); // No room supporting Rc meetings
    }

    @Test
    void makeMeeting4Reservation() {
        NewReservation newReservation4=new NewReservation(11, ReservationTypeEnum.RS, 2);
        Response<Reservation> result4=this.underTestService.reserveSalle(newReservation4);
        assertThat(result4.isError()).isFalse();
    }

    @Test
    void makeMeeting5Reservation() {
        NewReservation newReservation5=new NewReservation(11, ReservationTypeEnum.SPEC, 9);
        Response<Reservation> result5=this.underTestService.reserveSalle(newReservation5);
        assertThat(result5.isError()).isTrue(); // No room has the cap for 9 people supporting Spec meetings
    }

    @Test
    void makeMeeting6Reservation() {
        NewReservation newReservation6=new NewReservation(9, ReservationTypeEnum.RC, 7);
        Response<Reservation> result6=this.underTestService.reserveSalle(newReservation6);
        assertThat(result6.isError()).isTrue(); // No room supports Rc meetings
    }

    @Test
    void makeMeeting7Reservation() {
        NewReservation newReservation7 = new NewReservation(8, ReservationTypeEnum.VC, 9);
        Response<Reservation> result7 = this.underTestService.reserveSalle(newReservation7);
        assertThat(result7.isError()).isFalse();
    }

    @Test
    void makeMeeting8Reservation() {
        NewReservation newReservation8 = new NewReservation(8, ReservationTypeEnum.SPEC, 10);
        Response<Reservation> result8 = this.underTestService.reserveSalle(newReservation8);
        assertThat(result8.isError()).isTrue(); // No room has the cap for 10 people supporting Spec meetings
    }

    @Test
    void makeMeeting9Reservation() {
        NewReservation newReservation9 = new NewReservation(9, ReservationTypeEnum.SPEC, 5);
        Response<Reservation> result9 = this.underTestService.reserveSalle(newReservation9);
        assertThat(result9.isError()).isFalse();
    }

    @Test
    void makeMeeting10Reservation() {
        NewReservation newReservation10 = new NewReservation(9, ReservationTypeEnum.RS, 4);
        Response<Reservation> result10 = this.underTestService.reserveSalle(newReservation10);
        assertThat(result10.isError()).isFalse();
    }

    @Test
    void makeMeeting11Reservation() {
        NewReservation newReservation11=new NewReservation(9, ReservationTypeEnum.RC, 8);
        Response<Reservation> result11=this.underTestService.reserveSalle(newReservation11);
        assertThat(result11.isError()).isTrue(); // No room can support Rc meetings
    }

    @Test
    void makeMeeting12Reservation() {
        NewReservation newReservation12=new NewReservation(11, ReservationTypeEnum.VC, 12);
        Response<Reservation> result12=this.underTestService.reserveSalle(newReservation12);
        assertThat(result12.isError()).isTrue(); // 12 as num of people > 9 as max cap in room E001
    }

    @Test
    void makeMeeting13Reservation() {
        NewReservation newReservation13 = new NewReservation(11, ReservationTypeEnum.SPEC, 5);
        Response<Reservation> result13 = this.underTestService.reserveSalle(newReservation13);
        assertThat(result13.isError()).isFalse();
    }

    @Test
    void makeMeeting14Reservation() {
        NewReservation newReservation14 = new NewReservation(8, ReservationTypeEnum.VC, 3);
        Response<Reservation> result14 = this.underTestService.reserveSalle(newReservation14);
        assertThat(result14.isError()).isFalse();
    }

    @Test
    void makeMeeting15Reservation() {
        NewReservation newReservation15 = new NewReservation(8, ReservationTypeEnum.SPEC, 2);
        Response<Reservation> result15 = this.underTestService.reserveSalle(newReservation15);
        assertThat(result15.isError()).isFalse();
    }

    @Test
    void makeMeeting16Reservation() {
        NewReservation newReservation16 = new NewReservation(8, ReservationTypeEnum.VC, 12);
        Response<Reservation> result16 = this.underTestService.reserveSalle(newReservation16);
        assertThat(result16.isError()).isTrue(); // No room supporting Vc meetings with cap 12,
        // we got E3001 but with max cap as 70% of 13 which is 9 < 12
    }

    @Test
    void makeMeeting17Reservation() {
        NewReservation newReservation17 = new NewReservation(10, ReservationTypeEnum.VC, 6);
        Response<Reservation> result17 = this.underTestService.reserveSalle(newReservation17);
        assertThat(result17.isError()).isFalse();
    }

    @Test
    void makeMeeting18Reservation() {
        NewReservation newReservation18 = new NewReservation(11, ReservationTypeEnum.RS, 2);
        Response<Reservation> result18 = this.underTestService.reserveSalle(newReservation18);
        assertThat(result18.isError()).isFalse();
    }

    @Test
    void makeMeeting19Reservation() {
        NewReservation newReservation19=new NewReservation(9, ReservationTypeEnum.RS, 4);
        Response<Reservation> result19=this.underTestService.reserveSalle(newReservation19);
        assertThat(result19.isError()).isFalse();
    }

    @Test
    void makeMeeting20Reservation() {
        NewReservation newReservation20=new NewReservation(9, ReservationTypeEnum.RC, 7);
        Response<Reservation> result20=this.underTestService.reserveSalle(newReservation20);
        assertThat(result20.isError()).isTrue(); // No room supporting Rc meetings
    }
}
