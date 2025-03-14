import java.awt.*;
import java.util.ArrayDeque;
import java.util.Deque;

public class CarTransport extends Truck {
    private final int maxCapacity;
    private final double maxCarLength;
    private final Deque<Car> loadedCars;
    private final int maxLoadDistance;

    private boolean rampDown;

    public CarTransport(int maxCapacity, double maxCarLength) {
        super(2, 12,300, Color.RED, "CarTransporter");
        this.maxCapacity = maxCapacity;
        this.maxCarLength = maxCarLength;
        this.loadedCars = new ArrayDeque<>();
        this.rampDown = false;
        this.maxLoadDistance = 10;
    }

    @Override
    protected boolean canDrive() {
        return !rampDown;
    }

    @Override
    public void move() {
        super.move();
        for (Car c : loadedCars) {
            c.setPosition(this.getPosition());
        }
    }

    public void raiseBed() {
        if (getCurrentSpeed() != 0) {
            throw new IllegalStateException("Cannot raise ramp while moving.");
        }
        rampDown = false;
    }

    public void lowerBed() {
        if (getCurrentSpeed() != 0) {
            throw new IllegalStateException("Cannot lower ramp while moving.");
        }
        rampDown = true;
    }

    public void loadCar(Car car) {
        if (!rampDown) {
            throw new IllegalStateException("Ramp must be down to load cars.");
        }
        if (getCurrentSpeed() != 0) {
            throw new IllegalStateException("Transport must be stationary to load cars.");
        }
        if (car instanceof CarTransport) {
            throw new IllegalArgumentException("Cannot load another CarTransport.");
        }
        if (loadedCars.size() >= maxCapacity) {
            throw new IllegalStateException("No capacity left on transport.");
        }
        if (car.getLength() > maxCarLength) {
            throw new IllegalStateException("Car is too long to load. Max length is " + maxCarLength + " meters.");
        }

        double distance = distanceBetween(this.getPosition(), car.getPosition());
        if (distance > maxLoadDistance) {
            throw new IllegalStateException("Car is too far away to be loaded. Distance=" + distance);
        }

        loadedCars.push(car);
        car.setPosition(getPosition());
    }

    public Car unloadCar() {
        if (!rampDown) {
            throw new IllegalStateException("Ramp must be down to unload cars.");
        }
        if (loadedCars.isEmpty()) {
            throw new IllegalStateException("No cars to unload.");
        }
        Car car = loadedCars.pop();

        Point unloadPos = new Point(getPosition().x - 1, getPosition().y);
        car.setPosition(unloadPos);

        return car;
    }

    private double distanceBetween(Point p1, Point p2) {
        double dx = p1.getX() - p2.getX();
        double dy = p1.getY() - p2.getY();
        return Math.sqrt(dx * dx + dy * dy);
    }
}