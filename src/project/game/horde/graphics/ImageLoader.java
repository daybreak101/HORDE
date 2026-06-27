 package project.game.horde.graphics;

import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

public class ImageLoader {
	static int upscaleFactor = 1;
	public static BufferedImage loadImage(String path) {
		System.out.println("Loading image: " + path);
		try {
			BufferedImage image = ImageIO.read(ImageLoader.class.getResource(path));
			return image;
			//return ImageUtils.upscaleImage(image, image.getWidth() * upscaleFactor, image.getHeight() * upscaleFactor);
		} catch (IOException e) {
			System.out.println("Error loading image: " + path);
			e.printStackTrace();
			System.exit(1);
		}
		return null;
	}
}
