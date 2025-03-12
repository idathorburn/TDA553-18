import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

public interface ModelObserver {
    public void actOnCarsUpdate(ArrayList<Car> cars);
    public void actOnScaniaBedUpdate(double angle);
    public void actOnSimulationUpdate();
    public void actOnEnivonmentUpdate(EnvironmentManager environmentManager);
    public void actOnImagesUpdate(ImageManager imageManager);
}
