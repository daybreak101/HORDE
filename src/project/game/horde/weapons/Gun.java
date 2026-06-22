package project.game.horde.weapons;

import java.awt.image.BufferedImage;
import java.awt.Graphics;
import project.game.horde.entities.creatures.Player;
import project.game.horde.main.Handler;
import project.game.horde.perks.DoubleTap;
import project.game.horde.perks.SleightOfHand;
import project.game.horde.sounds.Sounds;

public abstract class Gun {
	protected Player player;
	protected String name, upgradedName, originalName;
	protected int damage, fireRate, clip, maxReserve, reloadSpeed;
	protected Handler handler;
	protected int currentClip, currentAltClip;
	protected int currentReserve;
	protected float weight;
	protected int range;
	protected boolean isDual = false;

	protected boolean readyToFire = true;
	protected boolean altReadyToFire = true;
	protected boolean isReloading = false;
	protected boolean isAltReloading = false;
	protected int timerToFire = 0;
	protected int altTimerToFire = 0;
	protected int reloadTimer = 0;
	protected int altReloadTimer = 0;

	protected int doubletap = -1;
	protected int speedcola = -1;

	protected boolean isUpgraded = false;
	
	protected String reloadSound;
	protected BufferedImage top;
	protected GunImageDim gunImageDim;

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
	
	public BufferedImage getGunImage() {
		return top;
	}
	
	public void altShoot() {}
	
	public void altShootSingle() {}

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
		uniqueUpgrades();
	}
	
	public void uniqueUpgrades() {}

	public void reloadFinish() {
		if (currentReserve < (clip - currentClip)) {
			currentClip += currentReserve;
			currentReserve = 0;
		} else {
			currentReserve = currentReserve - (clip - currentClip);
			currentClip = clip;
		}
	}
	
	public void altReloadFinish() {
		if (currentReserve < (clip - currentAltClip)) {
			currentAltClip += currentReserve;
			currentReserve = 0;
		} else {
			currentReserve = currentReserve - (clip - currentAltClip);
			currentAltClip = clip;
		}
	}

	// public abstract void render();
	public void tick() {
		doubletap = player.getInv().getDoubletap();
		speedcola = player.getInv().getSpeedcola();
		if (isReloading) {
			reloadTimer++;
			if ((speedcola == 0 && reloadTimer >= reloadSpeed * SleightOfHand.BASE_RELOADBUFF)
				|| (speedcola == 1 && reloadTimer >= reloadSpeed * SleightOfHand.LVL1_RELOADBUFF)
				|| (speedcola == 2 && reloadTimer >= reloadSpeed * SleightOfHand.LVL2_RELOADBUFF) 
				|| (speedcola == 3 && reloadTimer >= reloadSpeed * SleightOfHand.LVL3_RELOADBUFF) 
				|| (reloadTimer >= reloadSpeed)) {
				reloadFinish();
				isReloading = false;
				reloadTimer = 0;
			}
		} else if ((doubletap >= 2 && timerToFire >= fireRate * DoubleTap.LVL2_FIRERATEBUFF) 
				|| (doubletap > -1 && timerToFire >= fireRate * DoubleTap.BASE_FIRERATEBUFF) 
				|| (timerToFire >= fireRate)) {
			readyToFire = true;
			timerToFire = 0;
		}
		// autoreload when clip is empty
		if (!isReloading && currentClip == 0 && readyToFire && player.getPlayerInput().canReload()) {
			reload();
		}
		timerToFire++;
		
		//alt
		if (isDual && isAltReloading) {
			altReloadTimer++;
			if ((speedcola == 0 && altReloadTimer >= reloadSpeed * SleightOfHand.BASE_RELOADBUFF) 
					|| (speedcola == 1 && altReloadTimer >= reloadSpeed * SleightOfHand.LVL1_RELOADBUFF) 
					|| (speedcola == 2 && altReloadTimer >= reloadSpeed * SleightOfHand.LVL2_RELOADBUFF) 
					|| (speedcola == 3 && altReloadTimer >= reloadSpeed * SleightOfHand.LVL3_RELOADBUFF)
					|| (altReloadTimer >= reloadSpeed)) {
				altReloadFinish();
				isAltReloading = false;
				altReloadTimer = 0;
			}
		} else if ((doubletap >= 2 && altTimerToFire >= fireRate * DoubleTap.LVL2_FIRERATEBUFF)
				|| (doubletap > -1 && altTimerToFire >= fireRate * DoubleTap.BASE_FIRERATEBUFF) 
				|| (altTimerToFire >= fireRate)) {
			altReadyToFire = true;
			altTimerToFire = 0;
		}
		// autoreload when clip is empty
		if (isDual && !isAltReloading && currentAltClip == 0 && altReadyToFire && player.getPlayerInput().canReload()) {
			altReload();
		}
		altTimerToFire++;
		
		postTick();
	}

	public void reload() {
		speedcola = player.getInv().getSpeedcola();
		// dont do reload animation when there is no reloading being done
		if ((currentClip != clip) && (currentReserve > 0) && !isReloading) {
			isReloading = true;
			switch(speedcola) {
			case 0:
				Sounds.playClip(reloadSound + "0", 1, -1.0f, false);
				break;
			case 1:
				Sounds.playClip(reloadSound + "1", 1.33f, -1.0f, false);
				break;
			case 2:
				Sounds.playClip(reloadSound + "2", 2, -1.0f, false);
				break;
			case 3:
				Sounds.playClip(reloadSound + "3", 3.33f, -1.0f, false);
				break;
			default:
				Sounds.playClip(reloadSound, 1, -1.0f, false);
				break;
			}	
		}
//		if(isDual) {
//			altReload();
//		}

	}
	
	public void altReload() {
		speedcola = player.getInv().getSpeedcola();
		// dont do reload animation when there is no reloading being done
		if ((currentAltClip != clip) && (currentReserve > 0) && !isAltReloading) {
			isAltReloading = true;
			switch(speedcola) {
			case 0:
				Sounds.playClip(reloadSound + "0", 1, -1.0f, false);
				break;
			case 1:
				Sounds.playClip(reloadSound + "1", 1.33f, -1.0f, false);
				break;
			case 2:
				Sounds.playClip(reloadSound + "2", 2, -1.0f, false);
				break;
			case 3:
				Sounds.playClip(reloadSound + "3", 3.33f, -1.0f, false);
				break;
			default:
				Sounds.playClip(reloadSound, 1, -1.0f, false);
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
	
	public void setAltReloading(boolean isReloading) {
		if(this.isAltReloading != isReloading && reloadSound != null) {
			Sounds.stopClip(reloadSound);
		}
		this.isAltReloading = isReloading;
		
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

	public int getCurrentAltClip() {
		return currentAltClip;
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
	
	public GunImageDim getGunImageDim() {
		return gunImageDim;
	}
	
	public void setGunImageDim(int x, int y, int width, int height) {
		gunImageDim = new GunImageDim(x, y, width, height);
	}
	
	public class GunImageDim {
		public int startX, startY, width, height;
		
		public GunImageDim()
		{
			
		}
		
		public GunImageDim(int x, int y, int width, int height) {
			this.startX = x;
			this.startY = y;
			this.width = width;
			this.height = height;
		}
	}

	public void setAltReadyToFire(boolean b) {
		altReadyToFire = b;
	}

	public boolean isDual() {
		// TODO Auto-generated method stub
		return isDual;
	}
	
	public boolean getIsAltReloading() {
		if(!isDual)
			return false;
		return isAltReloading;
	}

	public void setCurrentAltClip(int clip2) {
		currentAltClip = clip2;
	}
	
}
