import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.HashMap;
import java.util.Objects;

public class ImageManager {

    private final HashMap<String, BufferedImage> images = new HashMap<>();

    public ImageManager(){
        loadImages();
    }

    void loadImages() {
        // Print an error message in case file is not found with a try/catch block
        try {
            images.put("Volvo240", ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream("pics/Volvo240.jpg"))));
            images.put("Saab95", ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream("pics/Saab95.jpg"))));
            images.put("Scania", ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream("pics/Scania.jpg"))));

            images.put("Workshop<Volvo240>" ,ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream("pics/VolvoBrand.jpg"))));
            images.put("Workshop" ,ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream("pics/VolvoBrand.jpg"))));
        } catch (IOException ex)
        {
            ex.printStackTrace();
        }
    }

    public BufferedImage getImage(Class<?> imageClass) {
        return images.get(imageClass.getSimpleName());
    }

    public HashMap<String, BufferedImage> getImages() {
        return images;
    }

}
