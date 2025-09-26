package Component.Posting;

import javax.swing.*;

import Component.PhotoViewer.PhotoViewer;

import java.awt.*;
import java.util.ArrayList;

import EventHandler.PhotoUploader;

public class PhotoUploadPanel extends JPanel{
	private ArrayList<Image> imageList;
	private PhotoViewer photoViewer;
	private JButton uploadButton;
	
	public PhotoUploadPanel(PhotoUploader photoUploader) {
		setLayout(null);
		setBackground(Color.gray);
        setPreferredSize(new Dimension(400, 400));
		
        imageList = new ArrayList<>();
        photoViewer = new PhotoViewer(imageList);
        photoViewer.setBounds(0, 0, 400, 400);
        
        add(photoViewer);
        
        int panelWidth = this.getPreferredSize().width;
        int panelHeight = this.getPreferredSize().height;
        
        uploadButton = new JButton("select photos");
        uploadButton.setBounds((panelWidth-150)/2,(panelHeight-30)/2,150, 30);
        uploadButton.addActionListener(e ->{
            photoViewer.upLoadSetUp(photoUploader);
            uploadButton.setVisible(false);
        });
        add(uploadButton);	
        
        setComponentZOrder(uploadButton, 0);
        
	}
	public void resetUploadPanel() {
		photoViewer.reset();
	    uploadButton.setVisible(true);

	}
	public ArrayList<Image> getImageList(){
		return imageList;
	}
}
