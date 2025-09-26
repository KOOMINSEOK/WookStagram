package Component.Search;

import javax.swing.*;
import java.awt.*;

import Component.ColorPalette;
import Component.Profile.*;
import Manager.LoginManager;
import User.User;

public class OtherUserProfilePage extends JPanel {

    private JPanel contentPanel;
    public OtherUserProfilePage(User user) {
        setLayout(new GridBagLayout());


        contentPanel = new JPanel();
        contentPanel.setBackground(ColorPalette.background_color);
        contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS)); 
        contentPanel.setBorder(BorderFactory.createLineBorder(ColorPalette.borderline_color, 1));
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.NORTH;
        gbc.weighty = 1;

        add(contentPanel, gbc);


        LoginManager.getInstance().addObserver(() -> {
            updateContent(user);
        });



        updateContent(user);
    }

    private void updateContent(User user) {
        contentPanel.removeAll(); 


        ProfileTop top = new ProfileTop(user);
        top.setPreferredSize(new Dimension(610, 150));
        contentPanel.add(top);

        ProfileCenter center = new ProfileCenter(user);
        center.setPreferredSize(new Dimension(610, 550));
        center.setBorder(BorderFactory.createMatteBorder(1, 0, 0, 0, ColorPalette.borderline_color));
        contentPanel.add(center);

        contentPanel.revalidate();
        contentPanel.repaint();
    }
 
    
}

