package Component;

import java.awt.Color;
import java.awt.Font;
import java.awt.Image;

import javax.swing.*;

public class Logo extends JLabel {
	public Logo(int size) {
	     ImageIcon logo = new ImageIcon("src/UI_Icon/WookStaGram_Logo.png");
	     Image originalLogo = logo.getImage();
	     Image resizedLogo = originalLogo.getScaledInstance(size, size, Image.SCALE_SMOOTH);
	     ImageIcon resizedIcon = new ImageIcon(resizedLogo);
	     this.setIcon(resizedIcon);
	     this.setText("WookStagram");
	     this.setFont(new Font("Serif", Font.ITALIC, 18));
	     this.setForeground(ColorPalette.font_color);
		 
	     this.setHorizontalTextPosition(SwingConstants.CENTER);
	     this.setVerticalTextPosition(SwingConstants.BOTTOM);

	}
}
