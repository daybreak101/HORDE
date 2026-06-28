package project.game.horde.weapons;

import project.game.horde.entities.bullets.Bullet;
import project.game.horde.entities.creatures.Player;
import project.game.horde.graphics.Assets;
import project.game.horde.main.Handler;
import project.game.horde.sounds.GunSounds;
import project.game.horde.sounds.Sounds;

public class Type100 extends Gun{
	public Type100(Handler handler, Player player) {
		super(handler, player, 
				GunVars.TYPE100_DAMAGE, 
				GunVars.TYPE100_FIRERATE,
				GunVars.TYPE100_RELOADSPEED,
				GunVars.TYPE100_GUNCLIP, 
				GunVars.TYPE100_MAXRESERVE, 
				GunVars.TYPE100_WEIGHT, 
				GunVars.TYPE100_RANGE, 100);
		this.name = GunVars.TYPE100_NAME;
		originalName = name;
		upgradedName = GunVars.TYPE100_UPGRADEDNAME;
		reloadSound = GunSounds.TYPE100_RELOAD_ID;
		top = Assets.type100_top;
		gunImageDim = new GunImageDim(15, 35, 45, 100);
	}
	
	public void shoot() {		
		if(readyToFire == true && currentClip > 0 && isReloading == false) {
			readyToFire = false;
			currentClip--;
			Sounds.playClip(GunSounds.TYPE100_SHOT_ID, 1, -1.0f, false);

			//Sounds.playClip(GunSounds.m4_shot, 1, "m4_shot" + RandomUtil.nextInt(0, 10000), -1.0f, false);
			
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
