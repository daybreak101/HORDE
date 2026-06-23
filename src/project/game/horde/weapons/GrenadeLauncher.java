package project.game.horde.weapons;

import project.game.horde.entities.bullets.Grenade;
import project.game.horde.entities.creatures.Player;
import project.game.horde.graphics.Assets;
import project.game.horde.main.Handler;
import project.game.horde.sounds.GunSounds;
import project.game.horde.sounds.Sounds;
import project.game.horde.utils.Timer;
import project.game.horde.weapons.Gun.GunImageDim;

public class GrenadeLauncher extends Gun {

	public GrenadeLauncher(Handler handler, Player player) {
		super(handler, player, GunVars.GRENADELAUNCHER_DAMAGE, GunVars.GRENADELAUNCHER_FIRERATE,
				GunVars.GRENADELAUNCHER_RELOADSPEED, GunVars.GRENADELAUNCHER_GUNCLIP,
				GunVars.GRENADELAUNCHER_MAXRESERVE, GunVars.GRENADELAUNCHER_WEIGHT, GunVars.GRENADELAUNCHER_RANGE);
		this.name = GunVars.GRENADELAUNCHER_NAME;
		originalName = name;
		upgradedName = GunVars.GRENADELAUNCHER_UPGRADEDNAME;
		reloadSound = GunSounds.GRENADE_LAUNCHER_RELOAD_SHELL_ID;
		top = Assets.grenadeLauncher_top;
		//gunImageDim = new GunImageDim(40, 50, 20, 100);
		gunImageDim = new GunImageDim(30, 45, 40, 100);
	}

	private Timer cockTimerlmao;
	private float speedMod = 1;
	private float doubleMod = 1;
	private boolean finished = false;

