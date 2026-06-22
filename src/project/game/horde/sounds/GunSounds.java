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

	public static void init(Handler handler) {
		loadOtherSounds();
		loadAK47Sounds();
		loadGlock17Sounds();
		loadAA12Sounds();
		loadAWPSounds();
		loadM4Sounds();
		loadRPDSounds();
		loadP90Sounds();
		loadWinchester1901Sounds();
		loadRPGSounds();
		loadGrenadeLauncherSounds();
		loadFlamethrowerSounds();
		loadMinigunSounds();
		loadM1GarandSounds();
		loadArisakaSounds();
		
		loadBrenSounds();
		loadDoubleBarrelSounds();
		loadM16Sounds();
		loadM1911Sounds();
		loadM60Sounds();
		loadPythonSounds();
		loadType100Sounds();
		loadThompsonSounds();
		loadUziSounds();
	}
	
	//other sounds
	public static URL upgraded;
	public static final String UPGRADED_ID = "upgraded";
	public static void loadOtherSounds() {
		upgraded = Utils.class.getResource("/sounds/gunSounds/upgraded.wav");
		Sounds.preloadClip(UPGRADED_ID, upgraded, DEFAULT_QUANTITY, 1);
	}
	
	//ak47
	public static URL ak47_shot, ak47_reload;
	public static final String AK47_SHOT_ID = "ak47Shot", 
			AK47_RELOAD_ID = "ak47Reload",
			AK47_RELOAD_0_ID = "ak47Reload0", 
			AK47_RELOAD_1_ID = "ak47Reload1",
			AK47_RELOAD_2_ID = "ak47Reload2",
			AK47_RELOAD_3_ID = "ak47Reload3";
	public static void loadAK47Sounds() {
		ak47_shot = Utils.class.getResource("/sounds/gunSounds/ak47/ak47_shot.wav");
		ak47_reload = Utils.class.getResource("/sounds/gunSounds/ak47/ak47_reload.wav");
		Sounds.preloadClip(AK47_SHOT_ID, ak47_shot, DEFAULT_QUANTITY, 1);
		Sounds.preloadClip(AK47_RELOAD_ID, ak47_reload, DEFAULT_QUANTITY, 1);
		Sounds.preloadClip(AK47_RELOAD_0_ID, ak47_reload, DEFAULT_QUANTITY, SPEED_BASE);
		Sounds.preloadClip(AK47_RELOAD_1_ID, ak47_reload, DEFAULT_QUANTITY, SPEED_1);
		Sounds.preloadClip(AK47_RELOAD_2_ID, ak47_reload, DEFAULT_QUANTITY, SPEED_2);
		Sounds.preloadClip(AK47_RELOAD_3_ID, ak47_reload, DEFAULT_QUANTITY, SPEED_3);
	}
	
	//glock17
	public static URL glock17_shot, glock17_reload;
	public static final String GLOCK17_SHOT_ID = "glock17Shot",
			GLOCK17_RELOAD_ID = "glock17Reload",
			GLOCK17_RELOAD_0_ID = "glock17Reload0",
			GLOCK17_RELOAD_1_ID = "glock17Reload1",
			GLOCK17_RELOAD_2_ID = "glock17Reload2",
			GLOCK17_RELOAD_3_ID = "glock17Reload3";
	public static void loadGlock17Sounds() {
		glock17_shot = Utils.class.getResource("/sounds/gunSounds/glock17/glock17_shot.wav");
		glock17_reload = Utils.class.getResource("/sounds/gunSounds/glock17/glock17_reload.wav");
		Sounds.preloadClip(GLOCK17_SHOT_ID, glock17_shot, DEFAULT_QUANTITY, 1);
		Sounds.preloadClip(GLOCK17_RELOAD_ID, glock17_reload, DEFAULT_QUANTITY, 1);
		Sounds.preloadClip(GLOCK17_RELOAD_0_ID, glock17_reload, DEFAULT_QUANTITY, SPEED_BASE);
		Sounds.preloadClip(GLOCK17_RELOAD_1_ID, glock17_reload, DEFAULT_QUANTITY, SPEED_1);
		Sounds.preloadClip(GLOCK17_RELOAD_2_ID, glock17_reload, DEFAULT_QUANTITY, SPEED_2);
		Sounds.preloadClip(GLOCK17_RELOAD_3_ID, glock17_reload, DEFAULT_QUANTITY, SPEED_3);
	}
	
	//aa12
	public static URL aa12_shot, aa12_reload;
	public static final String AA12_SHOT_ID = "aa12Shot",
			AA12_RELOAD_ID = "aa12Reload",
			AA12_RELOAD_0_ID = "aa12Reload0",
			AA12_RELOAD_1_ID = "aa12Reload1",
			AA12_RELOAD_2_ID = "aa12Reload2",
			AA12_RELOAD_3_ID = "aa12Reload3";
	public static void loadAA12Sounds() {
		aa12_shot = Utils.class.getResource("/sounds/gunSounds/aa12/aa12_shot.wav");
		aa12_reload = Utils.class.getResource("/sounds/gunSounds/aa12/aa12_reload.wav");
		Sounds.preloadClip(AA12_SHOT_ID, aa12_shot, DEFAULT_QUANTITY, 1);
		Sounds.preloadClip(AA12_RELOAD_ID, aa12_reload, DEFAULT_QUANTITY, 1);
		Sounds.preloadClip(AA12_RELOAD_0_ID, aa12_reload, DEFAULT_QUANTITY, SPEED_BASE);
		Sounds.preloadClip(AA12_RELOAD_1_ID, aa12_reload, DEFAULT_QUANTITY, SPEED_1);
		Sounds.preloadClip(AA12_RELOAD_2_ID, aa12_reload, DEFAULT_QUANTITY, SPEED_2);
		Sounds.preloadClip(AA12_RELOAD_3_ID, aa12_reload, DEFAULT_QUANTITY, SPEED_3);
	}
	
	//awp
	public static URL awp_shot, awp_reload;
	public final static String AWP_SHOT_ID = "awpShot",
			AWP_RELOAD_ID = "awpReload",
			AWP_RELOAD_0_ID = "awpReload0",
			AWP_RELOAD_1_ID = "awpReload1", 
			AWP_RELOAD_2_ID = "awpReload2", 
			AWP_RELOAD_3_ID = "awpReload3";
	public static void loadAWPSounds() {
		awp_shot = Utils.class.getResource("/sounds/gunSounds/awp/awp_shot.wav");
		awp_reload = Utils.class.getResource("/sounds/gunSounds/awp/awp_reload.wav");
		Sounds.preloadClip(AWP_SHOT_ID, awp_shot, DEFAULT_QUANTITY, 1);
		Sounds.preloadClip(AWP_RELOAD_ID, awp_reload, DEFAULT_QUANTITY, 1);
		Sounds.preloadClip(AWP_RELOAD_0_ID, awp_reload, DEFAULT_QUANTITY, SPEED_BASE);
		Sounds.preloadClip(AWP_RELOAD_1_ID, awp_reload, DEFAULT_QUANTITY, SPEED_1);
		Sounds.preloadClip(AWP_RELOAD_2_ID, awp_reload, DEFAULT_QUANTITY, SPEED_2);
		Sounds.preloadClip(AWP_RELOAD_3_ID, awp_reload, DEFAULT_QUANTITY, SPEED_3);
	}
	
	//m4
	public static URL m4_shot, m4_reload;
	public final static String M4_SHOT_ID = "m4Shot",
			M4_RELOAD_ID = "m4Reload",
			M4_RELOAD_0_ID = "m4Reload0",
			M4_RELOAD_1_ID = "m4Reload1",
			M4_RELOAD_2_ID = "m4Reload2",
			M4_RELOAD_3_ID = "m4Reload3";
	public static void loadM4Sounds() {
		m4_shot = Utils.class.getResource("/sounds/gunSounds/m4/m4_shot.wav");
		m4_reload = Utils.class.getResource("/sounds/gunSounds/m4/m4_reload.wav");
		Sounds.preloadClip(M4_SHOT_ID, m4_shot, DEFAULT_QUANTITY, 1);
		Sounds.preloadClip(M4_RELOAD_ID, m4_reload, DEFAULT_QUANTITY, 1);
		Sounds.preloadClip(M4_RELOAD_0_ID, m4_reload, DEFAULT_QUANTITY, SPEED_BASE);
		Sounds.preloadClip(M4_RELOAD_1_ID, m4_reload, DEFAULT_QUANTITY, SPEED_1);
		Sounds.preloadClip(M4_RELOAD_2_ID, m4_reload, DEFAULT_QUANTITY, SPEED_2);
		Sounds.preloadClip(M4_RELOAD_3_ID, m4_reload, DEFAULT_QUANTITY, SPEED_3);
	}
	
	//rpd
	public static URL rpd_shot, rpd_reload;
	public final static String RPD_SHOT_ID = "rpdShot", 
			RPD_RELOAD_ID = "rpdReload",
			RPD_RELOAD_0_ID = "rpdReload0",
			RPD_RELOAD_1_ID = "rpdReload1", 
			RPD_RELOAD_2_ID = "rpdReload2", 
			RPD_RELOAD_3_ID = "rpdReload3";
	public static void loadRPDSounds() {
		rpd_shot = Utils.class.getResource("/sounds/gunSounds/rpd/rpd_shot.wav");
		rpd_reload = Utils.class.getResource("/sounds/gunSounds/rpd/rpd_reload.wav");
		Sounds.preloadClip(RPD_SHOT_ID, rpd_shot, DEFAULT_QUANTITY, 1);
		Sounds.preloadClip(RPD_RELOAD_ID, rpd_reload, DEFAULT_QUANTITY, 1);
		Sounds.preloadClip(RPD_RELOAD_0_ID, rpd_reload, DEFAULT_QUANTITY, SPEED_BASE);
		Sounds.preloadClip(RPD_RELOAD_1_ID, rpd_reload, DEFAULT_QUANTITY, SPEED_1);
		Sounds.preloadClip(RPD_RELOAD_2_ID, rpd_reload, DEFAULT_QUANTITY, SPEED_2);
		Sounds.preloadClip(RPD_RELOAD_3_ID, rpd_reload, DEFAULT_QUANTITY, SPEED_3);
	}
	
	//p90
	public static URL p90_shot, p90_reload;
	public final static String  P90_SHOT_ID = "p90Shot",
			P90_RELOAD_ID = "p90Reload",
			P90_RELOAD_0_ID = "p90Reload0",
			P90_RELOAD_1_ID = "p90Reload1", 
			P90_RELOAD_2_ID = "p90Reload2", 
			P90_RELOAD_3_ID = "p90Reload3";
	public static void loadP90Sounds() {
		p90_shot = Utils.class.getResource("/sounds/gunSounds/p90/p90_shot.wav");
		p90_reload = Utils.class.getResource("/sounds/gunSounds/p90/p90_reload.wav");
		Sounds.preloadClip(P90_SHOT_ID, p90_shot, DEFAULT_QUANTITY, 1);
		Sounds.preloadClip(P90_RELOAD_ID, p90_reload, DEFAULT_QUANTITY, 1);
		Sounds.preloadClip(P90_RELOAD_0_ID, p90_reload, DEFAULT_QUANTITY, SPEED_BASE);
		Sounds.preloadClip(P90_RELOAD_1_ID, p90_reload, DEFAULT_QUANTITY, SPEED_1);
		Sounds.preloadClip(P90_RELOAD_2_ID, p90_reload, DEFAULT_QUANTITY, SPEED_2);
		Sounds.preloadClip(P90_RELOAD_3_ID, p90_reload, DEFAULT_QUANTITY, SPEED_3);
	}
	
	//winchester1901
	public static URL winchester1901_shot, winchester1901_shell_reload, winchester1901_start_reload;
	public final static String WINCHESTER1901_SHOT_ID = "winchester1901Shot",
			WINCHESTER1901_SHELL_RELOAD_ID = "winchester1901ShellReload",
			WINCHESTER1901_START_RELOAD_ID = "winchester1901StartReload",
			WINCHESTER1901_SHELL_RELOAD_0_ID = "winchester1901ShellReload0",
			WINCHESTER1901_SHELL_RELOAD_1_ID = "winchester1901ShellReload1",
			WINCHESTER1901_SHELL_RELOAD_2_ID = "winchester1901ShellReload2",
			WINCHESTER1901_SHELL_RELOAD_3_ID = "winchester1901ShellReload3";
	public static void loadWinchester1901Sounds() {
		winchester1901_shot = Utils.class.getResource("/sounds/gunSounds/winchester1901/winchester1901_shot.wav");
		winchester1901_shell_reload = Utils.class.getResource("/sounds/gunSounds/winchester1901/winchester_shell_reload.wav");
		winchester1901_start_reload = Utils.class.getResource("/sounds/gunSounds/winchester1901/winchester_start_reload.wav");
		Sounds.preloadClip(WINCHESTER1901_SHOT_ID, winchester1901_shot, DEFAULT_QUANTITY, 1);
		Sounds.preloadClip(WINCHESTER1901_SHELL_RELOAD_ID, winchester1901_shell_reload, DEFAULT_QUANTITY, 1);
		Sounds.preloadClip(WINCHESTER1901_SHELL_RELOAD_0_ID, winchester1901_shell_reload, DEFAULT_QUANTITY, SPEED_BASE);
		Sounds.preloadClip(WINCHESTER1901_SHELL_RELOAD_1_ID, winchester1901_shell_reload, DEFAULT_QUANTITY, SPEED_1);
		Sounds.preloadClip(WINCHESTER1901_SHELL_RELOAD_2_ID, winchester1901_shell_reload, DEFAULT_QUANTITY, SPEED_2);
		Sounds.preloadClip(WINCHESTER1901_SHELL_RELOAD_3_ID, winchester1901_shell_reload, DEFAULT_QUANTITY, SPEED_3);
		Sounds.preloadClip(WINCHESTER1901_START_RELOAD_ID, winchester1901_start_reload, DEFAULT_QUANTITY, 1);
		//maybe add sped up versions
	}
	
	//rpg
	public static URL rpg_shot, rpg_reload;
	public final static String RPG_SHOT_ID = "rpgShot", 
			RPG_RELOAD_ID = "rpgReload",
			RPG_RELOAD_0_ID = "rpgReload0",
			RPG_RELOAD_1_ID = "rpgReload1",
			RPG_RELOAD_2_ID = "rpgReload2",
			RPG_RELOAD_3_ID = "rpgReload3";
	public static void loadRPGSounds() {
		rpg_shot = Utils.class.getResource("/sounds/gunSounds/rpg/rpg_shot.wav");
		rpg_reload = Utils.class.getResource("/sounds/gunSounds/rpg/rpg_reload.wav");
		Sounds.preloadClip(RPG_SHOT_ID, rpg_shot, DEFAULT_QUANTITY, 1);
		Sounds.preloadClip(RPG_RELOAD_ID, rpg_reload, DEFAULT_QUANTITY, 1);
		Sounds.preloadClip(RPG_RELOAD_0_ID, rpg_reload, DEFAULT_QUANTITY, SPEED_BASE);
		Sounds.preloadClip(RPG_RELOAD_1_ID, rpg_reload, DEFAULT_QUANTITY, SPEED_1);
		Sounds.preloadClip(RPG_RELOAD_2_ID, rpg_reload, DEFAULT_QUANTITY, SPEED_2);
		Sounds.preloadClip(RPG_RELOAD_3_ID, rpg_reload, DEFAULT_QUANTITY, SPEED_3);
	}
	
	//grenade launcher
	public static URL grenade_launcher_explosion, grenade_launcher_shot,
		grenade_launcher_reload_open, grenade_launcher_reload_shell, grenade_launcher_reload_close;
	public final static String GRENADE_LAUNCHER_EXPLOSION_ID = "grenadeLauncherExplosion",
			GRENADE_LAUNCHER_SHOT_ID = "grenadeLauncherShot",
			GRENADE_LAUNCHER_RELOAD_OPEN_ID = "grenadeLauncherReloadOpen",
			GRENADE_LAUNCHER_RELOAD_SHELL_ID = "grenadeLauncherReloadShell",
			GRENADE_LAUNCHER_RELOAD_CLOSE = "grenadeLauncherReloadClose",
			GRENADE_LAUNCHER_RELOAD_OPEN_0_ID = "grenadeLauncherReloadOpen0",
			GRENADE_LAUNCHER_RELOAD_OPEN_1_ID = "grenadeLauncherReloadOpen1",
			GRENADE_LAUNCHER_RELOAD_OPEN_2_ID = "grenadeLauncherReloadOpen2",
			GRENADE_LAUNCHER_RELOAD_OPEN_3_ID = "grenadeLauncherReloadOpen3",
			GRENADE_LAUNCHER_RELOAD_SHELL_0_ID = "grenadeLauncherReloadShell0",
			GRENADE_LAUNCHER_RELOAD_SHELL_1_ID = "grenadeLauncherReloadShell1",
			GRENADE_LAUNCHER_RELOAD_SHELL_2_ID = "grenadeLauncherReloadShell2",
			GRENADE_LAUNCHER_RELOAD_SHELL_3_ID = "grenadeLauncherReloadShell3",
			GRENADE_LAUNCHER_RELOAD_CLOSE_0 = "grenadeLauncherReloadClose0",
			GRENADE_LAUNCHER_RELOAD_CLOSE_1 = "grenadeLauncherReloadClose1", 
			GRENADE_LAUNCHER_RELOAD_CLOSE_2 = "grenadeLauncherReloadClose2", 
			GRENADE_LAUNCHER_RELOAD_CLOSE_3 = "grenadeLauncherReloadClose3";
	public static void loadGrenadeLauncherSounds() {
		grenade_launcher_explosion = Utils.class.getResource("/sounds/gunSounds/grenade_launcher/grenade_launcher_explosion.wav");
		grenade_launcher_shot = Utils.class.getResource("/sounds/gunSounds/grenade_launcher/grenade_launcher_shot_shell.wav");
		grenade_launcher_reload_open = Utils.class.getResource("/sounds/gunSounds/grenade_launcher/grenade_launcher_reload_open.wav");
		grenade_launcher_reload_shell = Utils.class.getResource("/sounds/gunSounds/grenade_launcher/grenade_launcher_reload_shell.wav");
		grenade_launcher_reload_close = Utils.class.getResource("/sounds/gunSounds/grenade_launcher/grenade_launcher_reload_close.wav");
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
	}
	
	//flamethrower
	public static int flamethrowerCurrent = 0;
	public static URL flamethrower_shot, flamethrower_startshot, flamethrower_endshot;
	public final static String FLAMETHROWER_SHOT_ID = "flamethrowerShot",
				FLAMETHROWER_START_SHOT_ID = "flamethrowerStartShot", 
				FLAMETHROWER_END_SHOT_ID = "flamethrowerEndShot";
	public static void loadFlamethrowerSounds() {
		flamethrower_startshot = Utils.class.getResource("/sounds/gunSounds/flamethrower/flamethrower_startshot.wav");
		flamethrower_endshot = Utils.class.getResource("/sounds/gunSounds/flamethrower/flamethrower_endshot.wav");
		flamethrower_shot = Utils.class.getResource("/sounds/gunSounds/flamethrower/flamethrower_newshot.wav");
		//for teammatess
		for(int i = 0; i < 10; i++) {
			Sounds.preloadClip(FLAMETHROWER_SHOT_ID + "_" + i, flamethrower_shot, 1, 1);

		}
		//for you
		Sounds.preloadClip(FLAMETHROWER_SHOT_ID, flamethrower_shot, 1, 1);
		Sounds.preloadClip(FLAMETHROWER_START_SHOT_ID, flamethrower_startshot, DEFAULT_QUANTITY, 1);
		Sounds.preloadClip(FLAMETHROWER_END_SHOT_ID, flamethrower_endshot, DEFAULT_QUANTITY, 1);
	}
	public static String getFlamethrowerCurrent() {
		int current = flamethrowerCurrent;
		flamethrowerCurrent++;
		if(flamethrowerCurrent > 9) {
			flamethrowerCurrent = 0;
		}
		return FLAMETHROWER_SHOT_ID + "_" + current;
	}
	
	//minigun
	public static URL minigun_spin_up, minigun_spin_down, minigun_shot, minigun_overheat;
	public final static String MINIGUN_FAILSAFE_RELOAD_ID = "minigunFailSafe",
			MINIGUN_SPIN_UP_ID = "minigunSpinUp", 
			MINIGUN_SPIN_DOWN_ID = "minigunSpinDown",
			MINIGUN_SHOT_ID = "minigunShot", 
			MINIGUN_OVERHEAT_ID = "minigunOverheat";
	public static void loadMinigunSounds() {
		minigun_spin_up = Utils.class.getResource("/sounds/gunSounds/minigun/minigun_spin-up.wav");
		minigun_spin_down = Utils.class.getResource("/sounds/gunSounds/minigun/minigun_spin-down.wav");
		minigun_shot = Utils.class.getResource("/sounds/gunSounds/minigun/minigun_firing.wav");
		minigun_overheat = Utils.class.getResource("/sounds/gunSounds/minigun/minigun_overheat.wav");
		Sounds.preloadClip(MINIGUN_SPIN_UP_ID, minigun_spin_up, DEFAULT_QUANTITY, 1);
		Sounds.preloadClip(MINIGUN_SPIN_DOWN_ID, minigun_spin_down, DEFAULT_QUANTITY, 1);
		Sounds.preloadClip(MINIGUN_SHOT_ID, minigun_shot, DEFAULT_QUANTITY, 1);
		Sounds.preloadClip(MINIGUN_OVERHEAT_ID, minigun_overheat, DEFAULT_QUANTITY, 1);
		Sounds.preloadClip(MINIGUN_FAILSAFE_RELOAD_ID, minigun_overheat, DEFAULT_QUANTITY, SPEED_1);
	}
	
	//m1 garand
	public static URL m1_garand_shot, m1_garand_reload, m1_garand_ding;
	public final static String M1_GARAND_SHOT_ID = "m1_garandShot", M1_GARAND_RELOAD_ID = "m1_garandReload";
	public final static String M1_GARAND_RELOAD_0_ID = "m1_garandReload0";
	public final static String M1_GARAND_RELOAD_1_ID = "m1_garandReload1";
	public final static String M1_GARAND_RELOAD_2_ID = "m1_garandReload2";
	public final static String M1_GARAND_RELOAD_3_ID = "m1_garandReload3";
	public final static String M1_GARAND_DING_ID = "m1_garandDing";
	public static void loadM1GarandSounds() {
		m1_garand_shot = Utils.class.getResource("/sounds/gunSounds/m1_garand/m1_garand_shot.wav");
		m1_garand_reload = Utils.class.getResource("/sounds/gunSounds/m1_garand/m1_garand_reload.wav");
		m1_garand_ding = Utils.class.getResource("/sounds/gunSounds/m1_garand/m1_garand_ping.wav");
		Sounds.preloadClip(M1_GARAND_SHOT_ID, m1_garand_shot, DEFAULT_QUANTITY, 1);
		Sounds.preloadClip(M1_GARAND_RELOAD_ID, m1_garand_reload, DEFAULT_QUANTITY, 1);
		Sounds.preloadClip(M1_GARAND_RELOAD_0_ID, m1_garand_reload, DEFAULT_QUANTITY, SPEED_BASE);
		Sounds.preloadClip(M1_GARAND_RELOAD_1_ID, m1_garand_reload, DEFAULT_QUANTITY, SPEED_1);
		Sounds.preloadClip(M1_GARAND_RELOAD_2_ID, m1_garand_reload, DEFAULT_QUANTITY, SPEED_2);
		Sounds.preloadClip(M1_GARAND_RELOAD_3_ID, m1_garand_reload, DEFAULT_QUANTITY, SPEED_3);
		Sounds.preloadClip(M1_GARAND_DING_ID, m1_garand_ding, DEFAULT_QUANTITY, 1);
	}
	
	//arisaka
	public static URL arisaka_shot, arisaka_reload;
	public final static String ARISAKA_SHOT_ID = "arisakaShot", ARISAKA_RELOAD_ID = "arisakaReload";
	public final static String ARISAKA_RELOAD_0_ID = "arisakaReload0";
	public final static String ARISAKA_RELOAD_1_ID = "arisakaReload1";
	public final static String ARISAKA_RELOAD_2_ID = "arisakaReload2";
	public final static String ARISAKA_RELOAD_3_ID = "arisakaReload3";
	public static void loadArisakaSounds() {
		arisaka_shot = Utils.class.getResource("/sounds/gunSounds/arisaka/arisaka_shot.wav");
		arisaka_reload = Utils.class.getResource("/sounds/gunSounds/arisaka/arisaka_reload.wav");
		Sounds.preloadClip(ARISAKA_SHOT_ID, arisaka_shot, DEFAULT_QUANTITY, 1);
		Sounds.preloadClip(ARISAKA_RELOAD_ID, arisaka_reload, DEFAULT_QUANTITY, 1);
		Sounds.preloadClip(ARISAKA_RELOAD_0_ID, arisaka_reload, DEFAULT_QUANTITY, SPEED_BASE);
		Sounds.preloadClip(ARISAKA_RELOAD_1_ID, arisaka_reload, DEFAULT_QUANTITY, SPEED_1);
		Sounds.preloadClip(ARISAKA_RELOAD_2_ID, arisaka_reload, DEFAULT_QUANTITY, SPEED_2);
		Sounds.preloadClip(ARISAKA_RELOAD_3_ID, arisaka_reload, DEFAULT_QUANTITY, SPEED_3);
	}
	
	//bren
	public static URL bren_shot, bren_reload;
	public final static String BREN_SHOT_ID = "brenShot", BREN_RELOAD_ID = "brenReload";
	public final static String BREN_RELOAD_0_ID = "brenReload0";
	public final static String BREN_RELOAD_1_ID = "brenReload1";
	public final static String BREN_RELOAD_2_ID = "brenReload2";
	public final static String BREN_RELOAD_3_ID = "brenReload3";
	public static void loadBrenSounds() {
		bren_shot = Utils.class.getResource("/sounds/gunSounds/bren/bren_shot.wav");
		bren_reload = Utils.class.getResource("/sounds/gunSounds/bren/bren_reload.wav");
		Sounds.preloadClip(BREN_SHOT_ID, bren_shot, DEFAULT_QUANTITY, 1);
		Sounds.preloadClip(BREN_RELOAD_ID, bren_reload, DEFAULT_QUANTITY, 1);
		Sounds.preloadClip(BREN_RELOAD_0_ID, bren_reload, DEFAULT_QUANTITY, SPEED_BASE);
		Sounds.preloadClip(BREN_RELOAD_1_ID, bren_reload, DEFAULT_QUANTITY, SPEED_1);
		Sounds.preloadClip(BREN_RELOAD_2_ID, bren_reload, DEFAULT_QUANTITY, SPEED_2);
		Sounds.preloadClip(BREN_RELOAD_3_ID, bren_reload, DEFAULT_QUANTITY, SPEED_3);
	}
	
	//double barrel
	public static URL db_shot, db_open, db_shell, db_close;
	public final static String DB_SHOT_ID = "dbShot";
	public final static String DB_OPEN_ID = "dbOpen";
	public final static String DB_OPEN_0_ID = "dbOpen0";
	public final static String DB_OPEN_1_ID = "dbOpen1";
	public final static String DB_OPEN_2_ID = "dbOpen2";
	public final static String DB_OPEN_3_ID = "dbOpen3";
	public final static String DB_SHELL_ID = "dbShell";
	public final static String DB_SHELL_0_ID = "dbShell0";
	public final static String DB_SHELL_1_ID = "dbShell1";
	public final static String DB_SHELL_2_ID = "dbShell2";
	public final static String DB_SHELL_3_ID = "dbShell3";
	public final static String DB_CLOSE_ID = "dbClose";
	public final static String DB_CLOSE_0_ID = "dbClose0";
	public final static String DB_CLOSE_1_ID = "dbClose1";
	public final static String DB_CLOSE_2_ID = "dbClose2";
	public final static String DB_CLOSE_3_ID = "dbClose3";
	public static void loadDoubleBarrelSounds() {
		db_shot = Utils.class.getResource("/sounds/gunSounds/doubleBarrel/db_shot.wav");
		db_open = Utils.class.getResource("/sounds/gunSounds/doubleBarrel/db_open.wav");
		db_shell = Utils.class.getResource("/sounds/gunSounds/doubleBarrel/db_shell.wav");
		db_close = Utils.class.getResource("/sounds/gunSounds/doubleBarrel/db_close.wav");
		Sounds.preloadClip(DB_SHOT_ID, db_shot, DEFAULT_QUANTITY, 1);
		Sounds.preloadClip(DB_OPEN_ID, db_open, DEFAULT_QUANTITY, 1);
		Sounds.preloadClip(DB_OPEN_0_ID, db_open, DEFAULT_QUANTITY, SPEED_BASE);
		Sounds.preloadClip(DB_OPEN_1_ID, db_open, DEFAULT_QUANTITY, SPEED_1);
		Sounds.preloadClip(DB_OPEN_2_ID, db_open, DEFAULT_QUANTITY, SPEED_2);
		Sounds.preloadClip(DB_OPEN_3_ID, db_open, DEFAULT_QUANTITY, SPEED_3);
		Sounds.preloadClip(DB_SHELL_ID, db_shell, DEFAULT_QUANTITY, 1);
		Sounds.preloadClip(DB_SHELL_0_ID, db_shell, DEFAULT_QUANTITY, SPEED_BASE);
		Sounds.preloadClip(DB_SHELL_1_ID, db_shell, DEFAULT_QUANTITY, SPEED_1);
		Sounds.preloadClip(DB_SHELL_2_ID, db_shell, DEFAULT_QUANTITY, SPEED_2);
		Sounds.preloadClip(DB_SHELL_3_ID, db_shell, DEFAULT_QUANTITY, SPEED_3);
		Sounds.preloadClip(DB_CLOSE_ID, db_close, DEFAULT_QUANTITY, 1);
		Sounds.preloadClip(DB_CLOSE_0_ID, db_close, DEFAULT_QUANTITY, SPEED_BASE);
		Sounds.preloadClip(DB_CLOSE_1_ID, db_close, DEFAULT_QUANTITY, SPEED_1);
		Sounds.preloadClip(DB_CLOSE_2_ID, db_close, DEFAULT_QUANTITY, SPEED_2);
		Sounds.preloadClip(DB_CLOSE_3_ID, db_close, DEFAULT_QUANTITY, SPEED_3);
	}
	
	//m16, uses m4 reload sounds
	public static URL m16_shot;
	public final static String M16_SHOT_ID = "m16Shot";
	public static void loadM16Sounds() {
		m16_shot = Utils.class.getResource("/sounds/gunSounds/m16/m16_burst.wav");
		Sounds.preloadClip(M16_SHOT_ID, m16_shot, DEFAULT_QUANTITY, 1);
	}
	
