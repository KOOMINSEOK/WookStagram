package Component.PhotoViewer;

import javax.swing.*;
import java.awt.*;

public class ArrowBtn extends JButton {
	public ArrowBtn(ImageIcon originalIcon) {
	     
		int newWidth = 35;  
	     int newHeight = 35; 
	     Image resizedImage = originalIcon.getImage().getScaledInstance(newWidth, newHeight, Image.SCALE_SMOOTH);
	     ImageIcon resizedIcon = new ImageIcon(resizedImage);
	     this.setIcon(resizedIcon);
	     this.setFocusPainted(false);
	     this.setContentAreaFilled(false);
	     this.setOpaque(false);  
	     this.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
	     
	}
}
