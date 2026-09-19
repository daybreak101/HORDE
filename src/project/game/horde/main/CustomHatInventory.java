package project.game.horde.main;

import java.awt.image.BufferedImage;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;

import project.game.horde.graphics.CharAssets;
import project.game.horde.utils.saved.SaveFileReader;
import project.game.horde.utils.saved.SaveFileUtils;
import project.game.horde.utils.saved.SaveFileWriter;

public class CustomHatInventory {

    public Handler handler;
    public static HashMap<String, Integer> inventory = new HashMap<>();

    public static final int COMMON = 0, RARE = 1, EPIC = 2, LEGENDARY = 3;
    public final int NUM_HATS = 3;
    public static final int NONE = 0,
            CHRISTMAS = 1,
            REINDEER = 2,
            BUNNY = 3;

    private int equipped = NONE;

    private int christmas = 0,
            reindeer = 0,
            bunny = 0;

    public static BufferedImage getHatImage(int hat) {
        return switch (hat) {
            case CHRISTMAS ->
                CharAssets.christmasHat;
            case REINDEER ->
                CharAssets.reindeer;
            case BUNNY ->
                CharAssets.bunny;
            default ->
                null;
        };
    }

    public BufferedImage getHat(int hat) {
        return switch (hat) {
            case CHRISTMAS ->
                CharAssets.christmasHat;
            case REINDEER ->
                CharAssets.reindeer;
            case BUNNY ->
                CharAssets.bunny;
            default ->
                null;
        };
    }

    public void unlockHat(int hat) {
        switch (hat) {
            case CHRISTMAS ->
                christmas = 1;
            case REINDEER ->
                reindeer = 1;
            case BUNNY ->
                bunny = 1;
            default -> {
            }
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
        //String unlockFilePath = Handler.SAVE_FOLDER + File.separator + Handler.CUSTOMHAT_FILE;

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
        try {
            // File is corrupted if number of tokens is not equal to number of hats
            if(tokens.length != NUM_HATS) {
                throw new IllegalArgumentException("Corrupted hats");
            }
            else {
                //initialize valid values
                christmas = getToken(tokens[0]);
                reindeer = getToken(tokens[1]);
                bunny = getToken(tokens[2]);
            }
        } catch (Exception e) {
            //if corrupted, delete file and set to default values and save new file
            deleteHatsFile();
            nullValues();
            writeToFile();
            e.printStackTrace();
        }

    }

    private int getToken(String token) {
        //function created to always check for valid tokens
        //if token is not a number, it will throw an exception
        //if token is not 0 or 1, it will throw an exception
        //throwing exceptions is to trigger file deletion
        int value = Integer.parseInt(token);
        if(value < 0 || value > 1) {
            throw new IllegalArgumentException("Corrupted hats");
        }
        return value;
    }

    //default values
    private void nullValues() {
        christmas = 0;
        reindeer = 0;
        bunny = 0;
    }

    private void deleteHatsFile() {
        String saveFolderPath = System.getProperty("user.home") + File.separator + "Documents" + File.separator
                + Handler.SAVE_FOLDER;
        File file = new File(saveFolderPath, Handler.CUSTOMHAT_FILE);
        if (file.exists()) {
            file.delete();
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
        }
    }

}
