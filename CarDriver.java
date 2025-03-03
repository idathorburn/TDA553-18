import java.lang.reflect.Array;
import java.util.ArrayList;

public class CarDriver {

    public Car car;

    public CarDriver(Car car){}

    void gas(int amount) {
        double gas = ((double) amount) / 100;
        car.gas(gas);
    }

    // Calls the break method for each car once
    void brake(int amount) {
        double brake = ((double) amount) / 100;
        car.brake(brake);
    }

    public void startCar() {
        car.startEngine();
    }

    public void stopCar() {
        car.stopEngine();
    }

    void setTurboOn() {
        if (car instanceof Saab95) {
            ((Saab95) car).setTurboOn();
        }
    }
    void setTurboOff() {
        if (car instanceof Saab95) {
            ((Saab95) car).setTurboOff();
        }
    }
    public Scania getScanias() {
        if (car instanceof Scania) {
            return (Scania) car;
        }
        return null;
    }
    void raiseBed(int angle) {
        if (car instanceof Scania) {
            ((Scania) car).raiseBed(angle);
        }
    }
    void lowerBed(int angle) {
        if (car instanceof Scania) {
            ((Scania) car).lowerBed(angle);
        }
    }
}