	public void tick() {
		doubletap = player.getInv().getDoubletap();
		speedcola = player.getInv().getSpeedcola();

		if (isReloading && !finished) {
			cockTimerlmao.tick();
			if (cockTimerlmao.checkIsReady()) {
				reloadTimer++;
				if (speedcola == 0) {
					speedMod = (float) 9 / 10;
				} else if (speedcola == 1) {
					speedMod = (float) 3 / 4;
				} else if (speedcola == 2) {
					speedMod = (float) 1 / 2;
				} else if (speedcola == 3) {
					speedMod = (float) 4 / 10;
				} else {
					speedMod = 1;
				}

				if (!finished && reloadTimer >= (float) (reloadSpeed * speedMod)) {
					reloadShell();
					// isReloading = false;
					reloadTimer = 0;
				}

				if (currentClip == clip || currentReserve == 0) {
					reloadTimer = 0;
					reloadFinish();
					finished = true;
				}

			}
		} else if (doubletap >= 2 && timerToFire >= fireRate / 2) {
			readyToFire = true;
			doubleMod = 2;
			timerToFire = 0;
		} else if (doubletap > -1 && timerToFire >= fireRate * 3 / 4) {
			readyToFire = true;
			doubleMod = 4 / 3;
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
		if (finished && readyToFire) {
			reloadTimer = 0;
			finished = false;
			isReloading = false;
			readyToFire = false;
		}
		timerToFire++;
		postTick();
	}

	public void reloadShell() {
		if (currentReserve > 0 && currentClip < clip) {
			currentReserve--;
			currentClip++;
			if (isUpgraded && currentClip < clip) {
				currentReserve--;
				currentClip++;
			}
			Sounds.playClip(reloadSound, 1, -1.0f, false);

			//Sounds.playClip(reloadSound, 1, "gunReload", -1.0f, false);
		}
	}

	public void reload() {
		// dont do reload animation when there is no reloading being done
		if ((currentClip != clip) && (currentReserve > 0) && !isReloading) {
			isReloading = true;
			Sounds.stopClip("grenade_launcher_shot");

			switch (speedcola) {
			case 0:
				Sounds.playClip(GunSounds.GRENADE_LAUNCHER_RELOAD_OPEN_ID, 10/9, -1.0f, false);
				//Sounds.playClip(GunSounds.grenade_launcher_reload_open, 10 / 9, "gunReload", -1.0f, false);
				cockTimerlmao = new Timer(40 / 10 / 9);
				break;
			case 1:
				Sounds.playClip(GunSounds.GRENADE_LAUNCHER_RELOAD_OPEN_ID, 4/3, -1.0f, false);
				//Sounds.playClip(GunSounds.grenade_launcher_reload_open, 4 / 3, "gunReload", -1.0f, false);
				cockTimerlmao = new Timer(40 / 4 / 3);
				break;
			case 2:
				Sounds.playClip(GunSounds.GRENADE_LAUNCHER_RELOAD_OPEN_ID, 2, -1.0f, false);
				//Sounds.playClip(GunSounds.grenade_launcher_reload_open, 2.0f, "gunReload", -1.0f, false);
				cockTimerlmao = new Timer(40 / 2);
				break;
			case 3:
				Sounds.playClip(GunSounds.GRENADE_LAUNCHER_RELOAD_OPEN_ID, 10/3, -1.0f, false);
				//Sounds.playClip(GunSounds.grenade_launcher_reload_open, 10 / 3, "gunReload", -1.0f, false);
				cockTimerlmao = new Timer(40 / 10 / 3);
				break;
			default:
				Sounds.playClip(GunSounds.GRENADE_LAUNCHER_RELOAD_OPEN_ID, 1, -1.0f, false);
				//Sounds.playClip(GunSounds.grenade_launcher_reload_open, 1, "gunReload", -1.0f, false);
				cockTimerlmao = new Timer(40);
				break;
			}
		}
	}

	public void reloadFinish() {
		cockTimerlmao.resetTimer();
		switch (speedcola) {
		case 0:
			Sounds.playClip(GunSounds.GRENADE_LAUNCHER_RELOAD_CLOSE, 10/9, -1.0f, false);
			break;
		case 1:
			Sounds.playClip(GunSounds.GRENADE_LAUNCHER_RELOAD_CLOSE, 4/3, -1.0f, false);
			break;
		case 2:
			Sounds.playClip(GunSounds.GRENADE_LAUNCHER_RELOAD_CLOSE, 2, -1.0f, false);
			break;
		case 3:
			Sounds.playClip(GunSounds.GRENADE_LAUNCHER_RELOAD_CLOSE, 10/3, -1.0f, false);
			break;
		default:
			Sounds.playClip(GunSounds.GRENADE_LAUNCHER_RELOAD_CLOSE, 1, -1.0f, false);
			break;
		}
	}

	
	public void shoot() {
		if (readyToFire == true && currentClip > 0 && isReloading == false) {
			readyToFire = false;
			currentClip--;

			Sounds.playClip(GunSounds.GRENADE_LAUNCHER_SHOT_ID, 1, -1.0f, false);
			//Sounds.playClip(GunSounds.grenade_launcher_shot, 1, "grenade_launcher_shot" + RandomUtil.nextInt(0, 10000),
			//		-1.0f, false);

			if (isUpgraded) {
				Sounds.playClip(GunSounds.UPGRADED_ID, 1, -1.0f, false);

				//Sounds.playClip(GunSounds.upgraded, 1, "upgraded" + RandomUtil.nextInt(0, 10000), -1.0f, false);
			}

			handler.getWorld().getEntityManager()
					.addEntity(new Grenade(handler, player.getCenterX(), player.getCenterY(), isUpgraded,
							player.getMouseManager().getMouseX() + handler.getGameCamera().getxOffset(),
							player.getMouseManager().getMouseY() + handler.getGameCamera().getyOffset(), player, this));
			if (player.getPeer() != null) {
				player.getPeer().sendPlayerGrenadeLauncherShot(player.getUsername(),
						(int) (player.getMouseManager().getMouseX() + handler.getGameCamera().getxOffset()),
						(int) (player.getMouseManager().getMouseY() + handler.getGameCamera().getyOffset())
						);
			}
			timerToFire = 0;
		}
	}

}
