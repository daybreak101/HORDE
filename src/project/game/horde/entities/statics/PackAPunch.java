package project.game.horde.entities.statics;

import java.awt.Graphics;

import project.game.horde.entities.creatures.Player;
import project.game.horde.graphics.Assets;
import project.game.horde.main.Handler;
import project.game.horde.sounds.InteractSounds;
import project.game.horde.sounds.Sounds;
import project.game.horde.utils.Timer;
import project.game.horde.weapons.AA12;
import project.game.horde.weapons.AK47;
import project.game.horde.weapons.AWP;
import project.game.horde.weapons.Bren;
import project.game.horde.weapons.DoubleBarrel;
import project.game.horde.weapons.Flamethrower;
import project.game.horde.weapons.G18;
import project.game.horde.weapons.Glock17;
import project.game.horde.weapons.GrenadeLauncher;
import project.game.horde.weapons.Gun;
import project.game.horde.weapons.M16;
import project.game.horde.weapons.M1911;
import project.game.horde.weapons.M1Garand;
import project.game.horde.weapons.M4;
import project.game.horde.weapons.M60;
import project.game.horde.weapons.P90;
import project.game.horde.weapons.Python;
import project.game.horde.weapons.RPD;
import project.game.horde.weapons.RPG;
import project.game.horde.weapons.Thompson;
import project.game.horde.weapons.Type100;
import project.game.horde.weapons.Uzi;
import project.game.horde.weapons.Winchester1901;

public class PackAPunch extends InteractableStaticEntity {

	private boolean isUpgrading;
	private Gun gunPacked;
	private int packCounter, packTimer;
	private boolean cantAfford = false;
	private boolean cantUpgrade = false;

	public PackAPunch(Handler handler, int id, float x, float y) {
		super(handler, id, x, y, 100, 50);
		triggerText = "Press F to Upgrade Weapon: 5000";
		packTimer = 1000;
		isUpgrading = false;
	}

	@Override
	public void fulfillInteraction(Player player) {
		if(!handler.getWorld().isPowerOn()) {
			return;
		}
		
		if (usedByOtherPlayer) {

		} else if (!isUpgrading && cooldownTimer >= cooldown && !player.getInv().getGun().isUpgraded()) {
			// can afford
			if (player.getInv().purchase(5000)) {
				Sounds.playClip(InteractSounds.PURCHASE_ID, 1, 1, false);
				isUpgrading = true;
				cantAfford = false;
				cantUpgrade = false;
				cooldownTimer = 0;

				switch (player.getInv().getGun().getName()) {
				case "AA12":
					gunPacked = new AA12(handler, player);
					break;
				case "AK-47":
					gunPacked = new AK47(handler, player);
					break;
				case "AWP":
					gunPacked = new AWP(handler, player);
					break;
				case "Flamethrower":
					gunPacked = new Flamethrower(handler, player);
					break;
				case "Glock17":
					gunPacked = new Glock17(handler, player);
					break;
				case "Grenade Launcher":
					gunPacked = new GrenadeLauncher(handler, player);
					break;
				case "M4":
					gunPacked = new M4(handler, player);
					break;
				case "P90":
					gunPacked = new P90(handler, player);
					break;
				case "RPD":
					gunPacked = new RPD(handler, player);
					break;
				case "RPG":
					gunPacked = new RPG(handler, player);
					break;
				case "Winchester 1901":
					gunPacked = new Winchester1901(handler, player);
					break;
				case "M1 Garand":
					gunPacked = new M1Garand(handler, player);
					break;
				case "M16":
					gunPacked = new M16(handler, player);
					break;
				case "Bren":
					gunPacked = new Bren(handler, player);
					break;
				case "Double Barrel":
					gunPacked = new DoubleBarrel(handler, player);
					break;
				case "G18":
					gunPacked = new G18(handler, player);
					break;
				case "M60":
					gunPacked = new M60(handler, player);
					break;
				case "Python":
					gunPacked = new Python(handler, player);
					break;
				case "Thompson":
					gunPacked = new Thompson(handler, player);
					break;
				case "Type100":
					gunPacked = new Type100(handler, player);
					break;
				case "Uzi":
					gunPacked = new Uzi(handler, player);
					break;
				case "M1911":
					gunPacked = new M1911(handler, player);
					break;
				default:
					System.out.println("oof");
					break;

				}
				if (gunPacked == null) {
					isUpgrading = false;
					cantUpgrade = true;
					player.getInv().gainPoints(5000);
				} else {
					sendInteractableBusy();
					player.getInv().removeGunForUpgrade();
					handler.getGlobalStats().addGunUpgrade();
					gunPacked.upgradeWeapon();
				}
			}
			// can't afford

			else {
				Sounds.playClip(InteractSounds.CANTAFFORD_ID, 1, 1, false);
				cantAfford = true;
				cooldownTimer = 0;
			}

		} else if (!isUpgrading && cooldownTimer >= cooldown && player.getInv().getGun().isUpgraded()) {
			cantUpgrade = true;

		}
		// grab weapon
		else if (isUpgrading && cooldownTimer >= cooldown && packCounter < packTimer) {
			cooldownTimer = 0;
			isUpgrading = false;
			packCounter = 0;
			player.getInv().setGun(gunPacked);
			sendInteractableReady();
		}

	}

