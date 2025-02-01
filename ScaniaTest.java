import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.awt.*;

import static org.junit.jupiter.api.Assertions.*;

public class ScaniaTest {
    private Scania scania;

    @BeforeEach
    public void setUp() {
        scania = new Scania();
    }

    @Test
    public void testRaiseBedWhenTruckIsStill() {
        scania.raiseBed();
        assertFalse(scania.canDrive());
    }

    @Test
    public void testRaiseBedWhenTruckIsMoving() {
        scania.startEngine();
        scania.gas(1.0);
        assertThrows(IllegalStateException.class, () -> scania.raiseBed());
    }

    @Test
    public void testGasWhenBedIsUp() {
        scania.raiseBed();
        assertThrows(IllegalStateException.class, () -> scania.gas(0.5));
    }

    @Test
    public void testBedAngleNeverExceeds70() {
        scania.setBedAngle(75);
        assertThrows(IllegalArgumentException.class, () -> scania.setBedAngle(75));
    }
}
