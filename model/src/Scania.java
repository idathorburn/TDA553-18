import java.awt.*;

public class Scania extends Truck {

    private double bedAngle;

    public Scania() {
        super(2, 6.7, 200, Color.BLUE, "Scania");
        this.bedAngle = 0.0;
    }

    public double getBedAngle() {
        return bedAngle;
    }

    public double getMaxBedAngle() {
        return 70.0;
    }

    private void setBedAngle(double bedAngle) {
        if (bedAngle > this.getMaxBedAngle() || bedAngle < 0) {
            throw new IllegalArgumentException("Angle must be between 0 and 70");
        }
        this.bedAngle = bedAngle;
    }

    @Override
    protected boolean canDrive() {
        return bedAngle == 0.0;
    }


    protected void raiseBed(double amount) {
        if (getCurrentSpeed() != 0) {
            throw new IllegalStateException("Cannot raise bed while the truck is moving.");
        }
        setBedAngle(Math.min(bedAngle + amount, getMaxBedAngle())); // Increment up to max
    }

    protected void lowerBed(double amount) {
        if (getCurrentSpeed() != 0) {
            throw new IllegalStateException("Cannot lower bed while the truck is moving.");
        }
        setBedAngle(Math.max(bedAngle - amount, 0)); // Decrement down to 0
    }
}