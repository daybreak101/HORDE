package project.game.horde.main;

import java.awt.image.BufferedImage;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map.Entry;

import project.game.horde.graphics.CharAssets;
import project.game.horde.utils.saved.SaveFileReader;
import project.game.horde.utils.saved.SaveFileUtils;
import project.game.horde.utils.saved.SaveFileWriter;

public class CustomSkinInventory {

    public Handler handler;
    public static HashMap<Integer, Integer> inventory = new HashMap<>();

    public static final int COMMON = 0, RARE = 1, EPIC = 2, LEGENDARY = 3;
    public final int NUM_SKINS = 3;
    public static final int HARRY = 0,
            BLUE_ALIEN = 1,
            ROBOT = 2;
    public static final int HARRY_PRICE = 0;
    public static final int BLUE_ALIEN_PRICE = 30;
    public static final int ROBOT_PRICE = 30;

    private int equipped = HARRY;

    public BufferedImage[] getSkin(int skin) {
        return switch (skin) {
            case HARRY ->
                CharAssets.harry;
            case ROBOT ->
                CharAssets.robot;
            case BLUE_ALIEN ->
                CharAssets.blueAlien;
            default ->
                CharAssets.harry;
        };
    }

    public boolean checkOwned(int skin) {
        System.out.println(inventory);
        System.out.println(skin);
        System.out.println(inventory.get(skin));
        return inventory.get(skin) == 1;
    }

    public void unlockSkin(int skin) {
        inventory.replace(skin, 1);
        writeToFile();
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
            unlockData = "1\n0\n0";
            SaveFileWriter.writeToFile(Handler.SAVE_FOLDER, Handler.CUSTOMSKIN_FILE, unlockData);
        } else {
            // Load existing save file
            unlockData = SaveFileReader.readFromFile(Handler.SAVE_FOLDER, Handler.CUSTOMSKIN_FILE);
        }
        readUnlockedSkins(unlockData);
    }

    private void readUnlockedSkins(String file) {
        String[] tokens = file.split("[\\n\\s]+");
        try {
            // File is corrupted if number of tokens is not equal to number of skins - 1
            // It is -1 because of the default skin Harry, already being unlocked by default
            if (tokens.length != NUM_SKINS) {
                throw new IllegalArgumentException("Corrupted skins");
            } else {
                //initialize valid values
                inventory.put(HARRY, 1);
                for (int i = 0; i < NUM_SKINS; i++) {
                    inventory.put(i, getToken(tokens[i]));
                }
            }
        } catch (Exception e) {
            //if corrupted, delete file and set to default values and save new file
            deleteSkinsFile();
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
        if (value < 0 || value > 1) {
            throw new IllegalArgumentException("Corrupted skins");
        }
        return value;
    }

    private void deleteSkinsFile() {
        String saveFolderPath = System.getProperty("user.home") + File.separator + "Documents" + File.separator
                + Handler.SAVE_FOLDER;
        File file = new File(saveFolderPath, Handler.CUSTOMSKIN_FILE);
        if (file.exists()) {
            file.delete();
        }
    }

    //default values
    private void nullValues() {
        inventory.put(HARRY, 1);
        for (int i = 1; i < NUM_SKINS; i++) {
            inventory.put(i, 0);
        }
    }

    public void writeToFile() {
        String saveFolderPath = System.getProperty("user.home") + File.separator + "Documents" + File.separator
                + Handler.SAVE_FOLDER;
        String unlocksFilePath = saveFolderPath + File.separator + Handler.CUSTOMSKIN_FILE;

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(unlocksFilePath))) {
            // writer.write(Integer.toString(robot));
            // writer.newLine();
            // writer.write(Integer.toString(blueAlien));
            // writer.newLine();
            for (Entry<Integer, Integer> entry : inventory.entrySet()) {
                writer.write(Integer.toString(entry.getValue()));
                writer.newLine();
            }
        } catch (IOException e) {
        }
    }

}
