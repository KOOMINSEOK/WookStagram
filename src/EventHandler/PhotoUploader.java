package EventHandler;

import javax.swing.*;

import FileHandler.ThumnailPostMapper;

import java.awt.*;

import java.io.*;

import java.util.ArrayList;
import Manager.LoginManager;
import User.PostData;
import User.User;


public class PhotoUploader extends JFrame {
    private ArrayList<byte[]> byteList = new ArrayList<byte[]>();
    private static User loggedInUser;
    private String SAVE_FILE_PATH = "";
    private String THUMNAIL_FILE_PATH = "";
    
    private boolean checkAndSetPaths() {
        if (LoginManager.getInstance().isLoggedIn()) {
            loggedInUser = LoginManager.getInstance().getLoggedInUser();
            int postNumber = ThumnailPostMapper.getLastPostNumber(loggedInUser.getId())+1;
            SAVE_FILE_PATH = "users/" + loggedInUser.getId() + "/Post/post"+ postNumber +".dat";
            THUMNAIL_FILE_PATH = "users/" + loggedInUser.getId() + "/PostThumbnails.dat";
            return true;
        } else {
        	return false;
        }
    }
    
    public void uploadPhoto( JLabel imageLabel, ArrayList<Image> imageList) {
    	byteList.clear();
    	
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setMultiSelectionEnabled(true); 
        fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
        
        int result = fileChooser.showOpenDialog(this);
        
        if (result == JFileChooser.APPROVE_OPTION) {
            File[] selectedFiles = fileChooser.getSelectedFiles();
            
            
            imageList.clear();
            
            for( File file : selectedFiles) {
            	try {
            		byte[] photoData = readFileToByteArray(file);
            		byteList.add(photoData);
            		setImage(imageList, photoData);
                    
            	} catch(IOException ex) {
            		JOptionPane.showMessageDialog(this, "Error processing photo: " + ex.getMessage());
            	}
            }
            JOptionPane.showMessageDialog(this, "Photo uploaded successfully!");
               
        }
    }
    
    public void savePhotoAndText(String photoInfo) {
    	long timestamp = System.currentTimeMillis();
    	if(checkAndSetPaths()) {
    		if (!byteList.isEmpty()) {
    	        try (DataOutputStream dos = new DataOutputStream(new FileOutputStream(SAVE_FILE_PATH));
    	             DataOutputStream postThumnails = new DataOutputStream(new FileOutputStream(THUMNAIL_FILE_PATH, true))) {
    	             
    	            // 1. Save text
    	            byte[] textBytes = photoInfo.getBytes("UTF-8");
    	            dos.writeInt(textBytes.length); 
    	            dos.write(textBytes);
    	            
    	            // 2. Save timestamp
    	            dos.writeLong(timestamp);
    	            
    	            // 3. Save photos
    	            dos.writeInt(byteList.size());
    	            for (int i = 0; i < byteList.size(); i++) {
    	                byte[] photoData = byteList.get(i);
    	                dos.writeInt(photoData.length);
    	                dos.write(photoData);

    	                // Save the first photo to PostListPhoto
    	                if (i == 0) {
    	                	postThumnails.writeInt(photoData.length);
    	                	postThumnails.write(photoData);
    	                }
    	            }
    	            JOptionPane.showMessageDialog(null, "Photos and info saved successfully!");

    	        } catch (IOException ex) {
    	            JOptionPane.showMessageDialog(null, "Error saving photos and text: " + ex.getMessage());
    	        }
    	    }
    	}
    	else {
    		JOptionPane.showMessageDialog(this, "User is not logged in!");
    	}
    }

    public PostData loadPost(File postfile, ArrayList<Image> imageList) {
        byteList.clear();
        PostData postData = new PostData();

        try (DataInputStream dis = new DataInputStream(new FileInputStream(postfile))) {
            // 1. read Text
            int textLength = dis.readInt();
            byte[] textBytes = new byte[textLength];
            dis.readFully(textBytes);
            postData.setText( new String(textBytes, "UTF-8"));
            
            // 2. read timeStamp
            postData.setTimestamp(dis.readLong());

            // 3. read Photos
            int photoCount = dis.readInt();
            for (int i = 0; i < photoCount; i++) {
                int photoLength = dis.readInt();
                byte[] photoData = new byte[photoLength];
                dis.readFully(photoData);
                byteList.add(photoData);
                setImage(imageList, photoData);
            }


        } catch (IOException ex) {
            JOptionPane.showMessageDialog(null, "Error loading photos and text: " + ex.getMessage());
        }

        return postData;
    }
    
