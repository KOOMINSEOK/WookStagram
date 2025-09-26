package Component.PhotoViewer;

import javax.swing.*;

import java.awt.*;
import java.util.ArrayList;

import EventHandler.PhotoUploader;

public class PhotoViewer extends JPanel{
	private ArrayList<Image> imageList;
	private JLabel imageLabel;
	private PageIndicator pageIdc;
	private int currentIdx = 0;
	
	private JButton nextBtn,prevBtn;
	public PhotoViewer(ArrayList<Image> OriginimageList) {
		
		setLayout(null);
		setBackground(Color.gray);
        setPreferredSize(new Dimension(400, 400)); 
		
        this.imageList = OriginimageList;
        
        imageLabel = new JLabel("no images");
        imageLabel.setBounds(0, 0, 400, 400);
        imageLabel.setVisible(false); 
        add(imageLabel);
        
        int panelWidth = this.getPreferredSize().width;
        int panelHeight = this.getPreferredSize().height;
        	
        
        //pagination
        ImageIcon nextIcon = new ImageIcon("src/UI_Icon/next.png"); 
        nextBtn = new ArrowBtn(nextIcon);
        nextBtn.setBounds((panelWidth-35),(panelHeight-30)/2,35, 35);
        nextBtn.setVisible(false);
        add(nextBtn);
        
        ImageIcon prevIcon = new ImageIcon("src/UI_Icon/prev.png"); 
        prevBtn = new ArrowBtn(prevIcon);
        prevBtn.setBounds(0,(panelHeight-30)/2,35, 35);
        prevBtn.setVisible(false);
        add(prevBtn);
        
        
        if(imageList != null) {
        	pageIdc = new PageIndicator((currentIdx+1)+"/"+imageList.size(),30);
        }
        else {
        	pageIdc = new PageIndicator("0/0",30);
        }
        pageIdc.setBounds((panelWidth-40), 5, 35, 25);
        pageIdc.setBackground(new Color(0,0,0,180));
        pageIdc.setForeground(Color.white);
        pageIdc.setVisible(false);
        add(pageIdc);
        
        //make the button come first
        setComponentZOrder(nextBtn, 0);
        setComponentZOrder(prevBtn, 0);
        setComponentZOrder(pageIdc, 0);

        
        nextBtn.addActionListener(e->showNextPhoto());
        prevBtn.addActionListener(e->showPrevPhoto());
        
        
	}
	private void showPhoto(int index) {
        Image image = imageList.get(index);
        imageLabel.setIcon(new ImageIcon(image));
        imageLabel.setText(null); 
	}
	private void showNextPhoto() {
        if (!imageList.isEmpty()) {
        	if(currentIdx != imageList.size()-1) {
        		currentIdx = (currentIdx + 1) % imageList.size(); 
        		pageIdc.setText((currentIdx+1)+"/"+imageList.size());
        		showPhoto(currentIdx);
            }
        } else {
            JOptionPane.showMessageDialog(this, "No photos loaded. Please load photos first.");
        }
    }
	private void showPrevPhoto() {
        if (!imageList.isEmpty()) {
        	if(currentIdx != 0) {
        		currentIdx = (currentIdx - 1) % imageList.size(); 
        		pageIdc.setText((currentIdx+1)+"/"+imageList.size());
        		showPhoto(currentIdx);
        		
            }
        } else {
            JOptionPane.showMessageDialog(this, "No photos loaded. Please load photos first.");
        }
    }	
	public void setUp() {

    	showPhoto(currentIdx);
    	imageLabel.setVisible(true);
    	nextBtn.setVisible(true);
    	prevBtn.setVisible(true);

    	pageIdc.setText((currentIdx + 1) + "/" + imageList.size());
    	pageIdc.setVisible(true);
	}
	public void upLoadSetUp(PhotoUploader photoUploader) {
		photoUploader.uploadPhoto(imageLabel, imageList);
		setUp();
	}
	public void reset() { //for reselect photos
		imageList.clear();
	    currentIdx = 0;

	    imageLabel.setIcon(null);
	    imageLabel.setText("no images");
	    imageLabel.setVisible(false);

	    nextBtn.setVisible(false);
	    prevBtn.setVisible(false);
	    pageIdc.setVisible(false);

	}
	
	
}
