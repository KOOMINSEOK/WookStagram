package Component.Profile;

import java.awt.*;
import java.util.ArrayList;

import javax.swing.*;

import Component.ColorPalette;
import Component.PostObject;
import User.User;
import EventHandler.PhotoUploader;
import Manager.LoginManager;

import java.awt.event.*;


public class ProfileCenter extends JPanel {
	
	private ArrayList<Image> thumnailList;
	private PhotoUploader photoUploader = new PhotoUploader(); 
	
	public ProfileCenter(User user) {
		setLayout(new BorderLayout());
		setBackground(ColorPalette.background_color);
		JLabel title = new JLabel(" ");
		add(title,BorderLayout.NORTH);
		
		if(user.compare(LoginManager.getInstance().getLoggedInUser())) // for loggedinUser
			thumnailList = photoUploader.loadPostThumnails();
		else //for other user
			thumnailList = photoUploader.loadOtherUserThumnail(user);
		
		if (thumnailList != null && !thumnailList.isEmpty()) {
	        int totalImages = thumnailList.size();
	        int cols = 3;
	        int rows = (int) Math.ceil((double) totalImages / cols);
	        
	        JPanel photoGrid = new JPanel();
	        photoGrid.setBackground(ColorPalette.background_color);
	        photoGrid.setLayout(new GridLayout(rows, cols, 10, 10));

	        for (int i = thumnailList.size() - 1; i >= 0; --i) {
	            int index = i;
	            JLabel thumnailLabel = new JLabel();
	            Image thumnail = thumnailList.get(i).getScaledInstance(200, 200, Image.SCALE_SMOOTH);
	            thumnailLabel.setIcon(new ImageIcon(thumnail));
	            thumnailLabel.setText(null);
	            thumnailLabel.setHorizontalAlignment(JLabel.CENTER);
	            thumnailLabel.setVerticalAlignment(JLabel.TOP);
	            thumnailLabel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

	            thumnailLabel.addMouseListener(new MouseAdapter() {
	                @Override
	                public void mouseClicked(MouseEvent e) {
	                    displayPostObject(user, index);
	                }
	            });
	            photoGrid.add(thumnailLabel);
	        }
	        // fill emptyCells
            int emptyCells = (rows * cols) - totalImages;
            for (int i = 0; i < emptyCells; i++) {
                JPanel emptyPanel = new JPanel();
                emptyPanel.setBackground(ColorPalette.background_color);
                photoGrid.add(emptyPanel);
            }
	        
            JScrollPane scrollPane = new JScrollPane(photoGrid);
	        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
	        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
	        add(scrollPane, BorderLayout.CENTER);
	    } else {
	        JLabel noPhotosLabel = new JLabel("No photos to display.");
	        noPhotosLabel.setHorizontalAlignment(SwingConstants.CENTER);
	        add(noPhotosLabel, BorderLayout.CENTER);
	    }
		JLabel l = new JLabel(" ");
		add(l,BorderLayout.SOUTH);
		
	}
	
	 private void displayPostObject(User user, int thumbnailIndex) {
	        //show PostObject with new Window
	        JFrame postFrame = new JFrame("Post Detail");
	        postFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
	        postFrame.setSize(415, 530);
	        postFrame.setResizable(false);
	        postFrame.setLocationRelativeTo(this);

	        // create PostObject related to selected thumbnail
	        PostObject postObject = new PostObject(user,thumbnailIndex);
	        postFrame.add(postObject);
	        postFrame.setVisible(true);
	    }
}
