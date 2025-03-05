import java.util.HashMap;
import java.awt.*;

public class EnvironmentManager {
    private int width;
    private int height;

    public HashMap<Workshop<? extends Car>, Point> workshops;
    private final int workshopRadius = 10;

    public EnvironmentManager(){}

    public Workshop<?> insideWorkshop(Car c) {
        return new Workshop<Volvo240>(10);
    };

    public boolean isOutOfBounds(Car car) {
        var newP = car.nextPosition();
        boolean result;

        // Check if the new position would go outside the frame boundaries
        // 100x60 is the size of the "car"
        result = newP.x < 0 || newP.x + 100 > width || newP.y < 0 || newP.y + 60 > height;
        return result;
    };
}
