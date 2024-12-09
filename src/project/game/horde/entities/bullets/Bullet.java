package project.game.horde.entities.bullets;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.ArrayList;

import project.game.horde.entities.Entity;
import project.game.horde.entities.creatures.Player;
import project.game.horde.entities.creatures.Zombie;
import project.game.horde.entities.statics.InteractableStaticEntity;
import project.game.horde.entities.statics.Wall;
import project.game.horde.main.Handler;
import project.game.horde.perks.DoubleTap;
import project.game.horde.weapons.Gun;

public class Bullet extends Entity {
	protected Player player;
	protected float mouseX, mouseY;
	protected Rectangle cb;
	protected Gun gunFiredFrom;
	protected float speed;
	protected float xMove, yMove, angle;
	protected int range, rangeCounter;
	protected boolean fromTrap;
	ArrayList<Zombie> zombiesHit;

	// normal bullet
	public Bullet(Handler handler, float x, float y, int z, int range, Gun gun) {
		super(handler, x, y, z, 5, 5);
		this.gunFiredFrom = gun;
		this.player = gun.getPlayer();
		bounds = new Rectangle(0, 0, 0, 0);
		fromTrap = false;
		this.handler = handler;
		this.range = range;
		this.rangeCounter = 0;

		this.speed = 30;
		this.mouseX = x - player.getGameCamera().getxOffset() - (int) player.getMouseManager().getMouseX();
		this.mouseY = y - player.getGameCamera().getyOffset() - (int) player.getMouseManager().getMouseY();
		angle = (float) Math.atan2(-(mouseY), -(mouseX));
		xMove = (float) Math.cos(angle);
		yMove = (float) Math.sin(angle);
		zombiesHit = new ArrayList<Zombie>();
	}

	public Bullet(Handler handler, float x, float y, int z, int range, Player player) {
		super(handler, x, y, z, 5, 5);
		this.player = player;
		bounds = new Rectangle(0, 0, 0, 0);
		fromTrap = false;
		this.handler = handler;
		this.range = range;
		this.rangeCounter = 0;

		this.speed = 30;
		this.mouseX = x - player.getGameCamera().getxOffset() - (int) player.getMouseManager().getMouseX();
		this.mouseY = y - player.getGameCamera().getyOffset() - (int) player.getMouseManager().getMouseY();
		angle = (float) Math.atan2(-(mouseY), -(mouseX));
		xMove = (float) Math.cos(angle);
		yMove = (float) Math.sin(angle);
		zombiesHit = new ArrayList<Zombie>();
	}

	public Bullet(Handler handler, float x, float y, int z, int range, Color color) {
		super(handler, x, y, z, 5, 5);
		// this.player = player;
		bounds = new Rectangle(0, 0, 0, 0);
		fromTrap = false;
		this.handler = handler;
		this.range = range;
		this.rangeCounter = 0;

		this.speed = 30;
		this.mouseX = x - player.getGameCamera().getxOffset() - (int) player.getMouseManager().getMouseX();
		this.mouseY = y - player.getGameCamera().getyOffset() - (int) player.getMouseManager().getMouseY();
		angle = (float) Math.atan2(-(mouseY), -(mouseX));
		xMove = (float) Math.cos(angle);
		yMove = (float) Math.sin(angle);
		zombiesHit = new ArrayList<Zombie>();
	}

	// turret bullet
	public Bullet(Handler handler, Gun gun, float x, float y, int z, float targetX, float targetY, int range) {
		super(handler, x, y, z, 5, 5);
		bounds = new Rectangle(0, 0, 0, 0);
		fromTrap = true;
		this.handler = handler;
		this.range = range;
		this.rangeCounter = 0;
		this.gunFiredFrom = gun;
		this.speed = 30;
		this.mouseX = x - (int) targetX;
		this.mouseY = y - (int) targetY;
		angle = (float) Math.atan2(-mouseY, -mouseX);
		xMove = (float) Math.cos(angle);
		yMove = (float) Math.sin(angle);
		zombiesHit = new ArrayList<Zombie>();
	}

	// shotgun pellet
	public Bullet(Handler handler, float x, float y, int z, int range, float radianOffset, Gun gun) {
		super(handler, x, y, z, 5, 5);

		bounds = new Rectangle(0, 0, 0, 0);
		fromTrap = false;
		this.handler = handler;
		this.range = range;
		this.rangeCounter = 0;
		this.gunFiredFrom = gun;
		this.player = gun.getPlayer();
		this.speed = 30;
		this.mouseX = x - player.getGameCamera().getxOffset() - (int) player.getMouseManager().getMouseX();
		this.mouseY = y - player.getGameCamera().getyOffset() - (int) player.getMouseManager().getMouseY();
		angle = (float) Math.atan2(-mouseY, -mouseX);
		// xMove = (float) (speed * Math.cos(angle + radianOffset));
		// yMove = (float) (speed * Math.sin(angle + radianOffset));
		xMove = (float) Math.cos(angle + radianOffset);
		yMove = (float) Math.sin(angle + radianOffset);
		zombiesHit = new ArrayList<Zombie>();
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
			if (!fromTrap) {
				if (checkForImpact()
						&& (zombiesHit.size() >= 6 && player.getInv().getDoubletap() >= 1 || zombiesHit.size() >= 4)) {
					System.out.println("impacted");
					break;
				}
			} else {
				if (checkForImpactfromTrap() && zombiesHit.size() >= 4) {
					System.out.println("impacted");
					break;
				}
			}

		}
		travelTicker = 0;

