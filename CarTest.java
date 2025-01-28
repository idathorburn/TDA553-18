import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.awt.*;

import static org.junit.jupiter.api.Assertions.*;

public class CarTest {
    private Volvo240 volvo;
    private Saab95 saab;
    private final double epsilon = 1e-10; // margin of error allowed in test

    @BeforeEach
    public void setUp() {
        volvo = new Volvo240();
        saab = new Saab95();
    }

    @Test
    public void testVolvo240Constructor() {
        assertEquals(4, volvo.getNrDoors());
        assertEquals(100, volvo.getEnginePower());
        assertEquals(Color.black, volvo.getColor());
        assertEquals(0, volvo.getCurrentSpeed());
    }

    @Test
    public void testSaab95Constructor() {
        assertEquals(2, saab.getNrDoors());
        assertEquals(125, saab.getEnginePower());
        assertEquals(Color.red, saab.getColor());
        assertEquals(0, saab.getCurrentSpeed());
    }

    @Test
    public void testStartEngine() {
        volvo.startEngine();
        assertEquals(0.1, volvo.getCurrentSpeed());
    }

    @Test
    public void testStopEngine() {
        volvo.startEngine();
        volvo.stopEngine();
        assertEquals(0, volvo.getCurrentSpeed());
    }

    @Test
    public void testVolvo240SpeedFactor() {
        assertEquals(1.25, volvo.speedFactor(), 0.01);
    }

    @Test
    public void testSaab95SpeedFactorWithTurboOff() {
        saab.setTurboOff();
        assertEquals(1.25, saab.speedFactor(), 0.01);
    }

    @Test
    public void testSaab95SpeedFactorWithTurboOn() {
        saab.setTurboOn();
        assertEquals(1.625, saab.speedFactor(), 0.01);
    }

    @Test
    public void testGasValidAmount() {
        volvo.startEngine();
        volvo.gas(0.5);
        assertTrue(volvo.getCurrentSpeed() > 0.1);
    }

    @Test
    public void testGasInvalidAmountThrowsException() {
        volvo.startEngine();
        assertThrows(IllegalArgumentException.class, () -> volvo.gas(-0.5));
        assertThrows(IllegalArgumentException.class, () -> volvo.gas(1.5));
    }

    @Test
    public void testBrakeValidAmount() {
        volvo.startEngine();
        volvo.gas(0.5);
        double speedBeforeBrake = volvo.getCurrentSpeed();
        volvo.brake(0.3);
        assertTrue(volvo.getCurrentSpeed() < speedBeforeBrake);
    }

    @Test
    public void testBrakeInvalidAmountThrowsException() {
        volvo.startEngine();
        assertThrows(IllegalArgumentException.class, () -> volvo.brake(-0.5));
        assertThrows(IllegalArgumentException.class, () -> volvo.brake(1.5));
    }

    @Test
    public void testMoveUpdatesPosition() {
        volvo.startEngine();
        volvo.gas(1);
        volvo.move();
        assertNotEquals(0, volvo.getX());
        assertEquals(0, volvo.getY());
    }

    @Test
    public void testMoveAfterTurnLeft() {
        volvo.startEngine();
        volvo.gas(1);
        volvo.turnLeft();
        volvo.move();
        assertNotEquals(0, volvo.getY());
        assertEquals(0, volvo.getX(), epsilon);
    }

    @Test
    public void testTurnLeft() {
        double initialDirection = volvo.getDirection();
        volvo.turnLeft();
        assertEquals((initialDirection + 90) % 360, volvo.getDirection());
    }

    @Test
    public void testTurnRight() {
        double initialDirection = volvo.getDirection();
        volvo.turnRight();
        assertEquals((initialDirection - 90 + 360) % 360, volvo.getDirection());
    }

    @Test
    public void testSetColor() {
        volvo.setColor(Color.blue);
        assertEquals(Color.blue, volvo.getColor());
    }

    @Test
    public void testSpeedDoesNotExceedEnginePower() {
        volvo.startEngine();
        for (int i = 0; i < 100; i++) {
            volvo.gas(1.0);
        }
        assertTrue(volvo.getCurrentSpeed() <= volvo.getEnginePower());
    }

    @Test
    public void testSpeedDoesNotGoBelowZero() {
        volvo.startEngine();
        volvo.brake(1.0);
        volvo.brake(1.0);
        assertEquals(0, volvo.getCurrentSpeed());
    }

    @Test
    public void testNoMovementWhenSpeedIsZero() {
        volvo.stopEngine();
        double initialX = volvo.getX();
        double initialY = volvo.getY();
        volvo.move();
        assertEquals(initialX, volvo.getX());
        assertEquals(initialY, volvo.getY());
    }
}