	int updater = 10;
	Timer updateSound = new Timer(updater);
	String currentFryingSound = "";
	float lastFryingVolume = 0;
	long lastFryingPosition = 0;

	public void fryingSounds() {
		float newvolume = InteractSounds.calculateVolumeBasedOffDistance(this, handler.getCurrentPlayer());

		// turn off current playing sound if it is too far
		if (newvolume <= 0)
			Sounds.stopClip(currentFryingSound);
		else {
			String newSound = "";
			if (isUpgrading)
				newSound = InteractSounds.FRYER_UPGRADING;
			else
				newSound = InteractSounds.FRYER_STATIC;

			if (!newSound.equals(currentFryingSound)) {
				Sounds.stopClip(currentFryingSound);
				currentFryingSound = newSound;
				Sounds.playClip(currentFryingSound, 1.0f, newvolume, true);
			}

			if (lastFryingVolume != newvolume) {
				lastFryingPosition = Sounds.getMillisecondPosition(currentFryingSound, updater);
				Sounds.stopClip(currentFryingSound);
				Sounds.playClipFrom(currentFryingSound, 1.0f, newvolume, lastFryingPosition, true);
				lastFryingVolume = newvolume;
			}
		}
	}

	public void beepingSounds() {
		float newvolume = InteractSounds.calculateVolumeBasedOffDistance(this, handler.getCurrentPlayer());
		if (isUpgrading && cooldownTimer >= cooldown && packCounter < packTimer) {
			Sounds.playClip(InteractSounds.FRYER_DONE, 1.0f, newvolume, false);
		} else {
			Sounds.stopClip(InteractSounds.FRYER_DONE);
		}
	}

	@Override
	public void postTick() {
		if(!handler.getWorld().isPowerOn()) {
			triggerText = "Requires power";
			return;
		}
		
		updateSound.tick();
		if (updateSound.isReady()) {
			fryingSounds();
		}
		beepingSounds();

		if (usedByOtherPlayer) {
			packCounter = 0;
			triggerText = "Busy";
		} else if (cantAfford && cooldownTimer < cooldown) {
			packCounter = 0;
			triggerText = "Not enough points!";
		} else if (cantUpgrade && cooldownTimer < cooldown) {
			triggerText = "     	 Weapon is not upgradable!";
		} else if (isUpgrading && cooldownTimer >= cooldown) {
			triggerText = "Press F to pick up " + gunPacked.getName();
			packCounter++;
			if (packCounter >= packTimer) {
				isUpgrading = false;
				sendInteractableReady();
			}
		} else if (!isUpgrading && cooldownTimer >= cooldown) {
			packCounter = 0;
			triggerText = "Press F to upgrade weapon: 5000";
			gunPacked = null;
		} else if (isUpgrading) {
			triggerText = "Upgrading...";
		} else {
			triggerText = "";
		}
	}

	@Override
	public void render(Graphics g) {
		g.drawImage(Assets.fryer, (int) (x - handler.getGameCamera().getxOffset()), 
				(int) (y - handler.getGameCamera().getyOffset()),
				width, height + 20, null);
//		g.setColor(Color.black);
//		g.fillRect((int) (x - handler.getGameCamera().getxOffset()), (int) (y - handler.getGameCamera().getyOffset()),
//				width, height);
//		g.setColor(Color.magenta);
//		g.fillRect((int) (x + 10 - handler.getGameCamera().getxOffset()),
//				(int) (y + 10 - handler.getGameCamera().getyOffset()), width - 40, height - 40);

	}



}
