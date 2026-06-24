package project.game.horde.entities.facade;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.geom.Ellipse2D;

import project.game.horde.entities.bullets.Explosion;
import project.game.horde.entities.creatures.Player;
import project.game.horde.entities.creatures.Zombie;
import project.game.horde.entities.statics.Barrier;
import project.game.horde.entities.statics.InteractableStaticEntity;
import project.game.horde.entities.statics.Wall;
import project.game.horde.main.Handler;
import project.game.horde.sounds.GunSounds;
import project.game.horde.sounds.Sounds;
import project.game.horde.utils.Utils;

public class OnlineGrenade extends OnlineBullet {
	private int counter, timer, explosionTimer;
	private Ellipse2D explosionRadius;
	private Color color;
	private boolean isImpact, impacted = false;
	private float destX, destY;

	// normal grenades
	public OnlineGrenade(Handler handler, float x, float y, boolean isImpact, float destX, float destY,
			float angle) {
		super(handler, x, y, 10000, angle, false);
		x = (int) x;
		y = (int) y;
		width = 10;
		height = 10;
		counter = 0;
		timer = 60;
		explosionTimer = 65;
		speed = 8;
		this.color = Color.orange;
		this.isImpact = isImpact;
		this.angle = (float) Math.toRadians(angle - 90);
		xMove = (float) (Math.cos(this.angle));
		yMove = (float) (Math.sin(this.angle));
		this.destX = destX;
		this.destY = destY;
		isUpgraded = isImpact;
	}

        @Override
	public void tick() {
		counter++;
		if (counter >= timer && !isImpact) {
			if (counter >= explosionTimer) {
				findEntitiesInRadius();
				handler.getWorld().getEntityManager().getEntities().remove(this);
			}
		} else if (isImpact && slowed) {
			findEntitiesInRadius();
			handler.getWorld().getEntityManager().getEntities().remove(this);
		} else {
			while (travelTicker < speed) {
				moveX();
				moveY();
				travelTicker++;

				if (((int) x == (int) destX || (int) y == (int) destY) && !slowed) {
					slowed = true;
					speed = speed / 8;
				}
			}
			travelTicker = 0;
		}
		if (impacted) {
			findEntitiesInRadius();
			handler.getWorld().getEntityManager().getEntities().remove(this);
		}
	}

	boolean slowed = false;

        @Override
	public boolean checkForImpact() {
		cb = new Rectangle((int) (x), (int) (y), width, height);

		for (Zombie e : handler.getWorld().getEntityManager().getZombies()) {
			if (e.getCollisionBounds(0, 0).intersects(cb)) {
				return true;
			}
		}

		for (InteractableStaticEntity e : handler.getWorld().getEntityManager().getInteractables()) {
			if (!(e instanceof Barrier)
				//!handler.getWorld().getEntityManager().getBarriers().contains(e)
					&& e.getCollisionBounds(0, 0).intersects(cb)) {
				return true;
			}
		}
		for (Wall e : handler.getWorld().getEntityManager().getWalls()) {
			if (e.getCollisionBounds(0, 0).intersects(cb)) {
				return true;
			}
		}
		return false;

	}

	public void findEntitiesInRadius() {
		Player player = handler.getCurrentPlayer();
		explosionRadius = new Ellipse2D.Float(x - 100, y - 100, 200, 200);
		for (Zombie f : handler.getWorld().getEntityManager().getZombies()) {
			if (explosionRadius.intersects(f.getHitBox(0, 0))) {
			}
		}
		handler.getWorld().getEntityManager().addExplosion(new Explosion(handler, x - 100, y - 100, 200, 200, isUpgraded));
		float dist = Utils.getEuclideanDistance(x, y, player.getX(), player.getY());
		
		//Sounds.playClip(GunSounds.grenade_launcher_explosion, 1, "grenade_explosion", ((float) 1.0f - dist / 2000), false);
		Sounds.playClip(GunSounds.GRENADE_LAUNCHER_EXPLOSION_ID, 1, ((float) 1.0f - dist / 2000), false);

	}

	public void findPlayerInRadius() {
		explosionRadius = new Ellipse2D.Float(x - 100, y - 100, 200, 200);
		handler.getWorld().getEntityManager().addExplosion(new Explosion(handler, x - 100, y - 100, 200, 200, isUpgraded));
	}

        @Override
	public void moveX() {
		x += xMove;
		if (checkForImpact()) {
			if (isImpact) {
				impacted = true;
			} else {
				speed = speed / 4;
				x -= xMove;
				xMove = -xMove;
			}
		}

	}

        @Override
	public void moveY() {
		y += yMove;
		if (checkForImpact()) {
			if (isImpact) {
				impacted = true;
			} else {
				speed = speed / 4;
				y -= yMove;
				yMove = -yMove;
			}
		}
	}

	@Override
	public void render(Graphics g) {

		g.setColor(new Color(150, 200, 100));

		g.fillOval((int) (x - handler.getGameCamera().getxOffset()), (int) (y - handler.getGameCamera().getyOffset()),
				width, height);
	}
	
}
