package project.game.horde.utils.saved;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class SaveFileReader {
    public static String readFromFile(String folderName, String filename) {
        // Get the user's Documents directory
        String userHome = System.getProperty("user.home");
        File documentsDir = new File(userHome, "Documents");
        File saveFolder = new File(documentsDir, folderName);
        File file = new File(saveFolder, filename);

        StringBuilder content = new StringBuilder();

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                content.append(line).append("\n");
            }
        } catch (IOException e) {
        }

        return content.toString();
    }
}
