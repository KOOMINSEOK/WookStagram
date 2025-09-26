package Component.Search;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

import Component.CircularImageIcon;
import Component.ColorPalette;
import Component.CustomButton;
import User.User;
import util.FollowBtnToggle;

public class SearchObject extends JPanel{
	
	private CustomButton followBtn;
	public SearchObject(User user) {
		setLayout(new BorderLayout());
        setBackground(ColorPalette.background_color);
		setBorder(BorderFactory.createMatteBorder(0, 1, 1, 1, ColorPalette.borderline_color));

        
        JPanel profilePanel = new JPanel();
        profilePanel.setBackground(ColorPalette.background_color);
        profilePanel.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 0));
        String profileImagePath = "src/UI_Icon/profile.png";
        CircularImageIcon profileImagLabel = new CircularImageIcon(user, 50);
        profilePanel.add(profileImagLabel);
        
        add(profilePanel, BorderLayout.WEST);


        JPanel textPanel = new JPanel();
        textPanel.setLayout(new BoxLayout(textPanel, BoxLayout.Y_AXIS));
        textPanel.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 10));
        textPanel.setBackground(ColorPalette.background_color);

        JLabel idLabel = new JLabel(user.getId());
        idLabel.setFont(new Font("Arial", Font.BOLD, 14));

        JLabel followLabel = new JLabel(user.getNickName());
        followLabel.setFont(new Font("Arial", Font.PLAIN, 12));
        followLabel.setForeground(Color.GRAY);

        textPanel.add(idLabel);
        textPanel.add(followLabel);
        textPanel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        textPanel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
            	displayProfile(user);

            }
        });
        
        add(textPanel, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setBackground(ColorPalette.background_color);
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        followBtn = new CustomButton("Follow",ColorPalette.light_blue);
    	FollowBtnToggle.followBtnToggle(user,followBtn,this);
    		
    	

        
        buttonPanel.add(followBtn);
        
        add(buttonPanel, BorderLayout.EAST);
        
        setPreferredSize(new Dimension(300, 60));
	}
	
	private void displayProfile(User user) {

        JFrame profileFrame = new JFrame("Post Detail");
        profileFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        profileFrame.setSize(630, 740);
        profileFrame.setResizable(false);
        profileFrame.setLocationRelativeTo(this);


        OtherUserProfilePage profilePage = new OtherUserProfilePage(user);
        profileFrame.add(profilePage);
        profileFrame.setVisible(true);
    }
}
