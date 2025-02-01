import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.awt.*;

public class CarTransportTest {

    private CarTransport transport;
    private Car volvo;

    @BeforeEach
    void setUp() {
        transport = new CarTransport(3);
        volvo = new Volvo240();
        volvo.setPosition(transport.getPosition());
    }

    @Test
    void testLowerRampWhileMoving() {
        transport.startEngine();
        transport.gas(1.0);
        assertThrows(IllegalStateException.class, () -> transport.lowerBed());
    }

    @Test
    void testLoadCarWhenRampIsUp() {
        transport.stopEngine();
        transport.raiseBed();
        assertThrows(IllegalStateException.class, () -> transport.loadCar(volvo));
    }

    @Test
    void testLoadCarWhenRampDownAndCarClose() {
        transport.stopEngine();
        transport.lowerBed();
        assertDoesNotThrow(() -> transport.loadCar(volvo));
    }

    @Test
    void testLoadCarTooFarAway() {
        transport.stopEngine();
        transport.lowerBed();
        volvo.setPosition(new Point(9999, 9999));
        assertThrows(IllegalStateException.class, () -> transport.loadCar(volvo));
    }

    @Test
    void testUnloadCarWhenRampIsUp() {
        transport.stopEngine();
        transport.lowerBed();
        transport.loadCar(volvo);
        transport.raiseBed();
        assertThrows(IllegalStateException.class, () -> transport.unloadCar());
    }

    @Test
    void testUnloadCarsInLIFOOrder() {
        transport.stopEngine();
        transport.lowerBed();

        Car volvo1 = new Volvo240();
        Car volvo2 = new Volvo240();

        volvo1.setPosition(transport.getPosition());
        volvo2.setPosition(transport.getPosition());

        transport.loadCar(volvo1);
        transport.loadCar(volvo2);

        Car firstOut = transport.unloadCar();
        assertSame(volvo2, firstOut, "The last loaded car should come out first");

        Car secondOut = transport.unloadCar();
        assertSame(volvo1, secondOut, "Then the first loaded car comes out second");
    }

    @Test
    void testCannotLoadAnotherCarTransport() {
        transport.stopEngine();
        transport.lowerBed();

        CarTransport another = new CarTransport(2);
        another.setPosition(transport.getPosition());

        assertThrows(IllegalArgumentException.class, () -> transport.loadCar(another));
    }

    @Test
    void testLoadedCarFollowsTransport() {
        transport.stopEngine();
        transport.lowerBed();
        transport.loadCar(volvo);

        transport.raiseBed();
        transport.startEngine();
        transport.gas(0.5);
        transport.move();

        assertEquals(transport.getPosition(), volvo.getPosition(),
                "Loaded car should have the same position as the transport.");
    }

    @Test
    void testUnloadCarPositionIsNear() {
        transport.stopEngine();
        transport.lowerBed();
        transport.loadCar(volvo);

        Car unloaded = transport.unloadCar();

        Point transportPos = transport.getPosition();
        Point carPos = unloaded.getPosition();

        assertEquals(transportPos.x - 1, carPos.x, "Car should be 1 meter behind in x-direction");
        assertEquals(transportPos.y, carPos.y,    "Car should have the same y-position");
    }

    @Test
    void testCannotMoveWhenRampIsDown() {
        transport.stopEngine();
        transport.lowerBed();

        transport.startEngine();
        transport.gas(1.0);
        assertThrows(IllegalStateException.class, () -> transport.move());
    }
}
