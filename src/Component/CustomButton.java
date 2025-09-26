package Component;

import javax.swing.*;
import java.awt.*;

import java.awt.event.*;

public class CustomButton extends JButton {
	private Color buttonColor; 
    private Color pressedColor; 

    public CustomButton(String text, Color buttonColor) {
        super(text);
        this.buttonColor = buttonColor;
        this.pressedColor = buttonColor.darker();
        
        setFocusPainted(false);
        setBorderPainted(false);
        setContentAreaFilled(false);
        setOpaque(false);
        setForeground(Color.WHITE);
        setFont(new Font("Arial", Font.BOLD, 14));
        setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        
        addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                repaint();
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                repaint();
            }
        });
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);


        if (getModel().isPressed()) {
            g2.setColor(pressedColor); 
        } else {
            g2.setColor(buttonColor); 
        }
        
        g2.fillRoundRect(0, 0, getWidth(), getHeight(), 20, 20);

        super.paintComponent(g);
        g2.dispose();
    }

    @Override
    protected void paintBorder(Graphics g) {
    	//remove border
    }
    @Override
    public void setBackground(Color buttonColor) {
    	this.buttonColor = buttonColor;
    	this.pressedColor = buttonColor.darker();
    }
}
