package FileHandler;

import java.io.IOException;

import Manager.LoginManager;
import User.User;

public class FollowingHandler {
	public static boolean follow(User targetUser) {
		if(LoginManager.getInstance().isLoggedIn()) {
			
			User loggedInUser = LoginManager.getInstance().getLoggedInUser();
		    
		    if (loggedInUser != null && targetUser != null) {

		    	loggedInUser.follow(targetUser.getId());
		    	LoginManager.getInstance().updateLoggedInUser(loggedInUser);
		       
		    	targetUser.addFollower(loggedInUser.getId());

		    	try {
		            UserFileHandler.saveUser(loggedInUser);
		            UserFileHandler.saveUser(targetUser);
		        } catch (IOException ex) {
		            ex.printStackTrace();
		        }
		    }
		    return true;
		}
		else {
			return false;
		}
	}
	public static boolean unfollow(User targetUser) {
		if(LoginManager.getInstance().isLoggedIn()) {
			
			User loggedInUser = LoginManager.getInstance().getLoggedInUser();
		    
		    if (loggedInUser != null && targetUser != null) {
		        
		    	loggedInUser.unfollow(targetUser.getId());
		    	LoginManager.getInstance().updateLoggedInUser(loggedInUser);
		    	
		        
		    	targetUser.removeFollower(loggedInUser.getId());

		        try {
		            UserFileHandler.saveUser(loggedInUser);
		            UserFileHandler.saveUser(targetUser);
		        } catch (IOException ex) {
		            ex.printStackTrace();
		        }
		    }
		    return true;
		}
		else {
			return false;
		}
	}
}
