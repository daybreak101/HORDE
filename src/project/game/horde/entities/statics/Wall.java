package project.game.horde.entities.statics;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Polygon;
import java.awt.geom.Point2D;
import java.awt.Graphics2D;

import project.game.horde.entities.creatures.Player;
import project.game.horde.main.Handler;

public class Wall extends InteractableStaticEntity {

	int whatWall;
	boolean wallVisible;
	boolean showCoords;

	// 0 = horizontal up
	// 1 = horizontal down
	// 2 = vertical right
	// 3 = vertical left
	public Wall(Handler handler, int id, float x, float y, int z, int length, int whatWall) {
		super(handler, id, x, y, z, 100, 100);
		this.whatWall = whatWall;
		if (whatWall == 0) {
			bounds.x = 0;
			bounds.y = 75;
			bounds.width = length;
			bounds.height = 25;
		} else if (whatWall == 1) {
			bounds.x = 0;
			bounds.y = 0;
			bounds.width = length;
			bounds.height = 25;
		} else if (whatWall == 2) {
			bounds.x = 75;
			bounds.y = 0;
			bounds.width = 25;
			bounds.height = length;
		} else if (whatWall == 3) {
			bounds.x = 0;
			bounds.y = 0;
			bounds.width = 25;
			bounds.height = length;
		}
		width = bounds.width;
		height = bounds.height;
		
		wallVisible = true;
		showCoords = false;
	}

	@Override
	public void render(Graphics g) {
	    Graphics2D g2d = (Graphics2D) g;
		g2d.setColor(new Color(30, 50, 40));
		g2d.fillRect((int) (x + bounds.x - handler.getGameCamera().getxOffset()),
				(int) (y + bounds.y - handler.getGameCamera().getyOffset()), bounds.width, bounds.height);

		if (wallVisible) {
			switch (whatWall) {
			case 1:
				renderPeripherals3(g, handler.getCurrentPlayer());
				break;
			case 3:
				renderPeripherals3(g, handler.getCurrentPlayer());
				break;
			}
		}
		if(showCoords) {
			//corner 1
			g2d.setColor(Color.white);
			int newX = (int) (x + bounds.x - handler.getGameCamera().getxOffset());
			int newY = (int) (y + bounds.y - handler.getGameCamera().getyOffset());
			int xx = (int) x;
			int yy = (int) y;
			int xwidth = (int) bounds.width;
			int yheight = (int) bounds.height;

			g2d.drawString(xx + "," + yy,
					newX - 50, newY);
			g2d.drawString((xx + xwidth) + "," + yy,
					newX + bounds.width, newY);
			g2d.drawString(xx + "," + (yy + yheight),
					newX - 50, newY + bounds.height);
			g2d.drawString((xx + xwidth) + "," + (yy + yheight),
					newX + bounds.width, newY + bounds.height);
		}
	}
	

	@Override
	public void renderBW(Graphics g) {
	    Graphics2D g2d = (Graphics2D) g;
		g2d.setColor(new Color(42,42,42));
		g2d.fillRect((int) (x + bounds.x - handler.getGameCamera().getxOffset()),
				(int) (y + bounds.y - handler.getGameCamera().getyOffset()), bounds.width, bounds.height);

		if (wallVisible) {
			switch (whatWall) {
			case 1:
				renderPeripherals3(g, handler.getCurrentPlayer());
				break;
			case 3:
				renderPeripherals3(g, handler.getCurrentPlayer());
				break;
			}
		}
	}

	public int whatWall() {
		return whatWall;
	}
	

