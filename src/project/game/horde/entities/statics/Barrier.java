package project.game.horde.entities.statics;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Polygon;
import java.awt.Rectangle;
import java.awt.geom.Point2D;

import project.game.horde.entities.creatures.Player;
import project.game.horde.main.Handler;
import project.game.horde.sounds.InteractSounds;
import project.game.horde.sounds.Sounds;

public class Barrier extends InteractableStaticEntity {

	private int health;
	private boolean isBroken, cantAfford;
	private int length, whatWall;
	private Rectangle playerBarrier;

	public Barrier(Handler handler, int id, float x, float y, int z, int whatWall) {
		super(handler, id, x, y, z, 100, 100);
		isBroken = false;
		cantAfford = false;
		length = 100;
		health = 100;
		this.whatWall = whatWall;

		triggerText = "Press F to repair barricade";
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
		playerBarrier = new Rectangle((int) (x + bounds.x), (int) (y + bounds.y), bounds.width, bounds.height);
		System.out.println("x: " + playerBarrier.x);
		System.out.println("y: " + playerBarrier.y);
		System.out.println("width: " + playerBarrier.width);
		System.out.println("height: " + playerBarrier.height);
	}

	public void takeDamage(int damage) {
		health -= damage;
	}

	@Override
	public void render(Graphics g) {
		if (isBroken) {
			g.setColor(new Color(153, 102, 51));
			g.fillRect((int) (x + bounds.x + playerBarrier.width / 2 - handler.getGameCamera().getxOffset()),
					(int) (y + bounds.y - handler.getGameCamera().getyOffset()), playerBarrier.width / 3,
					playerBarrier.height / 5);
			g.fillRect((int) (x + bounds.x + playerBarrier.width / 4 - handler.getGameCamera().getxOffset()),
					(int) (y + 5 + bounds.y - handler.getGameCamera().getyOffset()), playerBarrier.width / 3,
					playerBarrier.height / 5);

			g.fillRect((int) (x + bounds.x + playerBarrier.width / 2 - handler.getGameCamera().getxOffset()),
					(int) (y + 20 + bounds.y - handler.getGameCamera().getyOffset()), playerBarrier.width / 3,
					playerBarrier.height / 5);
			g.fillRect((int) (x - 30 + bounds.x - handler.getGameCamera().getxOffset()),
					(int) (y + 20 + bounds.y - handler.getGameCamera().getyOffset()), 2 * playerBarrier.width / 3,
					playerBarrier.height / 5);
		} else {

			g.setColor(new Color(153, 102, 51));
			g.fillRect((int) (x + bounds.x - handler.getGameCamera().getxOffset()),
					(int) (y + bounds.y - handler.getGameCamera().getyOffset()), bounds.width, bounds.height);
			//renderPeripherals3(g);
		}
	}
	
	@Override
	public void renderBW(Graphics g) {
		if (isBroken) {
			g.setColor(new Color(112,112,112));
			g.fillRect((int) (x + bounds.x + playerBarrier.width / 2 - handler.getGameCamera().getxOffset()),
					(int) (y + bounds.y - handler.getGameCamera().getyOffset()), playerBarrier.width / 3,
					playerBarrier.height / 5);
			g.fillRect((int) (x + bounds.x + playerBarrier.width / 4 - handler.getGameCamera().getxOffset()),
					(int) (y + 5 + bounds.y - handler.getGameCamera().getyOffset()), playerBarrier.width / 3,
					playerBarrier.height / 5);

			g.fillRect((int) (x + bounds.x + playerBarrier.width / 2 - handler.getGameCamera().getxOffset()),
					(int) (y + 20 + bounds.y - handler.getGameCamera().getyOffset()), playerBarrier.width / 3,
					playerBarrier.height / 5);
			g.fillRect((int) (x - 30 + bounds.x - handler.getGameCamera().getxOffset()),
					(int) (y + 20 + bounds.y - handler.getGameCamera().getyOffset()), 2 * playerBarrier.width / 3,
					playerBarrier.height / 5);
		} else {

			g.setColor(new Color(112,112,112));
			g.fillRect((int) (x + bounds.x - handler.getGameCamera().getxOffset()),
					(int) (y + bounds.y - handler.getGameCamera().getyOffset()), bounds.width, bounds.height);
			//renderPeripherals3(g);
		}
	}

