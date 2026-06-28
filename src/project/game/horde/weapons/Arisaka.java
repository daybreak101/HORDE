package project.game.horde.weapons;

import project.game.horde.entities.bullets.Bullet;
import project.game.horde.entities.creatures.Player;
import project.game.horde.graphics.Assets;
import project.game.horde.main.Handler;
import project.game.horde.sounds.GunSounds;
import project.game.horde.sounds.Sounds;

public class Arisaka extends Gun{

	public Arisaka(Handler handler, Player player) {
		super(handler, player, 
				GunVars.ARISAKA_DAMAGE, 
				GunVars.ARISAKA_FIRERATE,
				GunVars.ARISAKA_RELOADSPEED,
				GunVars.ARISAKA_GUNCLIP, 
				GunVars.ARISAKA_MAXRESERVE, 
				GunVars.ARISAKA_WEIGHT, 
				GunVars.ARISAKA_RANGE, 90);
		this.name = GunVars.ARISAKA_NAME;
		originalName = name;
		upgradedName = GunVars.ARISAKA_UPGRADEDNAME;
		reloadSound = GunSounds.ARISAKA_RELOAD_ID;
		top = Assets.arisaka_top;
		gunImageDim = new GunImageDim(40, 50, 20, 120);
	}
	
	public void shoot() {	
		if(readyToFire == true && currentClip > 0 && isReloading == false) {
			readyToFire = false;
			currentClip--;
			
			//Sounds.playClip(GunSounds.awp_shot, 1, "awp_shot" + RandomUtil.nextInt(0, 10000), -1.0f, false);
			Sounds.playClip(GunSounds.ARISAKA_SHOT_ID, 1, -1.0f, false);

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
