package Component;

import javax.imageio.ImageIO;
import javax.swing.*;

import User.User;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;

public class CircularImageIcon extends JLabel {

    public CircularImageIcon(User user, int diameter) {
        setIcon(createCircularImageIcon(user, diameter));
        setPreferredSize(new Dimension(diameter, diameter));
    }

    private ImageIcon createCircularImageIcon(User user, int diameter) {
        try {
        	BufferedImage image = ImageIO.read(new ByteArrayInputStream(user.getProfilePicture()));
            Image scaledImage = image.getScaledInstance(diameter, diameter, Image.SCALE_SMOOTH);
            
            BufferedImage circularImage = new BufferedImage(diameter, diameter, BufferedImage.TYPE_INT_ARGB);
            Graphics2D g2 = circularImage.createGraphics();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

            g2.setClip(new java.awt.geom.Ellipse2D.Float(0, 0, diameter, diameter));
            g2.drawImage(scaledImage, 0, 0, null);

            g2.setClip(null);
            g2.setColor(Color.GRAY);
            g2.setStroke(new BasicStroke(2));
            g2.drawOval(1, 1, diameter - 2, diameter - 2);

            g2.dispose();
            return new ImageIcon(circularImage);
        } catch (Exception e) {
            System.out.println("Error loading image: " + e.getMessage());
            return null;
        }
    }
}