package project.game.horde.weapons;

import project.game.horde.entities.bullets.ShotgunBullet;
import project.game.horde.entities.creatures.Player;
import project.game.horde.graphics.Assets;
import project.game.horde.main.Handler;
import project.game.horde.sounds.GunSounds;
import project.game.horde.sounds.Sounds;
import project.game.horde.utils.RandomUtil;
import project.game.horde.utils.Timer;
import project.game.horde.weapons.Gun.GunImageDim;

public class Winchester1901 extends Gun {

	public Winchester1901(Handler handler, Player player) {
		super(handler, player, 
				GunVars.WINCHESTER1901_DAMAGE, 
				GunVars.WINCHESTER1901_FIRERATE,
				GunVars.WINCHESTER1901_RELOADSPEED,
				GunVars.WINCHESTER1901_GUNCLIP, 
				GunVars.WINCHESTER1901_MAXRESERVE, 
				GunVars.WINCHESTER1901_WEIGHT, 
				GunVars.WINCHESTER1901_RANGE);
		this.name = GunVars.WINCHESTER1901_NAME;
		originalName = name;
		upgradedName = GunVars.WINCHESTER1901_UPGRADEDNAME;
		reloadSound = GunSounds.WINCHESTER1901_SHELL_RELOAD_ID;
		top = Assets.winchester1901_top;
		gunImageDim = new GunImageDim(30, 45, 20, 100);
	}

	private Timer cockTimerlmao;
	private float speedMod = 1;
	private float doubleMod = 1;
	public void tick() {
		doubletap = player.getInv().getDoubletap();
		speedcola = player.getInv().getSpeedcola();

		if (isReloading) {
			cockTimerlmao.tick();
			if (cockTimerlmao.checkIsReady()) {
				reloadTimer++;
				//Sounds.playClip(Sounds.winchester1901_shell_reload, 1, "gunReload");
				if (speedcola == 0) {
					speedMod = (float) 9/10;
				} else if (speedcola == 1) {
					speedMod = (float) 3/4;
				} else if (speedcola == 2) {
					speedMod = (float) 1/2;
				} else if (speedcola == 3) {
					speedMod = (float) 4/10;
				} else {
					speedMod = 1;
				}
				
				if(reloadTimer >= (float) (reloadSpeed * speedMod)) {
					reloadShell();
					//isReloading = false;
					reloadTimer = 0;
				}
				if(currentClip == clip || currentReserve == 0) {
					isReloading = false;
					reloadTimer = 0;
					reloadFinish();
				}
				
			}

		} else if (doubletap >= 2 && timerToFire >= fireRate / 2) {
			readyToFire = true;
			doubleMod = 2;
			timerToFire = 0;
		} else if (doubletap > -1 && timerToFire >= fireRate * 3 / 4) {
			readyToFire = true;
			doubleMod = 4/3;
			timerToFire = 0;
		} else if (timerToFire >= fireRate) {
			readyToFire = true;
			doubleMod = 1;
			timerToFire = 0;
		}
		// autoreload when clip is empty
		if (currentClip == 0 && readyToFire) {
			reload();
		}
		timerToFire++;
		postTick();
	}
	
	public void reloadShell() {
		if(currentReserve > 0 && currentClip < clip) {
			currentReserve--;
			currentClip++;
			if(isUpgraded && currentClip < clip) {
				currentReserve--;
				currentClip++;
			}
			Sounds.playClip(reloadSound, 1, -1.0f, false);

			//Sounds.playClip(reloadSound, 1, "gunReload", -1.0f, false);
		}
//		if (currentReserve < (clip - currentClip)) {
//			currentClip += currentReserve;
//			currentReserve = 0;
//		} else {
//			currentReserve = currentReserve - (clip - currentClip);
//			currentClip = clip;
//		}
	}

	public void reload() {
		// dont do reload animation when there is no reloading being done
		if ((currentClip != clip) && (currentReserve > 0) && !isReloading) {
			isReloading = true;
			Sounds.stopClip("winchester1901_shot");

			switch (speedcola) {
			case 0:
				Sounds.playClip(GunSounds.WINCHESTER1901_START_RELOAD_ID, 1, -1.0f, false);

				//Sounds.playClip(GunSounds.winchester1901_start_reload, 10 / 9, "gunReload", -1.0f, false);
				cockTimerlmao = new Timer(40 / 10 / 9);
				break;
			case 1:
				Sounds.playClip(GunSounds.WINCHESTER1901_START_RELOAD_ID, 1, -1.0f, false);

				//Sounds.playClip(GunSounds.winchester1901_start_reload, 4 / 3, "gunReload", -1.0f, false);
				cockTimerlmao = new Timer(40 / 4 / 3);
				break;
			case 2:
				Sounds.playClip(GunSounds.WINCHESTER1901_START_RELOAD_ID, 1, -1.0f, false);

				//Sounds.playClip(GunSounds.winchester1901_start_reload, 2.0f, "gunReload", -1.0f, false);
				cockTimerlmao = new Timer(40 / 2);
				break;
			case 3:
				Sounds.playClip(GunSounds.WINCHESTER1901_START_RELOAD_ID, 1, -1.0f, false);

				//Sounds.playClip(GunSounds.winchester1901_start_reload, 10 / 3, "gunReload", -1.0f, false);
				cockTimerlmao = new Timer(40 / 10 / 3);
				break;
			default:
				Sounds.playClip(GunSounds.WINCHESTER1901_START_RELOAD_ID, 1, -1.0f, false);

				//Sounds.playClip(GunSounds.winchester1901_start_reload, 1, "gunReload", -1.0f, false);
				cockTimerlmao = new Timer(40);
				break;
			}

		}

	}

	public void reloadFinish() {
//		if (currentReserve < (clip - currentClip)) {
//			currentClip += currentReserve;
//			currentReserve = 0;
//		} else {
//			currentReserve = currentReserve - (clip - currentClip);
//			currentClip = clip;
//		}
		cockTimerlmao.resetTimer();
	}

	public void shoot() {
		if (readyToFire == true && currentClip > 0 && isReloading == false) {
			readyToFire = false;
			currentClip--;
			
			Sounds.playClip(GunSounds.WINCHESTER1901_SHOT_ID, 1, -1.0f, false);

			//Sounds.playClip(GunSounds.winchester1901_shot, doubleMod, "shot", -1.0f, false);

			if (isUpgraded) {
				Sounds.playClip(GunSounds.UPGRADED_ID, 1, -1.0f, false);

				//Sounds.playClip(GunSounds.upgraded, 1, "upgraded" + RandomUtil.nextInt(0, 10000), -1.0f, false);
			}

			handler.getWorld().getEntityManager().addEntity(new ShotgunBullet(handler,
					player.getCenterX(), player.getCenterY(), player.getZ(),
					range, GunVars.WINCHESTER1901_PELLET_SPREAD, 
					GunVars.WINCHESTER1901_PELLET_COUNT, this));
			if(player.getPeer() != null) {
				player.getPeer().sendPlayerShot(player.getUsername());
			}
			timerToFire = 0;
		}
	}

}
