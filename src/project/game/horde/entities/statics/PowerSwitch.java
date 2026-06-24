package project.game.horde.entities.statics;

import java.awt.Color;
import java.awt.Graphics;

import project.game.horde.entities.creatures.Player;
import project.game.horde.main.Handler;

public class PowerSwitch extends InteractableStaticEntity {
	
	private boolean isPowerOn = false;
	
	public PowerSwitch(Handler handler, int id, float x, float y) {
		super(handler, id, x, y, 50, 50);
	}
	
	@Override
	public void fulfillInteraction(Player player) {
		// spin for perk
		if (usedByOtherPlayer) {
			isPowerOn = true;
			handler.getWorld().setPowerOn(true);
			cooldownTimer = 0;
		} else if (!isPowerOn && cooldownTimer >= cooldown) {
			sendInteractableBusy();
			isPowerOn = true;
			handler.getWorld().setPowerOn(true);
			cooldownTimer = 0;
		}
	}


	@Override
	public void postTick() {
		if (usedByOtherPlayer) {
			triggerText = "Busy";
		} else if (!isPowerOn && cooldownTimer >= cooldown) {
			triggerText = "Press F to turn on power";
		} else {
			triggerText = "Already powered on!";
		}
	}


	@Override
	public void render(Graphics g) {
		g.setColor(new Color(100, 100, 255));
		g.fillRect((int) (x - handler.getGameCamera().getxOffset()),
				(int) (y - handler.getGameCamera().getyOffset()), width, height);

	}

}
