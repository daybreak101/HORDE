package project.game.horde.sounds;


import java.net.URL;

import project.game.horde.main.Handler;
import project.game.horde.utils.Utils;

public class MenuSounds {
	public static URL coinsPurchase, menuButtonClicks, menuButtonDenied;
	public static final String COINS_PURCHASE_ID = "coinsPurchase",
							   MENU_BUTTON_CLICKS_ID = "menuButtonClicks",
							   MENU_BUTTON_DENIED_ID = "menuButtonDenied";
	
	public static void init(Handler handler) {
		coinsPurchase = Utils.class.getResource("/sounds/menu/coins-purchase.wav");
		menuButtonClicks = Utils.class.getResource("/sounds/menu/menuButtonClicks.wav");
		menuButtonDenied = Utils.class.getResource("/sounds/menu/menuButtonDenied.wav");

		Sounds.preloadClip(COINS_PURCHASE_ID, coinsPurchase, 2, 1);
		Sounds.preloadClip(MENU_BUTTON_CLICKS_ID, menuButtonClicks, 5, 1);
		Sounds.preloadClip(MENU_BUTTON_DENIED_ID, menuButtonDenied, 5, 1);

	}
}
