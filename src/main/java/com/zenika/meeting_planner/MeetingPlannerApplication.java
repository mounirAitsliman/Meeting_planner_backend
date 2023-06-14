package com.zenika.meeting_planner;

import com.zenika.meeting_planner.Entities.Reservation;
import com.zenika.meeting_planner.Entities.Salle;
import com.zenika.meeting_planner.Equipements.Equipement;
import com.zenika.meeting_planner.ReservationTypes.RcReservation;
import com.zenika.meeting_planner.ReservationTypes.RsReservation;
import com.zenika.meeting_planner.ReservationTypes.SpecReservation;
import com.zenika.meeting_planner.ReservationTypes.VcReservation;
import com.zenika.meeting_planner.Wrappers.ReservationWrapper;
import com.zenika.meeting_planner.Wrappers.SalleWrapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

@SpringBootApplication
public class MeetingPlannerApplication {

	public static void main(String[] args) {
		SpringApplication.run(MeetingPlannerApplication.class, args);
	}

//	public static void testingAlgo() {
//		//Generating salles
//		List<SalleWrapper> salles=new ArrayList<>();
//		salles.add(new SalleWrapper("E1001", 23, Arrays.asList(Equipement.NEANT)));
//		salles.add(new SalleWrapper("E1002", 10, Arrays.asList(Equipement.ECRAN)));
//		salles.add(new SalleWrapper("E1003", 8, Arrays.asList(Equipement.PIEUVRE)));
//		salles.add(new SalleWrapper("E1004", 4, Arrays.asList(Equipement.TABLEAU)));
//
//		salles.add(new SalleWrapper("E2001", 4, Arrays.asList(Equipement.NEANT)));
//		salles.add(new SalleWrapper("E2002", 15, Arrays.asList(Equipement.ECRAN, Equipement.WEBCAM)));
//		salles.add(new SalleWrapper("E2003", 7, Arrays.asList(Equipement.NEANT)));
//		salles.add(new SalleWrapper("E2004", 9, Arrays.asList(Equipement.TABLEAU)));
//
//		salles.add(new SalleWrapper("E3001", 13, Arrays.asList(Equipement.ECRAN, Equipement.WEBCAM, Equipement.PIEUVRE)));
//		salles.add(new SalleWrapper("E3002", 8, Arrays.asList(Equipement.NEANT)));
//		salles.add(new SalleWrapper("E3003", 9, Arrays.asList(Equipement.ECRAN, Equipement.PIEUVRE)));
//		salles.add(new SalleWrapper("E3004", 4, Arrays.asList(Equipement.NEANT)));
//
//		//Generating reservations
//		List<ReservationWrapper> reservations=new ArrayList<>();
//		reservations.add(new ReservationWrapper(9, new VcReservation(), 8));
//		reservations.add(new ReservationWrapper(9, new VcReservation(), 6));   //2
//		//reservations.add(new Reservation(11, new RcReservation(), 4));
//		//reservations.add(new Reservation(11, new RsReservation(), 2));
//		//reservations.add(new Reservation(11, new SpecReservation(), 9));   //12
//
//		//reservations.add(new Reservation(9, new RcReservation(), 7));
//		//reservations.add(new Reservation(8, new VcReservation(), 9));
//		//reservations.add(new Reservation(8, new SpecReservation(), 10));
//		//reservations.add(new Reservation(9, new SpecReservation(), 5));
//		//reservations.add(new Reservation(9, new RsReservation(), 4));
//
//		//reservations.add(new Reservation(9, new RcReservation(), 8));
//		//reservations.add(new Reservation(11, new VcReservation(), 12));
//		//reservations.add(new Reservation(11, new SpecReservation(), 5));
//		//reservations.add(new Reservation(8, new VcReservation(), 3));
//		//reservations.add(new Reservation(8, new SpecReservation(), 2));
//
//		//reservations.add(new Reservation(8, new VcReservation(), 12));
//		//reservations.add(new Reservation(10, new VcReservation(), 6));
//		//reservations.add(new Reservation(11, new RsReservation(), 2));
//		//reservations.add(new Reservation(9, new RsReservation(), 4));
//		//reservations.add(new Reservation(9, new RcReservation(), 7));
//
//		Map<ReservationWrapper, SalleWrapper> solution=new ConstraintSatisfactionAlgo().solve(reservations, salles);
//		if(solution==null){
//			System.out.println("Solution Not Found");
//		}else{
//			System.out.println("Yes we did it");
//			System.out.println(solution.isEmpty());
//			for (Map.Entry<ReservationWrapper, SalleWrapper> entry : solution.entrySet()) {
//				System.out.println("Reservation # "+entry.getKey().getId()
//						+" will be at salle"+entry.getValue().getName()+"\n");
//			}
//		}
//	}

	}
