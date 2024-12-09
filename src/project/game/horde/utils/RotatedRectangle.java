package project.game.horde.utils;

import java.awt.Rectangle;
import java.awt.Point;
import java.awt.geom.AffineTransform;

public class RotatedRectangle extends Rectangle {
	   private double angle;  // Rotation angle in radians

	    public RotatedRectangle(int x, int y, int width, int height, int angleInDegrees) {
	        super(x, y, width, height);
	        this.angle = Math.toRadians(angleInDegrees); 
	    }

	    // Method to set the rotation angle (in radians)
	    public void rotate(int angleInDegrees) {
	    	 this.angle = Math.toRadians(angleInDegrees); 
	    }

	    // Get the rotated bounding box
	    public Rectangle getRotatedBounds() {
	        // Calculate the center of the rectangle
	        Point center = new Point(x + width / 2, y + height / 2);

	        // Create an AffineTransform and rotate around the center point
	        AffineTransform transform = new AffineTransform();
	        transform.rotate(angle, center.x, center.y);

	        // Get the coordinates of the corners of the rectangle
	        Point[] corners = new Point[4];
	        corners[0] = new Point(x, y);  // Top-left
	        corners[1] = new Point(x + width, y);  // Top-right
	        corners[2] = new Point(x, y + height);  // Bottom-left
	        corners[3] = new Point(x + width, y + height);  // Bottom-right

	        // Apply the transformation to each corner
	        for (int i = 0; i < 4; i++) {
	            transform.transform(corners[i], corners[i]);
	        }

	        // Find the bounding box that contains all the rotated corners
	        int minX = Integer.MAX_VALUE, minY = Integer.MAX_VALUE;
	        int maxX = Integer.MIN_VALUE, maxY = Integer.MIN_VALUE;
	        for (Point corner : corners) {
	            minX = Math.min(minX, corner.x);
	            minY = Math.min(minY, corner.y);
	            maxX = Math.max(maxX, corner.x);
	            maxY = Math.max(maxY, corner.y);
	        }

	        // Return a new rectangle that bounds the rotated corners
	        return new Rectangle(minX, minY, maxX - minX, maxY - minY);
	    }

	    // Check if this rotated rectangle intersects with another rotated rectangle
	    public boolean intersects(RotatedRectangle other) {
	        Rectangle thisBounds = getRotatedBounds();
	        Rectangle otherBounds = other.getRotatedBounds();
	        return thisBounds.intersects(otherBounds);
	    }
}
