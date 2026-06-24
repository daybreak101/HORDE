package project.game.horde.entities.statics.traps;

import java.awt.Color;
import java.awt.Graphics;

import project.game.horde.entities.creatures.Player;
import project.game.horde.entities.statics.InteractableStaticEntity;
import project.game.horde.main.Handler;


public class TrapSwitch extends InteractableStaticEntity {

	private int rotation;
	private Trap trap;
	private boolean cantAfford;
	boolean isActive;

	public TrapSwitch(Handler handler, int id, float x, float y, int rotation, Trap trap, int cooldown) {
		super(handler, id, x, y, 25, 25);

		// 0 is up
		// 1 is down
		// 2 is left
		// 3 is right
		this.rotation = rotation;
		this.trap = trap;
		this.cooldown = cooldown;
		cooldownTimer = cooldown;
		bounds.x = 0;
		bounds.y = 0;
		bounds.width = 0;
		bounds.height = 0;
	}

	@Override
	public void fulfillInteraction(Player player) {
		if (!isActive && cooldownTimer >= cooldown) {

			// can afford
			if (player.getInv().purchase(trap.getCost())) {
				sendInteractableBusy();
				trap.fulfillInteraction(player);
				cantAfford = false;

			}
			// can't afford
			else {

				cantAfford = true;
				cooldownTimer = 0;
			}
		}
	}

	@Override
	public void postTick() {
		isActive = trap.getActivation();
		if(!isActive && usedByOtherPlayer) {
			trap.fulfillOtherPlayerInteraction();
			usedByOtherPlayer = false;
			//sendInteractableReady();
			cooldownTimer = 0;
		}
		if (cantAfford && cooldownTimer < cooldown) {
			triggerText = "              Not enough points!";
		} else if (!isActive && cooldownTimer < cooldown) {
			triggerText = "Trap is cooling down...";

		} else if (!isActive && cooldownTimer >= cooldown) {

			triggerText = "Press F to activate trap: " + trap.getCost();
		} else if (isActive) {
			triggerText = "            Trap is running...";
			cooldownTimer = 0;
		} else {
			triggerText = "";
		}

	}

	@Override
	public void render(Graphics g) {
		g.setColor(Color.red);

		// 0 is up
		// 1 is down
		// 2 is left
		// 3 is right

		switch (rotation) {
		case 0:
			g.fillRect((int) (x - handler.getGameCamera().getxOffset()),
					(int) (y - height/2 - handler.getGameCamera().getyOffset()), width, height);
			break;
		case 1:
			g.fillRect((int) (x - handler.getGameCamera().getxOffset()),
					(int) (y + height/2 - handler.getGameCamera().getyOffset()), width, height);
			break;
		case 2:
			g.fillRect((int) (x - width/2 - handler.getGameCamera().getxOffset()),
					(int) (y - handler.getGameCamera().getyOffset()), width, height);
			break;
		case 3:
			g.fillRect((int) (x + width/2 - handler.getGameCamera().getxOffset()),
					(int) (y - handler.getGameCamera().getyOffset()), width, height);
			break;
		default:
			g.fillRect((int) (x - handler.getGameCamera().getxOffset()),
					(int) (y - handler.getGameCamera().getyOffset()), width, height);
			break;
		}

	}
	


}
