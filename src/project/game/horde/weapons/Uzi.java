package project.game.horde.weapons;

import project.game.horde.entities.bullets.Bullet;
import project.game.horde.entities.creatures.Player;
import project.game.horde.graphics.Assets;
import project.game.horde.main.Handler;
import project.game.horde.sounds.GunSounds;
import project.game.horde.sounds.Sounds;

public class Uzi extends Gun{
	public Uzi(Handler handler, Player player) {
		super(handler, player, 
				GunVars.UZI_DAMAGE, 
				GunVars.UZI_FIRERATE,
				GunVars.UZI_RELOADSPEED,
				GunVars.UZI_GUNCLIP, 
				GunVars.UZI_MAXRESERVE, 
				GunVars.UZI_WEIGHT, 
				GunVars.UZI_RANGE, 70);
		this.name = GunVars.UZI_NAME;
		originalName = name;
		upgradedName = GunVars.UZI_UPGRADEDNAME;
		reloadSound = GunSounds.UZI_RELOAD_ID;
		top = Assets.uzi_top;
		gunImageDim = new GunImageDim(40, 35, 12, 80);
	}
	
	public void shoot() {		
		if(readyToFire == true && currentClip > 0 && isReloading == false) {
			readyToFire = false;
			currentClip--;
			Sounds.playClip(GunSounds.UZI_SHOT_ID, 1, -1.0f, false);

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
