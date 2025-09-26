package FileHandler;

import java.io.File;
import java.io.IOException;

public class FolderCreator {

    public static boolean createUserFolder(String userId) throws IOException {
        String basePath = "users/";
        String userFolderPath = basePath + userId;
        String PostFolderPath = userFolderPath+"/Post";
        String thumnailFilePath = userFolderPath+"/PostThumbnails.dat";
        
        File userFolder = new File(userFolderPath);
        File userPostFolder = new File(PostFolderPath);
        File thumnailFile = new File(thumnailFilePath);

        if (!userFolder.exists()) {
            boolean isCreated = (userFolder.mkdirs() && userPostFolder.mkdirs() && thumnailFile.createNewFile());
            if (isCreated) {
                System.out.println("Folder created: " + userFolderPath);
            } else {
                System.out.println("Failed to create folder: " + userFolderPath);
            }
            return isCreated;
        } else {
            System.out.println("Folder already exists: " + userFolderPath);
            return false;
        }
    }
}
