package project.game.horde.vfx;

import java.awt.*;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;

import project.game.horde.main.Handler;

public class LightSource {

	private float x, y;
	private int z;
	private int radius;
	private int intensity;
	private BufferedImage lightImage;
	private Handler handler;

	// 0.0 - 1.0
	public LightSource(Handler handler, int x, int y, int z, int radius, int intensity) {
		this.handler = handler;
		this.x = x;
		this.y = y;
		this.z = z;
		this.radius = radius;
		this.intensity = intensity;
		//intensity is out of 255, alpha value
//        this.lightImage = updateLightImage(handler, new Color(0, 0, 0, 0));
	}
	
	public void updateLightImage(Graphics2D g2d, Color transparentColor) {
		// Define the colors for the radial gradient
		float[] fractions = { 0f, 1f };
		Color[] colors = { transparentColor, new Color(0, 0, 0, 200) };

		// Create a RadialGradientPaint
		RadialGradientPaint radialGradient = new RadialGradientPaint(x, y, radius, fractions, colors);

		// Set the paint of the Graphics2D object to the radial gradient
		g2d.setPaint(radialGradient);

		// Draw the radial gradient
		g2d.fillOval((int) (x - radius), (int) (y - radius), 2 * radius, 2 * radius);
	}

//	public static BufferedImage createLightImage(int radius, float intensity) {
//		BufferedImage lightImage = new BufferedImage(radius * 2, radius * 2, BufferedImage.TYPE_INT_ARGB);
//		Graphics2D g2d = (Graphics2D) lightImage.getGraphics();
//
//		// Create a radial gradient for the light source
//		Point2D center = new Point2D.Float(radius, radius);
//		float[] dist = { 0.0f, 0.5f, 1.0f };
//		Color[] colors = { 
//				new Color(0, 0, 0, intensity),
//				new Color(0, 0, 0, intensity / 2),
//				new Color(0, 0, 0, 0) };
//		RadialGradientPaint paint = new RadialGradientPaint(center, radius, dist, colors);
//
//		g2d.setPaint(paint);
//		g2d.fillRect(0, 0, radius * 2, radius * 2);
//		g2d.dispose();
//
//		return lightImage;
//	}

	public void render(Graphics g) {
		// Additive blending
		//g.drawImage(lightImage, (int) (x - radius - handler.getGameCamera().getxOffset()), (int) (y - radius - handler.getGameCamera().getyOffset()), null);
		//((Graphics2D) g).setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1.0f));

		Graphics2D g2d = (Graphics2D) g.create();
		g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1.0f));
		g2d.drawImage(lightImage, (int) (x - radius - handler.getGameCamera().getxOffset()), (int) (y - radius - handler.getGameCamera().getyOffset()), null);
		g2d.dispose();
	}

	public float getX() {
		return x;
	}

	public float getY() {
		return y;
	}
	
	public int getZ() {
		return z;
	}

	public int getRadius() {
		return radius;
	}

	public BufferedImage getLightImage() {
		return lightImage;
	}

	public int getIntensity() {
		// TODO Auto-generated method stub
		return (int) intensity;
	}
}