	public void renderPeripherals3(Graphics g, Player player) {
		g.setColor(new Color(30, 50, 40, 255));

		// intialize useful points
		float playerX = (player.getX() + player.getWidth() / 2);
		float playerY = (player.getY() + player.getHeight() / 2);
		Point2D.Float p1 = new Point2D.Float(x, y), // top left
				p2 = new Point2D.Float(x + bounds.width, y), // top right
				p3 = new Point2D.Float(x, y + bounds.height), // top left
				p4 = new Point2D.Float(x + bounds.width, y + bounds.height), // top right
				// poly 1 and 2 are points on the wall, 3 and 4 are world borders
				poly1 = null, poly2 = null, poly3 = null, poly4 = null;

		// find polygon points
		float tanOfAngle, oppositeLength;

		// quadrent 1,2,3
		if (playerY < y + height / 2) {
			poly1 = p1;
			poly2 = p2;
			tanOfAngle = (poly2.x - playerX) / (poly2.y - playerY);
			oppositeLength = (height) * tanOfAngle;
			poly3 = new Point2D.Float(p4.x + oppositeLength, p4.y);

			tanOfAngle = (playerX - poly1.x) / (poly1.y - playerY);
			oppositeLength = (height) * tanOfAngle;
			poly4 = new Point2D.Float(p3.x - oppositeLength, p3.y);
		} else if (playerY == y + bounds.height / 2) {
			return;
		}
		// quadrent 6,7,8
		else if (playerY > y + height / 2) {
			poly1 = p4;
			poly2 = p3;
			tanOfAngle = (poly1.x - playerX) / (playerY - poly1.y);
			oppositeLength = height* tanOfAngle;
			poly4 = new Point2D.Float(p2.x + oppositeLength, p2.y);
			tanOfAngle = (playerX - poly2.x) / (playerY - poly2.y);
			oppositeLength = height * tanOfAngle;
			poly3 = new Point2D.Float(p1.x - oppositeLength, p1.y);

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

	public void fulfillInteraction(Player player) {
		if(usedByOtherPlayer) {
			
		}
		else if (cooldownTimer >= cooldown && (health < 100)) {
			cooldownTimer = 0;
			if (player.getInv().purchase(50)) {
				Sounds.playClip(InteractSounds.PURCHASE_ID, 1, 1, false);
				sendInteractableBusy();
				health = 100;
				handler.getProgression().gainXP(5);
				cantAfford = false;
			} else {
				Sounds.playClip(InteractSounds.CANTAFFORD_ID, 1, 1, false);
				cantAfford = true;
				cooldownTimer = 0;
			}

		}

	}

	@Override
	public void postTick() {
		if(usedByOtherPlayer) {
			health = 100;
			usedByOtherPlayer = false;
		}
		else if (cantAfford && cooldownTimer < cooldown) {
			triggerText = "Not enough points!";
		} else if (health == 100 && cooldownTimer < cooldown) {
			triggerText = "Already repaired!";
		} else if (cooldownTimer >= cooldown) {
			triggerText = "Press F to repair barricade: 50";
		} else {
			triggerText = "";
		}

		if (health <= 0) {
			isBroken = true;
			bounds.x = 0;
			bounds.y = 0;
			bounds.width = 0;
			bounds.height = 0;
		} else {
			isBroken = false;
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
		}
	}

	public boolean getIsBroken() {
		return isBroken;
	}

	public Rectangle getPlayerBarrier() {
		return playerBarrier;
	}
}
