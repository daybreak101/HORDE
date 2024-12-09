package project.game.horde.sounds;

import java.net.URL;

import project.game.horde.main.Handler;
import project.game.horde.perks.SleightOfHand;
import project.game.horde.utils.Utils;

public class GunSounds {
	public static float SPEED_BASE = 1 + (1 - SleightOfHand.BASE_RELOADBUFF),
			SPEED_1 = 1 + (1 - SleightOfHand.LVL1_RELOADBUFF),
			SPEED_2 = 1 + (1 - SleightOfHand.LVL2_RELOADBUFF),
			SPEED_3 = 1 + (1 - SleightOfHand.LVL3_RELOADBUFF);
	
	
	public static int DEFAULT_QUANTITY = 30;
	public static URL upgraded;
	public static URL ak47_shot, ak47_reload, glock17_shot, glock17_reload, aa12_shot, aa12_reload, awp_shot,
			awp_reload, m4_shot, m4_reload, rpd_shot, rpd_reload, p90_shot, p90_reload, winchester1901_shot,
			winchester1901_shell_reload, winchester1901_start_reload, rpg_shot, rpg_reload, minigun_spin_up,
			minigun_spin_down, minigun_shot, minigun_overheat, grenade_launcher_explosion, grenade_launcher_shot,
			grenade_launcher_reload_open, grenade_launcher_reload_shell, grenade_launcher_reload_close,
			flamethrower_shot, flamethrower_startshot, flamethrower_endshot;

	public static final String UPGRADED_ID = "upgraded", AK47_SHOT_ID = "ak47Shot", AK47_RELOAD_ID = "ak47Reload",
			GLOCK17_SHOT_ID = "glock17Shot", GLOCK17_RELOAD_ID = "glock17Reload", AA12_SHOT_ID = "aa12Shot",
			AA12_RELOAD_ID = "aa12Reload", AWP_SHOT_ID = "awpShot", AWP_RELOAD_ID = "awpReload", M4_SHOT_ID = "m4Shot",
			M4_RELOAD_ID = "m4Reload", RPD_SHOT_ID = "rpdShot", RPD_RELOAD_ID = "rpdReload", P90_SHOT_ID = "p90Shot",
			P90_RELOAD_ID = "p90Reload", WINCHESTER1901_SHOT_ID = "winchester1901Shot",
			WINCHESTER1901_SHELL_RELOAD_ID = "winchester1901ShellReload",
			WINCHESTER1901_START_RELOAD_ID = "winchester1901StartReload", RPG_SHOT_ID = "rpgShot",
			RPG_RELOAD_ID = "rpgReload", MINIGUN_SPIN_UP_ID = "minigunSpinUp", MINIGUN_SPIN_DOWN_ID = "minigunSpinDown",
			MINIGUN_SHOT_ID = "minigunShot", MINIGUN_OVERHEAT_ID = "minigunOverheat",
			GRENADE_LAUNCHER_EXPLOSION_ID = "grenadeLauncherExplosion",
			GRENADE_LAUNCHER_SHOT_ID = "grenadeLauncherShot",
			GRENADE_LAUNCHER_RELOAD_OPEN_ID = "grenadeLauncherReloadOpen",
			GRENADE_LAUNCHER_RELOAD_SHELL_ID = "grenadeLauncherReloadShell",
			GRENADE_LAUNCHER_RELOAD_CLOSE = "grenadeLauncherReloadClose", FLAMETHROWER_SHOT_ID = "flamethrowerShot",
			FLAMETHROWER_START_SHOT_ID = "flamethrowerStartShot", FLAMETHROWER_END_SHOT_ID = "flamethrowerEndShot";

