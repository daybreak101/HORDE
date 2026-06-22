package project.game.horde.entities.statics;

import java.awt.Graphics;

import project.game.horde.entities.creatures.Player;
import project.game.horde.graphics.Assets;
import project.game.horde.graphics.BWAssets;
import project.game.horde.main.Handler;
import project.game.horde.sounds.InteractSounds;
import project.game.horde.sounds.Sounds;

public class AmmoRefill extends InteractableStaticEntity {

	private boolean cantAfford = false;
	private boolean fullAmmo = false;

	public AmmoRefill(Handler handler, int id, float x, float y) {
		super(handler, id, x, y, 75, 75);
		triggerText = "Press F to refill current weapon: 1000";
	}

	@Override
	public void render(Graphics g) {
		g.drawImage(Assets.ammoBox, (int) (x - handler.getGameCamera().getxOffset()),
				(int) (y - handler.getGameCamera().getyOffset()), width, height, null);

	}

	@Override
	public void renderBW(Graphics g) {
		g.drawImage(BWAssets.ammoBox, (int) (x - handler.getGameCamera().getxOffset()),
				(int) (y - handler.getGameCamera().getyOffset()), width, height, null);

	}

	public void fulfillInteraction(Player player) {
		if (usedByOtherPlayer) {

		} else if (cooldownTimer >= cooldown
				&& !(player.getInv().getGun().getCurrentReserve() == player.getInv().getGun().getMaxReserve())) {
			cooldownTimer = 0;
			if (player.getInv().purchase(1000)) {
				sendInteractableBusy();
				player.getInv().purchaseAmmo();
				cantAfford = false;
			} else {
				cantAfford = true;
				cooldownTimer = 0;
			}

		}
		if (cooldownTimer >= cooldown
				&& (player.getInv().getGun().getName() == "Flamethrower"
						|| player.getInv().getGun().getName() == "HotBox")
				&& !(player.getInv().getGun().getCurrentClip() == player.getInv().getGun().getClip())) {
			cooldownTimer = 0;
			if (player.getInv().purchase(1000)) {
				Sounds.playClip(InteractSounds.PURCHASE_ID, 1, 1, false);
				sendInteractableBusy();
				player.getInv().purchaseAmmo();
				cantAfford = false;
			} else {
				Sounds.playClip(InteractSounds.CANTAFFORD_ID, 1, 1, false);
				cantAfford = true;
				cooldownTimer = 0;
			}

		} else if (player.getInv().getGun().getCurrentReserve() == player.getInv().getGun().getMaxReserve()) {
			cooldownTimer = 0;
			fullAmmo = true;
		}

	}

	@Override
	public void postTick() {
		if (usedByOtherPlayer) {
			triggerText = "Busy";
		} else if (cantAfford && cooldownTimer < cooldown) {
			triggerText = "Not enough points!";
		} else if (fullAmmo && cooldownTimer < cooldown) {
			triggerText = "Full on ammo!";
		} else if (cooldownTimer >= cooldown) {
			fullAmmo = false;
			triggerText = "Press F to refill current weapon: 1000";
			sendInteractableReady();
		} else {
			triggerText = "";
		}
	}

}
