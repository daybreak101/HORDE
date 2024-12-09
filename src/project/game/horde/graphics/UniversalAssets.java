package project.game.horde.graphics;

import java.awt.image.BufferedImage;

public class UniversalAssets {
	private static int upscaleFactor = 3;
	private static final String folder = "/textures/universal/";
	private static final int width = 100 * upscaleFactor, height = 100 * upscaleFactor;

	public static final int POWERUPS = 4;
	public static final int DOUBLE = 0, INSTA = 1, MINIGUN = 2, INFINTE = 3; 
	public static BufferedImage blue_powerups[], cyan_powerups[], green_powerups[], magenta_powerups[], orange_powerups[], red_powerups[], white_powerups[], yellow_powerups[];

	public static void init() {
		SpriteSheet sheet;
		
		blue_powerups = new BufferedImage[POWERUPS];
		sheet = new SpriteSheet(ImageLoader.loadImage(folder + "hud_powerups/hud_powerups_blue.png"));
		for(int i = 0; i < POWERUPS; i++) {
			blue_powerups[i] = sheet.crop(i * width, 0, width, height); 
		}
		
		cyan_powerups = new BufferedImage[POWERUPS];
		sheet = new SpriteSheet(ImageLoader.loadImage(folder + "hud_powerups/hud_powerups_cyan.png"));
		for(int i = 0; i < POWERUPS; i++) {
			cyan_powerups[i] = sheet.crop(i * width, 0, width, height); 
		}
		
		green_powerups = new BufferedImage[POWERUPS];
		sheet = new SpriteSheet(ImageLoader.loadImage(folder + "hud_powerups/hud_powerups_green.png"));
		for(int i = 0; i < POWERUPS; i++) {
			green_powerups[i] = sheet.crop(i * width, 0, width, height); 
		}
		

	}
}
