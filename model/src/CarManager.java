import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;

public class CarManager implements ModelInterface {

    public HashMap<Car, Point> cars;
    public SimulationManager simulationManager;
    public EnvironmentManager environmentManager;
    public ArrayList<ModelObserver> observers;
    public ImageManager imageManager;

    public void addObserver(ModelObserver modelObserver){
        observers.add(modelObserver);
    };

    public void createCar(Class<? extends Car> carClass, Point position) {
        try {
            Car car = carClass.getDeclaredConstructor().newInstance();
            cars.put(car, position);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void checkCollision(Car car) {
        var workshop = environmentManager.insideWorkshop(car);
        if (workshop != null) {
            workshop.addCar(car);
        }

        if (environmentManager.isOutOfBounds(car)) {
            turnCarAround(car);
        }
    };

    private void turnCarAround(Car car) {
        car.stopEngine();  // Stop the car
        car.setDirection(car.getDirection() + 180);  // Reverse direction
        car.startEngine();  // Start the car
    }


    public Car car;

    public CarManager(Car c){
        car = c;
    }

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
