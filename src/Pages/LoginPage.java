package Pages;

import java.awt.*;
import java.util.ArrayList;

import javax.swing.*;
import javax.swing.event.*;

import Component.PlaceHolderTextField;
import Component.CustomButton;
import Component.Logo;
import Component.ColorPalette;

import User.*;
import FileHandler.UserFileHandler;

import Manager.LoginManager;

public class LoginPage extends JPanel {
	
	private PlaceHolderTextField idInput;
	private JPasswordField pwInput; 
	private CustomButton loginBtn, signUpBtn;
	private User loginUser;
	private String id,pw;
	public LoginPage(JPanel parentPanel, CardLayout cardLayout) {
		 
		 setLayout(new GridBagLayout());
		 
		 JPanel contentPanel = new JPanel();
		 contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS) );
	     contentPanel.setPreferredSize(new Dimension(300,400));
	     contentPanel.setBackground(ColorPalette.background_color);
	     contentPanel.setBorder(BorderFactory.createLineBorder(ColorPalette.borderline_color, 1));
	    
	     
	     JLabel logoLabel = new Logo(80);
	     
	     idInput = new PlaceHolderTextField("Enter your ID");
	     idInput.setPreferredSize(new Dimension(200, 45));
	     idInput.setMaximumSize(new Dimension(200, 45));
	     idInput.setBorder(BorderFactory.createTitledBorder("ID"));
	     
	     pwInput = new JPasswordField();
	     pwInput.setPreferredSize(new Dimension(200, 45));
	     pwInput.setMaximumSize(new Dimension(200, 45));
	     pwInput.setBorder(BorderFactory.createTitledBorder("Password"));
	        
	     loginBtn = new CustomButton("Login", ColorPalette.light_blue);
	     loginBtn.setMaximumSize(new Dimension(200, 30));
	     loginBtn.setAlignmentX(Component.CENTER_ALIGNMENT);
	     loginBtn.addActionListener(e->{
	    	 
	    	 id = idInput.getText().trim();
	    	 pw = new String(pwInput.getPassword()).trim();
		     
		     if(loginValidate(id,pw)) {
		    	 reset();
		    	 JOptionPane.showMessageDialog(this, "Login Success");
		    	 cardLayout.show(parentPanel,"MainPage");
		     }
		     else {
		    	 reset();
		    	 JOptionPane.showMessageDialog(this, "Login Failed");
		     }
	     });
	     loginBtn.setEnabled(false);
	     
	     signUpBtn = new CustomButton("SignUP", ColorPalette.light_purple);
	     signUpBtn.setMaximumSize(new Dimension(200, 30));
	     signUpBtn.setAlignmentX(Component.CENTER_ALIGNMENT);
	     signUpBtn.addActionListener(e->cardLayout.show(parentPanel,"SignUpPage"));
	     
	     DocumentListener inputListener = createInputListener();
	     idInput.getDocument().addDocumentListener(inputListener);
	     pwInput.getDocument().addDocumentListener(inputListener);

	     
	     contentPanel.add(Box.createVerticalStrut(30));
	     logoLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
	     contentPanel.add(logoLabel);
	     contentPanel.add(Box.createVerticalStrut(10));
	     contentPanel.add(idInput);
	     contentPanel.add(Box.createVerticalStrut(10));
	     contentPanel.add(pwInput);
	     contentPanel.add(Box.createVerticalStrut(10));
	     contentPanel.add(loginBtn);
	     contentPanel.add(Box.createVerticalStrut(10));
	     contentPanel.add(signUpBtn);
	     
	     add(contentPanel);
	    
	}
	
	public DocumentListener createInputListener() {
			DocumentListener inputListener = new DocumentListener() {
            private void validateInputs() {
                String idText = idInput.getText().trim();
                String pwdText = new String(pwInput.getPassword()).trim();
                loginBtn.setEnabled(!idText.isEmpty() && !pwdText.isEmpty());
            }

            @Override
            public void insertUpdate(DocumentEvent e) {
                validateInputs();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                validateInputs();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                validateInputs();
            }
        };
        return inputListener;
	}
	public boolean loginValidate(String id, String pw) {
		 if(!UserFileHandler.checkUserExists(id)) {
			 return false;
		 }
		 else {
			 loginUser = new User(id,pw);
			 try {
				User userChecker = UserFileHandler.loadUser(id);
				if(userChecker == null)
					 return false;
				 if(userChecker.compare(loginUser)) {
					 LoginManager.getInstance().logIn(userChecker);
					 return true;
				 }
				 else return false;
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
				return false;
			}
		 }
	}
	public void reset() {
		idInput.setText("");
		pwInput.setText("");
	}
}
