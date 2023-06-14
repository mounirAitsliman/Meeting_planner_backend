package com.zenika.meeting_planner.UnitTests.ReservationServiceTests;

import com.zenika.meeting_planner.DTO.NewReservation;
import com.zenika.meeting_planner.DTO.ReservationTypeEnum;
import com.zenika.meeting_planner.Entities.Reservation;
import com.zenika.meeting_planner.Entities.Salle;
import com.zenika.meeting_planner.Equipements.Equipement;
import com.zenika.meeting_planner.Repositories.ReservationRepository;
import com.zenika.meeting_planner.Repositories.SalleRepository;
import com.zenika.meeting_planner.Response.Response;
import com.zenika.meeting_planner.Services.ReservationService;
import com.zenika.meeting_planner.Wrappers.SalleWrapper;
import org.junit.jupiter.api.*;
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
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class ReservationServiceDependantTest {
    private static List<Salle> sallesList=new ArrayList<>();
    private List<Reservation> reservationList=new ArrayList<>();
    @Mock
    private ReservationRepository reservationRepository;
    @Mock
    private SalleRepository salleRepository;
    @InjectMocks
    private ReservationService underTestService;

    @BeforeAll
    static void beforeAll() {
        List<SalleWrapper> salles = Arrays.asList(
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
        for (SalleWrapper salle : salles) {
            Salle newSalle = Salle.builder()
                    .name(salle.getName())
                    .capacity(salle.getRealCapacity())
                    .availableEquipements(salle.getAvailableEquipements())
                    .build();
            sallesList.add(newSalle);
        }
    }

    @BeforeEach
    public void setUp() {
        when(salleRepository.findAll()).thenReturn(sallesList);
        when(reservationRepository.findAll()).thenReturn(reservationList);
    }

    // Now let's test the meetings we have in the use case(Monday Morning Reservations)
    @Test
    @Order(1)
    void itShouldMake8FirstReservationsFromUseCase() {
        // Testing from meeting 1 to 8 in the use case
        NewReservation newReservation1 = new NewReservation(9, ReservationTypeEnum.VC, 8);
        Response<Reservation> result1 = this.underTestService.reserveSalle(newReservation1);
        Reservation res1=result1.getData();
        res1.setSalle(sallesList.get(0)); // the algo is supposed to assign this
        // meeting to salle E1001 # 1, but with 0 based indexing it's # 0
        reservationList.add(res1);
        assertThat(result1.isError()).isFalse();

        NewReservation newReservation2 = new NewReservation(9, ReservationTypeEnum.VC, 6);
        Response<Reservation> result2 = this.underTestService.reserveSalle(newReservation2);
        assertThat(result2.isError()).isTrue(); // It failed cuz salle is already booked

        NewReservation newReservation3=new NewReservation(11, ReservationTypeEnum.RC, 4);
        Response<Reservation> result3=this.underTestService.reserveSalle(newReservation3);
        assertThat(result3.isError()).isTrue(); // No salle supporting Rc meetings

        NewReservation newReservation4=new NewReservation(11, ReservationTypeEnum.RS, 2);
        Response<Reservation> result4=this.underTestService.reserveSalle(newReservation4);
        Reservation res4=result4.getData();
        res4.setSalle(sallesList.get(2));
        reservationList.add(res4);
        assertThat(result4.isError()).isFalse();

        NewReservation newReservation5=new NewReservation(11, ReservationTypeEnum.SPEC, 9);
        Response<Reservation> result5=this.underTestService.reserveSalle(newReservation5);
        assertThat(result5.isError()).isTrue(); // No salle has the cap for 9 people supporting Spec meetings

        NewReservation newReservation6=new NewReservation(9, ReservationTypeEnum.RC, 7);
        Response<Reservation> result6=this.underTestService.reserveSalle(newReservation6);
        assertThat(result6.isError()).isTrue(); // No salle supports Rc meetings

        NewReservation newReservation7 = new NewReservation(8, ReservationTypeEnum.VC, 9);
        Response<Reservation> result7 = this.underTestService.reserveSalle(newReservation7);
        assertThat(result7.isError()).isTrue();

        NewReservation newReservation8 = new NewReservation(8, ReservationTypeEnum.SPEC, 10);
        Response<Reservation> result8 = this.underTestService.reserveSalle(newReservation8);
        assertThat(result8.isError()).isTrue(); // No salle has the cap for 10 people supporting Spec meetings

    }
}
