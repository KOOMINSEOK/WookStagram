package Component;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.*;

import Component.PhotoViewer.PhotoViewer;
import EventHandler.PhotoUploader;
import FileHandler.ThumnailPostMapper;
import FileHandler.UserFileHandler;
import Manager.LoginManager;
import User.PostData;
import User.User;

public class PostObject extends JPanel {
	private ArrayList<Image> imageList;
	private PostData postData;
	public PostObject(User user, int thumbnailIndex) {
		
		setLayout(new BorderLayout());
		setBackground(new Color(255,255,255));
		setPreferredSize(new Dimension(400, 500));
		
		
		//top
		JPanel topPanel = new JPanel();
		topPanel.setBackground(ColorPalette.background_color);
		topPanel.setLayout(new BoxLayout(topPanel, BoxLayout.X_AXIS));
		topPanel.setBorder(BorderFactory.createMatteBorder(1, 0, 1, 0, ColorPalette.borderline_color));
		topPanel.setBorder(BorderFactory.createEmptyBorder(5, 0, 5, 0));
		
		JLabel profileImage = new CircularImageIcon(user,30);
		JLabel userName = new JLabel(user.getNickName());
		
		userName.setPreferredSize(new Dimension(400, 30));
		userName.setFont(new Font("Dialog", Font.BOLD, 15));
		
		
		topPanel.add(Box.createHorizontalStrut(10)); 
		topPanel.add(profileImage);
		topPanel.add(Box.createHorizontalStrut(10)); 
		topPanel.add(userName);
		
		//can delete only  my own post
		if(user.compare(LoginManager.getInstance().getLoggedInUser())) {
			ImageIcon image = new ImageIcon("src/UI_Icon/sideMenu.png");
			Image resizedImage = image.getImage().getScaledInstance(30, 30, Image.SCALE_SMOOTH);
			ImageIcon resizedIcon = new ImageIcon(resizedImage);
			JLabel sideMenuLabel = new JLabel();
			sideMenuLabel.setIcon(resizedIcon);
			sideMenuLabel.setText(null);
			sideMenuLabel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
			sideMenuLabel.addMouseListener(new MouseAdapter() {
	            @Override
	            public void mouseClicked(MouseEvent e) {
	            	displayDeletePost(user,thumbnailIndex);
	            }
	        });
			topPanel.add(sideMenuLabel);
			
		}
		
		
		
		
		add(topPanel, BorderLayout.NORTH);
		
		//center
		imageList = new ArrayList<Image>();
		PhotoUploader photoUploader = new PhotoUploader();
		postData = new PostData();
		
		
		File postfile = ThumnailPostMapper.getPostFileForThumbnail(user, thumbnailIndex);
		postData = photoUploader.loadPost(postfile,imageList);
		
		
		PhotoViewer photoPanel = new PhotoViewer(imageList);
		photoPanel.setUp();
		photoPanel.setPreferredSize(new Dimension(400, 400));
		add(photoPanel, BorderLayout.CENTER);
		
		//bottom
		JPanel contentPanel = new JPanel();
		contentPanel.setLayout(new BorderLayout());
		contentPanel.setBorder(BorderFactory.createMatteBorder(1, 0, 1, 0, ColorPalette.borderline_color));
		
		JPanel titlePanel = new JPanel();
		titlePanel.setBackground(Color.white);
		titlePanel.setLayout(new BoxLayout(titlePanel, BoxLayout.X_AXIS));

	    JLabel userName2 = new JLabel(user.getNickName());
	    userName2.setFont(new Font("Dialog", Font.BOLD, 15));
	    
		JLabel comment = new JLabel(postData.getText());
	    
		titlePanel.add(Box.createHorizontalStrut(10)); 
	    titlePanel.add(userName2);
	    titlePanel.add(Box.createHorizontalStrut(10)); 
	    titlePanel.add(comment);

		contentPanel.add(titlePanel, BorderLayout.NORTH);
		contentPanel.setPreferredSize(new Dimension(400, 70));
		contentPanel.setBackground(Color.white);
		add(contentPanel, BorderLayout.SOUTH);
	}

    public long getTimestamp() {
        return postData.getTimestamp();
    }
    public void displayDeletePost(User user, int thumbnailIndex) {
    	JFrame deletePostFrame = new JFrame("delete Post");
    	deletePostFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    	deletePostFrame.setSize(100, 70);
    	Point parentLocation = this.getLocationOnScreen();
    	deletePostFrame.setLocation(parentLocation.x + this.getWidth() - deletePostFrame.getWidth(), parentLocation.y);
    	deletePostFrame.setResizable(false);
    	
    	CustomButton deleteBtn = new CustomButton("Delete",ColorPalette.light_purple);
    	deleteBtn.addActionListener(e->deletPost(user,thumbnailIndex));
    	deletePostFrame.add(deleteBtn);
    	deletePostFrame.setVisible(true);
    	
    }
    private void deletPost(User user, int thumbnailIndex) {
    	if(user.compare(LoginManager.getInstance().getLoggedInUser())) {
    		PhotoUploader photoUploader = new PhotoUploader();
    		
    		File postfile = ThumnailPostMapper.getPostFileForThumbnail(user, thumbnailIndex);
    		boolean postDeleted = photoUploader.deletePost(postfile); 
    	    boolean thumbnailDeleted = photoUploader.deleteThumbnail(thumbnailIndex);
    	    
    	    if (postDeleted && thumbnailDeleted) {
    	    	user.delPostCount();
    	    	try {
					UserFileHandler.saveUser(user);
				} catch (IOException e) {
					e.printStackTrace();
				}
    	    	LoginManager.getInstance().setLoggedInUser(user);
    	        LoginManager.getInstance().notifyObservers();
    	    }
    	}
    }
}
