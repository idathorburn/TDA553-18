import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.Buffer;
import java.util.ArrayList;
import java.util.HashMap;
import javax.imageio.ImageIO;
import javax.swing.*;

// This panel represents the animated part of the view with the car images.

public class DrawPanel extends JPanel{

    // To keep track of all cars
    private HashMap<String, BufferedImage> carImages = new HashMap<>();
    private HashMap<Car, Point> carPositions = new HashMap<>();

    private HashMap<String, BufferedImage> workshopImages = new HashMap<>();
    private HashMap<Workshop<? extends Car>, Point> workshopPositions = new HashMap<>();
    //BufferedImage volvoWorkshopImage;
    //Point volvoWorkshopPoint = new Point(300,300);

    // TODO: Make this general for all cars
    void moveit(Car car, int x, int y){
        carPositions.put(car, new Point(x, y));
    }

    void hideit(Car car) {
        car.stopEngine();
        car.setPosition(new Point(200, 1000));
    }

    <T extends Car> void addWorkshop (Workshop<T> ws, Point p) {
        workshopPositions.put(ws, p);
    }

    // Initializes the panel and reads the images
    public DrawPanel(int x, int y) {
        this.setDoubleBuffered(true);
        this.setPreferredSize(new Dimension(x, y));
        this.setBackground(Color.green);
        // Print an error message in case file is not found with a try/catch block
        try {
            carImages.put("Volvo240", ImageIO.read(DrawPanel.class.getResourceAsStream("pics/Volvo240.jpg")));
            carImages.put("Saab95", ImageIO.read(DrawPanel.class.getResourceAsStream("pics/Saab95.jpg")));
            carImages.put("Scania", ImageIO.read(DrawPanel.class.getResourceAsStream("pics/Scania.jpg")));

            workshopImages.put("Workshop<Volvo240>" ,ImageIO.read(DrawPanel.class.getResourceAsStream("pics/VolvoBrand.jpg")));
            workshopImages.put("Workshop" ,ImageIO.read(DrawPanel.class.getResourceAsStream("pics/VolvoBrand.jpg")));
        } catch (IOException ex)
        {
            ex.printStackTrace();
        }
    }

    // This method is called each time the panel updates/refreshes/repaints itself
    // TODO: Change to suit your needs.
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        for (Car car : carPositions.keySet()) {
            BufferedImage img = carImages.get(car.getClass().getSimpleName());
            if (img != null) {
                Point pos = carPositions.get(car);
                g.drawImage(img, pos.x, pos.y, null);
            }
        }
        for (var ws : workshopPositions.keySet()) {
            BufferedImage img = workshopImages.get(ws.getClass().getSimpleName());
            if (img != null) {
                Point pos = workshopPositions.get(ws);
                g.drawImage(img, pos.x, pos.y, null);
            }
        }
    }

}
