package project.game.horde.weapons;

import java.net.URL;

import project.game.horde.entities.creatures.Player;
import project.game.horde.main.Handler;
import project.game.horde.perks.DoubleTap;
import project.game.horde.perks.SleightOfHand;
import project.game.horde.sounds.GunSounds;
import project.game.horde.sounds.Sounds;

public abstract class Gun {
	protected Player player;
	protected String name, upgradedName, originalName;
	protected int damage, fireRate, clip, maxReserve, reloadSpeed;
	protected Handler handler;
	protected int currentClip;
	protected int currentReserve;
	protected float weight;
	protected int range;

	protected boolean readyToFire = true;
	protected boolean isReloading = false;
	protected int timerToFire = 0;
	protected int reloadTimer = 0;

	protected int doubletap = -1;
	protected int speedcola = -1;

	protected boolean isUpgraded = false;
	
	protected String reloadSound;

	// add weight that would be subtracted from player speed
	public Gun(Handler handler, Player owner, int damage, int fireRate, int reloadSpeed, int clip, int maxReserve, float weight,
			int range) {
		this.handler = handler;
		this.player = owner;
		this.damage = damage;
		this.fireRate = fireRate;
		this.reloadSpeed = reloadSpeed;
		this.clip = clip;
		if (player != null && player.getInv() != null) {
			if (player.getInv().getMule() >= 2) {
				this.maxReserve = maxReserve + (clip * 2);
			} 
			else {
				this.maxReserve = maxReserve;
			}
		}
		else {
			this.maxReserve = maxReserve;
		}

		this.currentClip = clip;
		this.currentReserve = maxReserve;
		this.weight = weight;
		this.range = range;
	}

	public void shoot() {}

	public void postTick() {}

	public void giveClip() {
		if (currentReserve + clip > maxReserve) {
			currentReserve = maxReserve;
		} else {
			currentReserve = currentReserve + clip;
		}
	}

	public void activateLevel2MuleKick() {
		maxReserve = maxReserve + clip;
		giveClip();
	}

	public void deactivateLevel2MuleKick() {
		maxReserve = maxReserve - clip;

		if (currentReserve - clip > 0) {
			currentReserve = currentReserve - clip;
		}
	}

	public void upgradeWeapon() {
		if (!isUpgraded) {
			isUpgraded = true;
			damage = damage * 2;
			clip = clip + clip / 2;
			maxReserve = maxReserve + maxReserve / 2;
			currentClip = clip;
			currentReserve = maxReserve;
			name = upgradedName;
		}
	}

	public void reloadFinish() {
		if (currentReserve < (clip - currentClip)) {
			currentClip += currentReserve;
			currentReserve = 0;
		} else {
			currentReserve = currentReserve - (clip - currentClip);
			currentClip = clip;
		}
	}

	// public abstract void render();
	public void tick() {
		doubletap = player.getInv().getDoubletap();
		speedcola = player.getInv().getSpeedcola();

		if (isReloading) {
			reloadTimer++;
			if (speedcola == 0 && reloadTimer >= reloadSpeed * SleightOfHand.BASE_RELOADBUFF) {
				reloadFinish();
				isReloading = false;
				reloadTimer = 0;
			} 
			else if (speedcola == 1 && reloadTimer >= reloadSpeed * SleightOfHand.LVL1_RELOADBUFF) {
				reloadFinish();
				isReloading = false;
				reloadTimer = 0;
			} 
			else if (speedcola == 2 && reloadTimer >= reloadSpeed * SleightOfHand.LVL2_RELOADBUFF) {
				reloadFinish();
				isReloading = false;
				reloadTimer = 0;
			} 
			else if (speedcola == 3 && reloadTimer >= reloadSpeed * SleightOfHand.LVL3_RELOADBUFF) {
				reloadFinish();
				isReloading = false;
				reloadTimer = 0;
			} 
			else if (reloadTimer >= reloadSpeed) {
				reloadFinish();
				isReloading = false;
				reloadTimer = 0;
			}
		} else if (doubletap >= 2 && timerToFire >= fireRate * DoubleTap.LVL2_FIRERATEBUFF) {
			readyToFire = true;
			timerToFire = 0;
		} else if (doubletap > -1 && timerToFire >= fireRate * DoubleTap.BASE_FIRERATEBUFF) {
			readyToFire = true;
			timerToFire = 0;
		} else if (timerToFire >= fireRate) {
			readyToFire = true;
			timerToFire = 0;
		}
		// autoreload when clip is empty
		if (currentClip == 0 && readyToFire && player.getPlayerInput().canReload()) {
			reload();
		}
		timerToFire++;
		postTick();
	}

	public void reload() {
		speedcola = player.getInv().getSpeedcola();
		// dont do reload animation when there is no reloading being done
		if ((currentClip != clip) && (currentReserve > 0) && !isReloading) {
			isReloading = true;
			switch(speedcola) {
			case 0:
				Sounds.playClip(reloadSound, 1, -1.0f, false);
				//Sounds.playClip(reloadSound, 10/9, "gunReload", -1.0f, false);
				break;
			case 1:
				Sounds.playClip(reloadSound, 4/3, -1.0f, false);
			//	Sounds.playClip(reloadSound, 4/3, "gunReload", -1.0f, false);
				break;
			case 2:
				Sounds.playClip(reloadSound, 2, -1.0f, false);
		//		Sounds.playClip(reloadSound, 2.0f, "gunReload", -1.0f, false);
				break;
			case 3:
				Sounds.playClip(reloadSound, 10/3, -1.0f, false);
	//			Sounds.playClip(reloadSound, 10/3, "gunReload", -1.0f, false);
				break;
			default:
				Sounds.playClip(reloadSound, 1, -1.0f, false);
//				Sounds.playClip(reloadSound, 1, "gunReload", -1.0f, false);
				break;
			}
			
		}

	}
	

	public float getReloadProgress() {
		return (float) reloadTimer / (float) reloadSpeed;
	}

	public boolean getIsReloading() {
		return isReloading;
	}

	public void setReloading(boolean isReloading) {
		if(this.isReloading != isReloading && reloadSound != null) {
			Sounds.stopClip(reloadSound);
		}
		this.isReloading = isReloading;
		
	}

	public int getDamage() {
		return damage;
	}

	public String getName() {
		return name;
	}

	public int getFireRate() {
		return fireRate;
	}

	public int getClip() {
		return clip;
	}

	public int getMaxReserve() {
		return maxReserve;
	}

	public Handler getHandler() {
		return handler;
	}

	public int getCurrentClip() {
		return currentClip;
	}

	public int getCurrentReserve() {
		return currentReserve;
	}

	public float getWeight() {
		return weight;
	}

	public void setCurrentReserve(int currentReserve) {
		this.currentReserve = currentReserve;
	}

	public void setReadyToFire(boolean readyToFire) {
		this.readyToFire = readyToFire;
	}

	public void setReloadTimer(int reloadTimer) {
		this.reloadTimer = reloadTimer;
	}

	public void setClip(int clip) {
		this.clip = clip;
	}

	public void setCurrentClip(int currentClip) {
		this.currentClip = currentClip;
	}

	public boolean isUpgraded() {
		return isUpgraded;
	}

	public void setUpgraded(boolean isUpgraded) {
		this.isUpgraded = isUpgraded;
	}

	public String getOriginalName() {
		return originalName;
	}

	public void setOriginalName(String originalName) {
		this.originalName = originalName;
	}
	
	public Player getPlayer() {
		return player;
	}
	
}
