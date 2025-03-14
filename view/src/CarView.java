import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

/**
 * This class represents the full view of the MVC pattern of your car simulator.
 * It initializes with being center on the screen and attaching it's controller in it's state.
 * It communicates with the Controller by calling methods of it when an action fires of in
 * each of it's components.
 * TODO: Write more actionListeners and wire the rest of the buttons
 **/

public class CarView extends JFrame implements ModelObserver{
    private static final int X = 800;
    private static final int Y = 800;

    DrawPanel drawPanel = new DrawPanel(X, Y-240);
    JPanel controlPanel = new JPanel();

    JPanel gasPanel = new JPanel();
    JSpinner gasSpinner = new JSpinner();
    int gasAmount = 0;
    JLabel gasLabel = new JLabel("Amount of gas");

    JPanel bedAnglePanel = new JPanel();
    JSpinner bedAngleSpinner = new JSpinner();
    int bedAngleAmount = 0;
    JLabel bedAngleLabel = new JLabel("Bed angle");
    JLabel currentBedAngleLabel = new JLabel("Current: 0");

    JButton gasButton = new JButton("Gas");
    JButton brakeButton = new JButton("Brake");
    JButton turboOnButton = new JButton("Saab Turbo on");
    JButton turboOffButton = new JButton("Saab Turbo off");
    JButton liftBedButton = new JButton("Scania Lift Bed");
    JButton lowerBedButton = new JButton("Lower Lift Bed");

    JButton addCarButton = new JButton("Add Car");
    JButton removeCarButton = new JButton("Remove Car");

    JButton startButton = new JButton("Start all cars");
    JButton stopButton = new JButton("Stop all cars");

    public CarView(String framename){
        initComponents(framename);
    }

    @Override
    public void actOnCarsUpdate(ArrayList<Car> cars) {
        drawPanel.EmptyCarPositions();
        for (var car : cars) {
            var p = car.getPosition();
            drawPanel.moveit(car, p.x, p.y);
        }
    }

    @Override
    public void actOnScaniaBedUpdate(double angle) {
        updateBedAngleDisplay(angle);
    }

    @Override
    public void actOnSimulationUpdate() {
        this.repaint();
    }

    @Override
    public void actOnEnivonmentUpdate(EnvironmentManager environmentManager) {
        for (var ws : environmentManager.workshops.keySet()) {
            var p = environmentManager.workshops.get(ws);
            drawPanel.moveit(ws, p.x, p.y);
        }
    }

    @Override
    public void actOnImagesUpdate(ImageManager imageManager) {
        drawPanel.putImages(imageManager.getImages());
    }

    // Sets everything in place and fits everything
    // TODO: Take a good look and make sure you understand how these methods and components work
    private void initComponents(String title) {
        this.setTitle(title);
        this.setPreferredSize(new Dimension(X,Y));
        this.setLayout(new FlowLayout(FlowLayout.LEFT, 0, 0));

        this.add(drawPanel);

        // ------- Gas Panel -------
        SpinnerModel gasSpinnerModel = new SpinnerNumberModel(0, 0, 100, 1);
        gasSpinner = new JSpinner(gasSpinnerModel);
        gasSpinner.addChangeListener(e -> gasAmount = (int) gasSpinner.getValue());

        gasPanel.setLayout(new BorderLayout());
        gasPanel.add(gasLabel, BorderLayout.PAGE_START);
        gasPanel.add(gasSpinner, BorderLayout.PAGE_END);

        // ------- Bed Angle Panel -------
        SpinnerModel bedSpinnerModel = new SpinnerNumberModel(0, 0, 100, 1);
        bedAngleSpinner = new JSpinner(bedSpinnerModel);
        bedAngleSpinner.addChangeListener(e -> bedAngleAmount = (int) bedAngleSpinner.getValue());

        bedAnglePanel.setLayout(new GridLayout(3, 1));
        bedAnglePanel.add(bedAngleLabel);
        bedAnglePanel.add(bedAngleSpinner);
        bedAnglePanel.add(currentBedAngleLabel);


        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new GridLayout(2, 1)); // Ensures vertical stacking
        inputPanel.add(gasPanel);
        inputPanel.add(bedAnglePanel);

        this.add(inputPanel);

        // ------- Control Panel -------
        controlPanel.setLayout(new GridLayout(2,4));
        controlPanel.add(gasButton);
        controlPanel.add(turboOnButton);
        controlPanel.add(liftBedButton);
        controlPanel.add(brakeButton);
        controlPanel.add(turboOffButton);
        controlPanel.add(lowerBedButton);
        controlPanel.add(addCarButton);
        controlPanel.add(removeCarButton);
        controlPanel.setPreferredSize(new Dimension((X/2)+4, 200));
        this.add(controlPanel);
        controlPanel.setBackground(Color.CYAN);

        startButton.setBackground(Color.blue);
        startButton.setForeground(Color.green);
        startButton.setPreferredSize(new Dimension(X/5-15,200));
        this.add(startButton);

        stopButton.setBackground(Color.red);
        stopButton.setForeground(Color.black);
        stopButton.setPreferredSize(new Dimension(X/5-15,200));
        this.add(stopButton);

        // Make the frame pack all it's components by respecting the sizes if possible.
        this.pack();

        // Get the computer screen resolution
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        // Center the frame
        this.setLocation(dim.width/2-this.getSize().width/2, dim.height/2-this.getSize().height/2);
        // Make the frame visible
        this.setVisible(true);
        // Make sure the frame exits when "x" is pressed
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public int getWindowWidth() {
        return X;
    }

    public int getWindowHeight() {
        return Y;
    }

    public void updateBedAngleDisplay(double currentAngle) {
        currentBedAngleLabel.setText("Current: " + currentAngle);
    }
}