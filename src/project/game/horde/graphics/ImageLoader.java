 package project.game.horde.graphics;

import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

public class ImageLoader {
	static int upscaleFactor = 1;
	public static BufferedImage loadImage(String path) {
		try {
			BufferedImage image = ImageIO.read(ImageLoader.class.getResource(path));
			return ImageUtils.upscaleImage(image, image.getWidth() * upscaleFactor, image.getHeight() * upscaleFactor);
		} catch (IOException e) {
			System.exit(1);
		}
		return null;
	}
}
