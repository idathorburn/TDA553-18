import org.w3c.dom.ranges.Range;

import javax.swing.*;
import javax.swing.text.Position;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;

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

    HashMap<Workshop<? extends Car>, Point> workshops = new HashMap<>();

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
        scania.setPosition(new Point(0, 290));
        brokenVolvo.setPosition(new Point(0, 300));

        // Add the cars to the list
        cc.cars.add(volvo);
        cc.cars.add(saab);
        cc.cars.add(scania);
        cc.cars.add(brokenVolvo);


        // Start a new view and send a reference of self
        cc.frame = new CarView("CarSim 1.0", cc);

        cc.workshops.put(new Workshop<Volvo240>(5), new Point(275, 275));
        for (var ws : cc.workshops.keySet()) {
            cc.frame.drawPanel.addWorkshop(ws, cc.workshops.get(ws));
        }

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

                checkFrameCollision(car);
                checkWorkshopCollision(car);

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

    private void checkFrameCollision(Car car) {
        Point position = car.getPosition();
        double direction = car.getDirection();
        double speed = car.getCurrentSpeed();

        // Check the new potential position after movement
        double radians = Math.toRadians(direction);
        int newX = (int) (position.x + Math.cos(radians) * speed);
        int newY = (int) (position.y + Math.sin(radians) * speed);

        // Check if the new position would go outside the frame boundaries
        // 100x60 is the size of the "car"
        if (newX < 0 || newX + 100 > windowWidth || newY < 0 || newY + 60 > windowHeight) {
            car.stopEngine();  // Stop the car
            car.setDirection(car.getDirection() + 180);  // Reverse direction
            car.startEngine();  // Start the car
        }
    }

    private void checkWorkshopCollision(Car car) {
        Point carPos = car.getPosition();

        double direction = car.getDirection();
        double speed = car.getCurrentSpeed();

        // Check the new potential position after movement
        double radians = Math.toRadians(direction);
        int newX = (int) (carPos.x + Math.cos(radians) * speed);
        int newY = (int) (carPos.y + Math.sin(radians) * speed);

        for (var ws : workshops.keySet()) {
            Point wsPos = workshops.get(ws);

            Point workshopCollisionOffset = new Point(50, 50);
            Point workshopMax = new Point(wsPos.x + workshopCollisionOffset.x,
                    wsPos.y + workshopCollisionOffset.y);
            Point workshopMin = new Point(wsPos.x - workshopCollisionOffset.x,
                    wsPos.y - workshopCollisionOffset.y);
            if (carPos.x > workshopMin.x && carPos.x < workshopMax.x && carPos.y > workshopMin.y && carPos.y < workshopMax.y) {
                if (car.getClass() == Volvo240.class) {
                    frame.drawPanel.hideit(car);
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
