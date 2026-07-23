package project.game.horde.graphics;

import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.awt.image.ColorConvertOp;
import java.awt.image.WritableRaster;

public class ImageUtils {

    public static BufferedImage upscaleImage(BufferedImage originalImage, int newWidth, int newHeight) {
        BufferedImage scaledImage = new BufferedImage(newWidth, newHeight, originalImage.getType());
        Graphics2D g2d = scaledImage.createGraphics();
        g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
        g2d.setRenderingHint(RenderingHints.KEY_ALPHA_INTERPOLATION, RenderingHints.VALUE_ALPHA_INTERPOLATION_QUALITY);
        g2d.setRenderingHint(RenderingHints.KEY_COLOR_RENDERING, RenderingHints.VALUE_COLOR_RENDER_QUALITY);
        g2d.setRenderingHint(RenderingHints.KEY_DITHERING, RenderingHints.VALUE_DITHER_ENABLE);
        g2d.drawImage(originalImage, 0, 0, newWidth, newHeight, null);
        g2d.dispose();
        return scaledImage;
    }

    public static BufferedImage thresholdImage(BufferedImage image, int threshold) {
        BufferedImage result = new BufferedImage(image.getWidth(), image.getHeight(), BufferedImage.TYPE_BYTE_GRAY);
        result.getGraphics().drawImage(image, 0, 0, null);
        WritableRaster raster = result.getRaster();
        int[] pixels = new int[image.getWidth()];

        for (int y = 0; y < image.getHeight(); y++) {
            raster.getPixels(0, y, image.getWidth(), 1, pixels);
            for (int i = 0; i < pixels.length; i++) {
                if (pixels[i] < threshold) {
                    pixels[i] = 0;
                } else {
                    pixels[i] = 255;
                }
            }
            raster.setPixels(0, y, image.getWidth(), 1, pixels);
        }
        return result;
    }

    public static BufferedImage blackAndWhite(BufferedImage image) {
        BufferedImage result = new BufferedImage(image.getWidth(), image.getHeight(), BufferedImage.TYPE_BYTE_GRAY);
        ColorConvertOp op = new ColorConvertOp(
                image.getColorModel().getColorSpace(),
                result.getColorModel().getColorSpace(), null);
        op.filter(image, result);

        return result;
    }

    public static BufferedImage makeWhite(BufferedImage src) {
    BufferedImage dst = new BufferedImage(
        src.getWidth(),
        src.getHeight(),
        BufferedImage.TYPE_INT_ARGB
    );

    for (int y = 0; y < src.getHeight(); y++) {
        for (int x = 0; x < src.getWidth(); x++) {
            int argb = src.getRGB(x, y);

            int alpha = (argb >>> 24) & 0xFF;

            if (alpha == 0)
                continue;

            dst.setRGB(x, y, (alpha << 24) | 0xFFFFFF);
        }
    }

    return dst;
}

    public static BufferedImage fadeImageAlpha(BufferedImage image, float alphaDecrement) {
        int width = image.getWidth();
        int height = image.getHeight();

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                int rgba = image.getRGB(x, y);
                int alpha = (rgba >> 24) & 0xff;
                int red = (rgba >> 16) & 0xff;
                int green = (rgba >> 8) & 0xff;
                int blue = rgba & 0xff;

                alpha -= alphaDecrement;
                if (alpha < 0) {
                    alpha = 0;
                }

                rgba = (alpha << 24) | (red << 16) | (green << 8) | blue;
                image.setRGB(x, y, rgba);
            }
        }
        return image;
    }

    public static BufferedImage rotate(BufferedImage image, double degrees) {

        double radians = Math.toRadians(degrees);

        double sin = Math.abs(Math.sin(radians));
        double cos = Math.abs(Math.cos(radians));

        int w = image.getWidth();
        int h = image.getHeight();

        int newW = (int) Math.floor(w * cos + h * sin);
        int newH = (int) Math.floor(h * cos + w * sin);

        BufferedImage rotated = new BufferedImage(
                newW,
                newH,
                BufferedImage.TYPE_INT_ARGB);

        Graphics2D g2 = rotated.createGraphics();
        AffineTransform at = new AffineTransform();

        // Move origin to center of new image
        at.translate(newW / 2.0, newH / 2.0);

        // Rotate
        at.rotate(radians);

        // Draw original centered
        at.translate(-w / 2.0, -h / 2.0);

        g2.drawImage(image, at, null);
        g2.dispose();

        return rotated;
    }

}
