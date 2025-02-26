import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import javax.imageio.ImageIO;
import javax.swing.*;

// This panel represents the animated part of the view with the car images.

public class DrawPanel extends JPanel{

    // To keep track of all cars
    private HashMap<String, BufferedImage> carImages = new HashMap<>();
    private HashMap<Car, Point> carPositions = new HashMap<>();

    BufferedImage volvoWorkshopImage;
    Point volvoWorkshopPoint = new Point(300,300);
    private Workshop<Volvo240> volvoWorkshop = new Workshop<>(3);

    // TODO: Make this general for all cars
    void moveit(Car car, int x, int y){
        carPositions.put(car, new Point(x, y));
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

            volvoWorkshopImage = ImageIO.read(DrawPanel.class.getResourceAsStream("pics/VolvoBrand.jpg"));
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

        g.drawImage(volvoWorkshopImage, volvoWorkshopPoint.x, volvoWorkshopPoint.y, null);

        for (Car car : carPositions.keySet()) {
            if (car instanceof Volvo240 && volvoWorkshop.getSize() > 0) {
                continue;  // Rita inte Volvo-bilar som är inuti verkstaden
            }

            BufferedImage img = carImages.get(car.getClass().getSimpleName());
            if (img != null) {
                Point pos = carPositions.get(car);
                g.drawImage(img, pos.x, pos.y, null);
            }
        }
    }

    public Workshop<Volvo240> getVolvoWorkshop() {
        return volvoWorkshop;
    }

    public Point getVolvoWorkshopPoint() {
        return volvoWorkshopPoint;
    }



}
