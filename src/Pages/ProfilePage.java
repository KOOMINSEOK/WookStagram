package Pages;

import javax.swing.*;
import java.awt.*;

import Component.ColorPalette;
import Component.Profile.*;
import Manager.LoginManager;
import User.User;

public class ProfilePage extends JPanel {

    private User loggedInUser;
    private JPanel contentPanel;

    public ProfilePage() {
        setLayout(new GridBagLayout());

        contentPanel = new JPanel();
        contentPanel.setBackground(ColorPalette.background_color);
        contentPanel.setMinimumSize(new Dimension(620, 700));
        contentPanel.setMaximumSize(new Dimension(620, 700));
        contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));
        contentPanel.setBorder(BorderFactory.createLineBorder(ColorPalette.borderline_color, 1));
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.NORTH;
        gbc.weighty = 1;

        add(contentPanel, gbc);

        LoginManager.getInstance().addObserver(this::updateContent);

        updateContent();
    }

    private void updateContent() {
        contentPanel.removeAll();

        if (LoginManager.getInstance().isLoggedIn()) {
            loggedInUser = LoginManager.getInstance().getLoggedInUser();

            ProfileTop top = new ProfileTop(loggedInUser);
            top.setPreferredSize(new Dimension(620, 150));
            contentPanel.add(top);

            ProfileCenter center = new ProfileCenter(loggedInUser);
            center.setPreferredSize(new Dimension(620, 550));
            center.setBorder(BorderFactory.createMatteBorder(1, 0, 0, 0, ColorPalette.borderline_color));
            contentPanel.add(center);

        } else {

            JLabel errorLabel = new JLabel("Please log in to view this page.");
            contentPanel.add(errorLabel);
        }


        contentPanel.revalidate();
        contentPanel.repaint();
    }
}
