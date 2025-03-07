import java.awt.*;
import java.util.HashMap;

public interface ModelObserver {
    public void actOnCarsUpdate(HashMap<Car, Point> cars);
    public void actOnSimulationUpdate();
    public void actOnEnivonmentUpdate(EnvironmentManager environmentManager);
    public void actOnlmagesUpdate(ImageManager imageManager);
}
