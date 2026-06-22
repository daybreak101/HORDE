package project.game.horde.sounds;
import java.net.URL;

import project.game.horde.entities.Entity;
import project.game.horde.main.Handler;
import project.game.horde.utils.Utils;

public class MiscWeaponSounds {
	public static URL gasGrenade, grenadeToss, grenadeUnclip;
	public static final String GAS_GRENADE = "gasGrenade", GRENADE_TOSS = "grenadeToss", GRENADE_UNCLIP = "grenadeUnclip";
	public static int gasGrenadeCurrent = 0;
	
	public static URL meleeWhoosh;
	public static final String MELEE_WHOOSH = "meleeWhoosh";

	public static void init(Handler handler) {
		gasGrenade = Utils.class.getResource("/sounds/gunSounds/gasGrenades/gas_grenade.wav");
		for(int i = 0; i < 10; i++) {
			Sounds.preloadClip(GAS_GRENADE + "_" + i, gasGrenade, 1, 1);

		}
		
		grenadeUnclip = Utils.class.getResource("/sounds/gunSounds/grenades/grenade_unclip.wav");
		Sounds.preloadClip(GRENADE_UNCLIP, grenadeUnclip, 5, 1);

		grenadeToss = Utils.class.getResource("/sounds/gunSounds/grenades/grenade_toss.wav");
		Sounds.preloadClip(GRENADE_TOSS, grenadeToss, 5, 1);
		
		meleeWhoosh = Utils.class.getResource("/sounds/player/melee.wav");
		Sounds.preloadClip(MELEE_WHOOSH, meleeWhoosh , 1, 1);
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
