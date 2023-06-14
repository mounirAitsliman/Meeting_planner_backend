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
import com.zenika.meeting_planner.Enclosures.SalleEnclosure;
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
        List<SalleEnclosure> salles = Arrays.asList(
                new SalleEnclosure("E1001", 23, Arrays.asList(Equipement.NEANT)),
                new SalleEnclosure("E1002", 10, Arrays.asList(Equipement.ECRAN)),
                new SalleEnclosure("E1003", 8, Arrays.asList(Equipement.PIEUVRE)),
                new SalleEnclosure("E1004", 4, Arrays.asList(Equipement.TABLEAU)),
                new SalleEnclosure("E2001", 4, Arrays.asList(Equipement.NEANT)),
                new SalleEnclosure("E2002", 15, Arrays.asList(Equipement.ECRAN, Equipement.WEBCAM)),
                new SalleEnclosure("E2003", 7, Arrays.asList(Equipement.NEANT)),
                new SalleEnclosure("E2004", 9, Arrays.asList(Equipement.TABLEAU)),
                new SalleEnclosure("E3001", 13, Arrays.asList(Equipement.ECRAN, Equipement.WEBCAM, Equipement.PIEUVRE)),
                new SalleEnclosure("E3002", 8, Arrays.asList(Equipement.NEANT)),
                new SalleEnclosure("E3003", 9, Arrays.asList(Equipement.ECRAN, Equipement.PIEUVRE)),
                new SalleEnclosure("E3004", 4, Arrays.asList(Equipement.NEANT))
        );
        sallesList = new ArrayList<>();
        for (SalleEnclosure salle : salles) {
            Salle newSalle = Salle.builder()
                    .name(salle.getName())
                    .capacity(salle.getRealCapacity())
                    .availableEquipements(salle.getAvailableEquipements())
                    .build();
            //Salle newSalle = new Salle(salle.getName(),salle.getRealCapacity(),salle.getAvailableEquipements());
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
    @DisplayName("monday use case test 1-8")
    void reserveSalleFirstEightReservations() {

        NewReservation newReservationOne = new NewReservation(9, ReservationTypeEnum.VC, 8);
        Response<Reservation> resultOne = this.underTestService.reserveSalle(newReservationOne);
        Reservation res1=resultOne.getData();
        res1.setSalle(sallesList.get(0));
        reservationList.add(res1);
        assertThat(resultOne.isError()).isFalse();

        NewReservation newReservationTwo = new NewReservation(9, ReservationTypeEnum.VC, 6);
        Response<Reservation> resultTwo = this.underTestService.reserveSalle(newReservationTwo);
        assertThat(resultTwo.isError()).isTrue(); // It failed cuz salle is already booked

        NewReservation newReservationThree=new NewReservation(11, ReservationTypeEnum.RC, 4);
        Response<Reservation> resultThree=this.underTestService.reserveSalle(newReservationThree);
        assertThat(resultThree.isError()).isTrue();


        NewReservation newReservationFour=new NewReservation(11, ReservationTypeEnum.RS, 2);
        Response<Reservation> resultFour=this.underTestService.reserveSalle(newReservationFour);
        Reservation resFour=resultFour.getData();
        resFour.setSalle(sallesList.get(2));
        reservationList.add(resFour);
        assertThat(resultFour.isError()).isFalse();

        NewReservation newReservationFive=new NewReservation(11, ReservationTypeEnum.SPEC, 9);
        Response<Reservation> resultFive=this.underTestService.reserveSalle(newReservationFive);
        assertThat(resultFive.isError()).isTrue();
        // No salle has the cota-capacity for 9 people supporting Spec meetings

        NewReservation newReservationSix=new NewReservation(9, ReservationTypeEnum.RC, 7);
        Response<Reservation> resultSix=this.underTestService.reserveSalle(newReservationSix);
        assertThat(resultSix.isError()).isTrue();
        // No salle with adequat equipement supporting Rc meetings

        NewReservation newReservationSeven = new NewReservation(8, ReservationTypeEnum.VC, 9);
        Response<Reservation> resultSeven = this.underTestService.reserveSalle(newReservationSeven);
        assertThat(resultSeven.isError()).isTrue();

        NewReservation newReservationEight = new NewReservation(8, ReservationTypeEnum.SPEC, 10);
        Response<Reservation> resultEight = this.underTestService.reserveSalle(newReservationEight);
        assertThat(resultEight.isError()).isTrue();
        // No salle has the cota-capacity for 10 people supporting Spec meetings

    }
}
