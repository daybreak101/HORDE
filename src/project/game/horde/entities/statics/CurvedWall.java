package project.game.horde.entities.statics;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Polygon;
import project.game.horde.main.Handler;
import java.awt.geom.Point2D;

public class CurvedWall extends Wall {

    private Point2D.Float startPoint;  // Start point of the arc
    private Point2D.Float endPoint;    // End point of the arc
    private Point2D.Float centerPoint; // Center of the arc (circle)
    private double radius;             // Radius of the arc (distance from center)
    private double startAngle;         // Start angle (in radians)
    private double endAngle;           // End angle (in radians)
    private Polygon arcShape;        // Polygon representing the arc for collision detection
    private int numSegments = 20;      // Number of segments to approximate the arc

    public CurvedWall(Handler handler, int id, float x, float y, 
                      float startX, float startY, float endX, float endY) {
        super(handler, id, x, y, 0, 0);  // Set default values as needed

        // Initialize start and end points
        this.startPoint = new Point2D.Float(startX, startY);
        this.endPoint = new Point2D.Float(endX, endY);
        
        // Calculate the center of the arc (midpoint of start and end points)
        this.centerPoint = new Point2D.Float((startX + endX) / 2, (startY + endY) / 2);

        // Calculate the radius (distance from center to start or end point)
        this.radius = startPoint.distance(centerPoint);

        // Calculate the angles from the center to the start and end points
        this.startAngle = Math.atan2(startY - centerPoint.y, startX - centerPoint.x);
        this.endAngle = Math.atan2(endY - centerPoint.y, endX - centerPoint.x);

        // Generate the polygon that represents the arc's collision box
        generateArcPolygon();
    }

    // Generate the polygon approximation of the arc for collision detection
    private void generateArcPolygon() {
        arcShape = new Polygon();
        
        // Divide the arc into small segments (more segments = more accurate)
        for (int i = 0; i <= numSegments; i++) {
            // Calculate the angle of each point on the arc
            double angle = startAngle + (endAngle - startAngle) * i / numSegments;
            
            // Convert polar coordinates to Cartesian
            int xPoint = (int) (centerPoint.x + radius * Math.cos(angle));
            int yPoint = (int) (centerPoint.y + radius * Math.sin(angle));
            
            // Add the point to the polygon
            arcShape.addPoint(xPoint, yPoint);
        }
    }

    @Override
    public void render(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        g2d.setColor(new Color(30, 50, 40)); // Color of the wall

	    // Offset values
	    int offsetX = (int) handler.getGameCamera().getxOffset();
	    int offsetY = (int) handler.getGameCamera().getyOffset();

	    // Create a new array to store the offsetted points
	    int[] offsetXPoints = new int[arcShape.npoints];
	    int[] offsetYPoints = new int[arcShape.npoints];

	    // Apply the offset to each point in the floorShape
	    for (int i = 0; i < arcShape.npoints; i++) {
	        offsetXPoints[i] = arcShape.xpoints[i] - offsetX;
	        offsetYPoints[i] = arcShape.ypoints[i] - offsetY;
	    }

	    // Create a new Polygon with the offset points
	    Polygon offsetPolygon = new Polygon(offsetXPoints, offsetYPoints, arcShape.npoints);

	    // Render the floor shape by drawing the polygon using the offset points
	    g2d.fillPolygon(offsetPolygon); // Draw the polygon outline
	    

    }

    @Override
    public void renderBW(Graphics g) {
    }

    // Getter for the collision polygon
    public Polygon getArcPolygon() {
        return arcShape;
    }

    // Setter for the collision polygon (if you want to modify it dynamically)
    public void setArcPolygon(Polygon arcPolygon) {
        this.arcShape = arcPolygon;
    }

    // Getter and Setter for arc properties
    public Point2D.Float getStartPoint() {
        return startPoint;
    }

    public void setStartPoint(Point2D.Float startPoint) {
        this.startPoint = startPoint;
        // Recalculate the radius and angles
        this.radius = centerPoint.distance(startPoint);
        this.startAngle = Math.atan2(startPoint.y - centerPoint.y, startPoint.x - centerPoint.x);
        generateArcPolygon();  // Regenerate the polygon if start point changes
    }

    public Point2D.Float getEndPoint() {
        return endPoint;
    }

    public void setEndPoint(Point2D.Float endPoint) {
        this.endPoint = endPoint;
        // Recalculate the radius and angles
        this.radius = centerPoint.distance(endPoint);
        this.endAngle = Math.atan2(endPoint.y - centerPoint.y, endPoint.x - centerPoint.x);
        generateArcPolygon();  // Regenerate the polygon if end point changes
    }
}
