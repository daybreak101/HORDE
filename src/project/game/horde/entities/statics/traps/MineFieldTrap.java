package project.game.horde.entities.statics.traps;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.geom.Ellipse2D;
import java.util.Random;

import project.game.horde.entities.bullets.Explosion;
import project.game.horde.entities.creatures.Player;
import project.game.horde.entities.creatures.Zombie;
import project.game.horde.main.Handler;
import project.game.horde.utils.RandomUtil;
import project.game.horde.utils.Timer;

public class MineFieldTrap extends Trap {

	Timer mineInterval = new Timer(18);

	public MineFieldTrap(Handler handler, int id, float x, float y, int z, float switchX, float switchY, int switchZ, int switchRotation) {
		super(handler, id, x, y, z, 500, 500, switchX, switchY, switchZ, switchRotation, 45 * 60, 1500);
		cooldown = 15 * 60;
	}

	public void postTick() {
		if (cooldownTimer > cooldown) {
			activatedBy = null;
			activated = false;
		} else if (activated && cooldownTimer <= cooldown && mineInterval.isReady()) {
			killInArea(activatedBy);
			mineInterval.resetTimer();
		} else if (activated && cooldownTimer <= cooldown && !mineInterval.isReady()) {
			mineInterval.tick();
		}
	}

	public void render(Graphics g) {
		g.setColor(Color.gray);
		g.fillRect((int) (x - handler.getGameCamera().getxOffset()), (int) (y - handler.getGameCamera().getyOffset()),
				width, height);

	}
	
	public void renderBW(Graphics g) {
		g.setColor(Color.gray);
		g.fillRect((int) (x - handler.getGameCamera().getxOffset()), (int) (y - handler.getGameCamera().getyOffset()),
				width, height);

	}


	Ellipse2D explosionRadius;

	public void killInArea(Player player) {
		int dx = RandomUtil.nextInt(100, width - 100);
		int dy = RandomUtil.nextInt(100, height - 100);

		explosionRadius = new Ellipse2D.Float(x + dx - 100, y + dy - 100, 200, 200);
		handler.getWorld().getEntityManager()
				.addExplosion(new Explosion(handler, x + dx - 100, y + dy - 100, z, 200, 200, false));
		for (Zombie f : handler.getWorld().getEntityManager().getZombies()) {
			int damage = 10000;
			if (explosionRadius.intersects(f.getHitBox(0, 0))) {
				f.takeDamage(damage, player);
				if (f.getHealth() / handler.getRoundLogic().getZombieHealth() < (f.getHealth() * 3 / 10)
						&& f.getZombieType() == 0) {
					f.turnToCrawler();
				}
			}
		}
		Player current = handler.getCurrentPlayer();
		if (explosionRadius.intersects(current.getCollisionBounds(0, 0))) {
			if (current.getInv().getPhd() == -1)
				current.takeDamage(60);
		}

	}

}
