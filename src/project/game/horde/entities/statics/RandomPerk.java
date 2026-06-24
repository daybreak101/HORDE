package project.game.horde.entities.statics;

import java.awt.Graphics;
import java.awt.geom.Point2D;
import java.util.Random;

import project.game.horde.entities.creatures.Player;
import project.game.horde.entities.creatures.playerinfo.PlayerActionState;
import project.game.horde.graphics.Assets;
import project.game.horde.main.Handler;
import project.game.horde.perks.DeadShot;
import project.game.horde.perks.DoubleTap;
import project.game.horde.perks.Juggernaut;
import project.game.horde.perks.MuleKick;
import project.game.horde.perks.Perk;
import project.game.horde.perks.PhD;
import project.game.horde.perks.Revive;
import project.game.horde.perks.SleightOfHand;
import project.game.horde.perks.StaminUp;
import project.game.horde.perks.Stronghold;
import project.game.horde.perks.Vampire;
import project.game.horde.sounds.InteractSounds;
import project.game.horde.sounds.Sounds;
import project.game.horde.utils.Timer;

public class RandomPerk extends InteractableStaticEntity {

	private boolean isSpun;
	private Perk perk;
	private int isSpunTimer, isSpunTime;
	private boolean cantAfford = false;
	private boolean fullPerks = false;

	public RandomPerk(Handler handler, int id, float x, float y) {
		super(handler, id, x, y, 75, 30);
		triggerText = "Press F to spin for a random perk: 2000";
		isSpun = false;
		isSpunTime = 1000;
	}

	@Override
	public void fulfillInteraction(Player player) {
		if (!handler.getWorld().isPowerOn()) {
			return;
		}
		// spin for perk
		if (usedByOtherPlayer) {

		} else if (isSpun == false && cooldownTimer >= cooldown) {
			if (isSpun == false && !player.getInv().checkPerkEmptySpot()) {
				fullPerks = true;
			} else if (player.getInv().purchase(2000)) {
				Sounds.playClip(InteractSounds.PURCHASE_ID, 1, 1, false);
				sendInteractableBusy();
				isSpun = true;
				cantAfford = false;
				cooldownTimer = 0;
				perk = getRandomPerk(player);
				handler.getGlobalStats().addPerkSpin();

				// don't give a perk player already has
				while (player.getInv().checkPerks(perk)) {
					perk = getRandomPerk(player);
				}
			} else {
				Sounds.playClip(InteractSounds.CANTAFFORD_ID, 1, 1, false);

				cantAfford = true;
				cooldownTimer = 0;
			}
		}
		// grab perk
		else if (isSpun == true && cooldownTimer >= cooldown && isSpunTimer < isSpunTime
				&& player.getPlayerInput().canEat()) {
			cooldownTimer = 0;
			isSpun = false;
			isSpunTimer = 0;
			handler.getGlobalStats().addPerk();
			player.getInv().addPerk(perk);
			sendInteractableReady();
			Sounds.playClip(InteractSounds.VENDING_GRAB, 1.0f, 1.0f, false);
			player.setActionState(PlayerActionState.EATING);
		}

	}

	public Perk getRandomPerk(Player player) {
		Random rand = new Random();
		int rng = rand.nextInt(12);
		int level;
		switch (rng) {
		case 0 -> {
                    level = handler.getUnlocks().getJuggLvl();
                    return new Juggernaut(handler, level, player);
                }
		case 1 -> {
                    level = handler.getUnlocks().getSpeedLvl();
                    return new SleightOfHand(handler, level, player);
                }
		case 2 -> {
                    level = handler.getUnlocks().getDoubletapLvl();
                    return new DoubleTap(handler, level, player);
                }
		case 3 -> {
                    level = handler.getUnlocks().getDeadshotLvl();
                    return new DeadShot(handler, level, player);
                }
		case 4 -> {
                    level = handler.getUnlocks().getPhdLvl();
                    return new PhD(handler, level, player);
                }
		case 5 -> {
                    level = handler.getUnlocks().getStaminaLvl();
                    return new StaminUp(handler, level, player);
                }
		case 6 -> {
                    level = handler.getUnlocks().getVampireLvl();
                    return new Vampire(handler, level, player);
                }
		case 7 -> {
                    level = handler.getUnlocks().getMuleLvl();
                    return new MuleKick(handler, level, player);
                }
		case 9 -> {
                    level = handler.getUnlocks().getReviveLvl();
                    return new Revive(handler, level, player);
                }
		case 11 -> {
                    level = handler.getUnlocks().getStrongholdLvl();
                    return new Stronghold(handler, level, player);
                }
		}
            // case 10:
//			level = handler.getUnlocks().getLunaLvl();
            // return new Luna(handler,0, player);
		level = handler.getUnlocks().getMuleLvl();
		return new MuleKick(handler, level, player);
	}

	int updater = 10;
	Timer updateSound = new Timer(updater);
	float lastStaticVolume = 0;
	long lastStaticPosition = 0;
	String currentStaticSound = "";