//	loadM1911Sounds();
	public static URL m1911_shot, m1911_reload;
	public final static String M1911_SHOT_ID = "m1911Shot", M1911_RELOAD_ID = "m1911Reload";
	public final static String M1911_RELOAD_0_ID = "m1911Reload0";
	public final static String M1911_RELOAD_1_ID = "m1911Reload1";
	public final static String M1911_RELOAD_2_ID = "m1911Reload2";
	public final static String M1911_RELOAD_3_ID = "m1911Reload3";
	public static void loadM1911Sounds() {
		m1911_shot = Utils.class.getResource("/sounds/gunSounds/m1911/m1911_shot.wav");
		m1911_reload = Utils.class.getResource("/sounds/gunSounds/m1911/m1911_reload.wav");
		Sounds.preloadClip(M1911_SHOT_ID, m1911_shot, DEFAULT_QUANTITY, 1);
		Sounds.preloadClip(M1911_RELOAD_ID, m1911_reload, DEFAULT_QUANTITY, 1);
		Sounds.preloadClip(M1911_RELOAD_0_ID, m1911_reload, DEFAULT_QUANTITY, SPEED_BASE);
		Sounds.preloadClip(M1911_RELOAD_1_ID, m1911_reload, DEFAULT_QUANTITY, SPEED_1);
		Sounds.preloadClip(M1911_RELOAD_2_ID, m1911_reload, DEFAULT_QUANTITY, SPEED_2);
		Sounds.preloadClip(M1911_RELOAD_3_ID, m1911_reload, DEFAULT_QUANTITY, SPEED_3);
	}
	
