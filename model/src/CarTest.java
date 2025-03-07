import org.junit.jupiter.api.Assertions;
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
        Assertions.assertEquals(4, volvo.getNrDoors());
        Assertions.assertEquals(4.8, volvo.getLength());
        Assertions.assertEquals(100, volvo.getEnginePower());
        Assertions.assertEquals(Color.black, volvo.getColor());
        Assertions.assertEquals(0, volvo.getCurrentSpeed());
    }

    @Test
    public void testSaab95Constructor() {
        Assertions.assertEquals(2, saab.getNrDoors());
        Assertions.assertEquals(5, saab.getLength());
        Assertions.assertEquals(125, saab.getEnginePower());
        Assertions.assertEquals(Color.red, saab.getColor());
        Assertions.assertEquals(0, saab.getCurrentSpeed());
    }

    @Test
    public void testStartEngine() {
        volvo.startEngine();
        Assertions.assertEquals(0.1, volvo.getCurrentSpeed());
    }

    @Test
    public void testStopEngine() {
        volvo.startEngine();
        volvo.stopEngine();
        Assertions.assertEquals(0, volvo.getCurrentSpeed());
    }

    @Test
    public void testVolvo240SpeedFactor() {
        Assertions.assertEquals(1.25, volvo.speedFactor(), 0.01);
    }

    @Test
    public void testSaab95SpeedFactorWithTurboOff() {
        saab.setTurboOff();
        Assertions.assertEquals(1.25, saab.speedFactor(), 0.01);
    }

    @Test
    public void testSaab95SpeedFactorWithTurboOn() {
        saab.setTurboOn();
        Assertions.assertEquals(1.625, saab.speedFactor(), 0.01);
    }

    @Test
    public void testGasValidAmount() {
        volvo.startEngine();
        volvo.gas(0.5);
        Assertions.assertTrue(volvo.getCurrentSpeed() > 0.1);
    }

    @Test
    public void testGasInvalidAmountThrowsException() {
        volvo.startEngine();
        Assertions.assertThrows(IllegalArgumentException.class, () -> volvo.gas(-0.5));
        Assertions.assertThrows(IllegalArgumentException.class, () -> volvo.gas(1.5));
    }

    @Test
    public void testBrakeValidAmount() {
        volvo.startEngine();
        volvo.gas(0.5);
        double speedBeforeBrake = volvo.getCurrentSpeed();
        volvo.brake(0.3);
        Assertions.assertTrue(volvo.getCurrentSpeed() < speedBeforeBrake);
    }

    @Test
    public void testBrakeInvalidAmountThrowsException() {
        volvo.startEngine();
        Assertions.assertThrows(IllegalArgumentException.class, () -> volvo.brake(-0.5));
        Assertions.assertThrows(IllegalArgumentException.class, () -> volvo.brake(1.5));
    }

    @Test
    public void testMoveUpdatesPosition() {
        volvo.startEngine();
        volvo.gas(1);
        volvo.move();
        Assertions.assertNotEquals(0, volvo.getPosition().x);
        Assertions.assertEquals(0, volvo.getPosition().y);
    }

    @Test
    public void testMoveAfterTurnLeft() {
        volvo.startEngine();
        volvo.gas(1);
        volvo.turnLeft();
        volvo.move();
        Assertions.assertNotEquals(0, volvo.getPosition().y);
        Assertions.assertEquals(0, volvo.getPosition().x, epsilon);
    }

    @Test
    public void testTurnLeft() {
        double initialDirection = volvo.getDirection();
        volvo.turnLeft();  // Turn left, so direction should decrease by 90 degrees
        Assertions.assertEquals((initialDirection - 90 + 360) % 360, volvo.getDirection());
    }

    @Test
    public void testTurnRight() {
        double initialDirection = volvo.getDirection();
        volvo.turnRight();  // Turn right, so direction should increase by 90 degrees
        Assertions.assertEquals((initialDirection + 90) % 360, volvo.getDirection());
    }

    @Test
    public void testSetColor() {
        volvo.setColor(Color.blue);
        Assertions.assertEquals(Color.blue, volvo.getColor());
    }

    @Test
    public void testSpeedDoesNotExceedEnginePower() {
        volvo.startEngine();
        for (int i = 0; i < 100; i++) {
            volvo.gas(1.0);
        }
        Assertions.assertTrue(volvo.getCurrentSpeed() <= volvo.getEnginePower());
    }

    @Test
    public void testSpeedDoesNotGoBelowZero() {
        volvo.startEngine();
        volvo.brake(1.0);
        volvo.brake(1.0);
        Assertions.assertEquals(0, volvo.getCurrentSpeed());
    }

    @Test
    public void testNoMovementWhenSpeedIsZero() {
        volvo.stopEngine();
        double initialX = volvo.getPosition().x;
        double initialY = volvo.getPosition().y;
        volvo.move();
        Assertions.assertEquals(initialX, volvo.getPosition().x);
        Assertions.assertEquals(initialY, volvo.getPosition().y);
    }
}
