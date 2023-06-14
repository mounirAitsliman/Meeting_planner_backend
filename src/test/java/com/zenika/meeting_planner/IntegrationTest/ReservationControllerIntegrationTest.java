package com.zenika.meeting_planner.IntegrationTest;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.zenika.meeting_planner.DTO.NewReservation;
import com.zenika.meeting_planner.DTO.ReservationTypeEnum;
import com.zenika.meeting_planner.Services.ReservationService;
import com.zenika.meeting_planner.Services.SalleService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest
class ReservationControllerIntegrationTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ReservationService reservationService;
    @MockBean
    private SalleService salleService;

    @Test
    void itShouldCheckIfTheEndpointIsAccessible() throws Exception {
        NewReservation newReservation = new NewReservation(8, ReservationTypeEnum.VC, 8);
        String json = new ObjectMapper().writeValueAsString(newReservation);

        mockMvc.perform(post("/api/v1/reservations")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andDo(print())
                .andExpect(status().isOk());
    }
}