//	loadM60Sounds();
	public static URL m60_shot, m60_reload;
	public final static String M60_SHOT_ID = "m60Shot", M60_RELOAD_ID = "m60Reload";
	public final static String M60_RELOAD_0_ID = "m60Reload0";
	public final static String M60_RELOAD_1_ID = "m60Reload1";
	public final static String M60_RELOAD_2_ID = "m60Reload2";
	public final static String M60_RELOAD_3_ID = "m60Reload3";
	public static void loadM60Sounds() {
		m60_shot = Utils.class.getResource("/sounds/gunSounds/m60/m60_shot.wav");
		m60_reload = Utils.class.getResource("/sounds/gunSounds/m60/m60_reload.wav");
		Sounds.preloadClip(M60_SHOT_ID, m60_shot, DEFAULT_QUANTITY, 1);
		Sounds.preloadClip(M60_RELOAD_ID, m60_reload, DEFAULT_QUANTITY, 1);
		Sounds.preloadClip(M60_RELOAD_0_ID, m60_reload, DEFAULT_QUANTITY, SPEED_BASE);
		Sounds.preloadClip(M60_RELOAD_1_ID, m60_reload, DEFAULT_QUANTITY, SPEED_1);
		Sounds.preloadClip(M60_RELOAD_2_ID, m60_reload, DEFAULT_QUANTITY, SPEED_2);
		Sounds.preloadClip(M60_RELOAD_3_ID, m60_reload, DEFAULT_QUANTITY, SPEED_3);
	}
	