	public void staticSounds() {
		float newvolume = InteractSounds.calculateVolumeBasedOffDistance(this, handler.getCurrentPlayer());

		// turn off current playing sound if it is too far
		if (newvolume <= 0) {
			Sounds.stopClip(currentStaticSound);
			currentStaticSound = "";
		} else {
			String newSound = InteractSounds.VENDING_STATIC;

			if (!newSound.equals(currentStaticSound)) {
				Sounds.stopClip(currentStaticSound);
				currentStaticSound = newSound;
				Sounds.playClip(currentStaticSound, 1.0f, newvolume, true);
			}

			if (lastStaticVolume != newvolume) {
				lastStaticPosition = Sounds.getMillisecondPosition(currentStaticSound, updater);
				Sounds.stopClip(currentStaticSound);
				Sounds.playClipFrom(currentStaticSound, 1.0f, newvolume, lastStaticPosition, true);
				lastStaticVolume = newvolume;
			}
		}
	}

	float lastActivationVolume = 0;
	long lastActivationPosition = 0;
	String currentActivationSound = "";

	public void activationSounds() {
		if (isSpun) {
			float newvolume = InteractSounds.calculateVolumeBasedOffDistance(this, handler.getCurrentPlayer());

			// turn off current playing sound if it is too far
			if (newvolume <= 0) {
				Sounds.stopClip(currentActivationSound);
				currentActivationSound = "";
			} else {
				String newSound = InteractSounds.VENDING_ACTIVATION;

				if (!newSound.equals(currentActivationSound)) {
					Sounds.stopClip(currentActivationSound);
					currentActivationSound = newSound;
					Sounds.playClip(currentActivationSound, 1.0f, newvolume, false);
				}

				if (lastStaticVolume != newvolume) {
					lastStaticPosition = Sounds.getMillisecondPosition(currentActivationSound, updater);
					Sounds.stopClip(currentActivationSound);
					Sounds.playClipFrom(currentActivationSound, 1.0f, newvolume, lastActivationPosition, false);
					lastActivationVolume = newvolume;
				}
			}
		} else {
			Sounds.stopClip(currentActivationSound);
			currentActivationSound = "";
		}
	}

	@Override
	public void postTick() {
		if (!handler.getWorld().isPowerOn()) {
			triggerText = "Requires power";
			return;
		}
		updateSound.tick();
		if (updateSound.isReady()) {
			staticSounds();
			activationSounds();
		}

		if (usedByOtherPlayer) {
			isSpunTimer = 0;
			triggerText = "Busy";
		} else if (cantAfford == true && cooldownTimer < cooldown) {
			isSpunTimer = 0;
			triggerText = "Not enough points!";
		} else if (fullPerks == true && cooldownTimer < cooldown) {
			isSpunTimer = 0;
			triggerText = "Can only have four perks!";
		} else if (isSpun == true && cooldownTimer >= cooldown) {
			triggerText = "Press F to pick up " + perk.getName();
			isSpunTimer++;
			if (isSpunTimer >= isSpunTime) {
				isSpun = false;
				sendInteractableReady();
			}
		} else if (isSpun == false && cooldownTimer >= cooldown) {
			isSpunTimer = 0;
			triggerText = "Press F to spin for a random perk: 2000";
			perk = null;
		} else if (isSpun == true) {
			triggerText = "Spinning...";
		} else {
			triggerText = "";
		}
	}

//									//item 				player
//	public Point2D.Float createPoint(Point2D.Float p1, Point2D.Float p2){
//		float slope = (p2.y - p1.y) / (p2.x - p1.x);
//		if(p2.x == p1.x) {
//			slope = Float.MAX_VALUE;
//		}
//		
//		float distance = 20;
//		double dx, dy;
//	    if (slope == Float.MAX_VALUE) {
//	        // For a vertical line, only move along the y-axis
//	        dx = p1.x;
//	        dy = p1.y + distance;
//	    } else {
//	    	double distanceFactor = 1 / Math.sqrt(Math.pow(slope, 2) + 1);
//	        dx = p1.x + distance * distanceFactor;
//	        dy = p1.y + distance * slope * distanceFactor;
//	    }
//		
//		return new Point2D.Float((float) dx, (float) dy);
//	}
	public Point2D.Float createPoint(Point2D.Float p1, Point2D.Float p2) {
	    // Calculate the difference in coordinates
	    float dx = p2.x - p1.x;
	    float dy = p2.y - p1.y;

	    // Normalize the vector (dx, dy) to get the direction
	    float length = (float) Math.sqrt(dx * dx + dy * dy);
	    float normX = dx / length;
	    float normY = dy / length;

	    // Move a fixed distance along the normalized direction
	    float distance = 20;
	    float newX = p1.x - normX * distance;
	    float newY = p1.y - normY * distance;

	    // Return the new point
	    return new Point2D.Float(newX, newY);
	}
	
	@Override
	public void render(Graphics g) {		
		g.drawImage(Assets.perkvendor, 
				(int) (x - handler.getGameCamera().getxOffset() - 24),
				(int) (y - handler.getGameCamera().getyOffset() - 60), 
				width + 43, height + 60, null);
	}


}
