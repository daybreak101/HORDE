package project.game.horde.weapons;

import java.awt.*;
import java.awt.geom.*;
import project.game.horde.entities.creatures.Player;
import project.game.horde.entities.creatures.Zombie;
import project.game.horde.main.Handler;
import project.game.horde.utils.Timer;
import project.game.horde.utils.Utils;

public class Knife {

	Handler handler;
	Timer meleeCooldown;
	int damage;
	Player player;
	int alpha;

	public Knife(Handler handler, Player player) {
		this.handler = handler;
		this.player = player;
		meleeCooldown = new Timer(60);
		damage = 150;
	}

	public void tick() {
		alpha -= 5;
		if(alpha < 0) {
			alpha = 0;
		}
		meleeCooldown.tick();
		postTick();
	}
	
	public void render(Graphics g) {
		if(ellipseBounds != null) {
			//g.fillRect((int) (ellipseBounds.x- handler.getGameCamera().getxOffset()), (int) (ellipseBounds.y- handler.getGameCamera().getyOffset()), (int)ellipseBounds.width, (int)ellipseBounds.height);
			g.setColor(new Color(255,255,255,alpha));
			g.drawArc((int) (meleeArc.x - handler.getGameCamera().getxOffset()), 
					  (int) (meleeArc.y - handler.getGameCamera().getyOffset()),
					  (int) meleeArc.width, (int) meleeArc.height,
					  (int) meleeArc.start, (int) meleeArc.extent);
		}
	}

	// also get mouse
	Rectangle2D.Float ellipseBounds = null;
	Arc2D.Float meleeArc = null;
	public void damageNearbyZombie() {
		if (meleeCooldown.isReady()) {
			alpha = 255;
			float playerX = player.getX();
			float playerY = player.getY();
			float mouseX = playerX - handler.getGameCamera().getxOffset() - (int) player.getMouseManager().getMouseX();
			float mouseY = playerY - handler.getGameCamera().getyOffset() - (int) player.getMouseManager().getMouseY();
			float midAngle = (float) Math.atan2(mouseY, -mouseX);

			float startAngle = (float) (midAngle - Math.PI / 6);
			float xMove = (float) (Math.cos(startAngle) * 50) + playerX;
			float yMove = (float) (Math.sin(startAngle) * 50) + playerY;
			//System.out.println("Player x: " + playerX + ", y: " + playerY);
			//System.out.println("Rectangle x: " + xMove + ", y: " + yMove);
			ellipseBounds = new Rectangle2D.Float(playerX + player.getWidth()/2 - 100, playerY + player.getHeight()/2 - 100, 200, 200);
			// float angleWidth = (float) (Math.PI/2);

			meleeArc = new Arc2D.Float(ellipseBounds, (float) Math.toDegrees(startAngle), (float) Math.toDegrees(Math.PI / 3), Arc2D.PIE);
			
			Zombie closestZombie = null;
			float closestDist = 999999;
			float zDist;
			for (Zombie z : handler.getWorld().getEntityManager().getZombies()) {
				if (meleeArc.intersects(z.getHitBox(0, 0))) {
					zDist = Utils.getEuclideanDistance(playerX, playerY, z.getX(), z.getY());
					if (closestZombie == null) {
						closestZombie = z;
						closestDist = zDist;
					}
					if (zDist < closestDist) {
						closestZombie = z;
						closestDist = zDist;
					}
				}
			}
			if (closestZombie != null) {
				closestZombie.takeDamage(damage, player);
				//System.out.println("HERE");
			}
				
			
			meleeCooldown.resetTimer();
		}
	}

	public void setDamage(int newDamage) {
		damage = newDamage;
	}

	public int getDamage() {
		return damage;
	}

	public void setMeleeCooldown(int newCooldown) {
		meleeCooldown = new Timer(newCooldown);
	}

	public int getMeleeCooldown() {
		return meleeCooldown.limit;
	}

	public void postTick() {
		// used for future implementations of different melees that would have different
		// effects and such
	}
}