	public void renderPeripherals3(Graphics g, Player player) {
		g.setColor(new Color(30, 50, 40, 255));

		// intialize useful points
		float playerX = player.getCenterX();
		float playerY = player.getCenterY();
		Point2D.Float p1 = new Point2D.Float(x, y), // top left
				p2 = new Point2D.Float(x + bounds.width, y), // top right
				p3 = new Point2D.Float(x, y + bounds.height), // bottom left
				p4 = new Point2D.Float(x + bounds.width, y + bounds.height), // bottom right
				center = null, // center point based on player y
				// poly 1 and 2 are points on the wall, 3 and 4 are world borders
				poly1 = null, poly2 = null, poly3 = null, poly4 = null;

		// find polygon points
		float tanOfAngle, oppositeLength;

		if (playerY < y) {
			// quadrent 1
			if (playerX < x) {
				poly1 = p3;
				poly2 = p2;
			}
			// quadrent 2
			else if (playerX >= x && playerX <= x + bounds.width) {
				poly1 = p1;
				poly2 = p2;
			}
			// quadrent 3
			else if (playerX > x) {
				poly1 = p1;
				poly2 = p4;
			}
			if (poly1 == null) {
				poly1 = p1;
			}
			if (poly2 == null) {
				poly2 = p2;
			}
			if (poly3 == null) {
				poly3 = p3;
			}
			if (poly4 == null) {
				poly4 = p4;
			}
			tanOfAngle = (poly2.x - playerX) / (poly2.y - playerY);
			oppositeLength = (handler.getWorld().getHeight() * 100 - playerY) * tanOfAngle;
			poly3 = new Point2D.Float(playerX + oppositeLength, handler.getWorld().getHeight() * 100);

			tanOfAngle = (playerX - poly1.x) / (poly1.y - playerY);
			oppositeLength = (handler.getWorld().getHeight() * 100 - playerY) * tanOfAngle;
			poly4 = new Point2D.Float(playerX - oppositeLength, handler.getWorld().getHeight() * 100);

		} else if (playerY >= y && playerY <= y + bounds.height) {
			// quadrent 4
			if (playerX < x) {
				poly1 = p3;
				poly2 = p1;

				if (poly1 == null) {
					poly1 = p1;
				}
				if (poly2 == null) {
					poly2 = p2;
				}
				if (poly3 == null) {
					poly3 = p3;
				}
				if (poly4 == null) {
					poly4 = p4;
				}

				tanOfAngle = (playerY - poly1.y) / (poly1.x - playerX);
				oppositeLength = (handler.getWorld().getWidth() * 100 - playerX) * tanOfAngle;
				poly4 = new Point2D.Float(handler.getWorld().getWidth() * 100, playerY - oppositeLength);

				tanOfAngle = (poly2.y - playerY) / (poly2.x - playerX);
				oppositeLength = (handler.getWorld().getWidth() * 100 - playerX) * tanOfAngle;
				poly3 = new Point2D.Float(handler.getWorld().getWidth() * 100, playerY + oppositeLength);

			}
			// quadrent 5
			else if (playerX > x) {
				poly1 = p2;
				poly2 = p4;

				if (poly1 == null) {
					poly1 = p1;
				}
				if (poly2 == null) {
					poly2 = p2;
				}
				if (poly3 == null) {
					poly3 = p3;
				}
				if (poly4 == null) {
					poly4 = p4;
				}

				tanOfAngle = (poly1.y - playerY) / (playerX - poly1.x);
				oppositeLength = (playerX) * tanOfAngle;
				poly4 = new Point2D.Float(0, playerY + oppositeLength);

				tanOfAngle = (playerY - poly2.y) / (playerX - poly2.x);
				oppositeLength = (playerX) * tanOfAngle;
				poly3 = new Point2D.Float(0, playerY - oppositeLength);

			}

		} else if (playerY > y + bounds.height) {
			// quadrent 6
			if (playerX < x) {
				poly1 = p4;
				poly2 = p1;
			}
			// quadrent 7
			else if (playerX >= x && playerX <= x + bounds.width) {
				poly1 = p4;
				poly2 = p3;
			}
			// quadrent 8
			else if (playerX > x + bounds.width) {
				poly1 = p2;
				poly2 = p3;
			}

			if (poly1 == null) {
				poly1 = p1;
			}
			if (poly2 == null) {
				poly2 = p2;
			}
			if (poly3 == null) {
				poly3 = p3;
			}
			if (poly4 == null) {
				poly4 = p4;
			}

			tanOfAngle = (playerX - poly2.x) / (playerY - poly2.y);
			oppositeLength = playerY * tanOfAngle;
			poly3 = new Point2D.Float(playerX - oppositeLength, 0);

			tanOfAngle = (poly1.x - playerX) / (playerY - poly1.y);
			oppositeLength = playerY * tanOfAngle;
			poly4 = new Point2D.Float(playerX + oppositeLength, 0);
		}

		if (poly1 == null) {
			poly1 = p1;
		}
		if (poly2 == null) {
			poly2 = p2;
		}
		if (poly3 == null) {
			poly3 = p3;
		}
		if (poly4 == null) {
			poly4 = p4;
		}

		// draw polygon using points
		Polygon trapezoid = new Polygon();
		trapezoid.addPoint((int) (poly1.x - handler.getGameCamera().getxOffset()),
				(int) (poly1.y - handler.getGameCamera().getyOffset()));
		trapezoid.addPoint((int) (poly2.x - handler.getGameCamera().getxOffset()),
				(int) (poly2.y - handler.getGameCamera().getyOffset()));
		trapezoid.addPoint((int) (poly3.x - handler.getGameCamera().getxOffset()),
				(int) (poly3.y - handler.getGameCamera().getyOffset()));
		trapezoid.addPoint((int) (poly4.x - handler.getGameCamera().getxOffset()),
				(int) (poly4.y - handler.getGameCamera().getyOffset()));

		g.fillPolygon(trapezoid);
	}
	
	public void setVisible(boolean isVisible) {
		this.wallVisible = isVisible;
	}

}
