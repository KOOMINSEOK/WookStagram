package Component.Profile;

import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.util.ArrayList;

import Component.CircularImageIcon;
import Component.ColorPalette;
import Component.CustomButton;
import Component.Search.SearchObject;
import FileHandler.UserFileHandler;
import Manager.LoginManager;
import User.User;

import util.FollowBtnToggle;

public class ProfileTop extends JPanel{
	private CustomButton profileBtn;
	public ProfileTop(User user){
		setLayout(new BorderLayout());
        setBackground(Color.WHITE);

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());
        mainPanel.setBackground(Color.WHITE);

        JPanel leftPanel = new JPanel();
        leftPanel.setBackground(Color.WHITE);
        leftPanel.setPreferredSize(new Dimension(150, 150));
        leftPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        CircularImageIcon profileImageLabel = new CircularImageIcon(user, 120);
        leftPanel.add(profileImageLabel);

        JPanel rightPanel = new JPanel();
        rightPanel.setLayout(new BoxLayout(rightPanel, BoxLayout.Y_AXIS));
        rightPanel.setBackground(Color.WHITE);

       
        JPanel userPanel = new JPanel();
        userPanel.setLayout(new FlowLayout(FlowLayout.LEFT, 10, 5));
        userPanel.setBackground(Color.WHITE);

        JLabel userNameLabel = new JLabel(user.getNickName());
        userNameLabel.setFont(new Font("Arial", Font.BOLD, 18));
        userNameLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        
        profileBtn = new CustomButton("Follow",ColorPalette.light_blue);
        FollowBtnToggle.followBtnToggle(user, profileBtn, this);
        
        
        userPanel.add(userNameLabel);
        userPanel.add(profileBtn);


        JPanel statsPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 5));
        statsPanel.setBackground(Color.WHITE);

        JLabel postsLabel = new JLabel("Post "+ user.getPostCount());
        JLabel followersLabel = new JLabel("Follower "+ user.getFollowerCount());
        JLabel followingLabel = new JLabel("Following "+user.getFollowingCount());
        
        followersLabel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        followersLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                displayUserList("Followers", user.getFollowerList());
            }
        });
        
        followingLabel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        followingLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                displayUserList("Followings", user.getFollowingList());
            }
        });
        
        statsPanel.add(postsLabel);
        statsPanel.add(followersLabel);
        statsPanel.add(followingLabel);


        rightPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        rightPanel.add(userPanel);
        rightPanel.add(statsPanel);


        mainPanel.add(leftPanel, BorderLayout.WEST);
        mainPanel.add(rightPanel, BorderLayout.CENTER);

        add(mainPanel, BorderLayout.NORTH);
	}
	
	private void displayUserList(String title, ArrayList<String> userList) {
	    JFrame listFrame = new JFrame(title);
	    listFrame.setSize(450, 400);
	    listFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
	    listFrame.setLayout(new BorderLayout());
	    listFrame.setResizable(false);
	    listFrame.setLocationRelativeTo(this);
	    
	    JPanel titlePanel = new JPanel();
	    titlePanel.setBackground(ColorPalette.background_color);
	    titlePanel.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));
	    titlePanel.setBorder(BorderFactory.createMatteBorder(1, 0, 0, 0, ColorPalette.borderline_color));
	    JLabel titleLabel = new JLabel(title);
	    titleLabel.setFont(new Font("Arial", Font.BOLD, 18));
	    titlePanel.add(titleLabel);
	    listFrame.add(titlePanel,BorderLayout.NORTH);
	    
	    JPanel listPanel = new JPanel();
	    listPanel.setLayout(new BoxLayout(listPanel, BoxLayout.Y_AXIS));
	    listPanel.setBackground(Color.WHITE);


	    updateUserList(listPanel, userList);

	    JScrollPane scrollPane = new JScrollPane(listPanel);
	    scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);

	    listFrame.add(scrollPane, BorderLayout.CENTER);
	    listFrame.setVisible(true);


	    LoginManager.getInstance().addObserver(() -> {
	        updateUserList(listPanel, userList);
	        listPanel.revalidate();
	        listPanel.repaint();
	    });
	}


	private void updateUserList(JPanel listPanel, ArrayList<String> userList) {
	    listPanel.removeAll();
	    for (String userId : userList) {
	        try {
	            User user = UserFileHandler.loadUser(userId);
	            SearchObject userObject = new SearchObject(user);
	            userObject.setPreferredSize(new Dimension(440, 60));
	            userObject.setMaximumSize(new Dimension(440, 60));
	            listPanel.add(userObject);
	        } catch (ClassNotFoundException e) {
	            e.printStackTrace();
	        }
	    }
	}
	

}
