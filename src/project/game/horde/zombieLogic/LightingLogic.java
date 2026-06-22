package project.game.horde.zombieLogic;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.awt.GradientPaint;
import java.awt.AlphaComposite;

import project.game.horde.main.Handler;
import project.game.horde.utils.Utils;
import project.game.horde.vfx.LightSource;
import project.game.horde.worlds.World;

public class LightingLogic {
	private Handler handler;
	private BufferedImage shadowMap;
	private World world;
	private ArrayList<LightSource> lightPositions;

	public LightingLogic(Handler handler, World world, String lightsPath) {
		this.handler = handler;
		this.world = world;
		lightPositions = new ArrayList<LightSource>();
		shadowMap = new BufferedImage(handler.getWidth(), handler.getHeight(),
				BufferedImage.TYPE_INT_ARGB);
//		shadowMap = new BufferedImage(handler.getGame().getWidth(), handler.getGame().getHeight(),
//				BufferedImage.TYPE_INT_ARGB);
		createLightSources(lightsPath);
	}

	public void createLightSources(String lightsPath) {
		// read file
		String file = Utils.loadFileAsString(lightsPath);
		String[] tokens = file.split("\\s+");

		// get number of nodes
		int i = 0;

		// process nodes
		int x, y, z, radius, intensity;
		while (i < tokens.length) {
			x = Utils.parseInt(tokens[i++]);
			y = Utils.parseInt(tokens[i++]);
			z = Utils.parseInt(tokens[i++]);
			radius = Utils.parseInt(tokens[i++]);
			intensity = Utils.parseInt(tokens[i++]);
			lightPositions.add(new LightSource(handler, x, y, z, radius, intensity));
		}
	}

	public void renderLighting(Graphics g) {
		for (LightSource light : lightPositions) {

			// Iterate over the shadow map pixels in the light's area
			for (int y = (int) (light.getY() - light.getRadius()); y < (int) (light.getY() + light.getRadius()); y++) {
				for (int x = (int) (light.getX() - light.getRadius()); x < (int) (light.getX()
						+ light.getRadius()); x++) {
					System.out.println("X: " + x + ", y: " + y);
					// Calculate the distance from the center of the light source
					double dist = Math.sqrt(Math.pow(x - light.getX(), 2) + Math.pow(y - light.getY(), 2));

					// If the pixel is within the light's radius, modify its alpha value
					if (dist <= light.getRadius()) {
						// Determine the alpha based on distance (fading effect)
						int fadeAlpha = (int) Math.max(0,
								light.getIntensity() - (dist / light.getRadius()) * light.getIntensity());

						// Get the current pixel color at (x, y)
						int currentColor = shadowMap.getRGB(x, y);
						int currentAlpha = (currentColor >> 24) & 0xFF;

						// Mix the current shadow color with the new alpha value
						int newAlpha = Math.min(currentAlpha, fadeAlpha); // Don't increase alpha beyond the original
																			// shadow opacity

						// Rebuild the color with the new alpha value and the original RGB components
						int newColor = (newAlpha << 24) | (currentColor & 0x00FFFFFF);

						// Update the shadow map pixel with the new color
						shadowMap.setRGB(x, y, newColor);
					}
				}
			}
		}
		// Now, draw the shadow map with lighting effects over the existing scene
		g.drawImage(shadowMap,
				(int) (handler.getCurrentPlayer().getCenterX() - handler.getGame().getWidth() / 2
						- handler.getGameCamera().getxOffset()),
				(int) (handler.getCurrentPlayer().getCenterY() - handler.getGame().getHeight() / 2
						- handler.getGameCamera().getyOffset()),
				null);

	}

	public ArrayList<LightSource> getLightPositions() {
		return lightPositions;
	}
}
