package project.game.horde.weapons;

import project.game.horde.entities.bullets.Bullet;
import project.game.horde.entities.creatures.Player;
import project.game.horde.graphics.Assets;
import project.game.horde.main.Handler;
import project.game.horde.sounds.GunSounds;
import project.game.horde.sounds.Sounds;
import project.game.horde.weapons.Gun.GunImageDim;

public class M1Garand extends Gun {

	public M1Garand(Handler handler, Player owner) {
		super(handler, owner,		
				GunVars.M1GARAND_DAMAGE, 
				GunVars.M1GARAND_FIRERATE,
				GunVars.M1GARAND_RELOADSPEED,
				GunVars.M1GARAND_GUNCLIP, 
				GunVars.M1GARAND_MAXRESERVE, 
				GunVars.M1GARAND_WEIGHT, 
				GunVars.M1GARAND_RANGE);
		this.name = GunVars.M1GARAND_NAME;
		originalName = name;
		upgradedName = GunVars.M1GARAND_UPGRADEDNAME;
		reloadSound = GunSounds.M1_GARAND_RELOAD_ID;
		top = Assets.m1Garand_top;
		gunImageDim = new GunImageDim(40, 50, 20, 120);
	}

	public void shoot() {
	}

	int heldShot = 0;

	// guess i figured out how to work semi-auto guns
	public void postTick() {
		if (player.getMouseManager().isLeftPressed() && !isReloading) {
			heldShot++;
		} else if (!player.getMouseManager().isLeftPressed() && heldShot > 0 && !isReloading &&
				player.getPlayerInput().canShoot()) {
			shootSingleShot();
			heldShot = 0;
		}

	}

	public void shootSingleShot() {
		if (currentClip > 0 && !isReloading) {
			readyToFire = false;

			Sounds.playClip(GunSounds.M1_GARAND_SHOT_ID, 1, -1.0f, false);

			if (isUpgraded) {
				Sounds.playClip(GunSounds.UPGRADED_ID, 1, -1.0f, false);
			}
			currentClip--;
			handler.getWorld().getEntityManager().addEntity(
					new Bullet(handler, 
							player.getCenterX(), player.getCenterY(),
							range, this));
			if(player.getPeer() != null) {
				player.getPeer().sendPlayerShot(player.getUsername());
			}

			timerToFire = 0;
			if(currentClip == 0) {
				Sounds.playClip(GunSounds.M1_GARAND_DING_ID, 1, -1.0f, false);
			}
		}

	}
	
	public void reload() {
		speedcola = player.getInv().getSpeedcola();
		// dont do reload animation when there is no reloading being done
		if ((currentClip == 0) && (currentReserve > 0) && !isReloading) {
			isReloading = true;
			switch(speedcola) {
			case 0:
				Sounds.playClip(reloadSound, 1, -1.0f, false);
				break;
			case 1:
				Sounds.playClip(reloadSound, 1.33f, -1.0f, false);
				break;
			case 2:
				Sounds.playClip(reloadSound, 2, -1.0f, false);
				break;
			case 3:
				Sounds.playClip(reloadSound, 3.33f, -1.0f, false);
				break;
			default:
				Sounds.playClip(reloadSound, 1, -1.0f, false);
				break;
			}
			
		}

	}
}
