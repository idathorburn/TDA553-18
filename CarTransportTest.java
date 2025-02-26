import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.awt.*;

public class CarTransportTest {

    private CarTransport transport;
    private Car volvo;

    @BeforeEach
    void setUp() {
        transport = new CarTransport(3, 5);
        volvo = new Volvo240();
        volvo.setPosition(transport.getPosition());
    }

    @Test
    void testCannotMoveOrAccelerateWhenRampIsDown() {
        transport.stopEngine();
        transport.lowerBed();
        assertThrows(IllegalStateException.class, () -> transport.move());
    }

    @Test
    void testLoadCarWhenRampIsUp() {
        transport.stopEngine();
        transport.raiseBed();
        assertThrows(IllegalStateException.class, () -> transport.loadCar(volvo));
    }

    @Test
    void testUnloadCarWhenRampIsDown() {
        transport.stopEngine();
        transport.lowerBed();
        transport.loadCar(volvo);
        Car unloaded = transport.unloadCar();
        assertEquals(transport.getPosition().x - 1, unloaded.getPosition().x);
    }

    @Test
    void testCannotLoadCarIfTooFarAway() {
        transport.stopEngine();
        transport.lowerBed();
        volvo.setPosition(new Point(9999, 9999));
        assertThrows(IllegalStateException.class, () -> transport.loadCar(volvo));
    }

    @Test
    void testCannotUnloadWhenRampIsUp() {
        transport.stopEngine();
        transport.lowerBed();
        transport.loadCar(volvo);
        transport.raiseBed();
        assertThrows(IllegalStateException.class, () -> transport.unloadCar());
    }

    @Test
    void testLengthOfTruck() {
        Car scaniaTruck = new Scania();
        transport.stopEngine();
        transport.lowerBed();
        assertThrows(IllegalStateException.class, () -> transport.loadCar(scaniaTruck));
    }

    @Test
    void testCarMovesWithTransport() {
        transport.lowerBed();
        transport.loadCar(volvo);
        transport.raiseBed();
        transport.move();
        assertEquals(transport.getPosition(),volvo.getPosition());
    }
}
