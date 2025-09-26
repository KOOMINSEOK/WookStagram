package Component.Profile;

import javax.swing.*;
import Component.CircularImageIcon;
import java.io.File;
import java.io.IOException;
import FileHandler.UserFileHandler;
import Manager.LoginManager;
import User.User;

public class EditProfilePanel extends JPanel {
    private CircularImageIcon profilePictureLabel;
    private JTextField nicknameField;
    private JButton uploadButton;
    private JButton saveButton;
    private User user;

    public EditProfilePanel(User user) {
        this.user = user;
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        profilePictureLabel = new CircularImageIcon(user, 100);
        profilePictureLabel.setHorizontalAlignment(JLabel.CENTER);
        add(profilePictureLabel);

        uploadButton = new JButton("Upload Picture");
        uploadButton.addActionListener(e -> uploadProfilePicture());
        add(uploadButton);

        add(new JLabel("Nickname:"));
        nicknameField = new JTextField(user.getNickName());
        add(nicknameField);

        saveButton = new JButton("Save Changes");
        saveButton.addActionListener(e -> saveChanges());
        add(saveButton);
    }

    private void uploadProfilePicture() {
        JFileChooser fileChooser = new JFileChooser();
        int result = fileChooser.showOpenDialog(this);
        if (result == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();
            user.setProfilePicture(selectedFile);

            profilePictureLabel.setIcon(new CircularImageIcon(user, 100).getIcon());
        }
    }

    private void saveChanges() {
        String newNickname = nicknameField.getText().trim();
        if (!newNickname.isEmpty()) {
            user.setNickName(newNickname);
        }

        try {
            UserFileHandler.saveUser(user);
            LoginManager.getInstance().setLoggedInUser(user);
            LoginManager.getInstance().notifyObservers();
            JOptionPane.showMessageDialog(this, "Profile updated successfully!");
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Error saving profile: " + e.getMessage());
        }
    }

}
