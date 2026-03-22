package src;

import javafx.scene.image.Image;
import javafx.scene.image.PixelReader;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;

public class GlitchEngine {

    public WritableImage applyEffect(Image source, int stripSize) {
        WritableImage small = resize(source, 400, 500);
        WritableImage gray = toGrayscale(small);
        WritableImage grid = makeGrid(gray);
        WritableImage v = verticalShuffle(grid, stripSize);
        WritableImage result = horizontalShuffle(v, stripSize);
        return result;
    }

    private WritableImage resize(Image source, int targetWidth, int targetHeight) {
        WritableImage result = new WritableImage(targetWidth, targetHeight);
        PixelWriter writer = result.getPixelWriter();
        PixelReader reader = source.getPixelReader();

        double xScale = source.getWidth() / targetWidth;
        double yScale = source.getHeight() / targetHeight;

        for (int y = 0; y < targetHeight; y++) {
            for (int x = 0; x < targetWidth; x++) {
                int srcX = (int) (x * xScale);
                int srcY = (int) (y * yScale);
                writer.setColor(x, y, reader.getColor(srcX, srcY));
            }
        }
        return result;
    }

    private WritableImage toGrayscale(Image source) {
        int width = (int) source.getWidth();
        int height = (int) source.getHeight();

        PixelReader reader = source.getPixelReader();
        WritableImage result = new WritableImage(width, height);
        PixelWriter writer = result.getPixelWriter();

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                Color color = reader.getColor(x, y);
                double gray = (color.getRed() + color.getGreen() + color.getBlue()) / 3.0;
                writer.setColor(x, y, new Color(gray, gray, gray, 1.0));
            }
        }
        return result;
    }

    private WritableImage makeGrid(Image source) {
        int w = (int) source.getWidth();
        int h = (int) source.getHeight();

        PixelReader reader = source.getPixelReader();
        WritableImage result = new WritableImage(w * 2, h * 2);
        PixelWriter writer = result.getPixelWriter();

        for (int y = 0; y < h; y++) {
            for (int x = 0; x < w; x++) {
                Color color = reader.getColor(x, y);
                writer.setColor(x, y, color);
                writer.setColor(x + w, y, color);
                writer.setColor(x, h + (h - 1 - y), color);
                writer.setColor(x + w, h + (h - 1 - y), color);
            }
        }
        return result;
    }

    private WritableImage verticalShuffle(Image source, int stripWidth) {
        int width = (int) source.getWidth();
        int height = (int) source.getHeight();
        int totalStrips = width / stripWidth;

        PixelReader reader = source.getPixelReader();
        WritableImage result = new WritableImage(width, height);
        PixelWriter writer = result.getPixelWriter();

        int left = 0;
        int right = totalStrips - 1;
        int destStrip = 0;
        boolean takeFromLeft = true;

        while (left <= right) {
            int srcStrip = takeFromLeft ? left++ : right--;

            int srcX = srcStrip * stripWidth;
            int destX = destStrip * stripWidth;

            for (int col = 0; col < stripWidth; col++) {
                for (int y = 0; y < height; y++) {
                    int sx = Math.min(srcX + col, width - 1);
                    int dx = Math.min(destX + col, width - 1);
                    writer.setColor(dx, y, reader.getColor(sx, y));
                }
            }

            takeFromLeft = !takeFromLeft;
            destStrip++;
        }
        return result;
    }

    private WritableImage horizontalShuffle(Image source, int stripHeight) {
        int width = (int) source.getWidth();
        int height = (int) source.getHeight();
        int totalStrips = height / stripHeight;

        PixelReader reader = source.getPixelReader();
        WritableImage result = new WritableImage(width, height);
        PixelWriter writer = result.getPixelWriter();

        int top = 0;
        int bottom = totalStrips - 1;
        int destStrip = 0;
        boolean takeFromTop = true;

        while (top <= bottom) {
            int srcStrip = takeFromTop ? top++ : bottom--;

            int srcY = srcStrip * stripHeight;
            int destY = destStrip * stripHeight;

            for (int row = 0; row < stripHeight; row++) {
                for (int x = 0; x < width; x++) {
                    int sy = Math.min(srcY + row, height - 1);
                    int dy = Math.min(destY + row, height - 1);
                    writer.setColor(x, dy, reader.getColor(x, sy));
                }
            }

            takeFromTop = !takeFromTop;
            destStrip++;
        }
        return result;
    }
}