	// speed cola vars
	public static final String AK47_RELOAD_0_ID = "ak47Reload0", AK47_RELOAD_1_ID = "ak47Reload1", AK47_RELOAD_2_ID = "ak47Reload2", AK47_RELOAD_3_ID = "ak47Reload3",
			GLOCK17_RELOAD_0_ID = "glock17Reload0",GLOCK17_RELOAD_1_ID = "glock17Reload1",GLOCK17_RELOAD_2_ID = "glock17Reload2",GLOCK17_RELOAD_3_ID = "glock17Reload3",
			AA12_RELOAD_0_ID = "aa12Reload0",AA12_RELOAD_1_ID = "aa12Reload1",AA12_RELOAD_2_ID = "aa12Reload2",AA12_RELOAD_3_ID = "aa12Reload3",
			AWP_RELOAD_0_ID = "awpReload0", AWP_RELOAD_1_ID = "awpReload1", AWP_RELOAD_2_ID = "awpReload2", AWP_RELOAD_3_ID = "awpReload3", 
			M4_RELOAD_0_ID = "m4Reload0",M4_RELOAD_1_ID = "m4Reload1", M4_RELOAD_2_ID = "m4Reload2", M4_RELOAD_3_ID = "m4Reload3",
			RPD_RELOAD_0_ID = "rpdReload0",RPD_RELOAD_1_ID = "rpdReload1", RPD_RELOAD_2_ID = "rpdReload2", RPD_RELOAD_3_ID = "rpdReload3",
			P90_RELOAD_0_ID = "p90Reload0",P90_RELOAD_1_ID = "p90Reload1", P90_RELOAD_2_ID = "p90Reload2", P90_RELOAD_3_ID = "p90Reload3", 
			WINCHESTER1901_SHELL_RELOAD_0_ID = "winchester1901ShellReload0",WINCHESTER1901_SHELL_RELOAD_1_ID = "winchester1901ShellReload1",WINCHESTER1901_SHELL_RELOAD_2_ID = "winchester1901ShellReload2",WINCHESTER1901_SHELL_RELOAD_3_ID = "winchester1901ShellReload3",
			RPG_RELOAD_0_ID = "rpgReload0", RPG_RELOAD_1_ID = "rpgReload1", RPG_RELOAD_2_ID = "rpgReload2", RPG_RELOAD_3_ID = "rpgReload3",
			GRENADE_LAUNCHER_RELOAD_OPEN_0_ID = "grenadeLauncherReloadOpen0",GRENADE_LAUNCHER_RELOAD_OPEN_1_ID = "grenadeLauncherReloadOpen1",GRENADE_LAUNCHER_RELOAD_OPEN_2_ID = "grenadeLauncherReloadOpen2",GRENADE_LAUNCHER_RELOAD_OPEN_3_ID = "grenadeLauncherReloadOpen3",
			GRENADE_LAUNCHER_RELOAD_SHELL_0_ID = "grenadeLauncherReloadShell0",GRENADE_LAUNCHER_RELOAD_SHELL_1_ID = "grenadeLauncherReloadShell1",GRENADE_LAUNCHER_RELOAD_SHELL_2_ID = "grenadeLauncherReloadShell2",GRENADE_LAUNCHER_RELOAD_SHELL_3_ID = "grenadeLauncherReloadShell3",
			GRENADE_LAUNCHER_RELOAD_CLOSE_0 = "grenadeLauncherReloadClose0",GRENADE_LAUNCHER_RELOAD_CLOSE_1 = "grenadeLauncherReloadClose1", GRENADE_LAUNCHER_RELOAD_CLOSE_2 = "grenadeLauncherReloadClose2", GRENADE_LAUNCHER_RELOAD_CLOSE_3 = "grenadeLauncherReloadClose3",
			MINIGUN_FAILSAFE_RELOAD_ID = "minigunFailSafe";
	public static int flamethrowerCurrent = 0;
	
