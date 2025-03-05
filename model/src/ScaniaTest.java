import org.junit.jupiter.api.Assertions;
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
        Assertions.assertFalse(scania.canDrive());
    }

    @Test
    public void testRaiseBedWhenTruckIsMoving() {
        scania.startEngine();
        scania.gas(1.0);
        Assertions.assertThrows(IllegalStateException.class, () -> scania.raiseBed(50.0));
    }

    @Test
    public void testGasWhenBedIsUp() {
        scania.raiseBed(30.0);
        Assertions.assertThrows(IllegalStateException.class, () -> scania.move());
    }

    @Test
    public void testBedAngleNeverExceedsMax() {
        scania.raiseBed(scania.getMaxBedAngle() + 10);
        Assertions.assertEquals(scania.getMaxBedAngle(), scania.getBedAngle());
    }
}
