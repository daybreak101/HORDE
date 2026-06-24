package project.game.horde.main;

import java.awt.Color;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import project.game.horde.ui.ColorIndex;
import project.game.horde.utils.Utils;
import project.game.horde.utils.saved.SaveFileReader;
import project.game.horde.utils.saved.SaveFileUtils;
import project.game.horde.utils.saved.SaveFileWriter;

public class Settings {

	private Handler handler;
	private double zoomLevel;
	private boolean gore, zombieCounter, toggleCrits, toggleDamage, healthBar;
	private Color laserColor;
	private Color hudColor;
	private float masterVolume;
	private int displayType;

	public Settings(Handler handler) {
		this.handler = handler;
		// default settings
		displayType = 0;
		zoomLevel = 1;
		gore = true;
		zombieCounter = false;
		toggleCrits = false;
		toggleDamage = false;
		healthBar = false;
		laserColor = Color.red;
		hudColor = Color.green;
		masterVolume = 10;

		String settingsData;
		String settingsFilePath = Handler.SAVE_FOLDER + File.separator + Handler.SETTINGS_FILE;

		if (!SaveFileUtils.fileExists(Handler.SAVE_FOLDER, Handler.SETTINGS_FILE)) {
			// Create a new save file with default data
			settingsData = "0\n0\n0\n2\n0\n0\n0\n0\n10";
			SaveFileWriter.writeToFile(Handler.SAVE_FOLDER, Handler.SETTINGS_FILE, settingsData);
			//System.out.println("Settings file created with default data.");
		} else {
			// Load existing save file
			settingsData = SaveFileReader.readFromFile(Handler.SAVE_FOLDER, Handler.SETTINGS_FILE);
			//System.out.println("Loaded Settings Data:");
			//System.out.println(settingsData);
		}

		useSavedSettings(settingsData);
	}

	public void useSavedSettings(String file) {
		// String file = Utils.loadFileAsString("/info/settings.txt");
		String[] tokens = file.split("[\\n\\s]+");
		// String[] tokens = file.split("\\s+");
		int i = 0;

		if (tokens.length == 0) {
			return;
		}
		int displayTypeToken = Utils.parseInt(tokens[i++]);
		int zoomLevelToken = Utils.parseInt(tokens[i++]);
		int hudColorToken = Utils.parseInt(tokens[i++]);
		int laserColorToken = Utils.parseInt(tokens[i++]);
		int zombieCounterToken = Utils.parseInt(tokens[i++]);
		int toggleCritsToken = Utils.parseInt(tokens[i++]);
		int toggleDamageToken = Utils.parseInt(tokens[i++]);
		int healthBarToken = Utils.parseInt(tokens[i++]);
		int masterVolumeToken = Utils.parseInt(tokens[i++]);

		displayType = displayTypeToken;
		switch (zoomLevelToken) {
		case 0:
			zoomLevel = 1.25;
			break;
		case 1:
			zoomLevel = 1.3;
			break;
		case 2:
			zoomLevel = 1.35;
			break;
		case 3:
			zoomLevel = 1.4;
			break;
		case 4:
			zoomLevel = 1.45;
			break;
		case 5:
			zoomLevel = 1.5;
			break;
		default:
			zoomLevel = 1.25;
			break;
		}
		hudColor = ColorIndex.getColor(hudColorToken);
		laserColor = ColorIndex.getColor(laserColorToken);

		switch (zombieCounterToken) {
		case 0:
			zombieCounter = false;
			break;
		case 1:
			zombieCounter = true;
			break;
		default:
			zombieCounter = false;
			break;
		}

		switch (toggleCritsToken) {
		case 0:
			toggleCrits = false;
			break;
		case 1:
			toggleCrits = true;
			break;
		default:
			toggleCrits = false;
			break;
		}

		switch (toggleDamageToken) {
		case 0:
			toggleDamage = false;
			break;
		case 1:
			toggleDamage = true;
			break;
		default:
			toggleDamage = false;
			break;
		}

		switch (healthBarToken) {
		case 0:
			healthBar = false;
			break;
		case 1:
			healthBar = true;
			break;
		default:
			healthBar = false;
			break;
		}

		if (masterVolumeToken > 10 || masterVolumeToken < 0) {
			masterVolume = 10;
		} else {
			masterVolume = masterVolumeToken;
		}

	}

