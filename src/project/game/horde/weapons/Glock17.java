package project.game.horde.weapons;

import project.game.horde.entities.bullets.Bullet;
import project.game.horde.entities.creatures.Player;
import project.game.horde.graphics.Assets;
import project.game.horde.main.Handler;
import project.game.horde.sounds.GunSounds;
import project.game.horde.sounds.Sounds;

public class Glock17 extends Gun {

	// File file = new File("res/shootBeta.wav");

	public Glock17(Handler handler, Player player) {
		super(handler, player, 
				GunVars.GLOCK17_DAMAGE, 
				GunVars.GLOCK17_FIRERATE,
				GunVars.GLOCK17_RELOADSPEED,
				GunVars.GLOCK17_GUNCLIP, 
				GunVars.GLOCK17_MAXRESERVE, 
				GunVars.GLOCK17_WEIGHT, 
				GunVars.GLOCK17_RANGE, 80);
		this.name = GunVars.GLOCK17_NAME;
		originalName = name;
		upgradedName = GunVars.GLOCK17_UPGRADEDNAME;
		reloadSound = GunSounds.GLOCK17_RELOAD_ID;
		top = Assets.glock17_top;
		gunImageDim = new GunImageDim(40, 50, 20, 100);
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

			//Sounds.playClip(GunSounds.glock17_shot, 1, "glock17_shot" + RandomUtil.nextInt(0, 10000), 1.0f, false);
			Sounds.playClip(GunSounds.GLOCK17_SHOT_ID, 1, -1.0f, false);

			if (isUpgraded) {
				Sounds.playClip(GunSounds.UPGRADED_ID, 1, -1.0f, false);

				//Sounds.playClip(GunSounds.upgraded, 1, "upgraded" + RandomUtil.nextInt(0, 10000), -1.0f, false);
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
		}

	}

}
