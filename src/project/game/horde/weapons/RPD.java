package project.game.horde.weapons;

import project.game.horde.entities.bullets.Bullet;
import project.game.horde.entities.creatures.Player;
import project.game.horde.main.Handler;
import project.game.horde.sounds.GunSounds;
import project.game.horde.sounds.Sounds;
import project.game.horde.utils.RandomUtil;

public class RPD extends Gun{

	public RPD(Handler handler, Player player) {
		super(handler, player, 
				GunVars.RPD_DAMAGE, 
				GunVars.RPD_FIRERATE,
				GunVars.RPD_RELOADSPEED,
				GunVars.RPD_GUNCLIP, 
				GunVars.RPD_MAXRESERVE, 
				GunVars.RPD_WEIGHT, 
				GunVars.RPD_RANGE);
		this.name = GunVars.RPD_NAME;
		originalName = name;
		upgradedName = GunVars.RPD_UPGRADEDNAME;
		reloadSound = GunSounds.RPD_RELOAD_ID;
	}
	
	public void shoot() {		
		if(readyToFire == true && currentClip > 0 && isReloading == false) {
			readyToFire = false;
			currentClip--;
			
			Sounds.playClip(GunSounds.RPD_SHOT_ID, 1, -1.0f, false);

			//Sounds.playClip(GunSounds.rpd_shot, 1, "rpd_shot" + RandomUtil.nextInt(0, 10000), -1.0f, false);
			
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
