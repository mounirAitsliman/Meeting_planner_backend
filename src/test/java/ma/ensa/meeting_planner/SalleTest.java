package ma.ensa.meeting_planner;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class SalleTest {
    @Test
    public void testAjouterSalle() {
        Salle salle = new Salle("Salle A", 10, "VC");
        assertEquals("Salle A", salle.getNom());
        assertEquals(10, salle.getCapacite());
        assertEquals("VC", salle.getTypeOutils());
    }
}
