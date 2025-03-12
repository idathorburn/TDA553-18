import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SimulationManager {

    // The delay (ms) corresponds to 20 updates a sec (hz)
    private final int delay = 50;
    // The timer is started with a listener (see below) that executes the statements
    // each step between delays.
    private Timer timer = null;

    public SimulationManager() {
        timer = new Timer(delay, null);
    }

    public void addListener(CarManager manager) {
        timer.addActionListener(manager);
    }

    public void startSimulation() {
        timer.start();
    }
}
