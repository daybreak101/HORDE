package project.game.horde.sounds;

import java.net.URL;

import project.game.horde.main.Handler;
import project.game.horde.utils.Utils;

public class PowerupSounds {
	public static URL nukePickedUp;
	
    public static final String NUKE_PICKED_UP_ID = "nukePickedUp";

	
	public static void init(Handler handler) {
		nukePickedUp = Utils.class.getResource("/sounds/powerups/Nuke-pickup-confirm.wav");
	
		Sounds.preloadClip(NUKE_PICKED_UP_ID, nukePickedUp, 1, 1);
	}
}
