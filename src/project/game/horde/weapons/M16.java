package project.game.horde.weapons;

import project.game.horde.entities.bullets.Bullet;
import project.game.horde.entities.creatures.Player;
import project.game.horde.graphics.Assets;
import project.game.horde.main.Handler;
import project.game.horde.perks.DoubleTap;
import project.game.horde.sounds.GunSounds;
import project.game.horde.sounds.Sounds;

public class M16 extends Gun {
	public M16(Handler handler, Player player) {
		super(handler, player, GunVars.M16_DAMAGE, GunVars.M16_FIRERATE, GunVars.M16_RELOADSPEED, GunVars.M16_GUNCLIP,
				GunVars.M16_MAXRESERVE, GunVars.M16_WEIGHT, GunVars.M16_RANGE, 80);
		this.name = GunVars.M16_NAME;
		originalName = name;
		upgradedName = GunVars.M16_UPGRADEDNAME;
		reloadSound = GunSounds.M4_RELOAD_ID;
		top = Assets.m16_top;
		gunImageDim = new GunImageDim(40, 45, 12, 80);
	}

	boolean inBurst = false;
	int burstRate = 4;
	int burstTimer = 0;
	int burstBullet = 0;

	public void postTick() {
		if (inBurst) {
			burstTimer++;
			if ((doubletap >= 2 && burstTimer >= burstRate * DoubleTap.LVL2_FIRERATEBUFF)
					|| (doubletap > -1 && burstTimer >= burstRate * DoubleTap.BASE_FIRERATEBUFF)
					|| (burstTimer >= burstRate)) {
				handler.getWorld().getEntityManager().addEntity(
						new Bullet(handler, player.getCenterX(), player.getCenterY(), range, this));
				burstBullet++;
				currentClip--;
				burstTimer = 0;
			}
			if(burstBullet == 3) {
				inBurst = false;
				burstBullet = 0;
			}
		}
	}

	public void shoot() {
		if (!inBurst && readyToFire == true && currentClip > 0 && isReloading == false) {
			inBurst = true;
			readyToFire = false;

			Sounds.playClip(GunSounds.M16_SHOT_ID, 1, -1.0f, false);
			if (isUpgraded) {
				Sounds.playClip(GunSounds.UPGRADED_ID, 1, -1.0f, false);
			}

			if (player.getPeer() != null) {
				player.getPeer().sendPlayerShot(player.getUsername());
			}

			timerToFire = 0;
		}
	}
}