//	loadPythonSounds();
	public static URL python_shot, python_open, python_shell, python_close;
	public final static String PYTHON_SHOT_ID = "pythonShot";
	public final static String PYTHON_OPEN_ID = "pythonOpen";
	public final static String PYTHON_OPEN_0_ID = "pythonOpen0";
	public final static String PYTHON_OPEN_1_ID = "pythonOpen1";
	public final static String PYTHON_OPEN_2_ID = "pythonOpen2";
	public final static String PYTHON_OPEN_3_ID = "pythonOpen3";
	public final static String PYTHON_SHELL_ID = "pythonShell";
	public final static String PYTHON_SHELL_0_ID = "pythonShell0";
	public final static String PYTHON_SHELL_1_ID = "pythonShell1";
	public final static String PYTHON_SHELL_2_ID = "pythonShell2";
	public final static String PYTHON_SHELL_3_ID = "pythonShell3";
	public final static String PYTHON_CLOSE_ID = "pythonClose";
	public final static String PYTHON_CLOSE_0_ID = "pythonClose0";
	public final static String PYTHON_CLOSE_1_ID = "pythonClose1";
	public final static String PYTHON_CLOSE_2_ID = "pythonClose2";
	public final static String PYTHON_CLOSE_3_ID = "pythonClose3";
	public static void loadPythonSounds() {
		python_shot = Utils.class.getResource("/sounds/gunSounds/python/python_shot.wav");
		python_open = Utils.class.getResource("/sounds/gunSounds/python/python_open.wav");
		python_shell = Utils.class.getResource("/sounds/gunSounds/python/python_shell.wav");
		python_close = Utils.class.getResource("/sounds/gunSounds/python/python_close.wav");
		Sounds.preloadClip(PYTHON_SHOT_ID, python_shot, DEFAULT_QUANTITY, 1);
		Sounds.preloadClip(PYTHON_OPEN_ID, python_open, DEFAULT_QUANTITY, 1);
		Sounds.preloadClip(PYTHON_OPEN_0_ID, python_open, DEFAULT_QUANTITY, SPEED_BASE);
		Sounds.preloadClip(PYTHON_OPEN_1_ID, python_open, DEFAULT_QUANTITY, SPEED_1);
		Sounds.preloadClip(PYTHON_OPEN_2_ID, python_open, DEFAULT_QUANTITY, SPEED_2);
		Sounds.preloadClip(PYTHON_OPEN_3_ID, python_open, DEFAULT_QUANTITY, SPEED_3);
		Sounds.preloadClip(PYTHON_SHELL_ID, python_shell, DEFAULT_QUANTITY, 1);
		Sounds.preloadClip(PYTHON_SHELL_0_ID, python_shell, DEFAULT_QUANTITY, SPEED_BASE);
		Sounds.preloadClip(PYTHON_SHELL_1_ID, python_shell, DEFAULT_QUANTITY, SPEED_1);
		Sounds.preloadClip(PYTHON_SHELL_2_ID, python_shell, DEFAULT_QUANTITY, SPEED_2);
		Sounds.preloadClip(PYTHON_SHELL_3_ID, python_shell, DEFAULT_QUANTITY, SPEED_3);
		Sounds.preloadClip(PYTHON_CLOSE_ID, python_close, DEFAULT_QUANTITY, 1);
		Sounds.preloadClip(PYTHON_CLOSE_0_ID, python_close, DEFAULT_QUANTITY, SPEED_BASE);
		Sounds.preloadClip(PYTHON_CLOSE_1_ID, python_close, DEFAULT_QUANTITY, SPEED_1);
		Sounds.preloadClip(PYTHON_CLOSE_2_ID, python_close, DEFAULT_QUANTITY, SPEED_2);
		Sounds.preloadClip(PYTHON_CLOSE_3_ID, python_close, DEFAULT_QUANTITY, SPEED_3);
	}
	
