package project.game.horde.weapons;

import project.game.horde.entities.bullets.Grenade;
import project.game.horde.entities.creatures.Player;
import project.game.horde.main.Handler;
import project.game.horde.sounds.GunSounds;
import project.game.horde.sounds.Sounds;

public class FragGrenade extends Gun{
	public FragGrenade(Handler handler, Player player) {
		super(handler, player, GunVars.GRENADE_DAMAGE,
				GunVars.GRENADELAUNCHER_FIRERATE,
				GunVars.GRENADELAUNCHER_RELOADSPEED, 
				0,
				4, 
				0, 
				GunVars.GRENADELAUNCHER_RANGE, 100);
		this.name = GunVars.GRENADE_NAME;
		originalName = name;
		upgradedName = "HUH";
		reloadSound = "";
		currentReserve = 0;
	}
	public void throwGrenade() {
		if (readyToFire == true && currentClip > 0 && isReloading == false) {
			readyToFire = false;
			currentClip--;

			Sounds.playClip(GunSounds.GRENADE_LAUNCHER_SHOT_ID, 1, -1.0f, false);
			//Sounds.playClip(GunSounds.grenade_launcher_shot, 1, "grenade_launcher_shot" + RandomUtil.nextInt(0, 10000),
			//		-1.0f, false);

			if (isUpgraded) {
				Sounds.playClip(GunSounds.UPGRADED_ID, 1, -1.0f, false);

				//Sounds.playClip(GunSounds.upgraded, 1, "upgraded" + RandomUtil.nextInt(0, 10000), -1.0f, false);
			}

			handler.getWorld().getEntityManager()
					.addEntity(new Grenade(handler, player.getCenterX(), player.getCenterY(), isUpgraded,
							player.getMouseManager().getMouseX() + handler.getGameCamera().getxOffset(),
							player.getMouseManager().getMouseY() + handler.getGameCamera().getyOffset(),
							player, this));
			if (player.getPeer() != null) {
				player.getPeer().sendPlayerGrenadeLauncherShot(player.getUsername(),
						(int) (player.getMouseManager().getMouseX() + handler.getGameCamera().getxOffset()),
						(int) (player.getMouseManager().getMouseY() + handler.getGameCamera().getyOffset())
						);
			}
			timerToFire = 0;
		}
	}
}
