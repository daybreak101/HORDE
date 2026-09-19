package project.game.horde.main;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import project.game.horde.utils.saved.SaveFileReader;
import project.game.horde.utils.saved.SaveFileUtils;
import project.game.horde.utils.saved.SaveFileWriter;

public class Unlocks {

    private Handler handler;
    public final int NUM_UNLOCKS = 11;
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
        try {
            // File is corrupted if number of tokens is not equal to number of unlocks
            if (tokens.length != NUM_UNLOCKS) {
                throw new IllegalArgumentException("Corrupted unlocks");
            }
            deadshotLvl = getToken(tokens[0]);
            doubletapLvl = getToken(tokens[1]);
            juggLvl = getToken(tokens[2]);
            lunaLvl = getToken(tokens[3]);
            muleLvl = getToken(tokens[4]);
            phdLvl = getToken(tokens[5]);
            reviveLvl = getToken(tokens[6]);
            speedLvl = getToken(tokens[7]);
            staminaLvl = getToken(tokens[8]);
            strongholdLvl = getToken(tokens[9]);
            vampireLvl = getToken(tokens[10]);

        } catch (Exception e) {
            deleteUnlocksFile();
            nullValues();
            writeToFile();
            e.printStackTrace();
        }
    }

    private void nullValues() {
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
    }

    private int getToken(String token) {
        //function created to always check for valid tokens
        //if token is not a number, it will throw an exception
        //if token is not 0 or 1, it will throw an exception
        //throwing exceptions is to trigger file deletion
        int value = Integer.parseInt(token);
        if (value < 0 || value > 3) {
            throw new IllegalArgumentException("Corrupted unlocks");
        }
        return value;
    }

    private void deleteUnlocksFile() {
        String saveFolderPath = System.getProperty("user.home") + File.separator + "Documents" + File.separator
                + Handler.SAVE_FOLDER;
        File file = new File(saveFolderPath, Handler.UNLOCKS_FILE);
        if (file.exists()) {
            file.delete();
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
