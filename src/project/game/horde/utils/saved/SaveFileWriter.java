package project.game.horde.utils.saved;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class SaveFileWriter {
    public static void writeToFile(String folderName, String filename, String content) {
        // Get the user's Documents directory
        String userHome = System.getProperty("user.home");
        File documentsDir = new File(userHome, "Documents");
        File saveFolder = new File(documentsDir, folderName);
        if (!saveFolder.exists()) {
            saveFolder.mkdirs(); // Create the folder if it doesn't exist
        }
        
        File file = new File(saveFolder, filename);

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
            writer.write(content);
        } catch (IOException e) {
        }
    }
}
