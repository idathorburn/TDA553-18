import javax.swing.text.Position;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.*;
import java.util.List;

public class CarManager implements ModelInterface, ActionListener {

    public ArrayList<Car> cars = new ArrayList<>();
    public SimulationManager simulationManager;
    public EnvironmentManager environmentManager;
    public ArrayList<ModelObserver> observers = new ArrayList<>();
    public ImageManager imageManager;
    private int MaxNumberOfCars = 8;

    public CarManager(SimulationManager sim, EnvironmentManager env, ImageManager img, ModelObserver view){
        addObserver(view);
        simulationManager = sim;
        simulationManager.addListener(this);
        environmentManager = env;
        imageManager = img;
        notifyEnvironmentUpdate(environmentManager);
        notifyImageUpdate(imageManager);
    }

    public void addObserver(ModelObserver modelObserver){
        observers.add(modelObserver);
    };

    public void actionPerformed(ActionEvent e) {
        var carsToRemove = new ArrayList<Car>();
        for (var car : cars) {
            carsToRemove = checkCollision(car);
        }
        for (var car : carsToRemove) {
            cars.remove(car);
        }
        for (var car : cars) {
            car.move();
        }
        notifyCarUpdate(cars);

        notifySimulationUpdate();
    }

    private void notifySimulationUpdate() {
        for(var observer : observers) {
            observer.actOnSimulationUpdate();
        }
    }

    private void notifyCarUpdate(ArrayList<Car> c) {
        for (var observer : observers) {
            observer.actOnCarsUpdate(c);
        }
    }

    private void notifyScaniaBedUpdate(double a) {
        for(var observer : observers) {
            observer.actOnScaniaBedUpdate(a);
        }
    }

    private void notifyEnvironmentUpdate(EnvironmentManager e) {
        for(var observer : observers) {
            observer.actOnEnivonmentUpdate(e);
        }
    }

    private void notifyImageUpdate(ImageManager i) {
        for(var observer : observers) {
            observer.actOnImagesUpdate(i);
        }
    }

    public void createCar(Class<? extends Car> carClass, Point position) {
        if (cars.size() >= MaxNumberOfCars) return;
        try {
            Car car = carClass.getDeclaredConstructor().newInstance();
            car.setPosition(position);
            cars.add(car);
        } catch (Exception e) {
            e.printStackTrace();
        }
        notifyCarUpdate(cars);
    }

    public void addCarAtRandomPosition() {
        Random random = new Random();
        int x = random.nextInt(500+1);
        int y = random.nextInt(500+1);
        Point point = new Point(x, y);
        createCar(Volvo240.class, point);
    }

    public void removeRandomCar() {
        if (cars.size() == 0) return;
        Random random = new Random();
        var carId = random.nextInt(cars.size());
        cars.remove(carId);
        notifyCarUpdate(cars);
    }

    private ArrayList<Car> checkCollision(Car car) {
        var workshop = environmentManager.insideWorkshop(car);
        var res = new ArrayList<Car>();
        if (workshop != null && workshop.hasPlaceLeft()) {
            workshop.addCar(car);
            res.add(car);
        }

        if (environmentManager.isOutOfBounds(car)) {
            turnCarAround(car);
        }

        return res;
    };

    private void turnCarAround(Car car) {
        car.stopEngine();  // Stop the car
        car.setDirection(car.getDirection() + 180);  // Reverse direction
        car.startEngine();  // Start the car
    }

    public void gas(int amount) {
        double gas = ((double) amount) / 100;
        for (Car car : cars) {
            car.gas(gas);
        }
        notifyCarUpdate(cars);
    }

    // Calls the break method for each car once
    public void brake(int amount) {
        double brake = ((double) amount) / 100;
        for (Car car : cars) {
            car.brake(brake);
        }
    }

    public void startCars() {
        for (Car car : cars) {
            car.startEngine();
        }
    }

    public void stopCars() {
        for (Car car : cars) {
            car.stopEngine();
        }
    }

    public void setTurboOn() {
        for (Car car : cars) {
            if (car instanceof Saab95) {
                ((Saab95) car).setTurboOn();
            }
        }
    }

    public void setTurboOff() {
        for (Car car : cars) {
            if (car instanceof Saab95) {
                ((Saab95) car).setTurboOff();
            }
        }
    }

    public void raiseBed(int angle) {
        double finalAngel = 0;
        for (Car car : cars) {
            if (car instanceof Scania) {
                ((Scania) car).raiseBed(angle);
                finalAngel = ((Scania) car).getBedAngle();
            }
        }
        notifyScaniaBedUpdate(finalAngel);
    }

    public void lowerBed(int angle) {
        double finalAngel = 0;
        for (Car car : cars) {
            if (car instanceof Scania) {
                ((Scania) car).lowerBed(angle);
                finalAngel = ((Scania) car).getBedAngle();
            }
        }
        notifyScaniaBedUpdate(finalAngel);
    }

    public Scania getScanias() {
        for (Car car : cars) {
            if (car instanceof Scania) {
                return (Scania) car;
            }
        }
        return null;
    }
}