	public void writeToFile() {
		String saveFolderPath = System.getProperty("user.home") + File.separator + "Documents" + File.separator
				+ Handler.SAVE_FOLDER;
		String progressionFilePath = saveFolderPath + File.separator + Handler.SETTINGS_FILE;

		try (BufferedWriter writer = new BufferedWriter(new FileWriter(progressionFilePath))) {
			writer.write(Integer.toString(displayType));
			writer.newLine();
			if (zoomLevel == 1.25) {
				writer.write("0");
			} else if (zoomLevel == 1.3) {
				writer.write("1");
			} else if (zoomLevel == 1.35) {
				writer.write("2");
			} else if (zoomLevel == 1.4) {
				writer.write("3");
			} else if (zoomLevel == 1.45) {
				writer.write("4");
			} else if (zoomLevel == 1.5) {
				writer.write("5");
			}
			writer.newLine();
			writer.write(Integer.toString(ColorIndex.getKeyByValue(hudColor)));
			writer.newLine();
			writer.write(Integer.toString(ColorIndex.getKeyByValue(laserColor)));
			writer.newLine();
			if (zombieCounter == true) {
				writer.write("1");
			} else {
				writer.write("0");
			}
			writer.newLine();

			if (toggleCrits == true) {
				writer.write("1");
			} else {
				writer.write("0");
			}
			writer.newLine();

			if (toggleDamage == true) {
				writer.write("1");
			} else {
				writer.write("0");
			}
			writer.newLine();

			if (healthBar == true) {
				writer.write("1");
			} else {
				writer.write("0");
			}
			writer.newLine();

			writer.write(Integer.toString(Math.round(masterVolume)));

			writer.close();
		} catch (IOException e) {
		}
	}

	public Handler getHandler() {
		return handler;
	}

	public void setHandler(Handler handler) {
		this.handler = handler;
	}

	public double getZoomLevel(boolean forSettings) {
		if(forSettings)
			return zoomLevel;
		if(handler.getSettings().getDisplayType() != handler.getGame().getDisplay().STANDARD)
			return zoomLevel + .5;
		return zoomLevel;
	}
	

	public void setZoomLevel(double zoomLevel) {
		this.zoomLevel = zoomLevel;
	}

	public boolean isGore() {
		return gore;
	}

	public void setGore(boolean gore) {
		this.gore = gore;
	}

	public Color getLaserColor() {
		return laserColor;
	}

	public void setLaserColor(Color laserColor) {
		this.laserColor = laserColor;
	}

	public Color getHudColor() {
		return hudColor;
	}

	public void setHudColor(Color hudColor) {
		this.hudColor = hudColor;
	}

	public boolean isZombieCounter() {
		return zombieCounter;
	}

	public void setZombieCounter(boolean zombieCounter) {
		this.zombieCounter = zombieCounter;
	}

	public boolean isToggleCrits() {
		return toggleCrits;
	}

	public void setToggleCrits(boolean toggleCrits) {
		this.toggleCrits = toggleCrits;
	}

	public boolean isToggleDamage() {
		return toggleDamage;
	}

	public void setToggleDamage(boolean toggleDamage) {
		this.toggleDamage = toggleDamage;
	}

	public boolean isHealthBar() {
		return healthBar;
	}

	public void setHealthBar(boolean healthBar) {
		this.healthBar = healthBar;
	}

	public void setMasterVolume(float masterVolume) {
		this.masterVolume = masterVolume;
	}

	public float getMasterVolume() {
		return masterVolume;
	}
	
	public void setDisplayType(int type) {
		this.displayType = type;
	}
	
	public int getDisplayType() {
		return displayType;
	}
}
