package project.game.horde.weapons;

import project.game.horde.entities.bullets.Rocket;
import project.game.horde.entities.creatures.Player;
import project.game.horde.main.Handler;
import project.game.horde.sounds.GunSounds;
import project.game.horde.sounds.Sounds;
import project.game.horde.utils.RandomUtil;

public class RPG extends Gun{

	public RPG(Handler handler, Player player) {
		super(handler, player, 
				GunVars.RPG_DAMAGE, 
				GunVars.RPG_FIRERATE,
				GunVars.RPG_RELOADSPEED,
				GunVars.RPG_GUNCLIP, 
				GunVars.RPG_MAXRESERVE, 
				GunVars.RPG_WEIGHT, 
				GunVars.RPG_RANGE);
		this.name = GunVars.RPG_NAME;
		originalName = name;
		upgradedName = GunVars.RPG_UPGRADEDNAME;
		reloadSound = GunSounds.RPG_RELOAD_ID;
	}
	
	public void shoot() {		
		if(readyToFire == true && currentClip > 0 && isReloading == false) {
			readyToFire = false;
			currentClip--;
			handler.getWorld().getEntityManager().addEntity(new Rocket(handler, 
					player.getCenterX(),
					player.getCenterY(),
					player.getZ(),
					this
					));
			Sounds.playClip(GunSounds.RPG_SHOT_ID, 1, -1.0f, false);

			//Sounds.playClip(GunSounds.rpg_shot, 1, "rpg_shot" + RandomUtil.nextInt(0, 10000), -1.0f, false);
			
			if(isUpgraded) {
				Sounds.playClip(GunSounds.UPGRADED_ID, 1, -1.0f, false);

				//Sounds.playClip(GunSounds.upgraded, 1, "upgraded" + RandomUtil.nextInt(0, 10000), -1.0f, false);
			}
			if(player.getPeer() != null) {
				player.getPeer().sendPlayerShot(player.getUsername());
			}
			timerToFire = 0;
		}
	}
	

}
