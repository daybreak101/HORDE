package project.game.horde.entities.statics;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Polygon;
import java.awt.Rectangle;
import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;

import project.game.horde.main.Handler;

public class InvisibleBounds extends InteractableStaticEntity {

	private int orientation;
	private Polygon pBounds;
	
	public InvisibleBounds(Handler handler, int id, float x, float y, int width, int height, int orientation) {
		super(handler, id, x, y, width, height);
		this.orientation = orientation;
        Rectangle rect = new Rectangle((int) x, (int) y, width, height);
        double centerX = rect.getCenterX();
        double centerY = rect.getCenterY();
        double angle = Math.toRadians(orientation); 
        // Get the coordinates of the rectangle corners
        Point2D[] rotatedPoints = rotateRectangle(rect, centerX, centerY, angle);
        
        // Create a Polygon from the rotated points
        pBounds = new Polygon();
        for (Point2D point : rotatedPoints) {
            pBounds.addPoint((int) point.getX(), (int) point.getY());
        }

	}

    // Method to rotate the rectangle and return the new corner points
    public static Point2D[] rotateRectangle(Rectangle rect, double centerX, double centerY, double angle) {
        Point2D[] points = new Point2D[4];
        
        // Original rectangle corner points
        Point2D[] corners = {
            new Point2D.Double(rect.x, rect.y),
            new Point2D.Double(rect.x + rect.width, rect.y),
            new Point2D.Double(rect.x + rect.width, rect.y + rect.height),
            new Point2D.Double(rect.x, rect.y + rect.height)
        };
        
        // Apply rotation to each corner point
        for (int i = 0; i < 4; i++) {
            points[i] = rotatePoint(corners[i], centerX, centerY, angle);
        }
        
        return points;
    }

    // Method to rotate a single point around a center by an angle in radians
    public static Point2D rotatePoint(Point2D point, double centerX, double centerY, double angle) {
        double x = point.getX() - centerX;
        double y = point.getY() - centerY;
        
        double newX = x * Math.cos(angle) - y * Math.sin(angle) + centerX;
        double newY = x * Math.sin(angle) + y * Math.cos(angle) + centerY;
        System.out.println("x: " + x + ", y: " + y);
        System.out.println("x: " + newX + ", y: " + newY);
        return new Point2D.Double(newX, newY);
    }

	@Override
	public void render(Graphics g) {
	    Graphics2D g2d = (Graphics2D) g;
		g2d.setColor(new Color(30, 50, 40));
		
		AffineTransform originalTransform = g2d.getTransform(); // Save the current transform
        g2d.translate(x + width/2- handler.getGameCamera().getxOffset(), y + height/2- handler.getGameCamera().getyOffset()); // Move origin to the center
        g2d.rotate(Math.toRadians(orientation));               // Rotate by the specified angle
        g2d.translate(-(x + width/2- handler.getGameCamera().getxOffset()), -(y + height/2- handler.getGameCamera().getyOffset())); // Move origin back to the original position

		g2d.fillRect((int) (x + bounds.x - handler.getGameCamera().getxOffset()),
				(int) (y + bounds.y - handler.getGameCamera().getyOffset()), bounds.width, bounds.height);
		
		g2d.setTransform(originalTransform); // Restore the original transform

	
	}
	
	public Polygon getCollisionBounds() {
		return pBounds;
	}
	

	@Override
	public void renderBW(Graphics g) {
	}


}
