package Pages;

import javax.swing.*;
import java.awt.*;
import java.util.*;

import Component.ColorPalette;
import Component.PostObject;
import FileHandler.UserFileHandler;
import Manager.LoginManager;
import User.User;

public class MainPage extends JScrollPane {
	private JPanel contentPanel;
    public MainPage() {
    	
        JPanel outerPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.CENTER;

        contentPanel = new JPanel();
        contentPanel.setBackground(new Color(255, 255, 255));
        contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS)); 

        JLabel label = new JLabel("Main Page");
        label.setForeground(Color.black);
        label.setAlignmentX(Component.CENTER_ALIGNMENT);
        contentPanel.add(label);
        contentPanel.add(Box.createRigidArea(new Dimension(0, 20)));
        contentPanel.setBorder(BorderFactory.createLineBorder(ColorPalette.borderline_color, 1));
        
        outerPanel.add(contentPanel, gbc);

        this.setViewportView(outerPanel);


        this.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        this.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        

        LoginManager.getInstance().addObserver(this::updateContent);
        
        updateContent();
    }
    private void updateContent() {
        contentPanel.removeAll();

        if(LoginManager.getInstance().isLoggedIn()) {
	        User loggedInUser = LoginManager.getInstance().getLoggedInUser();
	        ArrayList<String> followingList = loggedInUser.getFollowingList();

	        ArrayList<PostObject> postObjectList = new ArrayList<PostObject>();
	        if(followingList.size() == 0) {
	        	JLabel messageLabel = new JLabel("No Post to display");
	        	contentPanel.add(messageLabel);
	        }
	        int count = 0;
	        while(count <15) {
	        	for(String userId : followingList) {
		        	try {
						User user = UserFileHandler.loadUser(userId);
						if(user.getPostCount()!=0) {
							for (int i = user.getPostCount() - 1; i >= Math.max(user.getPostCount()-3, 0) ; --i) {
								if(count >= 15)
									break;
		                        PostObject postObject = new PostObject(user,i);
		                        postObjectList.add(postObject);
		                        ++count;
		                    }	
						}
		        	
		        	} catch (ClassNotFoundException e) {
						e.printStackTrace();
					}
		        	
		        }
	        	break;
	        }
	        
	        postObjectList.sort((post1,post2)-> Long.compare(post2.getTimestamp(), post1.getTimestamp()));
	        
	        for(PostObject postObject : postObjectList) {
	        	postObject.setMinimumSize(new Dimension(400, 500));
                postObject.setMaximumSize(new Dimension(400, 500));
                postObject.setAlignmentX(Component.CENTER_ALIGNMENT);
                contentPanel.add(postObject);
	        }
        }
        else {
        	JLabel messageLabel = new JLabel("No Post to display");
        	contentPanel.add(messageLabel);
        }

        contentPanel.revalidate();
        contentPanel.repaint();
    }
}