	public static void init(Handler handler) {
		upgraded = Utils.class.getResource("/sounds/gunSounds/upgraded.wav");
		ak47_shot = Utils.class.getResource("/sounds/gunSounds/ak47/ak47_shot.wav");
		ak47_reload = Utils.class.getResource("/sounds/gunSounds/ak47/ak47_reload.wav");
		glock17_shot = Utils.class.getResource("/sounds/gunSounds/glock17/glock17_shot.wav");
		glock17_reload = Utils.class.getResource("/sounds/gunSounds/glock17/glock17_reload.wav");
		aa12_shot = Utils.class.getResource("/sounds/gunSounds/aa12/aa12_shot.wav");
		aa12_reload = Utils.class.getResource("/sounds/gunSounds/aa12/aa12_reload.wav");
		awp_shot = Utils.class.getResource("/sounds/gunSounds/awp/awp_shot.wav");
		awp_reload = Utils.class.getResource("/sounds/gunSounds/awp/awp_reload.wav");
		m4_shot = Utils.class.getResource("/sounds/gunSounds/m4/m4_shot.wav");
		m4_reload = Utils.class.getResource("/sounds/gunSounds/m4/m4_reload.wav");
		rpd_shot = Utils.class.getResource("/sounds/gunSounds/rpd/rpd_shot.wav");
		rpd_reload = Utils.class.getResource("/sounds/gunSounds/rpd/rpd_reload.wav");
		p90_shot = Utils.class.getResource("/sounds/gunSounds/p90/p90_shot.wav");
		p90_reload = Utils.class.getResource("/sounds/gunSounds/p90/p90_reload.wav");
		winchester1901_shot = Utils.class.getResource("/sounds/gunSounds/winchester1901/winchester1901_shot.wav");
		winchester1901_shell_reload = Utils.class
				.getResource("/sounds/gunSounds/winchester1901/winchester_shell_reload.wav");
		winchester1901_start_reload = Utils.class
				.getResource("/sounds/gunSounds/winchester1901/winchester_start_reload.wav");
		rpg_shot = Utils.class.getResource("/sounds/gunSounds/rpg/rpg_shot.wav");
		rpg_reload = Utils.class.getResource("/sounds/gunSounds/rpg/rpg_reload.wav");
		minigun_spin_up = Utils.class.getResource("/sounds/gunSounds/minigun/minigun_spin-up.wav");
		minigun_spin_down = Utils.class.getResource("/sounds/gunSounds/minigun/minigun_spin-down.wav");
		minigun_shot = Utils.class.getResource("/sounds/gunSounds/minigun/minigun_firing.wav");
		minigun_overheat = Utils.class.getResource("/sounds/gunSounds/minigun/minigun_overheat.wav");
		grenade_launcher_explosion = Utils.class
				.getResource("/sounds/gunSounds/grenade_launcher/grenade_launcher_explosion.wav");
		grenade_launcher_shot = Utils.class
				.getResource("/sounds/gunSounds/grenade_launcher/grenade_launcher_shot_shell.wav");
		grenade_launcher_reload_open = Utils.class
				.getResource("/sounds/gunSounds/grenade_launcher/grenade_launcher_reload_open.wav");
		grenade_launcher_reload_shell = Utils.class
				.getResource("/sounds/gunSounds/grenade_launcher/grenade_launcher_reload_shell.wav");
		grenade_launcher_reload_close = Utils.class
				.getResource("/sounds/gunSounds/grenade_launcher/grenade_launcher_reload_close.wav");
//		flamethrower_shot = Utils.class.getResource("/sounds/gunSounds/flamethrower/flamethrower_newshot.wav");
		flamethrower_startshot = Utils.class.getResource("/sounds/gunSounds/flamethrower/flamethrower_startshot.wav");
		flamethrower_endshot = Utils.class.getResource("/sounds/gunSounds/flamethrower/flamethrower_endshot.wav");

		Sounds.preloadClip(UPGRADED_ID, upgraded, DEFAULT_QUANTITY, 1);
		
		Sounds.preloadClip(AK47_SHOT_ID, ak47_shot, DEFAULT_QUANTITY, 1);
		Sounds.preloadClip(AK47_RELOAD_ID, ak47_reload, DEFAULT_QUANTITY, 1);
		Sounds.preloadClip(AK47_RELOAD_0_ID, ak47_reload, DEFAULT_QUANTITY, SPEED_BASE);
		Sounds.preloadClip(AK47_RELOAD_1_ID, ak47_reload, DEFAULT_QUANTITY, SPEED_1);
		Sounds.preloadClip(AK47_RELOAD_2_ID, ak47_reload, DEFAULT_QUANTITY, SPEED_2);
		Sounds.preloadClip(AK47_RELOAD_3_ID, ak47_reload, DEFAULT_QUANTITY, SPEED_3);
		
		Sounds.preloadClip(GLOCK17_SHOT_ID, glock17_shot, DEFAULT_QUANTITY, 1);
		Sounds.preloadClip(GLOCK17_RELOAD_ID, glock17_reload, DEFAULT_QUANTITY, 1);
		Sounds.preloadClip(GLOCK17_RELOAD_0_ID, glock17_reload, DEFAULT_QUANTITY, SPEED_BASE);
		Sounds.preloadClip(GLOCK17_RELOAD_1_ID, glock17_reload, DEFAULT_QUANTITY, SPEED_1);
		Sounds.preloadClip(GLOCK17_RELOAD_2_ID, glock17_reload, DEFAULT_QUANTITY, SPEED_2);
		Sounds.preloadClip(GLOCK17_RELOAD_3_ID, glock17_reload, DEFAULT_QUANTITY, SPEED_3);
		
		Sounds.preloadClip(AA12_SHOT_ID, aa12_shot, DEFAULT_QUANTITY, 1);
		Sounds.preloadClip(AA12_RELOAD_ID, aa12_reload, DEFAULT_QUANTITY, 1);
		Sounds.preloadClip(AA12_RELOAD_0_ID, aa12_reload, DEFAULT_QUANTITY, SPEED_BASE);
		Sounds.preloadClip(AA12_RELOAD_1_ID, aa12_reload, DEFAULT_QUANTITY, SPEED_1);
		Sounds.preloadClip(AA12_RELOAD_2_ID, aa12_reload, DEFAULT_QUANTITY, SPEED_2);
		Sounds.preloadClip(AA12_RELOAD_3_ID, aa12_reload, DEFAULT_QUANTITY, SPEED_3);
		
		Sounds.preloadClip(AWP_SHOT_ID, awp_shot, DEFAULT_QUANTITY, 1);
		Sounds.preloadClip(AWP_RELOAD_ID, awp_reload, DEFAULT_QUANTITY, 1);
		Sounds.preloadClip(AWP_RELOAD_0_ID, awp_reload, DEFAULT_QUANTITY, SPEED_BASE);
		Sounds.preloadClip(AWP_RELOAD_1_ID, awp_reload, DEFAULT_QUANTITY, SPEED_1);
		Sounds.preloadClip(AWP_RELOAD_2_ID, awp_reload, DEFAULT_QUANTITY, SPEED_2);
		Sounds.preloadClip(AWP_RELOAD_3_ID, awp_reload, DEFAULT_QUANTITY, SPEED_3);
		
		Sounds.preloadClip(M4_SHOT_ID, m4_shot, DEFAULT_QUANTITY, 1);
		Sounds.preloadClip(M4_RELOAD_ID, m4_reload, DEFAULT_QUANTITY, 1);
		Sounds.preloadClip(M4_RELOAD_0_ID, m4_reload, DEFAULT_QUANTITY, SPEED_BASE);
		Sounds.preloadClip(M4_RELOAD_1_ID, m4_reload, DEFAULT_QUANTITY, SPEED_1);
		Sounds.preloadClip(M4_RELOAD_2_ID, m4_reload, DEFAULT_QUANTITY, SPEED_2);
		Sounds.preloadClip(M4_RELOAD_3_ID, m4_reload, DEFAULT_QUANTITY, SPEED_3);
		
		Sounds.preloadClip(RPD_SHOT_ID, rpd_shot, DEFAULT_QUANTITY, 1);
		Sounds.preloadClip(RPD_RELOAD_ID, rpd_reload, DEFAULT_QUANTITY, 1);
		Sounds.preloadClip(RPD_RELOAD_0_ID, rpd_reload, DEFAULT_QUANTITY, SPEED_BASE);
		Sounds.preloadClip(RPD_RELOAD_1_ID, rpd_reload, DEFAULT_QUANTITY, SPEED_1);
		Sounds.preloadClip(RPD_RELOAD_2_ID, rpd_reload, DEFAULT_QUANTITY, SPEED_2);
		Sounds.preloadClip(RPD_RELOAD_3_ID, rpd_reload, DEFAULT_QUANTITY, SPEED_3);
		
		Sounds.preloadClip(P90_SHOT_ID, p90_shot, DEFAULT_QUANTITY, 1);
		Sounds.preloadClip(P90_RELOAD_ID, p90_reload, DEFAULT_QUANTITY, 1);
		Sounds.preloadClip(P90_RELOAD_0_ID, p90_reload, DEFAULT_QUANTITY, SPEED_BASE);
		Sounds.preloadClip(P90_RELOAD_1_ID, p90_reload, DEFAULT_QUANTITY, SPEED_1);
		Sounds.preloadClip(P90_RELOAD_2_ID, p90_reload, DEFAULT_QUANTITY, SPEED_2);
		Sounds.preloadClip(P90_RELOAD_3_ID, p90_reload, DEFAULT_QUANTITY, SPEED_3);
		
		Sounds.preloadClip(WINCHESTER1901_SHOT_ID, winchester1901_shot, DEFAULT_QUANTITY, 1);
		Sounds.preloadClip(WINCHESTER1901_SHELL_RELOAD_ID, winchester1901_shell_reload, DEFAULT_QUANTITY, 1);
		Sounds.preloadClip(WINCHESTER1901_SHELL_RELOAD_0_ID, winchester1901_shell_reload, DEFAULT_QUANTITY, SPEED_BASE);
		Sounds.preloadClip(WINCHESTER1901_SHELL_RELOAD_1_ID, winchester1901_shell_reload, DEFAULT_QUANTITY, SPEED_1);
		Sounds.preloadClip(WINCHESTER1901_SHELL_RELOAD_2_ID, winchester1901_shell_reload, DEFAULT_QUANTITY, SPEED_2);
		Sounds.preloadClip(WINCHESTER1901_SHELL_RELOAD_3_ID, winchester1901_shell_reload, DEFAULT_QUANTITY, SPEED_3);
		Sounds.preloadClip(WINCHESTER1901_START_RELOAD_ID, winchester1901_start_reload, DEFAULT_QUANTITY, 1);
		//maybe add sped up versions
		
		Sounds.preloadClip(RPG_SHOT_ID, rpg_shot, DEFAULT_QUANTITY, 1);
		Sounds.preloadClip(RPG_RELOAD_ID, rpg_reload, DEFAULT_QUANTITY, 1);
		Sounds.preloadClip(RPG_RELOAD_0_ID, rpg_reload, DEFAULT_QUANTITY, SPEED_BASE);
		Sounds.preloadClip(RPG_RELOAD_1_ID, rpg_reload, DEFAULT_QUANTITY, SPEED_1);
		Sounds.preloadClip(RPG_RELOAD_2_ID, rpg_reload, DEFAULT_QUANTITY, SPEED_2);
		Sounds.preloadClip(RPG_RELOAD_3_ID, rpg_reload, DEFAULT_QUANTITY, SPEED_3);
		
		Sounds.preloadClip(MINIGUN_SPIN_UP_ID, minigun_spin_up, DEFAULT_QUANTITY, 1);
		//maybe add double tap vars
		Sounds.preloadClip(MINIGUN_SPIN_DOWN_ID, minigun_spin_down, DEFAULT_QUANTITY, 1);
		//maybe add double tap vars
		Sounds.preloadClip(MINIGUN_SHOT_ID, minigun_shot, DEFAULT_QUANTITY, 1);
		Sounds.preloadClip(MINIGUN_OVERHEAT_ID, minigun_overheat, DEFAULT_QUANTITY, 1);
		
		Sounds.preloadClip(GRENADE_LAUNCHER_EXPLOSION_ID, grenade_launcher_explosion, DEFAULT_QUANTITY, 1);
		Sounds.preloadClip(GRENADE_LAUNCHER_SHOT_ID, grenade_launcher_shot, DEFAULT_QUANTITY, 1);
		Sounds.preloadClip(GRENADE_LAUNCHER_RELOAD_OPEN_ID, grenade_launcher_reload_open, DEFAULT_QUANTITY, 1);
		Sounds.preloadClip(GRENADE_LAUNCHER_RELOAD_OPEN_0_ID, grenade_launcher_reload_open, DEFAULT_QUANTITY, SPEED_BASE);
		Sounds.preloadClip(GRENADE_LAUNCHER_RELOAD_OPEN_1_ID, grenade_launcher_reload_open, DEFAULT_QUANTITY, SPEED_1);
		Sounds.preloadClip(GRENADE_LAUNCHER_RELOAD_OPEN_2_ID, grenade_launcher_reload_open, DEFAULT_QUANTITY, SPEED_2);
		Sounds.preloadClip(GRENADE_LAUNCHER_RELOAD_OPEN_3_ID, grenade_launcher_reload_open, DEFAULT_QUANTITY, SPEED_3);
		Sounds.preloadClip(GRENADE_LAUNCHER_RELOAD_SHELL_ID, grenade_launcher_reload_shell, DEFAULT_QUANTITY, 1);
		Sounds.preloadClip(GRENADE_LAUNCHER_RELOAD_SHELL_0_ID, grenade_launcher_reload_shell, DEFAULT_QUANTITY, SPEED_BASE);
		Sounds.preloadClip(GRENADE_LAUNCHER_RELOAD_SHELL_1_ID, grenade_launcher_reload_shell, DEFAULT_QUANTITY, SPEED_1);
		Sounds.preloadClip(GRENADE_LAUNCHER_RELOAD_SHELL_2_ID, grenade_launcher_reload_shell, DEFAULT_QUANTITY, SPEED_2);
		Sounds.preloadClip(GRENADE_LAUNCHER_RELOAD_SHELL_3_ID, grenade_launcher_reload_shell, DEFAULT_QUANTITY, SPEED_3);
		Sounds.preloadClip(GRENADE_LAUNCHER_RELOAD_CLOSE, grenade_launcher_reload_close, DEFAULT_QUANTITY, 1);
		Sounds.preloadClip(GRENADE_LAUNCHER_RELOAD_CLOSE_0, grenade_launcher_reload_close, DEFAULT_QUANTITY, SPEED_BASE);
		Sounds.preloadClip(GRENADE_LAUNCHER_RELOAD_CLOSE_1, grenade_launcher_reload_close, DEFAULT_QUANTITY, SPEED_1);
		Sounds.preloadClip(GRENADE_LAUNCHER_RELOAD_CLOSE_2, grenade_launcher_reload_close, DEFAULT_QUANTITY, SPEED_2);
		Sounds.preloadClip(GRENADE_LAUNCHER_RELOAD_CLOSE_3, grenade_launcher_reload_close, DEFAULT_QUANTITY, SPEED_3);

		flamethrower_shot = Utils.class.getResource("/sounds/gunSounds/flamethrower/flamethrower_newshot.wav");
		//for teammatess
		for(int i = 0; i < 10; i++) {
			Sounds.preloadClip(FLAMETHROWER_SHOT_ID + "_" + i, flamethrower_shot, 1, 1);

		}
		//for you
		Sounds.preloadClip(FLAMETHROWER_SHOT_ID, flamethrower_shot, 1, 1);
		Sounds.preloadClip(FLAMETHROWER_START_SHOT_ID, flamethrower_startshot, DEFAULT_QUANTITY, 1);
		Sounds.preloadClip(FLAMETHROWER_END_SHOT_ID, flamethrower_endshot, DEFAULT_QUANTITY, 1);
		
		Sounds.preloadClip(MINIGUN_FAILSAFE_RELOAD_ID, minigun_overheat, DEFAULT_QUANTITY, SPEED_1);

	}
	
	public static String getFlamethrowerCurrent() {
		int current = flamethrowerCurrent;
		flamethrowerCurrent++;
		if(flamethrowerCurrent > 9) {
			flamethrowerCurrent = 0;
		}
		return FLAMETHROWER_SHOT_ID + "_" + current;
	}
}
