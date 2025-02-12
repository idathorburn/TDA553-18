import java.awt.*;

public abstract class Truck extends Car {
    public Truck(int nrDoors, double length, double enginePower, Color color, String modelName) {
        super(nrDoors, length, enginePower, color, modelName);
    }

    protected abstract boolean canDrive();

//    raiseBed() and lowerBed() could be abstarct functions, however we did now find a good way to make it so that one
//    uses a double and the other works without a parameter

    @Override
    public void move() {
        if (!canDrive()) {
            throw new IllegalStateException("Cannot move in current condition!");
        }
        super.move();
    }

    @Override
    public double speedFactor() {
        return getEnginePower() * 0.01;
    }
}