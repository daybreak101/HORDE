package project.game.horde.utils.saved;

import java.io.File;

public class SaveFileUtils {
    public static boolean fileExists(String folderName, String filename) {
        // Get the user's Documents directory
        String userHome = System.getProperty("user.home");
        File documentsDir = new File(userHome, "Documents");
        File saveFolder = new File(documentsDir, folderName);
        File file = new File(saveFolder, filename);
        return file.exists();
    }
}
