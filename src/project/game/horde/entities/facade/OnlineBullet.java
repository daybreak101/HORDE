package project.game.horde.entities.facade;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;

import project.game.horde.entities.Entity;
import project.game.horde.entities.creatures.Player;
import project.game.horde.main.Handler;

public class OnlineBullet extends Entity {
	protected float mouseX, mouseY;
	protected Rectangle cb;
	protected float speed;
	protected float xMove, yMove, angle;
	protected int range, rangeCounter;
	protected boolean isUpgraded;

	//online player bullet, just for visuals
	public OnlineBullet(Handler handler, float x, float y, int range, float angle, boolean isUpgraded) {
		super(handler, x, y, 5, 5);	
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
	
	//online player shotgun pellet, just for visuals
	public OnlineBullet(Handler handler, float x, float y, int range, float angle, float radianOffset, boolean isUpgraded) {
		super(handler, x, y, 5, 5);	
		bounds = new Rectangle(0, 0, 0, 0);
		this.handler = handler;
		this.range = range;
		this.rangeCounter = 0;
		this.isUpgraded = isUpgraded;
		this.speed = 30;
		//for some reason 0 deg is pointing north.
		//traditionally it is pointing left.
		//subtracting by 90 ensures it is in traditional radian format
		this.angle = (float) Math.toRadians(angle - 90 + Math.toDegrees(radianOffset));
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
		if (isUpgraded)
			g.setColor(new Color(255, 160, 240));
		else
			g.setColor(Color.yellow);
		g.fillRect((int) (x - handler.getGameCamera().getxOffset()), (int) (y - handler.getGameCamera().getyOffset()),
				width, height);
	}

	public boolean checkForImpact() {
		//BSystem.out.println("checking for impact");
		cb = new Rectangle((int) (x + bounds.x - 1), (int) (y + bounds.y - 1), bounds.width + 1, bounds.height + 1);

		float damageMultiplier = 1;

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
