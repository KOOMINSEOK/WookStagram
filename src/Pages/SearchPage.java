package Pages;

import javax.swing.*;

import Component.CustomButton;
import Component.PlaceHolderTextField;
import Component.Search.SearchObject;
import Manager.LoginManager;
import Component.ColorPalette;

import java.awt.*;
import java.awt.event.*;
import java.io.*;

import User.User;

public class SearchPage extends JPanel {
    private PlaceHolderTextField searchField;
    private CustomButton searchButton;
    private JPanel resultPanel, contentPanel;

    public SearchPage() {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS)); 

        contentPanel = new JPanel();
        contentPanel.setLayout(new BorderLayout());
        contentPanel.setPreferredSize(new Dimension(600, 400));
        contentPanel.setMaximumSize(new Dimension(600,700));
        
        contentPanel.setBackground(ColorPalette.background_color);
        contentPanel.setBorder(BorderFactory.createLineBorder(ColorPalette.borderline_color, 1));

        JPanel searchPanel = new JPanel();
        searchPanel.setLayout(new FlowLayout());
        searchPanel.setPreferredSize(new Dimension(600, 50));
        searchPanel.setMaximumSize(new Dimension(600, 50)); 
        searchPanel.setBackground(ColorPalette.background_color);
        searchPanel.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, ColorPalette.borderline_color));

        searchField = new PlaceHolderTextField("Search");
        searchField.setColumns(20);
        searchButton = new CustomButton("Search", ColorPalette.light_blue);

        searchPanel.add(new JLabel("User ID:"));
        searchPanel.add(searchField);
        searchPanel.add(searchButton);

        contentPanel.add(searchPanel, BorderLayout.NORTH);

        resultPanel = new JPanel();
        resultPanel.setLayout(new BoxLayout(resultPanel, BoxLayout.Y_AXIS));
        resultPanel.setBackground(ColorPalette.background_color);
        resultPanel.setPreferredSize(new Dimension(600, 400));
        resultPanel.setMaximumSize(new Dimension(600, 400)); 


        contentPanel.add(resultPanel, BorderLayout.CENTER);

        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                searchUser(searchField.getText().trim());
            }
        });
        
        
        add(contentPanel);
        LoginManager.getInstance().addObserver(this::updateSearchResults);
    }


    private void searchUser(String userId) {
        resultPanel.removeAll();

        if (userId==null || userId.equals("")) {
            resultPanel.add(new JLabel("Please enter a User ID."));
            resultPanel.revalidate();
            resultPanel.repaint();
            return;
        }

        File userFolder = new File("users/" + userId);
        if (!userFolder.exists() || !userFolder.isDirectory()) {
            resultPanel.add(new JLabel("User not found."));
        } else {
            File userFile = new File(userFolder, "/"+userId+".dat");
            if (userFile.exists()) {
                try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(userFile))) {
                    User user = (User) ois.readObject();
                    SearchObject searchObject = new SearchObject(user);
                    searchObject.setPreferredSize(new Dimension(600, 60)); 
                    searchObject.setMaximumSize(new Dimension(600, 60));   

                    resultPanel.add(searchObject);
                } catch (Exception ex) {
                    resultPanel.add(new JLabel("Error loading user data: " + ex.getMessage()));
                }
            } else {
                resultPanel.add(new JLabel("No user data found in the folder."));
            }
        }

        resultPanel.revalidate();
        resultPanel.repaint();
    }
    
    private void updateSearchResults() {
        String currentSearchText = searchField.getText().trim();
        if (!currentSearchText.isEmpty()) {
            searchUser(currentSearchText);
        }
    }
}
