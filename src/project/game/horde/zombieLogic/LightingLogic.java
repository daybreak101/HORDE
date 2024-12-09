package project.game.horde.zombieLogic;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import project.game.horde.main.Handler;
import project.game.horde.utils.Utils;
import project.game.horde.vfx.LightSource;
import project.game.horde.worlds.World;

public class LightingLogic {
	private Handler handler;
	private boolean lightingRendered = false;
	private BufferedImage lightingImage;
	private World world;
	
	public LightingLogic(Handler handler, World world, String lightsPath) {
		this.handler = handler;
		this.world = world;
		lightPositions = new ArrayList<LightSource>();
		
	}
	
	public void createLightSources(String lightsPath) {
		// read file
		String file = Utils.loadFileAsString(lightsPath);
		String[] tokens = file.split("\\s+");

		// get number of nodes
		int i = 0;

		// process nodes
		int x, y, radius, intensity;
		while (i < tokens.length) {
			x = Utils.parseInt(tokens[i++]);
			y = Utils.parseInt(tokens[i++]);
			radius = Utils.parseInt(tokens[i++]);
			intensity = Utils.parseInt(tokens[i++]);
			lightPositions.add(new LightSource(handler, x, y, radius, intensity));
		}
	}
	
	private void renderLighting(Graphics g) {
		if (!lightingRendered) {
			BufferedImage image = new BufferedImage(world.getWidth() * 100, world.getHeight() * 100, BufferedImage.TYPE_INT_ARGB);
			Graphics2D g2d = image.createGraphics();
			// g2d.setColor(new Color(0,0,0,200));
			// g2d.fillRect(0, 0, width * 100, height * 100);

			for (LightSource light : lightPositions) {
				// light.render(g);
//				drawRadialGradient(g2d, (int) light.getX(), (int) light.getY(), light.getRadius(),
//						new Color(0, 0, 0, 0));
				light.updateLightImage(g2d, new Color(0, 0, 0, 0));
			}

			int width = image.getWidth();
			int height = image.getHeight();

			// Iterate through each pixel
			for (int y = 0; y < height; y++) {
				for (int x = 0; x < width; x++) {
					// Check if the pixel is transparent
					int pixel = image.getRGB(x, y);
					int alpha = (pixel >> 24) & 0xff;

					if (alpha == 0) { // If alpha is 0, the pixel is transparent
						// Set the pixel to the fill color
						image.setRGB(x, y, new Color(0, 0, 0, 200).getRGB());
					}
				}
			}
			lightingImage = image;
			lightingRendered = true;
			lightPositions.clear();
		} else {
			BufferedImage image = lightingImage;
			Graphics2D g2d = image.createGraphics();
			for (LightSource light : lightPositions) {

				light.updateLightImage(g2d, new Color(0, 0, 0, 0));

				int startX = Math.max(0, (int) (light.getX() - light.getRadius()));
				int startY = Math.max(0, (int) (light.getY() - light.getRadius()));
				int endX = Math.min(image.getWidth(), startX + 2 * light.getRadius());
				int endY = Math.min(image.getHeight(), startY + 2 * light.getRadius());
				for (int y = startY; y < endY; y++) {
					for (int x = startX; x < endX; x++) {
						// Check if the pixel is transparent
						int pixel = image.getRGB(x, y);
						int alpha = (pixel >> 24) & 0xff;

						int newColor = light.getLightImage().getRGB(x - startX, y - startY);
						// if (alpha != 0) { // If alpha is 0, the pixel is transparent
						image.setRGB(x, y, newColor);
						// }
					}
				}

			}
			lightingImage = image;
		}
		g.drawImage(lightingImage, (int) -handler.getGameCamera().getxOffset(),
				(int) -handler.getGameCamera().getyOffset(), null);
	}
	
	private ArrayList<LightSource> lightPositions;

	public ArrayList<LightSource> getLightPositions() {
		return lightPositions;
	}
}