//	loadType100Sounds();
	public static URL type100_shot, type100_reload;
	public final static String TYPE100_SHOT_ID = "type100Shot", TYPE100_RELOAD_ID = "type100Reload";
	public final static String TYPE100_RELOAD_0_ID = "type100Reload0";
	public final static String TYPE100_RELOAD_1_ID = "type100Reload1";
	public final static String TYPE100_RELOAD_2_ID = "type100Reload2";
	public final static String TYPE100_RELOAD_3_ID = "type100Reload3";
	public static void loadType100Sounds() {
		type100_shot = Utils.class.getResource("/sounds/gunSounds/type100/type100_shot.wav");
		type100_reload = Utils.class.getResource("/sounds/gunSounds/type100/type100_reload.wav");
		Sounds.preloadClip(TYPE100_SHOT_ID, type100_shot, DEFAULT_QUANTITY, 1);
		Sounds.preloadClip(TYPE100_RELOAD_ID, type100_reload, DEFAULT_QUANTITY, 1);
		Sounds.preloadClip(TYPE100_RELOAD_0_ID, type100_reload, DEFAULT_QUANTITY, SPEED_BASE);
		Sounds.preloadClip(TYPE100_RELOAD_1_ID, type100_reload, DEFAULT_QUANTITY, SPEED_1);
		Sounds.preloadClip(TYPE100_RELOAD_2_ID, type100_reload, DEFAULT_QUANTITY, SPEED_2);
		Sounds.preloadClip(TYPE100_RELOAD_3_ID, type100_reload, DEFAULT_QUANTITY, SPEED_3);
	}
	
