package project.game.horde.entities.creatures.playerinfo;

import project.game.horde.entities.creatures.Player;
import project.game.horde.utils.Timer;

public class BurnStatusForPlayer {

	private Player entity;
	private boolean isBurning = false;
	private int burnDamage = 0;

	private Timer burnDuration = new Timer(300);
	private Timer eachBurn = new Timer(20);

	public BurnStatusForPlayer(Player entity) {
			this.entity = entity;
		}

	public void burn() {
		if (isBurning) {
			burnDuration.tick();
			eachBurn.tick();
			if (eachBurn.isReady()) {
				entity.takeDamage(burnDamage);
				eachBurn.resetTimer();
			}
			if (burnDuration.isReady()) {
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
