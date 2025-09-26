package FileHandler;

import java.io.*;
import java.util.ArrayList;

import User.User;

public class UserFileHandler {
    private static final String FILE_PATH = "users.dat";
    private static final String BASE_DIR = "users/";
   
    //for first User
    public static boolean checkFileExists(String id) {
        File file = new File(BASE_DIR + id+"/"+id+".dat");
        return file.exists();
    }
   
    public static void saveUser(User user) throws IOException{
    	File userFile = new File(BASE_DIR + user.getId() + "/"+user.getId()+".dat");
    	if(!userFile.exists()) {
    		FolderCreator.createUserFolder(user.getId());
    	}
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(userFile))) {
            oos.writeObject(user);
        }catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public static User loadUser(String userId) throws ClassNotFoundException {
        File userFile = new File(BASE_DIR + userId + "/"+userId+".dat");
        if (!userFile.exists()) return null;
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(userFile))) {
            return (User) ois.readObject();
        }catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static boolean deleteUser(String userId) {
        File userFile = new File(BASE_DIR + userId + "/"+userId+".dat");
        return userFile.delete();
    }
    
    public static boolean checkUserExists(String userId) {
    	File userFile = new File(BASE_DIR + userId + "/"+userId+".dat");
        return userFile.exists();
    }
    
    
    
}


