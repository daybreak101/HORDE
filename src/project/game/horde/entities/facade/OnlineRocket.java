package project.game.horde.entities.facade;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.geom.AffineTransform;
import java.awt.geom.Ellipse2D;

import project.game.horde.entities.Entity;
import project.game.horde.entities.bullets.Explosion;
import project.game.horde.entities.creatures.Player;
import project.game.horde.entities.creatures.Zombie;
import project.game.horde.entities.statics.InteractableStaticEntity;
import project.game.horde.entities.statics.Wall;
import project.game.horde.graphics.Assets;
import project.game.horde.main.Handler;

public class OnlineRocket extends Entity {
	protected float mouseX, mouseY;
	protected Rectangle cb;
	protected float speed;
	protected float xMove, yMove, angle;
	protected int range, rangeCounter;
	protected boolean isUpgraded;

	//online player bullet, just for visuals
	public OnlineRocket(Handler handler, float x, float y, int z, int range, float angle, boolean isUpgraded) {
		super(handler, x, y, z, 5, 5);	
		bounds = new Rectangle(0, 0, 0, 0);
		this.handler = handler;
		this.range = range;
		this.rangeCounter = 0;
		this.isUpgraded = isUpgraded;
		this.speed = 30;
		//for some reason 0 deg is pointing north.
		//traditionally it is pointing left.
		//subtracting by 90 ensures it is in traditional radian format
		this.angle = (float) Math.toRadians(angle - 90);
		xMove = (float) Math.cos(this.angle);
		yMove = (float) Math.sin(this.angle);
	}
	
	
	public float getAngle() {
		return angle;
	}

	public void setAngle(float angle) {
		this.angle = angle;

	}

	int travelTicker = 0;

	@Override
	public void tick() {
		// if bullet hits a rock, it should end there, since it cannot penetrate it
		while (travelTicker < speed) {
			moveX();
			moveY();
			travelTicker++;
			if(checkForImpact()) {
				//System.out.println("impacted");
				handler.getWorld().getEntityManager().addExplosion(new Explosion(handler, x - 150, y - 150, z, 300, 300, isUpgraded));

				break;	
			}			
		}
		travelTicker = 0;
		die(null);
		postTick();
	}

	public void postTick() {

	}

	public void moveX() {
		x += xMove;
	}

	public void moveY() {
		y += yMove;
	}

	@Override
	public void render(Graphics g) {
		Graphics2D g2d = (Graphics2D) g;
		AffineTransform old = g2d.getTransform();
		int dx = 0;
		int dy = 0;
		g2d.rotate(angle - Math.PI/2, x - handler.getGameCamera().getxOffset(), y - handler.getGameCamera().getyOffset());
		g.drawImage(Assets.rpg_rocket,
				Math.round(x - handler.getGameCamera().getxOffset()) + dx,
				Math.round(y - handler.getGameCamera().getyOffset()) + dy, 10, 50, null);
		g.fillRect((int) (x - handler.getGameCamera().getxOffset()), (int) (y - handler.getGameCamera().getyOffset()),
				width, height);
		g2d.setTransform(old);
//		if (isUpgraded)
//			g.setColor(new Color(255, 160, 240));
//		else
//			g.setColor(Color.yellow);
//		g.fillRect((int) (x - handler.getGameCamera().getxOffset()), (int) (y - handler.getGameCamera().getyOffset()),
//				width, height);
	}
	
	@Override
	public void renderBW(Graphics g) {
		if (isUpgraded)
			g.setColor(new Color(198,198,198));
		else
			g.setColor(new Color(225,225,225));
		g.fillRect((int) (x - handler.getGameCamera().getxOffset()), (int) (y - handler.getGameCamera().getyOffset()),
				width, height);
	}

	public boolean checkForImpact() {
		//BSystem.out.println("checking for impact");
		cb = new Rectangle((int) (x + bounds.x - 1), (int) (y + bounds.y - 1), bounds.width + 1, bounds.height + 1);

		for (Zombie e : handler.getWorld().getEntityManager().getZombies()) {
			if (e.getHitBox(0, 0).intersects(cb)) {
				handler.getWorld().getEntityManager().getEntities().remove(this);
				return true;
			}
		}
		for (InteractableStaticEntity e : handler.getWorld().getEntityManager().getInteractables()) {
			if (!handler.getWorld().getEntityManager().getBarriers().contains(e)
					&& e.getCollisionBounds(0, 0).intersects(cb)) {
				handler.getWorld().getEntityManager().getEntities().remove(this);
				return true;
			}
		}
		for (Wall e : handler.getWorld().getEntityManager().getWalls()) {
			if (e.getCollisionBounds(0, 0).intersects(cb)) {
				handler.getWorld().getEntityManager().getEntities().remove(this);
				return true;
			}
		}


		return false;
	}

	// bullet automatically dies if it goes off of screen
	@Override
	public void die(Player player) {
		rangeCounter++;
		if (rangeCounter >= range) {
			handler.getWorld().getEntityManager().getEntities().remove(this);
		}
	}

	public float getMouseX() {
		return mouseX;
	}

	public void setMouseX(float mouseX) {
		this.mouseX = mouseX;
	}

	public float getMouseY() {
		return mouseY;
	}

	public void setMouseY(float mouseY) {
		this.mouseY = mouseY;
	}

}
