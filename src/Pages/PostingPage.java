package Pages;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;


import Component.Posting.*;
import EventHandler.PhotoUploader;
import FileHandler.UserFileHandler;
import Manager.LoginManager;
import User.User;

public class PostingPage extends JPanel {
	private String photoInfo = "";
	
	public PostingPage() {
		setLayout(new BorderLayout()); 
        setBackground(new Color(240, 240, 240));
        
        PhotoUploader photoUploader = new PhotoUploader();
        
        
        JLabel title = new JLabel("New Posting", SwingConstants.CENTER);
        title.setPreferredSize(new Dimension(800, 50));
        title.setFont(new Font("Arial", Font.BOLD, 20));
        add(title, BorderLayout.NORTH);

        JPanel contentPanel = new JPanel();
        contentPanel.setLayout(new GridLayout(1,2));
        add(contentPanel,BorderLayout.CENTER);
        
        
        PhotoUploadPanel uploadPanel = new PhotoUploadPanel(photoUploader);
        contentPanel.add(uploadPanel); 
        
        
        InfoPanel infoPanel = new InfoPanel();
        contentPanel.add(infoPanel);
        
        //connect infoPanel and uploadPanel
        infoPanel.getReUploadBtn().addActionListener(e-> uploadPanel.resetUploadPanel());
        infoPanel.getPostBtn().addActionListener(e->{
        	photoInfo = infoPanel.getPhotoInfo();
        	
        	try {
                photoUploader.savePhotoAndText(photoInfo);
                userInfoUpdate();
                LoginManager.getInstance().notifyObservers();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
        	
        	infoPanel.resetInfoPanel();
        	uploadPanel.resetUploadPanel();
        });
        
        JLabel bottom = new JLabel();
        bottom.setPreferredSize(new Dimension(800, 50));
        add(bottom, BorderLayout.SOUTH);
	}


	public void userInfoUpdate() throws IOException {
		if(LoginManager.getInstance().isLoggedIn()) {
			User updateUser = LoginManager.getInstance().getLoggedInUser();
	    	updateUser.addPostCount();
	    	UserFileHandler.saveUser(updateUser);
	    	LoginManager.getInstance().updateLoggedInUser(updateUser);	
		}

	}
	
}