//	loadThompsonSounds();
	public static URL thompson_shot, thompson_reload;
	public final static String THOMPSON_SHOT_ID = "thompsonShot", THOMPSON_RELOAD_ID = "thompsonReload";
	public final static String THOMPSON_RELOAD_0_ID = "thompsonReload0";
	public final static String THOMPSON_RELOAD_1_ID = "thompsonReload1";
	public final static String THOMPSON_RELOAD_2_ID = "thompsonReload2";
	public final static String THOMPSON_RELOAD_3_ID = "thompsonReload3";
	public static void loadThompsonSounds() {
		thompson_shot = Utils.class.getResource("/sounds/gunSounds/thompson/thompson_shot.wav");
		thompson_reload = Utils.class.getResource("/sounds/gunSounds/thompson/thompson_reload.wav");
		Sounds.preloadClip(THOMPSON_SHOT_ID, thompson_shot, DEFAULT_QUANTITY, 1);
		Sounds.preloadClip(THOMPSON_RELOAD_ID, thompson_reload, DEFAULT_QUANTITY, 1);
		Sounds.preloadClip(THOMPSON_RELOAD_0_ID, thompson_reload, DEFAULT_QUANTITY, SPEED_BASE);
		Sounds.preloadClip(THOMPSON_RELOAD_1_ID, thompson_reload, DEFAULT_QUANTITY, SPEED_1);
		Sounds.preloadClip(THOMPSON_RELOAD_2_ID, thompson_reload, DEFAULT_QUANTITY, SPEED_2);
		Sounds.preloadClip(THOMPSON_RELOAD_3_ID, thompson_reload, DEFAULT_QUANTITY, SPEED_3);
	}
	
