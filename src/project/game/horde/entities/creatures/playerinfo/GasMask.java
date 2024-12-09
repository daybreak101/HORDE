package project.game.horde.entities.creatures.playerinfo;

import project.game.horde.main.Handler;
import project.game.horde.utils.Timer;

public class GasMask {

	private Handler handler;
	private int currentDurability, maxDurability;
	private Timer tickDamage;

	public GasMask(Handler handler) {
		this.handler = handler;
		tickDamage = new Timer(30);
		currentDurability = 50;
		maxDurability = 50;
	}

	public boolean protectFromGas() {	
		if (tickDamage.isReady() && currentDurability > 0) {
			tickDamage.resetTimer();
			currentDurability--;
		}
		if(currentDurability > 0) {
			tickDamage.tick();
			return true;
		}
		else {
			currentDurability = 0;
			return false;
		}
		
	}

	public void repairMask() {
		currentDurability = 100;
	}

	public int getCurrentDurability() {
		return currentDurability;
	}

	public int getMaxDurability() {
		return maxDurability;
	}
	
	public void setCurrentDurability(int newDurability) {
		currentDurability = newDurability;
	}
}
