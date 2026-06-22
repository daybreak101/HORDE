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

public class CustomHatInventory {
	public Handler handler;
	public static HashMap<String, Integer> inventory = new HashMap<String, Integer>();
	
	public static final int COMMON = 0, RARE = 1, EPIC = 2, LEGENDARY = 3;
	
	public static final int NONE = 0,
			CHRISTMAS = 1,
			REINDEER = 2,
			BUNNY = 3;
	
	private int equipped = NONE;
	
	private int christmas = 0, 
			reindeer = 0,
			bunny = 0;
	
	public static BufferedImage getHatImage(int hat) {
		switch(hat) {
		case CHRISTMAS:
			return CharAssets.christmasHat;
		case REINDEER:
			return CharAssets.reindeer;
		case BUNNY:
			return CharAssets.bunny;
		default:
			return null;
		}
	}
	
	public BufferedImage getHat(int hat) {
		switch(hat) {
		case CHRISTMAS:
			return CharAssets.christmasHat;
		case REINDEER:
			return CharAssets.reindeer;
		case BUNNY:
			return CharAssets.bunny;
		default:
			return null;
		}
	}
	
	public void unlockHat(int hat) {
		switch(hat) {
		case CHRISTMAS:
			christmas = 1;
			break;
		case REINDEER:
			reindeer = 1;
			break;
		case BUNNY:
			bunny = 1;
			break;
		default:
			break;
		}
	}
	
	public boolean setHat(int hat) {
		equipped = hat;
		return true;
	}
	
	public int getEquippedHat() {
		return equipped;
	}
	
	public CustomHatInventory(Handler handler) {
		this.handler = handler;
		String unlockData;
		String unlockFilePath = Handler.SAVE_FOLDER + File.separator + Handler.CUSTOMHAT_FILE;

		if (!SaveFileUtils.fileExists(Handler.SAVE_FOLDER, Handler.CUSTOMHAT_FILE)) {
			// Create a new save file with default data
			unlockData = "0\n0\n0";
			SaveFileWriter.writeToFile(Handler.SAVE_FOLDER, Handler.CUSTOMHAT_FILE, unlockData);
		} else {
			// Load existing save file
			unlockData = SaveFileReader.readFromFile(Handler.SAVE_FOLDER, Handler.CUSTOMHAT_FILE);
		}
		readUnlockedHats(unlockData);
	}

	private void readUnlockedHats(String file) {
		String[] tokens = file.split("[\\n\\s]+");
		if (tokens.length == 0) {
			christmas = 0;
			reindeer = 0;
			bunny = 0;
		} else {
			christmas = Utils.parseInt(tokens[0]);
			reindeer = Utils.parseInt(tokens[1]);
			bunny = Utils.parseInt(tokens[2]);
		}
	}

	public void writeToFile() {
		String saveFolderPath = System.getProperty("user.home") + File.separator + "Documents" + File.separator
				+ Handler.SAVE_FOLDER;
		String unlocksFilePath = saveFolderPath + File.separator + Handler.CUSTOMHAT_FILE;

		try (BufferedWriter writer = new BufferedWriter(new FileWriter(unlocksFilePath))) {
			writer.write(Integer.toString(christmas));
			writer.newLine();
			writer.write(Integer.toString(reindeer));
			writer.newLine();
			writer.write(Integer.toString(bunny));
			writer.newLine();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
