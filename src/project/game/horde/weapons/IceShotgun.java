package project.game.horde.weapons;

import project.game.horde.entities.bullets.IceBullet;
import project.game.horde.entities.bullets.IceStorm;
import project.game.horde.entities.creatures.Player;
import project.game.horde.main.Handler;
import project.game.horde.sounds.Sounds;

public class IceShotgun extends Gun {
	public IceShotgun(Handler handler, Player player) {
		// change values
		super(handler, player, 
				GunVars.ICESHOTGUN_DAMAGE, 
				GunVars.ICESHOTGUN_FIRERATE,
				GunVars.ICESHOTGUN_RELOADSPEED,
				GunVars.ICESHOTGUN_GUNCLIP, 
				GunVars.ICESHOTGUN_MAXRESERVE, 
				GunVars.ICESHOTGUN_WEIGHT, 
				GunVars.ICESHOTGUN_RANGE);
		this.name = GunVars.ICESHOTGUN_NAME;
		originalName = name;
		upgradedName = GunVars.ICESHOTGUN_UPGRADEDNAME;
	}

	int heldShot = 0;
	int heldShotMax = 180;
	boolean heldPrev = false;

	//guess i figured out how to work semi-auto guns
	public void postTick() {
		//System.out.println("HeldShot:" + heldShot);
		if (player.getMouseManager().isLeftPressed() && !isReloading) {
			System.out.println("charge: " + heldShot);
			heldShot++;
		}
		else if (heldPrev && !player.getMouseManager().isLeftPressed() && heldShot >= heldShotMax && !isReloading && isUpgraded) {
			heldShot = 0;
			System.out.println("shot charged shot");
			shootChargedShot();
		}
		else if(!player.getMouseManager().isLeftPressed() && heldShot < heldShotMax && heldShot > 0 && !isReloading) {
			shootSingleShot();
			System.out.println("shot single shot");
			heldShot = 0;
		}
		heldPrev = player.getMouseManager().isLeftPressed();

	}

	public void shootChargedShot() {
		if (isUpgraded && currentClip == clip && !isReloading) {
			System.out.println("out here");
			currentClip = 0;
			handler.getWorld().getEntityManager().addEntity(new IceStorm(handler,
					player.getCenterX(), player.getCenterY(), player.getZ(), this));
			timerToFire = 0;
			readyToFire = false;
			if(player.getPeer() != null) {
				player.getPeer().sendPlayerShot(player.getUsername());
			}
		}
	}

	public void shootSingleShot() {
		if ( currentClip > 0 && !isReloading) {
			readyToFire = false;

			// Sounds.SHOOT.play();

			currentClip--;
			handler.getWorld().getEntityManager().addEntity(new IceBullet(handler,
					player.getCenterX(), player.getCenterY(), player.getZ(), range, this));
			if(player.getPeer() != null) {
				player.getPeer().sendPlayerShot(player.getUsername());
			}
			timerToFire = 0;
		}

	}
	
}
