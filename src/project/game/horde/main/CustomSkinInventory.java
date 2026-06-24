package project.game.horde.main;

import java.awt.image.BufferedImage;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;

import project.game.horde.graphics.CharAssets;
import project.game.horde.utils.Utils;
import project.game.horde.utils.saved.SaveFileReader;
import project.game.horde.utils.saved.SaveFileUtils;
import project.game.horde.utils.saved.SaveFileWriter;

public class CustomSkinInventory {
	public Handler handler;
	public static HashMap<String, Integer> inventory = new HashMap<>();
	
	public static final int COMMON = 0, RARE = 1, EPIC = 2, LEGENDARY = 3;
	
	public static final int HARRY = 0,
			ROBOT = 1,
			BLUE_ALIEN = 2;
	
	private int equipped = HARRY;
	
	private int robot = 0, 
			blueAlien = 0;
	
	public static BufferedImage[] getSkinImage(int skin) {
            return switch (skin) {
                case ROBOT -> CharAssets.robot;
                case BLUE_ALIEN -> CharAssets.blueAlien;
                default -> CharAssets.harry;
            };
	}
	
	public BufferedImage[] getSkin(int skin) {
            return switch (skin) {
                case ROBOT -> CharAssets.robot;
                case BLUE_ALIEN -> CharAssets.blueAlien;
                default -> CharAssets.harry;
            };
	}
	
	public void unlockSkin(int skin) {
		switch(skin) {
		case ROBOT -> robot = 1;
		case BLUE_ALIEN -> blueAlien = 1;
		}
	}
	
	public boolean setSkin(int skin) {
		equipped = skin;
		return true;
	}
	
	public int getEquippedSkin() {
		return equipped;
	}
	
	public CustomSkinInventory(Handler handler) {
		this.handler = handler;
		String unlockData;
		String unlockFilePath = Handler.SAVE_FOLDER + File.separator + Handler.CUSTOMSKIN_FILE;

		if (!SaveFileUtils.fileExists(Handler.SAVE_FOLDER, Handler.CUSTOMSKIN_FILE)) {
			// Create a new save file with default data
			unlockData = "0\n0";
			SaveFileWriter.writeToFile(Handler.SAVE_FOLDER, Handler.CUSTOMSKIN_FILE, unlockData);
		} else {
			// Load existing save file
			unlockData = SaveFileReader.readFromFile(Handler.SAVE_FOLDER, Handler.CUSTOMSKIN_FILE);
		}
		readUnlockedSkins(unlockData);
	}

	private void readUnlockedSkins(String file) {
		String[] tokens = file.split("[\\n\\s]+");
		if (tokens.length == 0) {
			robot = 0;
			blueAlien = 0;
		} else {
			robot = Utils.parseInt(tokens[0]);
			blueAlien = Utils.parseInt(tokens[1]);
		}
	}

	public void writeToFile() {
		String saveFolderPath = System.getProperty("user.home") + File.separator + "Documents" + File.separator
				+ Handler.SAVE_FOLDER;
		String unlocksFilePath = saveFolderPath + File.separator + Handler.CUSTOMSKIN_FILE;

		try (BufferedWriter writer = new BufferedWriter(new FileWriter(unlocksFilePath))) {
			writer.write(Integer.toString(robot));
			writer.newLine();
			writer.write(Integer.toString(blueAlien));
			writer.newLine();
		} catch (IOException e) {
		}
	}

}
