/*
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class OldApplication {

    private OldCarController cc;

    // The delay (ms) corresponds to 20 updates a sec (hz)
    private final int delay = 50;
    // The timer is started with a listener (see below) that executes the statements
    // each step between delays.
    private javax.swing.Timer timer = new Timer(delay, new OldApplication.TimerListener());

    */
/* Each step the TimerListener moves all the cars in the list and tells the
     * view to update its images. Change this method to your needs.
     * *//*

    private class TimerListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            for (CarManager carDriver : cc.cars) {
                if (carDriver.car instanceof Scania && !((Scania) carDriver.car).canDrive()) {
                    continue; // Skip moving this car
                }

                cc.checkFrameCollision(carDriver.car);
                cc.checkWorkshopCollision(carDriver.car);

                carDriver.car.move();
                int x = (int) Math.round(carDriver.car.getPosition().getX());
                int y = (int) Math.round(carDriver.car.getPosition().getY());

                cc.frame.drawPanel.moveit(carDriver.car, x, y);
                // repaint() calls the paintComponent method of the panel
                cc.frame.drawPanel.repaint();
            }
        }
    }

    public static void oldMain(String[] args) {
        // Instance of this class
        OldApplication sim = new OldApplication();

        sim.cc = new OldCarController();

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
        sim.cc.cars.add(new CarManager(volvo));
        sim.cc.cars.add(new CarManager(saab));
        sim.cc.cars.add(new CarManager(scania));
        sim.cc.cars.add(new CarManager(brokenVolvo));


        // Start a new view and send a reference of self
        sim.cc.frame = new CarView("CarSim 1.0", sim.cc);

        sim.cc.workshops.put(new Workshop<Volvo240>(5), new Point(275, 275));
        for (var ws : sim.cc.workshops.keySet()) {
            sim.cc.frame.drawPanel.addWorkshop(ws, sim.cc.workshops.get(ws));
        }

        // Get window dimensions from CarView
        sim.cc.windowWidth = sim.cc.frame.getWindowWidth();
        sim.cc.windowHeight = sim.cc.frame.getWindowHeight();

        // Start the timer
        sim.timer.start();
    }

}
*/
