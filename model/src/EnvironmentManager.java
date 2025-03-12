import java.util.HashMap;
import java.awt.*;

public class EnvironmentManager {
    private int width = 800;
    private int height = 800;

    public HashMap<Workshop<? extends Car>, Point> workshops = new HashMap<>();
    private final int workshopRadius = 10;

    public EnvironmentManager(){}

    public Workshop<?> insideWorkshop(Car c) {
        var carPos = c.getPosition();
        for(var ws : workshops.keySet()){
            Point wsPos = workshops.get(ws);

            Point workshopCollisionOffset = new Point(50, 50);
            Point workshopMax = new Point(wsPos.x + workshopCollisionOffset.x,
                    wsPos.y + workshopCollisionOffset.y);
            Point workshopMin = new Point(wsPos.x - workshopCollisionOffset.x,
                    wsPos.y - workshopCollisionOffset.y);

            if (carPos.x > workshopMin.x && carPos.x < workshopMax.x && carPos.y > workshopMin.y && carPos.y < workshopMax.y) {
                return ws;
            }
        }
        return null;
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
