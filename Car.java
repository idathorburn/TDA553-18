import java.awt.*;

public abstract class Car implements Moveable{
    private int nrDoors; // Number of doors on the car
    private double enginePower; // Engine power of the car
    private double currentSpeed; // The current speed of the car
    private Color color; // Color of the car
    private String modelName; // The car model name
    private Point position;
    private double direction; // direction in degrees

    public Car(int nrDoors, double enginePower, Color color, String modelName) {
        this.nrDoors = nrDoors;
        this.enginePower = enginePower;
        this.currentSpeed = 0; // Cars are stationary when created
        this.color = color;
        this.modelName = modelName;
        this.position = new Point(0, 0);
        this.direction = 0;
    }

    public int getNrDoors(){
        return nrDoors;
    }

    public double getEnginePower(){
        return enginePower;
    }

    public void setCurrentSpeed(double speed) {
        this.currentSpeed = Math.max(0, Math.min(speed, enginePower));
    }

    public double getCurrentSpeed(){
        return currentSpeed;
    }

    public void setColor(Color clr){
        color = clr;
    }

    public Color getColor(){
        return color;
    }

    public void startEngine() {
        setCurrentSpeed(0.1);
    }

    public void stopEngine() {
        setCurrentSpeed(0);
    }

    public abstract double speedFactor();

    public void incrementSpeed(double amount) {
        setCurrentSpeed(getCurrentSpeed() + speedFactor() * amount);
    }

    public void decrementSpeed(double amount) {
        setCurrentSpeed(getCurrentSpeed() - speedFactor() * amount);
    }

    public void setPosition(Point position) {
        this.position = position;}

    public Point getPosition() {
        return position;}

    public void setDirection(double degrees) {
        this.direction = (degrees % 360 + 360) % 360;}

    public double getDirection() {
        return direction;}

    // TODO fix this method according to lab pm
    public void gas(double amount){
        if (amount < 0 || amount > 1) {
            throw new IllegalArgumentException("Amount must be between 0 and 1");
        }
        incrementSpeed(amount);
    }

    // TODO fix this method according to lab pm
    public void brake(double amount){
        if (amount < 0 || amount > 1) {
            throw new IllegalArgumentException("Amount must be between 0 and 1");
        }
        decrementSpeed(amount);
    }

    @Override
    public void move() {
        double radians = Math.toRadians(getDirection());
        int newX = (int) (getPosition().x + Math.cos(radians) * getCurrentSpeed());
        int newY = (int) (getPosition().y + Math.sin(radians) * getCurrentSpeed());
        setPosition(new Point(newX, newY));
    }

    @Override
    public void turnLeft() {
        setDirection(getDirection() - 90);
    }

    @Override
    public void turnRight() {
        setDirection(getDirection() + 90);
    }
}
