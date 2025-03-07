import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;

public class CarManager implements ModelInterface {

    public HashMap<Car, Point> cars;
    public SimulationManager simulationManager;
    public EnvironmentManager environmentManager;
    public ArrayList<ModelObserver> observers;
    public ImageManager imageManager;

    public CarManager(SimulationManager sim, EnvironmentManager env, ImageManager img){
        simulationManager = sim;
        environmentManager = env;
        imageManager = img;
    }

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


    public void gas(int amount) {
        double gas = ((double) amount) / 100;
        for (Car car : cars.keySet()) {
            car.gas(gas);
        }
    }

    // Calls the break method for each car once
    public void brake(int amount) {
        double brake = ((double) amount) / 100;
        for (Car car : cars.keySet()) {
            car.brake(brake);
        }
    }

    public void startCars() {
        for (Car car : cars.keySet()) {
            car.startEngine();
        }
    }

    public void stopCars() {
        for (Car car : cars.keySet()) {
            car.stopEngine();
        }
    }

    public void setTurboOn() {
        for (Car car : cars.keySet()) {
            if (car instanceof Saab95) {
                ((Saab95) car).setTurboOn();
            }
        }
    }

    public void setTurboOff() {
        for (Car car : cars.keySet()) {
            if (car instanceof Saab95) {
                ((Saab95) car).setTurboOff();
            }
        }
    }

    public void raiseBed(int angle) {
        for (Car car : cars.keySet()) {
            if (car instanceof Scania) {
                ((Scania) car).raiseBed(angle);
            }
        }
    }

    public void lowerBed(int angle) {
        for (Car car : cars.keySet()) {
            if (car instanceof Scania) {
                ((Scania) car).lowerBed(angle);
            }
        }
    }

    public Scania getScanias() {
        for (Car car : cars.keySet()) {
            if (car instanceof Scania) {
                return (Scania) car;
            }
        }
        return null;
    }
}
