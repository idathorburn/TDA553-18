import java.awt.*;

public interface Moveable {
    void move();
    void turnLeft();
    void turnRight();
    Point nextPosition();
}