		die(player);
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
		if (gunFiredFrom.isUpgraded())
			g.setColor(new Color(255, 160, 240));
		else
			g.setColor(Color.yellow);
		g.fillRect((int) (x - handler.getGameCamera().getxOffset()), (int) (y - handler.getGameCamera().getyOffset()),
				width, height);
	}

	@Override
	public void renderBW(Graphics g) {

		if (gunFiredFrom.isUpgraded())
			g.setColor(new Color(198, 198, 198));
		else
			g.setColor(new Color(226, 226, 226, 100));
		g.fillRect((int) (x - handler.getGameCamera().getxOffset()), (int) (y - handler.getGameCamera().getyOffset()),
				width, height);
	}

	public boolean checkForImpact() {
		cb = new Rectangle((int) (x + bounds.x - 1), (int) (y + bounds.y - 1), bounds.width + 1, bounds.height + 1);

		float damageMultiplier = 1;
		if (player.getInv().getDoubletap() == 3) {
			damageMultiplier += DoubleTap.LVL3_DAMAGEBUFF;
		}
		if (player.getInv().getStronghold() > -1) {
			damageMultiplier += player.getStrongholdDamageMultiplier();
		}

		for (Zombie e : handler.getWorld().getEntityManager().getZombies()) {
			if (z == e.getZ() && e.getHitBox(0, 0).intersects(cb) && !zombiesHit.contains(e)) {
				System.out.println("found a zombie");
				if (fromTrap)
					e.damageByTrap(gunFiredFrom.getDamage());
				else {
					if (e.getHealth() > 0) {
						e.takeDamage((int) (gunFiredFrom.getDamage() / (zombiesHit.size() + 1) * damageMultiplier),
								player);
						zombiesHit.add(e);
					}
				}
				if (player.getInv().getDoubletap() >= 1) {
					if (zombiesHit.size() >= DoubleTap.LVL1_PENETRATEBUFF) {
						handler.getWorld().getEntityManager().getEntities().remove(this);
						return true;
					}
				} else if (zombiesHit.size() >= 4) {
					handler.getWorld().getEntityManager().getEntities().remove(this);
					return true;
				}

			}
		}
		for (InteractableStaticEntity e : handler.getWorld().getEntityManager().getInteractables()) {

			if (z == e.getZ() && !handler.getWorld().getEntityManager().getBarriers().contains(e)
					&& e.getCollisionBounds(0, 0).intersects(cb)) {
				handler.getWorld().getEntityManager().getEntities().remove(this);
				return true;
			}
		}
		for (Wall e : handler.getWorld().getEntityManager().getWalls()) {
			if (z == e.getZ() && e.getCollisionBounds(0, 0).intersects(cb)) {
				handler.getWorld().getEntityManager().getEntities().remove(this);
				return true;
			}
		}
		return false;
	}

	public boolean checkForImpactfromTrap() {
		cb = new Rectangle((int) (x + bounds.x - 1), (int) (y + bounds.y - 1), bounds.width + 1, bounds.height + 1);

		for (Zombie e : handler.getWorld().getEntityManager().getZombies()) {
			if (z == e.getZ() && e.getHitBox(0, 0).intersects(cb) && !zombiesHit.contains(e)) {
				System.out.println("found a zombie");
				if (fromTrap)
					e.damageByTrap(gunFiredFrom.getDamage());
				else {
					if (e.getHealth() > 0) {
						e.takeDamage((int) (gunFiredFrom.getDamage() / (zombiesHit.size() + 1)), player);
						zombiesHit.add(e);
					}
				}
				if (zombiesHit.size() >= 4) {
					handler.getWorld().getEntityManager().getEntities().remove(this);
					return true;
				}

			}
		}
		for (InteractableStaticEntity e : handler.getWorld().getEntityManager().getInteractables()) {

			if (z == e.getZ() && !handler.getWorld().getEntityManager().getBarriers().contains(e)
					&& e.getCollisionBounds(0, 0).intersects(cb)) {
				handler.getWorld().getEntityManager().getEntities().remove(this);
				return true;
			}
		}
		for (Wall e : handler.getWorld().getEntityManager().getWalls()) {
			if (z == e.getZ() && e.getCollisionBounds(0, 0).intersects(cb)) {
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
		if (rangeCounter >= range * DoubleTap.LVL1_PENETRATEBUFF && player.getInv().getDeadshot() > -1) {
			handler.getWorld().getEntityManager().getEntities().remove(this);
		} else if (rangeCounter >= range) {
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
