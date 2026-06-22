package project.game.horde.weapons;

import java.awt.*;
import java.awt.geom.*;
import project.game.horde.entities.creatures.Player;
import project.game.horde.entities.creatures.Zombie;
import project.game.horde.entities.statics.Barrier;
import project.game.horde.entities.statics.InteractableStaticEntity;
import project.game.horde.entities.statics.Wall;
import project.game.horde.main.Handler;
import project.game.horde.sounds.MiscWeaponSounds;
import project.game.horde.sounds.Sounds;
import project.game.horde.utils.Timer;
import project.game.horde.utils.Utils;

public class Knife {

	Handler handler;
	Timer meleeCooldown;
	int damage;
	Player player;
	int alpha;
	int endAngle = 0;

	public Knife(Handler handler, Player player) {
		this.handler = handler;
		this.player = player;
		meleeCooldown = new Timer(60);
		damage = 150;
	}

	public void tick() {
		alpha -= 3;
		if(alpha < 0) {
			alpha = 0;
		}
		endAngle+= 3;
		if(endAngle > 60) {
			endAngle = 60;
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
					  (int) meleeArc.start, (int) endAngle);
		}
	}

	// also get mouse
	Rectangle2D.Float ellipseBounds = null;
	Arc2D.Float meleeArc = null;
	public void damageNearbyZombie() {
		
		if (meleeCooldown.isReady()) {	
			Sounds.playClip(MiscWeaponSounds.MELEE_WHOOSH, 1, 1, false);

			alpha = 255;
			float playerX = player.getCenterX();
			float playerY = player.getCenterY();
			float mouseX = player.getMouseManager().getMouseX() + handler.getGameCamera().getxOffset() ;
			float mouseY = player.getMouseManager().getMouseY() + handler.getGameCamera().getyOffset() ;
			
			
			float midAngle = (float) (Math.atan2(-(mouseY - playerY), mouseX - playerX));
			if (midAngle < 0) { midAngle += 2 * Math.PI; }			
			float startAngle = (float) (midAngle - Math.PI / 6);
			ellipseBounds = new Rectangle2D.Float(player.getCenterX()  - 75, player.getCenterY() - 75, 150, 150);
			meleeArc = new Arc2D.Float(ellipseBounds, (float) Math.toDegrees(startAngle), (float) Math.toDegrees(Math.PI / 3), Arc2D.PIE);
			endAngle = (int) startAngle;
			
			Zombie closestZombie = null;
			float closestDist = 999999;
			float zDist;
			Line2D.Float line;
			boolean found = false;
			for (Zombie z : handler.getWorld().getEntityManager().getZombies()) {
				found = false;
				if (Math.abs(z.getZ() - player.getZ()) < 75 && meleeArc.intersects(z.getHitBox(0, 0))) {
					//check if there are static entities in the way
					
					line = new Line2D.Float(player.getCenterX(), player.getCenterY(), z.getCenterX(), z.getCenterY());
					for(InteractableStaticEntity e : handler.getWorld().getEntityManager().getInteractables()) {
						if(!(e instanceof Barrier) && Math.abs(e.getZ() - player.getZ()) < 75 && line.intersects(e.getCollisionBounds(0, 0))) {
							found = true;
							break;
						}
					}
					if(found)
						continue;
					for(Wall e : handler.getWorld().getEntityManager().getWalls()) {
						if(Math.abs(e.getZ() - player.getZ()) < 75 && line.intersects(e.getCollisionBounds(0, 0))) {
							found = true;
							break;
						}
					}
					if(found)
						continue;
					
					
					
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
