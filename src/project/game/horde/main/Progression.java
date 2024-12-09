package project.game.horde.main;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import project.game.horde.hud.GoldenCoinNotification;
import project.game.horde.hud.LevelUpNotification;
import project.game.horde.sounds.MenuSounds;
import project.game.horde.sounds.Sounds;
import project.game.horde.utils.Utils;
import project.game.horde.utils.saved.SaveFileReader;
import project.game.horde.utils.saved.SaveFileUtils;
import project.game.horde.utils.saved.SaveFileWriter;

public class Progression {
	private Handler handler;
	private long xp;
	private int level;
	private int coins;
	private long xpNeeded;

	public Progression(Handler handler) {
		this.handler = handler;
		xp = 0;
		level = 1;
		coins = 0;
		String progressionData;
		String progressionFilePath = Handler.SAVE_FOLDER + File.separator + Handler.PROGRESSION_FILE;

		if (!SaveFileUtils.fileExists(Handler.SAVE_FOLDER, Handler.PROGRESSION_FILE)) {
			// Create a new save file with default data
			progressionData = "0\n1\n0";
			SaveFileWriter.writeToFile(Handler.SAVE_FOLDER, Handler.PROGRESSION_FILE, progressionData);
			//System.out.println("Save file created with default data.");
		} else {
			// Load existing save file
			progressionData = SaveFileReader.readFromFile(Handler.SAVE_FOLDER, Handler.PROGRESSION_FILE);
			//System.out.println("Loaded Game Data:");
			//System.out.println(progressionData);
		}

		readProgression(progressionData);
		calculateXPNeeded();
	}

	private void readProgression(String file) {
		// String file = Utils.loadFileAsString("/info/progression.txt");
		// String[] tokens = file.split("\\s+");
		String[] tokens = file.split("[\\n\\s]+");
		if (tokens.length == 0) {
			xp = 0;
			level = 1;
			coins = 0;
		} else {
			xp = Utils.parseInt(tokens[0]);
			level = Utils.parseInt(tokens[1]);
			coins = Utils.parseInt(tokens[2]);
		}
	}

//	public void writeToFile() {
//		try {
//			FileWriter writer = new FileWriter("res/info/progression.txt");
//			BufferedWriter buffer = new BufferedWriter(writer);
//
//			buffer.write(Long.toString(xp));
//			buffer.newLine();
//			buffer.write(Integer.toString(level));
//			buffer.newLine();
//			buffer.write(Integer.toString(coins));
//
//			buffer.close();
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
//	}
	public void writeToFile() {
		String saveFolderPath = System.getProperty("user.home") + File.separator + "Documents" + File.separator
				+ Handler.SAVE_FOLDER;
		String progressionFilePath = saveFolderPath + File.separator + Handler.PROGRESSION_FILE;

		try (BufferedWriter writer = new BufferedWriter(new FileWriter(progressionFilePath))) {
			writer.write(Long.toString(xp));
			writer.newLine();
			writer.write(Integer.toString(level));
			writer.newLine();
			writer.write(Integer.toString(coins));
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	// 100 per kill
	// 150 per headshot kill
	// 5 repair barricade
	// 100 * round survived
	public void gainXP(int xp) {
		this.xp += xp;
		checkLevel();
		writeToFile();
	}

	private void checkLevel() {
		if (xp >= xpNeeded) {
			level++;
			coins++;
			xp = xp - xpNeeded;
			calculateXPNeeded();
			checkLevel();
			handler.getCurrentPlayer().getHud().addNotifToQueue(new LevelUpNotification(handler, level));
			handler.getCurrentPlayer().getHud().addNotifToQueue(new GoldenCoinNotification(handler, 1, "Level Up Reward"));
		}
	}
	
	public void earnCoins(int amount) {
		coins += amount;
		handler.getCurrentPlayer().getHud().addNotifToQueue(new GoldenCoinNotification(handler, amount, "Milestone Reached"));
		writeToFile();

	}

	public boolean useGoldenCoins(int amount) {
		if (amount <= coins) {
			Sounds.playClip(MenuSounds.COINS_PURCHASE_ID, 1, 1, false);
			coins -= amount;
			writeToFile();
			return true;
		} else {
			return false;
		}

	}

	private void calculateXPNeeded() {
		xpNeeded = (long) (1000 * Math.pow(level, 1.1) + 1000);
		//xpNeeded = 100 * level;
	}

	public int getCoins() {
		return coins;
	}

	public int getLevel() {
		return level;
	}

	public long getXP() {
		return xp;
	}

	public long getXPNeeded() {
		return xpNeeded;
	}

}
