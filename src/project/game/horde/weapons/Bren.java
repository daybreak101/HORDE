package project.game.horde.weapons;

import project.game.horde.entities.bullets.Bullet;
import project.game.horde.entities.creatures.Player;
import project.game.horde.graphics.Assets;
import project.game.horde.main.Handler;
import project.game.horde.sounds.GunSounds;
import project.game.horde.sounds.Sounds;
import project.game.horde.weapons.Gun.GunImageDim;

public class Bren extends Gun{

	public Bren(Handler handler, Player owner) {
		super(handler, owner, 
				GunVars.BREN_DAMAGE, 
				GunVars.BREN_FIRERATE,
				GunVars.BREN_RELOADSPEED,
				GunVars.BREN_GUNCLIP, 
				GunVars.BREN_MAXRESERVE, 
				GunVars.BREN_WEIGHT, 
				GunVars.BREN_RANGE);
		this.name = GunVars.BREN_NAME;
		originalName = name;
		upgradedName = GunVars.BREN_UPGRADEDNAME;
		reloadSound = GunSounds.BREN_RELOAD_ID;
		top = Assets.bren_top;
		gunImageDim = new GunImageDim(30, 45, 40, 90);
	}

	public void shoot() {	
		if(readyToFire == true && currentClip > 0 && isReloading == false) {
			readyToFire = false;
			currentClip--;
			
			//Sounds.playClip(GunSounds.awp_shot, 1, "awp_shot" + RandomUtil.nextInt(0, 10000), -1.0f, false);
			Sounds.playClip(GunSounds.BREN_SHOT_ID, 1, -1.0f, false);

			if(isUpgraded) {
				Sounds.playClip(GunSounds.UPGRADED_ID, 1, -1.0f, false);

				//Sounds.playClip(GunSounds.upgraded, 1, "upgraded" + RandomUtil.nextInt(0, 10000), -1.0f, false);
			}
			
			handler.getWorld().getEntityManager().addEntity(new Bullet(handler, 
					player.getCenterX(),
					player.getCenterY(),
					
					range, this));
			if(player.getPeer() != null) {
				player.getPeer().sendPlayerShot(player.getUsername());
			}
			timerToFire = 0;
		}
	}
}
