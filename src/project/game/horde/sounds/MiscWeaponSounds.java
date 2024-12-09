package project.game.horde.sounds;
import java.net.URL;

import project.game.horde.entities.Entity;
import project.game.horde.main.Handler;
import project.game.horde.utils.Utils;

public class MiscWeaponSounds {
	public static URL gasGrenade, grenadeToss;
	public static final String GAS_GRENADE = "gasGrenade", GRENADE_TOSS = "grenadeToss";
	public static int gasGrenadeCurrent = 0;
	
	

	public static void init(Handler handler) {
		gasGrenade = Utils.class.getResource("/sounds/gunSounds/gasGrenades/gas_grenade.wav");
		for(int i = 0; i < 10; i++) {
			Sounds.preloadClip(GAS_GRENADE + "_" + i, gasGrenade, 1, 1);

		}

		grenadeToss = Utils.class.getResource("/sounds/gunSounds/grenades/grenade_toss_full.wav");
		Sounds.preloadClip(GRENADE_TOSS, grenadeToss, 5, 1);
	}
	
	public static String getGasGrenadeCurrent() {
		int current = gasGrenadeCurrent;
		gasGrenadeCurrent++;
		if(gasGrenadeCurrent > 9) {
			gasGrenadeCurrent = 0;
		}
		return GAS_GRENADE + "_" + current;
	}
	
	
}
