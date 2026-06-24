package project.game.horde.entities.creatures;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;

import project.game.horde.entities.blood.Blood;
import project.game.horde.entities.bullets.Grenade;
import project.game.horde.graphics.Assets;
import project.game.horde.main.Handler;
import project.game.horde.perks.Stronghold;
import project.game.horde.perks.Vampire;
import project.game.horde.utils.Timer;

public class Toxen extends Zombie {
	
	private Timer shootTimer;

	public Toxen(Handler handler, int id, float x, float y, float dspeed, int health) {
		super(handler, id, x, y, dspeed, health);
		zombieType = TOXEN;
		speed = 2.0f + dspeed - 1f;
		this.health = health * 3;
		shootTimer = new Timer(500);
	}
	
        @Override
	public void postTick() {
//		shootTimer.tick();
//		if(shootTimer.isReady()) {
//			System.out.println("SHOOT");
//			shootTimer.resetTimer();
//			justAttacked = true;
//			handler.getWorld().getEntityManager().addEntity(new ToxenBullet(handler, 
//					x + width/2,
//					y + height/2,
//					300, closestPlayer));	
//		}
	}
	
	@Override
	public void render(Graphics g) {	
		g.drawImage(Assets.shadow, (int) (x - handler.getGameCamera().getxOffset()),
				(int) (y - handler.getGameCamera().getyOffset()), width, height, null);

		if(burnStatus.isBurning()) {
			g.setColor(Color.orange);
			g.fillOval((int) (x - handler.getGameCamera().getxOffset()),
				(int) (y - handler.getGameCamera().getyOffset()), width, height);
		}
		
		float moveToX = closestPlayer.getCenterX() - handler.getGameCamera().getxOffset();
		float moveToY = closestPlayer.getCenterY() - handler.getGameCamera().getyOffset();
		float angle = (float) Math.toDegrees(Math.atan2(-(x  - handler.getGameCamera().getxOffset() - moveToX + width/2), y - handler.getGameCamera().getyOffset() - moveToY + height/2 ));
		Graphics2D g2d = (Graphics2D)g;
		AffineTransform old = g2d.getTransform();
		g2d.rotate(Math.toRadians(angle), x - handler.getGameCamera().getxOffset() + width/2, y  - handler.getGameCamera().getyOffset() + height/2);
		g2d.drawImage(Assets.toxen, (int) (x - handler.getGameCamera().getxOffset()), (int) (y - handler.getGameCamera().getyOffset()), width, height, null);
		g2d.setTransform(old);
	}
	

	@Override
	public void die(Player player) {
		if(player.getInv().getVamp() >= 1) {
			player.incrementTempHealth(Vampire.LVL1_BOSSDRAIN);
		}
		else if (player.getInv().getVamp() >= 0) {
			player.incrementTempHealth(Vampire.TEMPHEALTH_GAIN);
		}
		if(player.getInv().getStronghold() == 3) {
			if(player.getStrongholdRadius() != null) {
				if(player.getStrongholdRadius().intersects(getHitBox(0,0))) {
					player.gainStrongholdArmor(Stronghold.ARMOR_GAIN_INCREMENTS);
					player.gainStrongholdDamageMultiplier(Stronghold.DAMAGE_BUFF_INCREMENTS);
				}
			}
		}
		player.getStats().gainKill();
		if(burnStatus.isBurning()) {
			new Grenade(handler, x + width/2, y + height/2, new Color(144, 238, 144)).findEntitiesInRadius();
		}
		else
			handler.getWorld().getEntityManager().addBlood(new Blood(handler, x, y, ZombieType.TOXEN));
	}
}
