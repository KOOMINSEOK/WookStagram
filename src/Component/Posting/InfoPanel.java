package Component.Posting;

import javax.swing.*;

import Component.PlaceHolderTextArea;

import java.awt.*;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
public class InfoPanel extends JPanel{
	
	private Color borderColor = new Color(192,192,192);
	
	private JLabel nameLabel;
	private JPanel btnPanel;
	private JButton reUploadBtn,postBtn;
	
	private PlaceHolderTextArea photoTA;
	private String photoInfo = "";
	
	public InfoPanel() {
		setLayout(new BorderLayout());
        setBorder(BorderFactory.createLineBorder(borderColor, 1));
        
        //top
        nameLabel = new JLabel("user Name");
        nameLabel.setBorder(BorderFactory.createLineBorder(borderColor, 1));
        add(nameLabel, BorderLayout.NORTH);
        
        //center
        photoTA = new PlaceHolderTextArea("write what you want!");
        photoTA.setPreferredSize(new Dimension(400, 300));
        photoTA.setFont(getFont().deriveFont(16f));
        photoTA.setBorder(BorderFactory.createLineBorder(borderColor, 1));
        photoTA.addFocusListener(new FocusListener() {
            @Override
            public void focusLost(FocusEvent e) {
            	photoInfo = photoTA.getText();
            }
			@Override
			public void focusGained(FocusEvent e) {	
			}
        });

        add(photoTA, BorderLayout.CENTER);
        
        //bottom
        btnPanel = new JPanel();
        btnPanel.setPreferredSize(new Dimension(400, 100));
        btnPanel.setBorder(BorderFactory.createLineBorder(borderColor, 1));
        reUploadBtn = new JButton("select another Photos");
        btnPanel.add(reUploadBtn);
        postBtn = new JButton("Post");
        btnPanel.add(postBtn);
        
        add(btnPanel,BorderLayout.SOUTH);
	}
	public JButton getReUploadBtn() {
	    return reUploadBtn;
	}
	public JButton getPostBtn() {
		return postBtn;
	}
	public String getPhotoInfo() {
		return photoInfo;
	}
	public void resetInfoPanel() {
		photoTA.clearText();
	}
	
}
