import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

/*
* This class represents the Controller part in the MVC pattern.
* It's responsibilities is to listen to the View and responds in a appropriate manner by
* modifying the model state and the updating the view.
 */

public class CarController {
    // member fields:

    // The delay (ms) corresponds to 20 updates a sec (hz)
    private final int delay = 50;
    // The timer is started with a listener (see below) that executes the statements
    // each step between delays.
    private Timer timer = new Timer(delay, new TimerListener());

    // The frame that represents this instance View of the MVC pattern
    CarView frame;
    // A list of cars, modify if needed
    ArrayList<Car> cars = new ArrayList<>();
    // Window dimensions (from CarView)
    private int windowWidth;
    private int windowHeight;
    public CarController() {}

    //methods:

    public static void main(String[] args) {
        // Instance of this class
        CarController cc = new CarController();

        // Create the cars
        Volvo240 volvo = new Volvo240();
        Saab95 saab = new Saab95();
        Scania scania = new Scania();
        Volvo240 brokenVolvo = new Volvo240();


        // Set the start positions
        volvo.setPosition(new Point(0, 0));
        saab.setPosition(new Point(0, 100));
        scania.setPosition(new Point(0, 200));
        brokenVolvo.setPosition(new Point(0, 300));

        // Add the cars to the list
        cc.cars.add(volvo);
        cc.cars.add(saab);
        cc.cars.add(scania);
        cc.cars.add(brokenVolvo);

        // Start a new view and send a reference of self
        cc.frame = new CarView("CarSim 1.0", cc);

        // Get window dimensions from CarView
        cc.windowWidth = cc.frame.getWindowWidth();
        cc.windowHeight = cc.frame.getWindowHeight();

        // Start the timer
        cc.timer.start();
    }

    /* Each step the TimerListener moves all the cars in the list and tells the
    * view to update its images. Change this method to your needs.
    * */
    private class TimerListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            for (Car car : cars) {
                if (car instanceof Scania && !((Scania) car).canDrive()) {
                    continue; // Skip moving this car
                }

                checkCollision(car);

                car.move();
                int x = (int) Math.round(car.getPosition().getX());
                int y = (int) Math.round(car.getPosition().getY());

                frame.drawPanel.moveit(car, x, y);
                // repaint() calls the paintComponent method of the panel
                frame.drawPanel.repaint();
            }
        }
    }

    // Calls the gas method for each car once
    void gas(int amount) {
        double gas = ((double) amount) / 100;
        for (Car car : cars
                ) {
            car.gas(gas);
        }
    }

    // Calls the break method for each car once
    void brake(int amount) {
        double brake = ((double) amount) / 100;
        for (Car car : cars
        ) {
            car.brake(brake);
        }
    }

    public void startAllCars() {
        for (Car car : cars) {
            car.startEngine();
        }
    }

    public void stopAllCars() {
        for (Car car : cars) {
            car.stopEngine();
        }
    }

    private void checkCollision(Car car) {
        Point position = car.getPosition();
        double direction = car.getDirection();
        double speed = car.getCurrentSpeed();

        double radians = Math.toRadians(direction);
        int newX = (int) (position.x + Math.cos(radians) * speed);
        int newY = (int) (position.y + Math.sin(radians) * speed);

        // Check if the car is going outside the boundaries
        if (newX < 0 || newX + 100 > windowWidth || newY < 0 || newY + 60 > windowHeight) {
            car.stopEngine();
            car.setDirection(car.getDirection() + 180);
            car.startEngine();
        }

        // Check if the car is a Volvo and near the workshop
        if (car instanceof Volvo240) {
            Point volvoWorkshopPoint = frame.drawPanel.getVolvoWorkshopPoint();
            Workshop<Volvo240> volvoWorkshop = frame.drawPanel.getVolvoWorkshop();

            // Ensure only this car's position is checked
            if (Math.abs(newX - volvoWorkshopPoint.x) < 50 && Math.abs(newY - volvoWorkshopPoint.y) < 50) {
                // Check if this car is already in the workshop
                boolean isAlreadyStored = false;
                for (int i = 0; i < volvoWorkshop.getSize(); i++) {
                    if (volvoWorkshop.getCar(i) == car) {  // Compare object references
                        isAlreadyStored = true;
                        break;
                    }
                }

                if (!isAlreadyStored) {
                    try {
                        volvoWorkshop.addCar((Volvo240) car);
                        System.out.println("Volvo added to workshop: " + car);
                    } catch (IllegalStateException e) {
                        System.out.println("Workshop is full!");
                    }
                }
            }
        }
    }

    void setTurboOn() {
        for (Car car : cars) {
            if (car instanceof Saab95) {
                ((Saab95) car).setTurboOn();
            }
        }
    }
    void setTurboOff() {
        for (Car car : cars) {
            if (car instanceof Saab95) {
                ((Saab95) car).setTurboOff();
            }
        }
    }
    public Scania getScania() {
        for (Car car : cars) {
            if (car instanceof Scania) {
                return (Scania) car;
            }
        }
        return null;
    }
    void raiseBed(int angle) {
        for (Car car : cars) {
            if (car instanceof Scania) {
                ((Scania) car).raiseBed(angle);
            }
        }
    }
    void lowerBed(int angle) {
        for (Car car : cars) {
            if (car instanceof Scania) {
                ((Scania) car).lowerBed(angle);
            }
        }
    }
}
