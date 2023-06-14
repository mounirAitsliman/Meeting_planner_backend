package com.zenika.meeting_planner.Entities;

import com.zenika.meeting_planner.DTO.ReservationTypeEnum;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table
@Entity
public class Reservation {
    @Id
    @SequenceGenerator(
            name="reservation_sequence",
            sequenceName = "reservation_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "reservation_sequence"
    )
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "salle_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Salle salle;

    private int startingHour;
    private int numberOfPeople;
    private ReservationTypeEnum reservationTypeEnum;


}
