package project.game.horde.entities.facade;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.Shape;
import java.awt.geom.AffineTransform;
import java.awt.geom.Path2D;

import project.game.horde.entities.Entity;
import project.game.horde.entities.creatures.Player;
import project.game.horde.entities.statics.Barrier;
import project.game.horde.entities.statics.InteractableStaticEntity;
import project.game.horde.entities.statics.Wall;
import project.game.horde.graphics.Assets;
import project.game.horde.main.Handler;
import project.game.horde.utils.RandomUtil;
import project.game.horde.utils.Timer;

public class OnlineFlameBullet extends Entity {
	protected float mouseX, mouseY;
//	protected Rectangle cb;
	protected float speed;
	protected float xMove, yMove, angle;
	protected int range, rangeCounter;
	protected boolean isUpgraded;
	int frame;
	private Shape cb;
	int currentAlpha = 0;

	public OnlineFlameBullet(Handler handler, float x, float y, int range, float angle, boolean isUpgraded) {
		super(handler, x, y, 5, 5);
		frame = RandomUtil.nextInt(0, 13);
		bounds = new Rectangle(0, 0, 0, 0);
		width = 200;
		height = 70;
		this.handler = handler;
		this.range = range;
		this.rangeCounter = 0;
		this.isUpgraded = isUpgraded;
		this.speed = 1;
		// for some reason 0 deg is pointing north.
		// traditionally it is pointing left.
		// subtracting by 90 ensures it is in traditional radian format
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
		while (checkForWallImpact()) {
			width -= 10;
		}
		while (travelTicker < speed) {
			moveX();
			moveY();
			travelTicker++;
			if (checkForImpact()) {
				// System.out.println("impacted");
				break;
			}
			width -= 10;
		}
		travelTicker = 0;
		die(null);
		if (currentAlpha < 24)
			currentAlpha++;
		// postTick();
	}

	public void postTick() {

	}

	public void moveX() {
		x += xMove;
	}

	public void moveY() {
		y += yMove;
	}

	private Shape getRotatedRectangle(float x, float y, int width, int height, float angle) {
		// Create a rectangle
		Rectangle rect = new Rectangle((int) x, (int) y, width, height);

		// Create an AffineTransform instance
		AffineTransform transform = new AffineTransform();
		transform.rotate(angle, rect.getX(), rect.getY() + rect.height / 2);

		// Create a Path2D object from the rectangle
		Path2D path = new Path2D.Double();
		path.append(rect, false);

		// Apply the transformation to the path
		Shape rotatedRect = transform.createTransformedShape(path);
		return rotatedRect;
	}

	Timer waitToRender = new Timer(2);

	@Override
	public void render(Graphics g) {
		if (waitToRender.checkIsReady()) {
			Graphics2D g2d = (Graphics2D) g;
			AffineTransform old = g2d.getTransform();
			int dy = -50;
			int dx = 40;
			g2d.rotate(angle, x - handler.getGameCamera().getxOffset(), y - handler.getGameCamera().getyOffset());
			if (isUpgraded) {
				g2d.drawImage(Assets.upgraded_flamethrower_bullet[frame][currentAlpha],
						Math.round(x - handler.getGameCamera().getxOffset()) + dx,
						Math.round(y - handler.getGameCamera().getyOffset()) + dy, width, 100, null);
			} else {
				g2d.drawImage(Assets.flamethrower_bullet[frame][currentAlpha],
						Math.round(x - handler.getGameCamera().getxOffset()) + dx,
						Math.round(y - handler.getGameCamera().getyOffset()) + dy, width, 100, null);
			}

			g2d.setTransform(old);
		} else {
			waitToRender.tick();
		}
	}


	public boolean checkForImpact() {
		cb = getRotatedRectangle(x, y - height / 2, width, height, angle);

		return false;
	}

	public boolean checkForWallImpact() {
		cb = getRotatedRectangle(x, y - height / 2, width, height, angle);
		for (InteractableStaticEntity e : handler.getWorld().getEntityManager().getInteractables()) {
			if (
				!(e instanceof Barrier)
				//!handler.getWorld().getEntityManager().getBarriers().contains(e)
					&& cb.intersects(e.getCollisionBounds(0, 0))) {
				return true;
			}
		}
		for (Wall e : handler.getWorld().getEntityManager().getWalls()) {
			if (cb.intersects(e.getCollisionBounds(0, 0))) {
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
