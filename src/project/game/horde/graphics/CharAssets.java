package project.game.horde.graphics;

import java.awt.image.BufferedImage;

public class CharAssets {
	private static int upscaleFactor = 3;
	private static final String folder = "/textures/normal/";
	private static final int width = 100 * upscaleFactor, height = 100 * upscaleFactor;
	
	public static BufferedImage[] harry, blueAlien, robot;
	public static BufferedImage christmasHat, reindeer, bunny;

	public static void init() {
		SpriteSheet charSheet = new SpriteSheet(ImageLoader.loadImage(folder + "player/characters.png"));
		harry = new BufferedImage[2];
		harry[0] = charSheet.crop(0, 0, width, height); 
		harry[1] = charSheet.crop(0, height, width, height); 
		
		blueAlien = new BufferedImage[2];
		blueAlien[0] = charSheet.crop(width, 0, width, height); 
		blueAlien[1] = charSheet.crop(width, height, width, height); 
		
		robot = new BufferedImage[2];
		robot[0] = charSheet.crop(2 * width, 0, width, height); 
		robot[1] = charSheet.crop(2 * width, height, width, height); 
		
		initHats();
	}
	
	public static void initHats() {
		christmasHat = ImageLoader.loadImage(folder + "hats/christmas_hat.png");
		reindeer = ImageLoader.loadImage(folder + "hats/reindeer.png");
		bunny = ImageLoader.loadImage(folder + "hats/bunny.png");

	}

}
