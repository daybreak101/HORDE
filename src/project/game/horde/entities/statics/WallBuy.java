package project.game.horde.entities.statics;

import java.awt.Color;
import java.awt.Graphics;

import project.game.horde.entities.creatures.Player;
import project.game.horde.main.Handler;
import project.game.horde.sounds.InteractSounds;
import project.game.horde.sounds.Sounds;
import project.game.horde.weapons.Glock17;
import project.game.horde.weapons.Gun;
import project.game.horde.weapons.P90;

public class WallBuy extends InteractableStaticEntity {
	private int gunId;
	private int whatWall;
	private Gun gun4Sale;
	private int cost;
	private boolean cantAfford = false;
	private boolean gunOwned = false;
	private boolean gunOwnedUpgraded = false;
	private Gun gunOwnedRef = null;

	//top, bottom, right, left
	public WallBuy(Handler handler, int id, float x, float y, int z, int gunId, int whatWall) {
		super(handler, id, x, y, z, 1, 1);
		this.whatWall = whatWall;
		this.gunId = gunId;
		// bounds = new Rectangle((int)x, (int)y, 1, 1);
		if (whatWall == 0) {
			this.y -= 10;
			width = 50;
			height = 25;
		} else if (whatWall == 1) {
			this.y += 10;
			width = 50;
			height = 25;
		} else if (whatWall == 2) {
			this.x += 10;
			width = 25;
			height = 50;
		} else if (whatWall == 3) {
			this.x -= 10;
			width = 25;
			height = 50;
		}

	}

	public void postTick() {
		switch (this.gunId) {
		case 1:
			gun4Sale = new Glock17(handler, handler.getCurrentPlayer());
			cost = 500;
			break;
		case 2:
			gun4Sale = new P90(handler, handler.getCurrentPlayer());
			cost = 2000;
			break;
		}
		// don't give a weapon player already has
		Player player = handler.getCurrentPlayer();
		for (Gun gun : player.getInv().getArsenal()) {
			if (gun != null && gun.getOriginalName().equals(gun4Sale.getOriginalName())) {
				gunOwnedRef = gun;
				gunOwned = true;
				gunOwnedUpgraded = gunOwnedRef.isUpgraded();
			}
		}
		if (cantAfford == true && cooldownTimer < cooldown) {
			triggerText = "Not enough points!";
		} else if (!gunOwned && cooldownTimer >= cooldown) {
			triggerText = "Press F to purchase " + gun4Sale.getName() + ":" + cost;
		} else if (gunOwnedUpgraded && cooldownTimer >= cooldown) {
			triggerText = "Press F to purchase ammo:" + 4500;
		} else if (gunOwned && cooldownTimer >= cooldown) {
			triggerText = "Press F to purchase ammo: " + cost/2;
		} else {
			triggerText = "";
		}
	}

	public void fulfillInteraction(Player player) {
		if (cooldownTimer >= cooldown) {
			if (!gunOwned && player.getInv().purchase(cost)) {
				Sounds.playClip(InteractSounds.PURCHASE_ID, 1, 1, false);
				cantAfford = false;
				cooldownTimer = 0;
				player.getInv().setGun(gun4Sale);
			} else if (gunOwnedUpgraded && player.getInv().purchase(4500)) {
				Sounds.playClip(InteractSounds.PURCHASE_ID, 1, 1, false);
				cantAfford = false;
				cooldownTimer = 0;
				gunOwnedRef.setCurrentReserve(gunOwnedRef.getMaxReserve());
			} else if (gunOwned && player.getInv().purchase(cost / 2)) {
				Sounds.playClip(InteractSounds.PURCHASE_ID, 1, 1, false);
				cantAfford = false;
				cooldownTimer = 0;
				gunOwnedRef.setCurrentReserve(gunOwnedRef.getMaxReserve());
			} else {
				Sounds.playClip(InteractSounds.CANTAFFORD_ID, 1, 1, false);
				cantAfford = true;
				cooldownTimer = 0;
			}

		}
	}

	@Override
	public void render(Graphics g) {
		// TODO Auto-generated method stub
		g.setColor(new Color(255, 255, 255));
		g.fillRect((int) (x - handler.getGameCamera().getxOffset()),
				(int) (y - handler.getGameCamera().getyOffset()), width, height);

	}

	@Override
	public void renderBW(Graphics g) {
		// TODO Auto-generated method stub
		g.setColor(new Color(42, 42, 42));
		g.fillRect((int) (x + width - handler.getGameCamera().getxOffset()),
				(int) (y + height - handler.getGameCamera().getyOffset()), width, height);

	}

}
