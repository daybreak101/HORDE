package project.game.horde.entities.bullets;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.geom.Ellipse2D;
import java.util.ArrayList;

import project.game.horde.entities.creatures.Zombie;
import project.game.horde.entities.statics.Barrier;
import project.game.horde.entities.statics.InteractableStaticEntity;
import project.game.horde.entities.statics.Wall;
import project.game.horde.main.Handler;
import project.game.horde.utils.RandomUtil;
import project.game.horde.weapons.Gun;

public class FlameBullet extends Bullet {
	Ellipse2D.Float staticCollision;
	
	public FlameBullet(Handler handler, float x, float y, int range, Gun gun) {
		super(handler, x, y, range, gun);
		this.speed = 4;	
		this.width = 50;
		this.height = 50;
		angle = (float) Math.atan2(-(mouseY + height/2), -(mouseX + width/2));

		xMove = (float) ( Math.cos(angle));
		yMove = (float) ( Math.sin(angle));

		zombiesHit = new ArrayList<>();
	}

        @Override
	public void postTick() {
		angle = (float) Math.atan2(-(mouseY + height/2), -(mouseX + width/2));
		xMove = (float) ( Math.cos(angle));
		yMove = (float) ( Math.sin(angle));
		width++;
		height++;
	}

        @Override
	public boolean checkForImpact() {
		cb.setBounds((int) (x + bounds.x), (int) (y + bounds.y), width, height);
		//cb = new Rectangle((int) (x + bounds.x), (int) (y + bounds.y), width, height);
		staticCollision.setFrame((int) (x + bounds.x + width/3), (int) (y + bounds.y + height/3), width/3, height/3);
		//staticCollision = new Ellipse2D.Float((x + bounds.x + width/3), (y + bounds.y + height/3), width/3, height/3);

		float damageMultiplier = 1;
		if(gunFiredFrom.getPlayer().getInv().getStronghold() > -1) {
			damageMultiplier += gunFiredFrom.getPlayer().getStrongholdDamageMultiplier();
		}
		
		for (Zombie e : handler.getWorld().getEntityManager().getZombies()) {
			if (!zombiesHit.contains(e) && e.getHitBox(0, 0).intersects(cb)) {
				e.takeDamage((int) (gunFiredFrom.getDamage() * damageMultiplier), player);
				e.getBurnStatus().setBurn((int) (gunFiredFrom.getDamage() / 2 * damageMultiplier));
				zombiesHit.add(e);
				//handler.getWorld().getEntityManager().getEntities().remove(this);
			}
		}

		for (InteractableStaticEntity e : handler.getWorld().getEntityManager().getInteractables()) {
			if (
				//!handler.getWorld().getEntityManager().getBarriers().contains(e) 
				!(e instanceof Barrier)
				&& staticCollision.intersects(e.getCollisionBounds(0, 0))) {
				handler.getWorld().getEntityManager().getEntities().remove(this);
				return true;
			}
		}
		for (Wall e : handler.getWorld().getEntityManager().getWalls()) {
			if (staticCollision.intersects(e.getCollisionBounds(0, 0))) {
				handler.getWorld().getEntityManager().getEntities().remove(this);
				return true;
			}
		}
		return false;
	}

	int color = 0;
	int alpha = 200;

	@Override
	public void render(Graphics g) {
		if (color < 10) {
			g.setColor(new Color(255,255,0,alpha));
			if(gunFiredFrom.isUpgraded())
				g.setColor(new Color(0,255,255,alpha));
		} else if (color < 20) {
			g.setColor(new Color(205, 50, 0,alpha));
			if(gunFiredFrom.isUpgraded())
				g.setColor(new Color(0, 100, 100,alpha));
		} else if (color < 30) {
			g.setColor(new Color(255,102,0,alpha));
			if(gunFiredFrom.isUpgraded())
				g.setColor(new Color(10, 0, 100,alpha));
		}

		
		
		
		g.fillOval((int) (x + RandomUtil.nextInt(-10,10) - handler.getGameCamera().getxOffset()), (int) (y + RandomUtil.nextInt(-10,10) - handler.getGameCamera().getyOffset()),
				width, height);
		
		color += 1;
		alpha -= 3;
		if(alpha < 0)
			alpha = 0;
		if (color >= 30) {
			color = 0;
		}
	}

}
