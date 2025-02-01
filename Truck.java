import java.awt.*;

public abstract class Truck extends Car {
    public Truck(int nrDoors, double enginePower, Color color, String modelName) {
        super(nrDoors, enginePower, color, modelName);
    }

    protected abstract boolean canDrive();

    protected abstract void raiseBed();

    protected abstract void lowerBed();

    @Override
    public void move() {
        if (!canDrive()) {
            throw new IllegalStateException("Cannot move in current condition!");
        }
        super.move();
    }
}
