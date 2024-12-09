package project.game.horde.sounds;
import java.net.URL;

import project.game.horde.entities.Entity;
import project.game.horde.main.Handler;
import project.game.horde.utils.Utils;

public class CreatureSounds {
	public static URL auroraEnter, auroraBark, auroraLeave, auroraGrowl;
	public static final String AURORA_ENTER = "auroraEnter",
							   AURORA_BARK = "auroraBark",
							   AURORA_LEAVE = "auroraLeave",
							   AURORA_GROWL = "auroraGrowl";
	public static void init(Handler handler) {
		auroraEnter = Utils.class.getResource("/sounds/aurora/aurora_enter.wav");
		auroraBark = Utils.class.getResource("/sounds/aurora/aurora_bark.wav");
		auroraLeave = Utils.class.getResource("/sounds/aurora/aurora_leave.wav");
		auroraGrowl = Utils.class.getResource("/sounds/aurora/aurora_growl.wav");
		Sounds.preloadClip(AURORA_ENTER, auroraEnter, 1, 1);
		Sounds.preloadClip(AURORA_BARK, auroraBark, 1, 1);
		Sounds.preloadClip(AURORA_LEAVE, auroraLeave, 1, 1);
		Sounds.preloadClip(AURORA_GROWL, auroraGrowl, 1, 1);

	}
}
