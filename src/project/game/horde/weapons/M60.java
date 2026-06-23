package project.game.horde.weapons;

import project.game.horde.entities.bullets.Bullet;
import project.game.horde.entities.creatures.Player;
import project.game.horde.graphics.Assets;
import project.game.horde.main.Handler;
import project.game.horde.sounds.GunSounds;
import project.game.horde.sounds.Sounds;
import project.game.horde.weapons.Gun.GunImageDim;

public class M60 extends Gun{

	public M60(Handler handler, Player player) {
		super(handler, player, 
				GunVars.M60_DAMAGE, 
				GunVars.M60_FIRERATE,
				GunVars.M60_RELOADSPEED,
				GunVars.M60_GUNCLIP, 
				GunVars.M60_MAXRESERVE, 
				GunVars.M60_WEIGHT, 
				GunVars.M60_RANGE);
		this.name = GunVars.M60_NAME;
		originalName = name;
		upgradedName = GunVars.M60_UPGRADEDNAME;
		reloadSound = GunSounds.M60_RELOAD_ID;
		top = Assets.m60_top;
		gunImageDim = new GunImageDim(30, 50, 60, 100);
	}
	
	public void uniqueUpgrades() {
		maxReserve = 0;
		currentReserve = 0;
		clip = 1000;
		currentClip = 1000;
	}
	
	public void shoot() {		
		if(readyToFire == true && currentClip > 0 && isReloading == false) {
			readyToFire = false;
			currentClip--;	
			Sounds.playClip(GunSounds.RPD_SHOT_ID, 1, -1.0f, false);
			if(isUpgraded) {
				Sounds.playClip(GunSounds.UPGRADED_ID, 1, -1.0f, false);
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
