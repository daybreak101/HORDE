package project.game.horde.sounds;
import java.net.URL;

import project.game.horde.main.Handler;
import project.game.horde.utils.Utils;

public class CreatureSounds {
	public static URL auroraEnter, auroraBark, auroraLeave, auroraGrowl,
					  heartbeat;
	public static final String AURORA_ENTER = "auroraEnter",
							   AURORA_BARK = "auroraBark",
							   AURORA_LEAVE = "auroraLeave",
							   AURORA_GROWL = "auroraGrowl",
							   SLOW_HEARTBEAT = "slowHeartbeat",
							   FAST_HEARTBEAT = "fastHeartbeat";
	
	public static void init(Handler handler) {
		auroraEnter = Utils.class.getResource("/sounds/aurora/aurora_enter.wav");
		auroraBark = Utils.class.getResource("/sounds/aurora/aurora_bark.wav");
		auroraLeave = Utils.class.getResource("/sounds/aurora/aurora_leave.wav");
		auroraGrowl = Utils.class.getResource("/sounds/aurora/aurora_growl.wav");
		//use audacity to change from mp3 to wav. mp3 doesn't seem to be supported.
		heartbeat = Utils.class.getResource("/sounds/player/heartbeat.wav");
		Sounds.preloadClip(AURORA_ENTER, auroraEnter, 1, 1);
		Sounds.preloadClip(AURORA_BARK, auroraBark, 1, 1);
		Sounds.preloadClip(AURORA_LEAVE, auroraLeave, 1, 1);
		Sounds.preloadClip(AURORA_GROWL, auroraGrowl, 1, 1);
		Sounds.preloadClip(SLOW_HEARTBEAT, heartbeat, 1, 1);
		Sounds.preloadClip(FAST_HEARTBEAT, heartbeat, 1, 1.5f);

	}
}
