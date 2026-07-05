package project.game.horde.entities.creatures.zombieinfo;

import project.game.horde.entities.creatures.Creature;
import project.game.horde.entities.creatures.Zombie;
import project.game.horde.main.Handler;
import project.game.horde.utils.Timer;

public class BurnStatus {
	private Handler handler;
	private Creature entity;
	private boolean isBurning = false;
	private int burnDamage = 0;
	
	private Timer burnDuration = new Timer(300);
	private Timer eachBurn = new Timer(60);

	public BurnStatus(Handler handler, Zombie entity) {
		this.handler = handler;
		this.entity = entity;
	}
	
	public void burn() {
		if (isBurning) {
			burnDuration.tick();
			eachBurn.tick();
			if(eachBurn.isReady()) {
				entity.takeDamage(burnDamage, handler.getCurrentPlayer());
				eachBurn.resetTimer();
			}
			if(burnDuration.isReady()) {
				isBurning = false;
				burnDuration.resetTimer();
			}
		}
	}
	
	public boolean isBurning() {
		return isBurning;
	}
	
	public void setBurn(int damage) {
		isBurning = true;
		burnDuration.resetTimer();
		burnDamage = damage;
	}
}
