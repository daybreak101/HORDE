package project.game.horde.weapons;

import project.game.horde.entities.bullets.Bullet;
import project.game.horde.entities.creatures.Player;
import project.game.horde.main.Handler;
import project.game.horde.sounds.GunSounds;
import project.game.horde.sounds.Sounds;
import project.game.horde.utils.RandomUtil;

public class M4 extends Gun {

	public M4(Handler handler, Player player) {
		super(handler, player, 
				GunVars.M4_DAMAGE, 
				GunVars.M4_FIRERATE,
				GunVars.M4_RELOADSPEED,
				GunVars.M4_GUNCLIP, 
				GunVars.M4_MAXRESERVE, 
				GunVars.M4_WEIGHT, 
				GunVars.M4_RANGE);
		this.name = GunVars.M4_NAME;
		originalName = name;
		upgradedName = GunVars.M4_UPGRADEDNAME;
		reloadSound = GunSounds.M4_RELOAD_ID;
	}
	
	public void shoot() {		
		if(readyToFire == true && currentClip > 0 && isReloading == false) {
			readyToFire = false;
			currentClip--;
			Sounds.playClip(GunSounds.M4_SHOT_ID, 1, -1.0f, false);

			//Sounds.playClip(GunSounds.m4_shot, 1, "m4_shot" + RandomUtil.nextInt(0, 10000), -1.0f, false);
			
			if(isUpgraded) {
				Sounds.playClip(GunSounds.UPGRADED_ID, 1, -1.0f, false);

				//Sounds.playClip(GunSounds.upgraded, 1, "upgraded" + RandomUtil.nextInt(0, 10000), -1.0f, false);
			}
			
			
			handler.getWorld().getEntityManager().addEntity(new Bullet(handler, 
					player.getCenterX(),
					player.getCenterY(),
					player.getZ(),
					range, this));
			if(player.getPeer() != null) {
				player.getPeer().sendPlayerShot(player.getUsername());
			}
			
			timerToFire = 0;
		}
	}
	
}
