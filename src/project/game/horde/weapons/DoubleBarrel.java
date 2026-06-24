package project.game.horde.weapons;

import project.game.horde.entities.bullets.ShotgunBullet;
import project.game.horde.entities.creatures.Player;
import project.game.horde.graphics.Assets;
import project.game.horde.main.Handler;
import project.game.horde.sounds.GunSounds;
import project.game.horde.sounds.Sounds;
import project.game.horde.utils.Timer;

public class DoubleBarrel extends Gun {

	public DoubleBarrel(Handler handler, Player player) {
		super(handler, player, GunVars.DB_DAMAGE, GunVars.DB_FIRERATE, GunVars.DB_RELOADSPEED, GunVars.DB_GUNCLIP,
				GunVars.DB_MAXRESERVE, GunVars.DB_WEIGHT, GunVars.DB_RANGE);
		this.name = GunVars.DB_NAME;
		originalName = name;
		upgradedName = GunVars.DB_UPGRADEDNAME;
		reloadSound = GunSounds.DB_SHELL_ID;
		top = Assets.doubleBarrel_top;
		gunImageDim = new GunImageDim(40, 55, 15, 100);
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

	int heldShot = 0;

	public void postTick() {
		if (player.getMouseManager().isLeftPressed() && !isReloading) {
			heldShot++;
		} else if (!player.getMouseManager().isLeftPressed() && heldShot > 0 && !isReloading
				&& player.getPlayerInput().canShoot()) {
			shootSingleShot();
			heldShot = 0;
		}

	}

	public void shootSingleShot() {
		if (currentClip > 0 && !isReloading) {
			readyToFire = false;
			currentClip--;

			Sounds.playClip(GunSounds.DB_SHOT_ID, 1, -1.0f, false);

			if (isUpgraded) {
				Sounds.playClip(GunSounds.UPGRADED_ID, 1, -1.0f, false);
			}

			handler.getWorld().getEntityManager()
					.addEntity(new ShotgunBullet(handler, player.getCenterX(), player.getCenterY(),
							range, GunVars.DB_PELLET_SPREAD, GunVars.DB_PELLET_COUNT, this));
			if (player.getPeer() != null) {
				player.getPeer().sendPlayerShot(player.getUsername());
			}
			timerToFire = 0;
		}
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

			// Sounds.playClip(reloadSound, 1, "gunReload", -1.0f, false);
		}
	}

	public void reload() {
		// dont do reload animation when there is no reloading being done
		if ((currentClip != clip) && (currentReserve > 0) && !isReloading) {
			isReloading = true;
			Sounds.stopClip("db_shot");

			switch (speedcola) {
			case 0:
				Sounds.playClip(GunSounds.DB_OPEN_ID, 10 / 9, -1.0f, false);
				cockTimerlmao = new Timer(40 / 10 / 9);
				break;
			case 1:
				Sounds.playClip(GunSounds.DB_OPEN_ID, 4 / 3, -1.0f, false);
				cockTimerlmao = new Timer(40 / 4 / 3);
				break;
			case 2:
				Sounds.playClip(GunSounds.DB_OPEN_ID, 2, -1.0f, false);
				cockTimerlmao = new Timer(40 / 2);
				break;
			case 3:
				Sounds.playClip(GunSounds.DB_OPEN_ID, 10 / 3, -1.0f, false);
				cockTimerlmao = new Timer(40 / 10 / 3);
				break;
			default:
				Sounds.playClip(GunSounds.DB_OPEN_ID, 1, -1.0f, false);
				cockTimerlmao = new Timer(40);
				break;
			}
		}
	}

	public void reloadFinish() {
		cockTimerlmao.resetTimer();
		switch (speedcola) {
		case 0:
			Sounds.playClip(GunSounds.DB_CLOSE_ID, 10 / 9, -1.0f, false);
			break;
		case 1:
			Sounds.playClip(GunSounds.DB_CLOSE_ID, 4 / 3, -1.0f, false);
			break;
		case 2:
			Sounds.playClip(GunSounds.DB_CLOSE_ID, 2, -1.0f, false);
			break;
		case 3:
			Sounds.playClip(GunSounds.DB_CLOSE_ID, 10 / 3, -1.0f, false);
			break;
		default:
			Sounds.playClip(GunSounds.DB_CLOSE_ID, 1, -1.0f, false);
			break;
		}
	}

	public void shoot() {

	}
}
