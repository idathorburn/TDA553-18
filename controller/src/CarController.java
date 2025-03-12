import java.awt.*;

public class CarController {

    private CarView frame;
    private CarManager carManager;

    public CarController(CarView carView, CarManager manager) {
        frame = carView;
        carManager = manager;
        addActionListeners();
    }

    private void addActionListeners() {
        frame.gasButton.addActionListener(e -> gas( frame.gasAmount));
        frame.brakeButton.addActionListener(e -> brake( frame.gasAmount));
        frame.liftBedButton.addActionListener(e -> {raiseBed( frame.bedAngleAmount);});
        frame.lowerBedButton.addActionListener(e -> {lowerBed( frame.bedAngleAmount);});
        frame.startButton.addActionListener(e -> startAllCars());
        frame.stopButton.addActionListener(e -> stopAllCars());
        frame.turboOnButton.addActionListener(e -> setTurboOn());
        frame.turboOffButton.addActionListener(e -> setTurboOff());
        frame.addCarButton.addActionListener(e -> addCar());
        frame.removeCarButton.addActionListener(e -> removeCar());
    }

    public void gas(int amount) {
        carManager.gas(amount);
    }

    // Calls the break method for each car once
    public void brake(int amount) {
        carManager.brake(amount);
    }

    public void startAllCars() {
        carManager.startCars();
    }

    public void stopAllCars() {
        carManager.stopCars();
    }

    public void setTurboOn() {
        carManager.setTurboOn();
    }

    public void setTurboOff() {
        carManager.setTurboOff();
    }

    public void raiseBed(int angle) {
        carManager.raiseBed(angle);
    }

    public void lowerBed(int angle) {
        carManager.lowerBed(angle);
    }

    public void addCar() { carManager.addCarAtRandomPosition(); }

    public void removeCar() { carManager.removeRandomCar(); }

}
