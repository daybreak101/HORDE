package project.game.horde.sounds;

import java.net.URL;

import project.game.horde.main.Handler;
import project.game.horde.utils.Utils;

public class Music {
	public static URL farmOutro;
	
	public static final String FARM_OUTRO_ID = "farmOutro";
	
	public static void init(Handler handler) {
		farmOutro = Utils.class.getResource("/music/Farm-Outro-Music.wav");
		
		Sounds.preloadClip(FARM_OUTRO_ID, farmOutro, 1, 1);
	}
}
