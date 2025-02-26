import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


import static org.junit.jupiter.api.Assertions.*;

public class ScaniaTest {
    private Scania scania;

    @BeforeEach
    public void setUp() {
        scania = new Scania();
    }

    @Test
    public void testRaiseBedWhenTruckIsStill() {
        scania.raiseBed(70.0);
        assertFalse(scania.canDrive());
    }

    @Test
    public void testRaiseBedWhenTruckIsMoving() {
        scania.startEngine();
        scania.gas(1.0);
        assertThrows(IllegalStateException.class, () -> scania.raiseBed(50.0));
    }

    @Test
    public void testGasWhenBedIsUp() {
        scania.raiseBed(30.0);
        assertThrows(IllegalStateException.class, () -> scania.move());
    }

    @Test
    public void testBedAngleNeverExceedsMax() {
        scania.raiseBed(scania.getMaxBedAngle() + 10);
        assertEquals(scania.getMaxBedAngle(), scania.getBedAngle());
    }
}
