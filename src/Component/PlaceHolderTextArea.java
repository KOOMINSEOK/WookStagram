package Component;

import javax.swing.*;
import java.awt.event.*;
import java.awt.*;

public class PlaceHolderTextArea extends JTextArea {
	private String placeholder;      
	private boolean showingPlaceholder; 

    public PlaceHolderTextArea(String placeholder) {
        this.placeholder = placeholder;
        this.showingPlaceholder = true;

        setText(placeholder);
        setForeground(Color.GRAY);

        addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                
                if (showingPlaceholder) {
                    setText("");
                    setForeground(Color.BLACK);
                    showingPlaceholder = false;
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                
                if (getText().isEmpty()) {
                    setText(placeholder);
                    setForeground(Color.GRAY);
                    showingPlaceholder = true;
                }
            }
        });
    }

    @Override
    public String getText() {
        
        return showingPlaceholder ? "" : super.getText();
    }
    public void clearText() {
        setText(placeholder); 
        setForeground(Color.GRAY);
        showingPlaceholder = true;
    }
}
