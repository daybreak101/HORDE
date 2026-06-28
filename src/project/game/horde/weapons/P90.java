package project.game.horde.weapons;

import project.game.horde.entities.bullets.Bullet;
import project.game.horde.entities.creatures.Player;
import project.game.horde.graphics.Assets;
import project.game.horde.main.Handler;
import project.game.horde.sounds.GunSounds;
import project.game.horde.sounds.Sounds;

public class P90 extends Gun {

	public P90(Handler handler, Player player) {
		super(handler, player, 
				GunVars.P90_DAMAGE, 
				GunVars.P90_FIRERATE,
				GunVars.P90_RELOADSPEED,
				GunVars.P90_GUNCLIP, 
				GunVars.P90_MAXRESERVE, 
				GunVars.P90_WEIGHT, 
				GunVars.P90_RANGE, 100);
		this.name = GunVars.P90_NAME;
		originalName = name;
		upgradedName = GunVars.P90_UPGRADEDNAME;
		reloadSound = GunSounds.P90_RELOAD_ID;
		top = Assets.p90_top;
		gunImageDim = new GunImageDim(40, 45, 20, 100);
	}
	
	public void shoot() {		
		if(readyToFire == true && currentClip > 0 && isReloading == false) {
			readyToFire = false;
			currentClip--;
			
			Sounds.playClip(GunSounds.P90_SHOT_ID, 1, -1.0f, false);

			//Sounds.playClip(GunSounds.p90_shot, 1, "p90_shot" + RandomUtil.nextInt(0, 10000), -1.0f, false);
			
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
