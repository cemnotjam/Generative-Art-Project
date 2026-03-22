package src;

import javafx.scene.image.Image;
import javafx.scene.image.WritableImage;
import javafx.embed.swing.SwingFXUtils;
import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;

public class ImageProcessor {

    public Image loadImage(String path) {
        return new Image("file:" + path, 0, 0, true, true, false);
    }

    public void saveImage(WritableImage image, String outputPath) {
        File file = new File(outputPath);
        try {
            ImageIO.write(SwingFXUtils.fromFXImage(image, null), "png", file);
            System.out.println("Saved to: " + outputPath);
        } catch (IOException e) {
            System.out.println("Failed to save: " + e.getMessage());
        }
    }
}
