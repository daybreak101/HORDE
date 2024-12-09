package project.game.horde.main;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import project.game.horde.utils.Utils;
import project.game.horde.utils.saved.SaveFileReader;
import project.game.horde.utils.saved.SaveFileUtils;
import project.game.horde.utils.saved.SaveFileWriter;

public class Unlocks {
	private Handler handler;
	private int deadshotLvl = 0, doubletapLvl = 0, juggLvl = 0, lunaLvl = 0, muleLvl = 0, phdLvl = 0, reviveLvl = 0,
			speedLvl = 0, staminaLvl = 0, strongholdLvl = 0, vampireLvl = 0;

	public Unlocks(Handler handler) {
		this.handler = handler;
		String unlockData;
		String unlockFilePath = Handler.SAVE_FOLDER + File.separator + Handler.UNLOCKS_FILE;

		if (!SaveFileUtils.fileExists(Handler.SAVE_FOLDER, Handler.UNLOCKS_FILE)) {
			// Create a new save file with default data
			unlockData = "0\n0\n0\n0\n0\n0\n0\n0\n0\n0\n0";
			SaveFileWriter.writeToFile(Handler.SAVE_FOLDER, Handler.UNLOCKS_FILE, unlockData);
			//System.out.println("Unlocks file created with default data.");
		} else {
			// Load existing save file
			unlockData = SaveFileReader.readFromFile(Handler.SAVE_FOLDER, Handler.UNLOCKS_FILE);
			//System.out.println("Loaded Unlocks Data:");
			//System.out.println(unlockData);
		}

		readUnlocks(unlockData);
	}

	private void readUnlocks(String file) {
		// String file = Utils.loadFileAsString("/info/progression.txt");
		// String[] tokens = file.split("\\s+");
		String[] tokens = file.split("[\\n\\s]+");
		if (tokens.length == 0) {
			deadshotLvl = 0;
			doubletapLvl = 0;
			juggLvl = 0;
			lunaLvl = 0;
			muleLvl = 0;
			phdLvl = 0;
			reviveLvl = 0;
			speedLvl = 0;
			staminaLvl = 0;
			strongholdLvl = 0;
			vampireLvl = 0;
		} else {
			deadshotLvl = Utils.parseInt(tokens[0]);
			doubletapLvl = Utils.parseInt(tokens[1]);
			juggLvl = Utils.parseInt(tokens[2]);
			lunaLvl = Utils.parseInt(tokens[3]);
			muleLvl = Utils.parseInt(tokens[4]);
			phdLvl = Utils.parseInt(tokens[5]);
			reviveLvl = Utils.parseInt(tokens[6]);
			speedLvl = Utils.parseInt(tokens[7]);
			staminaLvl = Utils.parseInt(tokens[8]);
			strongholdLvl = Utils.parseInt(tokens[9]);
			vampireLvl = Utils.parseInt(tokens[10]);

		}
	}

	public void writeToFile() {
		String saveFolderPath = System.getProperty("user.home") + File.separator + "Documents" + File.separator
				+ Handler.SAVE_FOLDER;
		String unlocksFilePath = saveFolderPath + File.separator + Handler.UNLOCKS_FILE;

		try (BufferedWriter writer = new BufferedWriter(new FileWriter(unlocksFilePath))) {
			writer.write(Long.toString(deadshotLvl));
			writer.newLine();
			writer.write(Integer.toString(doubletapLvl));
			writer.newLine();
			writer.write(Integer.toString(juggLvl));
			writer.newLine();	
			writer.write(Integer.toString(lunaLvl));
			writer.newLine();
			writer.write(Long.toString(muleLvl));
			writer.newLine();
			writer.write(Integer.toString(phdLvl));
			writer.newLine();
			writer.write(Integer.toString(reviveLvl));
			writer.newLine();
			writer.write(Long.toString(speedLvl));
			writer.newLine();
			writer.write(Integer.toString(staminaLvl));
			writer.newLine();
			writer.write(Long.toString(strongholdLvl));
			writer.newLine();
			writer.write(Integer.toString(vampireLvl));
			writer.newLine();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public int getDeadshotLvl() {
		return deadshotLvl;
	}

	public void setDeadshotLvl(int deadshotLvl) {
		this.deadshotLvl = deadshotLvl;
		writeToFile();
	}

	public int getDoubletapLvl() {
		return doubletapLvl;
	}

	public void setDoubletapLvl(int doubletapLvl) {
		this.doubletapLvl = doubletapLvl;
		writeToFile();
	}

	public int getJuggLvl() {
		return juggLvl;
	}

	public void setJuggLvl(int juggLvl) {
		this.juggLvl = juggLvl;
		writeToFile();
	}

	public int getLunaLvl() {
		return lunaLvl;
	}

	public void setLunaLvl(int lunaLvl) {
		this.lunaLvl = lunaLvl;
		writeToFile();
	}

	public int getMuleLvl() {
		return muleLvl;
	}

	public void setMuleLvl(int muleLvl) {
		this.muleLvl = muleLvl;
		writeToFile();
	}

	public int getPhdLvl() {
		return phdLvl;
	}

	public void setPhdLvl(int phdLvl) {
		this.phdLvl = phdLvl;
		writeToFile();
	}

	public int getReviveLvl() {
		return reviveLvl;
	}

	public void setReviveLvl(int reviveLvl) {
		this.reviveLvl = reviveLvl;
		writeToFile();
	}

	public int getSpeedLvl() {
		return speedLvl;
	}

	public void setSpeedLvl(int speedLvl) {
		this.speedLvl = speedLvl;
		writeToFile();
	}

	public int getStaminaLvl() {
		return staminaLvl;
	}

	public void setStaminaLvl(int staminaLvl) {
		this.staminaLvl = staminaLvl;
		writeToFile();
	}

	public int getStrongholdLvl() {
		return strongholdLvl;
	}

	public void setStrongholdLvl(int strongholdLvl) {
		this.strongholdLvl = strongholdLvl;
		writeToFile();
	}

	public int getVampireLvl() {
		return vampireLvl;
	}

	public void setVampireLvl(int vampireLvl) {
		this.vampireLvl = vampireLvl;
		writeToFile();
	}

}
