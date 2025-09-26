import javax.swing.*;

import Component.CircularImageIcon;
import Component.ColorPalette;
import Component.CustomButton;
import Component.Logo;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import Pages.*;

import Manager.LoginManager;


public class SNSUI extends JFrame {

	private CardLayout cardLayout;
	private JPanel cardPanel, buttonPanel, buttonContainer;

    public SNSUI() {
        JFrame frame = new JFrame("SNS UI");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 800);
        frame.setLayout(new BorderLayout());
        frame.setResizable(false);
        frame.setLocationRelativeTo(null); 

        //top bar
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(ColorPalette.background_color);
        headerPanel.setPreferredSize(new Dimension(800, 50));
        headerPanel.setBorder(BorderFactory.createLineBorder(ColorPalette.borderline_color, 1));
        
        JLabel logoLabel = new Logo(35);
        logoLabel.setHorizontalTextPosition(SwingConstants.RIGHT); 
        logoLabel.setVerticalTextPosition(SwingConstants.CENTER);
        logoLabel.setBorder(BorderFactory.createEmptyBorder(0, 20, 0, 0));
        headerPanel.add(logoLabel, BorderLayout.WEST);
        
        buttonPanel = new JPanel();

        LoginManager.getInstance().addObserver(this::updateTopBar);
    	
        
        updateTopBar();
        
        headerPanel.add(buttonPanel, BorderLayout.EAST);
        
        //page naviagtion
        cardLayout = new CardLayout();
        cardPanel = new JPanel(cardLayout);
        
        JScrollPane mainPanel = new MainPage();  
        cardPanel.add(mainPanel, "MainPage");
        
        JPanel postingPanel = new PostingPage();  
        cardPanel.add(postingPanel, "PostingPage");
        
        JPanel profilePanel = new ProfilePage();  
        cardPanel.add(profilePanel, "ProfilePage");
        
        JPanel searchPanel = new SearchPage();
        cardPanel.add(searchPanel, "SearchPage");
        
        JPanel LoginPanel = new LoginPage(cardPanel,cardLayout);
        cardPanel.add(LoginPanel, "LoginPage");
        
        JPanel SignUpPanel = new SignUpPage(cardPanel,cardLayout);
        cardPanel.add(SignUpPanel, "SignUpPage");

        cardLayout.show(cardPanel, "LoginPage");
        
        // bottom bar
        JPanel bottomBarPanel = new JPanel();
        bottomBarPanel.setBackground(new Color(47, 49, 54));
        bottomBarPanel.setPreferredSize(new Dimension(800, 50));
        bottomBarPanel.setLayout(new BorderLayout());
        
        buttonContainer = new JPanel();
        buttonContainer.setLayout(new BoxLayout(buttonContainer, BoxLayout.X_AXIS));
        buttonContainer.setBackground(ColorPalette.background_color);
        buttonContainer.setMaximumSize(new Dimension(800, 40));

        LoginManager.getInstance().addObserver(this::updateBottomBar);
        
        updateBottomBar();
        
        bottomBarPanel.add(buttonContainer, BorderLayout.CENTER);
        
        frame.add(headerPanel, BorderLayout.NORTH);
        frame.add(cardPanel, BorderLayout.CENTER);
        frame.add(bottomBarPanel, BorderLayout.SOUTH);
        frame.setVisible(true);
    }

    public void updateTopBar() {
    	buttonPanel.removeAll();
    	
    	if(!LoginManager.getInstance().isLoggedIn()) {
    		 
    	     buttonPanel.setBackground(ColorPalette.background_color);
    	     buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 10));
    	        
    	     CustomButton logBtn = new CustomButton("Login", ColorPalette.light_blue);
    	     logBtn.addActionListener(e->{
    	     	cardLayout.show(cardPanel, "LoginPage");
    	     });
    	        
    	     buttonPanel.add(logBtn);
    	}
    	else {
    		buttonPanel.setBackground(ColorPalette.background_color);
   	     	buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 10));
   	        
   	     	JLabel userNickName = new JLabel(LoginManager.getInstance().getLoggedInUser().getNickName());
   	     	userNickName.setFont(new Font("Serif", Font.ITALIC, 16));
   	     	buttonPanel.add(userNickName);
   	     	
   	     	CustomButton logBtn = new CustomButton("Logout", ColorPalette.light_blue);
   	     	logBtn.addActionListener(e->{
   	     		LoginManager.getInstance().logout();
		    	 JOptionPane.showMessageDialog(this, "LogOut");
   	     		cardLayout.show(cardPanel, "LoginPage");
   	     	});
   	        
   	     	buttonPanel.add(logBtn);
    	}
    	
    	
    	buttonPanel.revalidate();
    	buttonPanel.repaint();
    }
    private void updateBottomBar() {
    	buttonContainer.removeAll();
        String[] menuItems = {"Main","Search","Posting", "Profile"};
        for(int i = 0; i< menuItems.length; ++i) {
        	
        	JPanel buttonPanel = new JPanel();
        	buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
        	buttonPanel.setBackground(ColorPalette.background_color);
			buttonPanel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
			buttonPanel.setBorder(BorderFactory.createMatteBorder(0, 0, 0, 1, ColorPalette.borderline_color));
        	JLabel imageLabel = new JLabel();
        	
        	if(LoginManager.getInstance().isLoggedIn() && i == menuItems.length-1) {
        		CircularImageIcon profileLabel = new CircularImageIcon(LoginManager.getInstance().getLoggedInUser(),40);
        		imageLabel.setIcon(profileLabel.getIcon());
        	}
        	else {
        		ImageIcon image = new ImageIcon("src/UI_Icon/"+menuItems[i]+".png");
            	Image resizedImage = image.getImage().getScaledInstance(35, 35, Image.SCALE_SMOOTH);
            	ImageIcon resizedIcon = new ImageIcon(resizedImage);
            	imageLabel.setIcon(resizedIcon);
        	}

        	buttonPanel.add(imageLabel);
            buttonContainer.add(Box.createRigidArea(new Dimension(10, 0)));
            int nextPanel = (i%menuItems.length);
            buttonPanel.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                	cardLayout.show(cardPanel,menuItems[nextPanel]+"Page");
                }
            });
            buttonContainer.add(buttonPanel);
        	
        }
        buttonContainer.revalidate();
        buttonContainer.repaint();

    }
    public static void main(String[] args) {
        new SNSUI();
    }
}
