package project.game.horde.entities.creatures.playerinfo;

import java.awt.geom.Ellipse2D;

import project.game.horde.entities.areas.Areas;
import project.game.horde.entities.creatures.Player;
import project.game.horde.entities.creatures.Zombie;
import project.game.horde.entities.creatures.ZombieType;
import project.game.horde.entities.statics.traps.IcyWater;
import project.game.horde.main.Handler;
import project.game.horde.utils.Timer;

public class FreezeStatusForPlayer {
	private Handler handler;
	protected boolean isFrozen = false;
	private boolean inWater = false;

	private Player player;
	
	public FreezeStatusForPlayer(Handler handler, Player player) {
		this.player = player;
		this.handler = handler;
	}
	
	public boolean isFrozen() {
		return isFrozen;
	}
	
	public void setFrozen(boolean isFrozen) {
		this.isFrozen = isFrozen;
	}
	
	
	public void checkIfInIcyWater() {
		boolean found = false;
		for (Areas e : handler.getWorld().getEntityManager().getAreas()) {
			if (e instanceof IcyWater && ((IcyWater) e).checkIfEntityIsContained(player.getCollisionBounds(0, 0))) {
				inWater = true;
				found = true;
			}
		}
		if (!found) {
			inWater = false;
		}
	}
	
	public boolean inWater() {
		return inWater;
	}
	
	private int iceCounter = 0, iceMax = 300;
	public void freezing() {
		if (inWater) {
			iceCounter++;
		} else {
			iceCounter--;
		}
		if (iceCounter >= iceMax) {
			isFrozen = true;
		}
	}
	
	private Timer breakCooldown = new Timer(60);
	private int breakCounter = 0;

	public void breakFreeFromIce() {
		if (breakCooldown.isReady()) {
			breakCooldown.resetTimer();
			breakCounter++;
		}
		if (breakCounter >= 3) {
			breakCounter = 0;
			isFrozen = false;
			iceCounter = 0;
		}
	}
	
	public Timer getBreakCooldown() {
		return breakCooldown;
	}
	
	public void breakPlayerIceWhenHit() {
		if (isFrozen) {
			isFrozen = false;
			iceCounter = 0;
		}
	}
}
