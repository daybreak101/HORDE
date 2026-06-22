package project.game.horde.entities.statics;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.Random;

import project.game.horde.entities.creatures.Player;
import project.game.horde.main.Handler;
import project.game.horde.sounds.InteractSounds;
import project.game.horde.sounds.Sounds;
import project.game.horde.utils.Timer;

public class RitualCircle extends InteractableStaticEntity {

	private int soulsFed;
	private String blessing;
	private boolean cantAfford = false;
	private Timer pickupTimer;
	private boolean canPickup;

	public RitualCircle(Handler handler, int id, float x, float y) {
		super(handler, id, x, y, 0, 0);
		triggerText = "Souls for a blessing";
		blessing = "";
		soulsFed = 0;
		canPickup = false;
		pickupTimer = new Timer(600);
	}

	public void feedSoul() {
		soulsFed++;
	}

	// spin for random blessing
	@Override
	public void fulfillInteraction(Player player) {
		// spin for blessing
		if (usedByOtherPlayer) {

		} else if (!canPickup && cooldownTimer >= cooldown) {

			if (handler.getBlessings().getEquipped().size() > 0) {
				if (player.getInv().purchase(500)) {
					Sounds.playClip(InteractSounds.PURCHASE_ID, 1, 1, false);
					canPickup = true;
					cantAfford = false;
					cooldownTimer = 0;
					blessing = getRandomBlessing();
					sendInteractableBusy();
					// change to blessing spins
					// handler.getGlobalStats().addBoxSpin();
				}
				else {
					Sounds.playClip(InteractSounds.CANTAFFORD_ID, 1, 1, false);
					cantAfford = true;
					cooldownTimer = 0;
				}
			} else {
				Sounds.playClip(InteractSounds.CANTAFFORD_ID, 1, 1, false);
				cantAfford = true;
				cooldownTimer = 0;
			}

		}
		// grab blessing
		else if (canPickup && cooldownTimer >= cooldown && !pickupTimer.isReady()) {
			cooldownTimer = 0;
			canPickup = false;
			pickupTimer.resetTimer();
			if(handler.getBlessings().getAmount(blessing) > 0) {
				player.getInv().getBlessings().setBlessing(blessing);
			}
			// change to blessings pulled
			// handler.getGlobalStats().addBoxPull();
			sendInteractableReady();
		}

	}

	public String getRandomBlessing() {
		Random rand = new Random();
		int rng = rand.nextInt(handler.getBlessings().getEquipped().size());
		ArrayList<String> blessings = handler.getBlessings().getEquipped();
		return blessings.get(rng);
	}

	@Override
	public void postTick() {
		if (usedByOtherPlayer) {
			pickupTimer.resetTimer();
			triggerText = "Busy";
		} else if (cantAfford && cooldownTimer < cooldown) {
			pickupTimer.resetTimer();
			;
			if (handler.getBlessings().getEquipped().size() <= 0) {
				triggerText = "Did not equip any Blessings!";
			} else {
				triggerText = "Not enough points!";
			}
		} else if (canPickup && cooldownTimer >= cooldown) {
			if (handler.getBlessings().getAmount(blessing) <= 0) {
				triggerText = "Out of " + blessing;
			} else {
				triggerText = "Press F to trade blessing for " + blessing;
			}
			pickupTimer.tick();
			if (pickupTimer.isReady()) {
				canPickup = false;
				sendInteractableReady();
			}
		} else if (!canPickup && cooldownTimer >= cooldown) {
			pickupTimer.resetTimer();
			;
			triggerText = "Press F to spin for a random blessing: 500";
			blessing = "";
		} else if (canPickup) {
			triggerText = "Calling...";
		} else {
			triggerText = "";
		}
	}

	@Override
	public void render(Graphics g) {
		g.setColor(new Color(255, 0, 255, 100));
		g.fillOval((int) (x - 100 - handler.getGameCamera().getxOffset()),
				(int) (y - 100 - handler.getGameCamera().getyOffset()), 200, 200);

	}

	@Override
	public void renderBW(Graphics g) {
		g.setColor(new Color(105, 105, 105, 100));
		g.fillOval((int) (x - 100 - handler.getGameCamera().getxOffset()),
				(int) (y - 100 - handler.getGameCamera().getyOffset()), 200, 200);

	}

}
