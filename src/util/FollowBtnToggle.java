package util;

import java.awt.BorderLayout;

import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

import Component.ColorPalette;
import Component.CustomButton;
import Component.Profile.EditProfilePanel;
import FileHandler.FollowingHandler;
import Manager.LoginManager;
import User.User;

public class FollowBtnToggle {
	public static void followBtnToggle(User user, CustomButton button,JComponent parent) {
    	if(LoginManager.getInstance().isLoggedIn()) {
    		User loggedInUser = LoginManager.getInstance().getLoggedInUser();
    		if(loggedInUser.compare(user)) {
    			button.setText("edit Profile");
    			button.setBackground(ColorPalette.light_cyan);
    			button.addActionListener(e->displayEditProfile(user,parent));
        		
        	}
        	else if(loggedInUser.checkFollowing(user.getId())) {
        		//already followed
    			button.setText("Unfollow");
    			button.setBackground(ColorPalette.mediumgreen);
    			button.addActionListener(e->{
        			if(!FollowingHandler.unfollow(user)) {
            			//when failed to follow
            			JOptionPane.showMessageDialog(parent, "You need to login first");
            		}
        		});
        	}
        	else {
        		button.addActionListener(e->{
        			if(!FollowingHandler.follow(user)) {
            			//when failed to follow
            			JOptionPane.showMessageDialog(parent, "You need to login first");
            		}
        		});
        	}
    	}
    	else {
    		button.addActionListener(e->{
    			if(!FollowingHandler.follow(user)) {
        			//when failed to follow
        			JOptionPane.showMessageDialog(parent, "You need to login first");
        		}
    		});
    	}
	}
	public static void displayEditProfile(User user,JComponent parent) {

		JFrame editProfileFrame = new JFrame("Edit Profile");
	    editProfileFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
	    editProfileFrame.setSize(400, 250);
	    editProfileFrame.setLayout(new BorderLayout());
	    editProfileFrame.setResizable(false);
	    editProfileFrame.setLocationRelativeTo(parent);

	    EditProfilePanel editProfilePanel = new EditProfilePanel(user);
	    editProfileFrame.add(editProfilePanel, BorderLayout.CENTER);
	    editProfileFrame.setVisible(true);
	}
	
}
