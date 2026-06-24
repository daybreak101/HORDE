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

public class Stoker extends Zombie {

	private Timer shootTimer, chargeTimer;
	private int angerHealthThreshold;
	private boolean isAngry = false;

	// make sure zombie cannot burn

	public Stoker(Handler handler, int id, float x, float y, float dspeed, int health) {
		super(handler, id, x, y, dspeed, health);
		zombieType = STOKER;
		speed = 2.0f + dspeed - 1f;
		this.health = health * 2;
		angerHealthThreshold = health / 5;
		shootTimer = new Timer(500);
		chargeTimer = new Timer(120);
	}

	@Override
	public void render(Graphics g) {
			g.drawImage(Assets.shadow, (int) (x - handler.getGameCamera().getxOffset()),
					(int) (y - handler.getGameCamera().getyOffset()), width, height, null);

		float moveToX = closestPlayer.getCenterX() - handler.getGameCamera().getxOffset();
		float moveToY = closestPlayer.getCenterY() - handler.getGameCamera().getyOffset();
		float angle = (float) Math
				.toDegrees(Math.atan2(-(x - handler.getGameCamera().getxOffset() - moveToX + width / 2),
						y - handler.getGameCamera().getyOffset() - moveToY + height / 2));
		Graphics2D g2d = (Graphics2D) g;
		AffineTransform old = g2d.getTransform();
		g2d.rotate(Math.toRadians(angle), x - handler.getGameCamera().getxOffset() + width / 2,
				y - handler.getGameCamera().getyOffset() + height / 2);
		g2d.drawImage(Assets.toxen, (int) (x - handler.getGameCamera().getxOffset()),
				(int) (y - handler.getGameCamera().getyOffset()), width, height, null);
		g2d.setTransform(old);

	}
	

	// implement where stoker only shoots when near player
        @Override
	public void postTick() {
		if (!freezeStatus.isFrozen()) {
			if (isAngry == false) {
				fireballAttack();
			}
			if (health < angerHealthThreshold && isAngry == false) {
				getAngry();
			}
			if (isAngry) {
				pulsateFlame();
			}
		}
	}

	Timer pulsateTimer = new Timer(60);

	public void pulsateFlame() {
		pulsateTimer.tick();
		if (pulsateTimer.isReady()) {
			new Grenade(handler, x + width / 2, y + height / 2, Color.orange).findPlayerInRadius();
		}
	}

	public void getAngry() {
		isAngry = true;
		dontMove();
		speed = speed * 2;
	}

	boolean attacking = false;

	public void fireballAttack() {
//		shootTimer.tick();
//		if (shootTimer.isReady()
//				&& Utils.getEuclideanDistance(x, y, closestPlayer.getCenterX(), closestPlayer.getCenterY()) <= 300) {
//			attacking = true;
//			chargeTimer.tick();
//		}
//		if (attacking) {
//			justAttacked = true;
//			chargeTimer.tick();
//		}
//		if (chargeTimer.isReady()) {
//			handler.getWorld().getEntityManager()
//					.addEntity(new StokerFireball(handler, x + width / 2, y + height / 2, 300, closestPlayer));
//			shootTimer.resetTimer();
//			chargeTimer.resetTimer();
//			justAttacked = false;
//			attacking = false;
//		}
	}

	@Override
	public void die(Player player) {
		if (player.getInv().getVamp() >= 1) {
			player.incrementTempHealth(Vampire.TEMPHEALTH_GAIN);
		} else if (player.getInv().getVamp() >= 0) {
			player.incrementTempHealth(Vampire.LVL1_BOSSDRAIN);
		}
		if (player.getInv().getStronghold() == 3) {
			if (player.getStrongholdRadius() != null) {
				if (player.getStrongholdRadius().intersects(getHitBox(0, 0))) {
					player.gainStrongholdArmor(Stronghold.ARMOR_GAIN_INCREMENTS);
					player.gainStrongholdDamageMultiplier(Stronghold.DAMAGE_BUFF_INCREMENTS);
				}
			}
		}
		player.getStats().gainKill();
		// put "flame" effect on death as blood that stays there for 7 seconds
		handler.getWorld().getEntityManager().addBlood(new Blood(handler, x, y, ZombieType.STOKER));
	}
}
