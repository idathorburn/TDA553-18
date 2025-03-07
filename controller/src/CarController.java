public class CarController {

    private CarView frame;
    private CarManager carManager;

    public CarController(CarView carView, CarManager manager) {
        frame = carView;
        carManager = manager;
    }

    public void addListeners(CarView carView) {

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

    public Scania getScania() {
        return carManager.getScanias();
    }

    public void raiseBed(int angle) {
        carManager.raiseBed(angle);
    }

    public void lowerBed(int angle) {
        carManager.lowerBed(angle);
    }
}
