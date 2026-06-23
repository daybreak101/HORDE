package project.game.horde.weapons;

import project.game.horde.entities.bullets.Bullet;
import project.game.horde.entities.creatures.Player;
import project.game.horde.graphics.Assets;
import project.game.horde.main.Handler;
import project.game.horde.sounds.GunSounds;
import project.game.horde.sounds.Sounds;
import project.game.horde.weapons.Gun.GunImageDim;

public class AK47 extends Gun {

	public AK47(Handler handler, Player player) {
		super(handler, player, 
				GunVars.AK47_DAMAGE, 
				GunVars.AK47_FIRERATE,
				GunVars.AK47_RELOADSPEED,
				GunVars.AK47_GUNCLIP, 
				GunVars.AK47_MAXRESERVE, 
				GunVars.AK47_WEIGHT, 
				GunVars.AK47_RANGE);
		this.name = GunVars.AK47_NAME;
		originalName = name;
		upgradedName = GunVars.AK47_UPGRADEDNAME;
		reloadSound = GunSounds.AK47_RELOAD_ID;
		top = Assets.ak47_top;
		gunImageDim = new GunImageDim(40, 35, 12, 110);
	}
	
	public void shoot() {
		if(readyToFire == true && currentClip > 0 && isReloading == false) {
			readyToFire = false;
			currentClip--;
			
			Sounds.playClip(GunSounds.AK47_SHOT_ID, 1, -1.0f, false);

			//Sounds.playClip(GunSounds.ak47_shot, 1, "ak47_shot" + RandomUtil.nextInt(0, 10000), -1.0f, false);
			
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
