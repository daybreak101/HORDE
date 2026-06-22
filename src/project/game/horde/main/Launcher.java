package project.game.horde.main;

import java.awt.Dimension;
import java.awt.Toolkit;

public class Launcher {
	public static void main(String[] args) {



		
		float targetWidth = 1920;
		float targetHeight = 1080;
		//resolution
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		float screenWidth = screenSize.width;
		float screenHeight = screenSize.height;
		System.out.println("screen width:" + screenSize.width);
        System.out.println("screen height: " + screenSize.height);
        
        
		System.out.println("new width: " + Math.round(screenWidth * 1000/targetWidth));
		System.out.println("new height: " + Math.round(screenHeight * 800/targetHeight));

		Game game = new Game("HORDE", 
				Math.round(screenWidth/targetWidth * 1000),
				Math.round(screenHeight/targetHeight * 800));

//		new Game("HORDE", 
//		1000,
//		800);
	}
}