//	loadUziSounds();
	public static URL uzi_shot, uzi_reload;
	public final static String UZI_SHOT_ID = "uziShot", UZI_RELOAD_ID = "uziReload";
	public final static String UZI_RELOAD_0_ID = "uziReload0";
	public final static String UZI_RELOAD_1_ID = "uziReload1";
	public final static String UZI_RELOAD_2_ID = "uziReload2";
	public final static String UZI_RELOAD_3_ID = "uziReload3";
	public static void loadUziSounds() {
		uzi_shot = Utils.class.getResource("/sounds/gunSounds/uzi/uzi_shot.wav");
		uzi_reload = Utils.class.getResource("/sounds/gunSounds/uzi/uzi_reload.wav");
		Sounds.preloadClip(UZI_SHOT_ID, uzi_shot, DEFAULT_QUANTITY, 1);
		Sounds.preloadClip(UZI_RELOAD_ID, uzi_reload, DEFAULT_QUANTITY, 1);
		Sounds.preloadClip(UZI_RELOAD_0_ID, uzi_reload, DEFAULT_QUANTITY, SPEED_BASE);
		Sounds.preloadClip(UZI_RELOAD_1_ID, uzi_reload, DEFAULT_QUANTITY, SPEED_1);
		Sounds.preloadClip(UZI_RELOAD_2_ID, uzi_reload, DEFAULT_QUANTITY, SPEED_2);
		Sounds.preloadClip(UZI_RELOAD_3_ID, uzi_reload, DEFAULT_QUANTITY, SPEED_3);
	}
}
