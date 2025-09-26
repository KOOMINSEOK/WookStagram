package User;

import java.io.*;
import java.util.*;

public class User implements Serializable {
	 	private String id;
	    private String pw;
	    private String nickName;
	    private int postCount;
	    private ArrayList<String> followingList;
	    private ArrayList<String> followerList;
	    private byte[] profilePicture;

	    public User(String id, String pw, String nickName) {
	    	 this.id = id;
	    	 this.pw = pw;
	    	 this.nickName = nickName;
	    	 this.followingList = new ArrayList<>();
	    	 this.followerList = new ArrayList<>();
	    	 this.profilePicture = null;
	    }
	    public User(String id, String pw) {
	        setId(id);
	        setPw(pw);
	    }
	    public User(String id) {
	        setId(id);
	    }
	    
	    public String getId() { return id; }
	    public void setId(String id) { this.id = id; }

	    public String getPw() { return pw; }
	    public void setPw(String pw) { this.pw = pw; }

	    public String getNickName() { return nickName; }
	    public void setNickName(String nickName) { this.nickName = nickName; }

	    public int getPostCount() { return postCount; }
	    public void setPostCount(int postCount) { this.postCount = postCount; }
	    public void addPostCount() {this.postCount++;}
	    public void delPostCount() {this.postCount--;}
	    
	    public int getFollowingCount() { return followingList.size(); }
	    public ArrayList<String> getFollowingList(){ return this.followingList; }
	    public int getFollowerCount() { return followerList.size(); }
	    public ArrayList<String> getFollowerList(){	return this.followerList; };
	    public void follow(String userId) {
	        if (!followingList.contains(userId)) {
	            followingList.add(userId);
	        }
	    }
	    public void addFollower(String userId) {
	        if (!followerList.contains(userId)) {
	            followerList.add(userId);
	        }
	    }
	    public void unfollow(String userId) {
	        followingList.remove(userId);
	    }
	    public void removeFollower(String userId) {
	        followerList.remove(userId);
	    }
	    
	    public boolean checkFollowing(String userId) {
	    	if(this.followingList.contains(userId)) return true;
	    	else return false;
	    }
	    
	    
	    
	    public byte[] getProfilePicture() { return profilePicture; }    
	    public void setProfilePicture(File file) {
	        try (FileInputStream fis = new FileInputStream(file);
	             ByteArrayOutputStream bos = new ByteArrayOutputStream()) {
	            byte[] buffer = new byte[1024];
	            int bytesRead;
	            while ((bytesRead = fis.read(buffer)) != -1) {
	                bos.write(buffer, 0, bytesRead);
	            }
	            this.profilePicture = bos.toByteArray();
	        } catch (IOException e) {
	            e.printStackTrace();
	        }
	    }

	    @Override
	    public boolean equals(Object o) {
	        if(o == null || !(o instanceof User)) {
	            return false;
	        }
	        User temp = (User)o;

	        return id.equals(temp.getId());
	    }
	    public boolean compare(Object o) {
	    	if(o == null || !(o instanceof User)) {
	            return false;
	        }
	        User temp = (User)o;

	        return id.equals(temp.getId()) && pw.equals(temp.getPw());
	    }
	    @Override
	    public String toString() {
	        return "User [ID=" + id + 
	               ", NickName=" + nickName + 
	               ", Posts=" + postCount + 
	               ", Following=" + followingList.size() + 
	               ", Followers=" + followerList.size() + "]";
	    }
}
