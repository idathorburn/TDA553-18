import java.awt.*;
import java.util.HashMap;

public interface ModelInterface {
    public HashMap<Car, Point> cars;
    public SimulationManager simulationManager;
    public EnvironmentManager environmentManager;
    public List<ModelObserver> observers;
    public ImageManager imageManager;

    void addObserver(ModelObserver modelObserver);

    void createCars(int numberOfCars, Point position);
    void checkCollision(T car);

    void gas(int amount);
    void brake(int amount);
    void startCars();
    void stopCars();
    void setTurboOn();
    void setTurboOff();
    void getScanias();
    void raiseBed();
    void lowerBed();
}
