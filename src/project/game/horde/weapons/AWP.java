package project.game.horde.weapons;

import project.game.horde.entities.bullets.Bullet;
import project.game.horde.entities.creatures.Player;
import project.game.horde.graphics.Assets;
import project.game.horde.main.Handler;
import project.game.horde.sounds.GunSounds;
import project.game.horde.sounds.Sounds;
import project.game.horde.utils.RandomUtil;
import project.game.horde.weapons.Gun.GunImageDim;

public class AWP extends Gun{

	public AWP(Handler handler, Player player) {
		super(handler, player, 
				GunVars.AWP_DAMAGE, 
				GunVars.AWP_FIRERATE,
				GunVars.AWP_RELOADSPEED,
				GunVars.AWP_GUNCLIP, 
				GunVars.AWP_MAXRESERVE, 
				GunVars.AWP_WEIGHT, 
				GunVars.AWP_RANGE);
		this.name = GunVars.AWP_NAME;
		originalName = name;
		upgradedName = GunVars.AWP_UPGRADEDNAME;
		reloadSound = GunSounds.AWP_RELOAD_ID;
		top = Assets.awp_top;
		gunImageDim = new GunImageDim(40, 50, 20, 100);
	}
	
	public void shoot() {	
		if(readyToFire == true && currentClip > 0 && isReloading == false) {
			readyToFire = false;
			currentClip--;
			
			//Sounds.playClip(GunSounds.awp_shot, 1, "awp_shot" + RandomUtil.nextInt(0, 10000), -1.0f, false);
			Sounds.playClip(GunSounds.AWP_SHOT_ID, 1, -1.0f, false);

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
