import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.HashMap;
import javax.imageio.ImageIO;
import javax.swing.*;

// This panel represents the animated part of the view with the car images.

public class DrawPanel extends JPanel{

    // To keep track of all cars
    private HashMap<Car, Point> carPositions = new HashMap<>();
    private HashMap<Workshop<? extends Car>, Point> workshopPositions = new HashMap<>();

    private HashMap<String, BufferedImage> images = new HashMap<>();
    //BufferedImage volvoWorkshopImage;
    //Point volvoWorkshopPoint = new Point(300,300);

    public void putImages(HashMap<String, BufferedImage> i){
        for (var imgName : i.keySet()) {
            images.put(imgName, i.get(imgName));
        }
    }

    public void EmptyCarPositions(){
        carPositions = new HashMap<>();
    }

    // TODO: Make this general for all cars
    void moveit(Car car, int x, int y){
        carPositions.put(car, new Point(x, y));
    }

    void moveit(Workshop<? extends Car> ws, int x, int y){
        workshopPositions.put(ws, new Point(x, y));
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
    }

    // This method is called each time the panel updates/refreshes/repaints itself
    // TODO: Change to suit your needs.
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        for (Car car : carPositions.keySet()) {
            BufferedImage img = images.get(car.getClass().getSimpleName());
            if (img != null) {
                Point pos = carPositions.get(car);
                g.drawImage(img, pos.x, pos.y, null);
            }
        }
        for (var ws : workshopPositions.keySet()) {
            BufferedImage img = images.get(ws.getClass().getSimpleName());
            if (img != null) {
                Point pos = workshopPositions.get(ws);
                g.drawImage(img, pos.x, pos.y, null);
            }
        }
    }

}
