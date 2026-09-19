package project.game.horde.main;

import java.awt.Color;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import project.game.horde.ui.ColorIndex;
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
        nullValues();

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

    public void nullValues() {
        displayType = 0;
        zoomLevel = 1;
        hudColor = Color.green;
        laserColor = Color.red;
        zombieCounter = false;
        toggleCrits = false;
        toggleDamage = false;
        healthBar = false;
        masterVolume = 10;
    }

    public void useSavedSettings(String file) {
        String[] tokens = file.split("[\\n\\s]+");
        int i = 0;

        if (tokens.length == 0) {
            return;
        }
        try {
            int displayTypeToken = Integer.parseInt(tokens[i++]);
            int zoomLevelToken = Integer.parseInt(tokens[i++]);
            int hudColorToken = Integer.parseInt(tokens[i++]);
            int laserColorToken = Integer.parseInt(tokens[i++]);
            int zombieCounterToken = Integer.parseInt(tokens[i++]);
            int toggleCritsToken = Integer.parseInt(tokens[i++]);
            int toggleDamageToken = Integer.parseInt(tokens[i++]);
            int healthBarToken = Integer.parseInt(tokens[i++]);
            int masterVolumeToken = Integer.parseInt(tokens[i++]);

            displayType = displayTypeToken;
            zoomLevel = switch (zoomLevelToken) {
                case 0 ->
                    1.25;
                case 1 ->
                    1.325;
                case 2 ->
                    1.4;
                case 3 ->
                    1.475;
                case 4 ->
                    1.55;
                case 5 ->
                    1.625;
                case 6 ->
                    1.7;
                case 7 ->
                    1.775;
                case 8 ->
                    1.85;
                case 9 ->
                    1.925;
                case 10 ->
                    2.0;
                default ->
                    1.25;
            };
            hudColor = ColorIndex.getColor(hudColorToken);
            laserColor = ColorIndex.getColor(laserColorToken);

            zombieCounter = switch (zombieCounterToken) {
                case 0 ->
                    false;
                case 1 ->
                    true;
                default ->
                    false;
            };

            toggleCrits = switch (toggleCritsToken) {
                case 0 ->
                    false;
                case 1 ->
                    true;
                default ->
                    false;
            };

            toggleDamage = switch (toggleDamageToken) {
                case 0 ->
                    false;
                case 1 ->
                    true;
                default ->
                    false;
            };

            healthBar = switch (healthBarToken) {
                case 0 ->
                    false;
                case 1 ->
                    true;
                default ->
                    false;
            };

            if (masterVolumeToken > 10 || masterVolumeToken < 0) {
                masterVolume = 10;
            } else {
                masterVolume = masterVolumeToken;
            }

        } catch (Exception e) {
            deleteProgressionFile();
            nullValues();
            writeToFile();
            e.printStackTrace();
        }
    }

    private void deleteProgressionFile() {
        String saveFolderPath = System.getProperty("user.home") + File.separator + "Documents" + File.separator
                + Handler.SAVE_FOLDER;
        File file = new File(saveFolderPath, Handler.SETTINGS_FILE);
        if (file.exists()) {
            file.delete();
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
            } else if (zoomLevel == 1.325) {
                writer.write("1");
            } else if (zoomLevel == 1.4) {
                writer.write("2");
            } else if (zoomLevel == 1.475) {
                writer.write("3");
            } else if (zoomLevel == 1.55) {
                writer.write("4");
            } else if (zoomLevel == 1.625) {
                writer.write("5");
            } else if (zoomLevel == 1.7) {
                writer.write("6");
            } else if (zoomLevel == 1.775) {
                writer.write("7");
            } else if (zoomLevel == 1.85) {
                writer.write("8");
            } else if (zoomLevel == 1.925) {
                writer.write("9");
            } else if (zoomLevel == 2.0) {
                writer.write("10");
            } else {
                writer.write("0");
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
        if (forSettings) {
            return zoomLevel;
        }
        if (handler.getSettings().getDisplayType() != handler.getGame().getDisplay().STANDARD) {
            return zoomLevel + .5;
        }
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
