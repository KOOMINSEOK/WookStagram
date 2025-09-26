package Pages;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import Component.PlaceHolderTextField;
import Component.CustomButton;
import Component.Logo;
import Component.ColorPalette;

import User.*;
import FileHandler.*;

public class SignUpPage extends JPanel {
	private String id;
	private String pw;
	private String nickName;
	private User newUser;
	private PlaceHolderTextField idInput, nickNameInput;
	private JPasswordField pwInput; 
	private CustomButton signUpBtn;
	
	public SignUpPage(JPanel parentPanel, CardLayout cardLayout) {
		 
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
	     
	     
	     nickNameInput = new PlaceHolderTextField("Enter your NickName");
	     nickNameInput.setPreferredSize(new Dimension(200, 45));
	     nickNameInput.setMaximumSize(new Dimension(200, 45));
	     nickNameInput.setBorder(BorderFactory.createTitledBorder("NickName"));
	     
	     
	     signUpBtn = new CustomButton("SignUP", ColorPalette.light_purple);
	     signUpBtn.setMaximumSize(new Dimension(200, 30));
	     signUpBtn.setAlignmentX(Component.CENTER_ALIGNMENT);
	     signUpBtn.addActionListener(e->{
	    	 if(idInput.getText() != null && pwInput.getPassword() != null && nickNameInput.getText() != null);
	    	 	id = idInput.getText().trim();
	    	 	pw = new String(pwInput.getPassword()).trim();
	    	 	nickName = nickNameInput.getText().trim();
	    	 	if(signUpValidate(id)) {
		    	 	newUser = new User(id,pw,nickName);
		    	 	File defaultPicture = new File("src/UI_Icon/Profile.png");
		    	 	newUser.setProfilePicture(defaultPicture);
		    	 	try {
						UserFileHandler.saveUser(newUser);
					} catch (IOException e1) {
						e1.printStackTrace();
					}
		    	 	reset();
		    	 	JOptionPane.showMessageDialog(this, "Success to creat account");
		    	 	cardLayout.show(parentPanel,"LoginPage");
	    	 	}
	    	 	else {
	    	 		 reset();
			    	 JOptionPane.showMessageDialog(this, "ID is already exists");
	    	 	}
	    	 		
	    	 });
	     signUpBtn.setEnabled(false);
	     
	     DocumentListener inputListener = createInputListener();
	     idInput.getDocument().addDocumentListener(inputListener);
	     pwInput.getDocument().addDocumentListener(inputListener);
	     nickNameInput.getDocument().addDocumentListener(inputListener);

	     
	     
	     JPanel moveToLoginPanel = new JPanel();
	     moveToLoginPanel.setLayout(new FlowLayout());
	     moveToLoginPanel.setBackground(ColorPalette.background_color);
	     JLabel textLabel = new JLabel("If you already have an account");
	     CustomButton moveToLoginBtn = new CustomButton("login",ColorPalette.light_blue);
	     moveToLoginBtn.addActionListener(e-> {cardLayout.show(parentPanel,"LoginPage"); reset();});
	     moveToLoginPanel.add(textLabel);
	     moveToLoginPanel.add(moveToLoginBtn);

	     
	     contentPanel.add(Box.createVerticalStrut(30));
	     logoLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
	     contentPanel.add(logoLabel);
	     contentPanel.add(Box.createVerticalStrut(10));
	     contentPanel.add(idInput);
	     contentPanel.add(Box.createVerticalStrut(10));
	     contentPanel.add(pwInput);
	     contentPanel.add(Box.createVerticalStrut(10));
	     contentPanel.add(nickNameInput);
	     contentPanel.add(Box.createVerticalStrut(10));
	     contentPanel.add(signUpBtn);
	     contentPanel.add(Box.createVerticalStrut(10));
	     contentPanel.add(moveToLoginPanel);
	     add(contentPanel);
	     
	     
   
	}
	
	public boolean signUpValidate(String id) {
		 if(!UserFileHandler.checkUserExists(id)) {
			 return true;
		 }
		 else {
			 return false;
		 }
	}
	
	public DocumentListener createInputListener() {
		DocumentListener inputListener = new DocumentListener() {
        private void validateInputs() {
            String idText = idInput.getText().trim();
            String pwdText = new String(pwInput.getPassword()).trim();
            String nickNameText =nickNameInput.getText().trim();
            signUpBtn.setEnabled(!idText.isEmpty() && !pwdText.isEmpty() && !nickNameText.isEmpty());
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
	public void reset() {
		idInput.setText("");
		pwInput.setText("");
		nickNameInput.setText("");
	}
}
