import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;

public interface ModelInterface {
    public HashMap<Car, Point> cars = new HashMap<>();
    public SimulationManager simulationManager = null;
    public EnvironmentManager environmentManager = null;
    public ArrayList<ModelObserver> observers = new ArrayList<>();
    public ImageManager imageManager = null;

    void addObserver(ModelObserver modelObserver);

    void createCar(Class<? extends Car> carClass, Point position);
    //void checkCollision(Car car);

    void gas(int amount);
    void brake(int amount);
    void startCars();
    void stopCars();
    void setTurboOn();
    void setTurboOff();
    void raiseBed(int angle);
    void lowerBed(int angle);
    Scania getScanias();
}