    public boolean deletePost(File postfile) {
        
        if (postfile.exists()) {
            if (postfile.delete()) {
                return true;
            } else {
                JOptionPane.showMessageDialog(null, "Failed to delete the post.");
            }
        } else {
            JOptionPane.showMessageDialog(null, "Post file not found.");
        }

        return false;
    }
    
    public ArrayList<Image> loadPostThumnails() {
    	if(checkAndSetPaths()) {
	        ArrayList<Image> postThumnailList = new ArrayList<>();
	        try (DataInputStream dis = new DataInputStream(new FileInputStream(THUMNAIL_FILE_PATH))) {
	            while (dis.available() > 0) {
	                int photoLength = dis.readInt();
	                byte[] photoData = new byte[photoLength];
	                dis.readFully(photoData);
	                
	                ImageIcon imageIcon = new ImageIcon(photoData);
	                Image image = imageIcon.getImage().getScaledInstance(200, 200, Image.SCALE_SMOOTH);
	                postThumnailList.add(image);
	            }
	        } catch (IOException ex) {
	            JOptionPane.showMessageDialog(null, "Error loading post list photos: " + ex.getMessage());
	        }
	        return postThumnailList;
        }
    	else {
    		JOptionPane.showMessageDialog(this, "User is not logged in!");
    	}
		return null;
    }
    
    public ArrayList<Image> loadOtherUserThumnail(User user) {
    	ArrayList<Image> postThumnailList = new ArrayList<>();
    	THUMNAIL_FILE_PATH = "users/" + user.getId() + "/PostThumbnails.dat";
        try (DataInputStream dis = new DataInputStream(new FileInputStream(THUMNAIL_FILE_PATH))) {
            while (dis.available() > 0) {
                int photoLength = dis.readInt();
                byte[] photoData = new byte[photoLength];
                dis.readFully(photoData);
                
                ImageIcon imageIcon = new ImageIcon(photoData);
                Image image = imageIcon.getImage().getScaledInstance(200, 200, Image.SCALE_SMOOTH);
                postThumnailList.add(image);
            }
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(null, "Error loading post list photos: " + ex.getMessage());
        }
        return postThumnailList;
    }
    
    public boolean deleteThumbnail(int thumbnailIndex) {
        if(checkAndSetPaths()) {
        	File thumbnailFile = new File(THUMNAIL_FILE_PATH);
        	 if (!thumbnailFile.exists()) {
                 JOptionPane.showMessageDialog(null, "Thumbnail file not found.");
                 return false;
             }

             ArrayList<byte[]> thumbnails = new ArrayList<>();

             try (DataInputStream dis = new DataInputStream(new FileInputStream(thumbnailFile))) {
                 while (dis.available() > 0) {
                     int photoLength = dis.readInt();
                     byte[] photoData = new byte[photoLength];
                     dis.readFully(photoData);
                     thumbnails.add(photoData);
                 }
             } catch (IOException e) {
                 JOptionPane.showMessageDialog(null, "Error reading thumbnails: " + e.getMessage());
                 return false;
             }

             if (thumbnailIndex < 0 || thumbnailIndex >= thumbnails.size()) {
                 JOptionPane.showMessageDialog(null, "Invalid thumbnail index.");
                 return false;
             }
             thumbnails.remove(thumbnailIndex);

             try (DataOutputStream dos = new DataOutputStream(new FileOutputStream(THUMNAIL_FILE_PATH))) {
                 for (byte[] thumbnail : thumbnails) {
                     dos.writeInt(thumbnail.length);
                     dos.write(thumbnail);
                 }
             } catch (IOException e) {
                 JOptionPane.showMessageDialog(null, "Error saving thumbnails: " + e.getMessage());
                 return false;
             }

             JOptionPane.showMessageDialog(null, "Thumbnail deleted successfully!");
             return true;
        }
        JOptionPane.showMessageDialog(null, "You are not qualified");
        return false;
       
    }
    
    private byte[] readFileToByteArray(File file) throws IOException {
        try (FileInputStream fis = new FileInputStream(file);
             ByteArrayOutputStream bos = new ByteArrayOutputStream()) {
            byte[] buffer = new byte[1024];
            int bytesRead;
            while ((bytesRead = fis.read(buffer)) != -1) {
                bos.write(buffer, 0, bytesRead);
            }
            return bos.toByteArray();
        }
    }
    
    private void setImage(ArrayList<Image> imageList,byte[] photoData) {
    	ImageIcon imageIcon = new ImageIcon(photoData);
        Image image = imageIcon.getImage().getScaledInstance(400, 400, Image.SCALE_SMOOTH);
        imageList.add(image);
    }
    
}
