
package app.mycomponents;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class CaptionComponent extends JPanel{

    public CaptionComponent(String text, int widthWindow,int heightPanel) {
        initComponents(text, widthWindow, heightPanel);
    }

    private void initComponents(String text, int widthWindow, int heightPanel) {
        this.setPreferredSize(new Dimension(widthWindow,heightPanel));
        this.setMinimumSize(this.getPreferredSize());
        this.setMaximumSize(this.getPreferredSize());
//        this.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        JLabel caption = new JLabel(text);
        caption.setPreferredSize(new Dimension(widthWindow,heightPanel));
        caption.setMinimumSize(caption.getPreferredSize());
        caption.setMaximumSize(caption.getPreferredSize());
//        caption.setBorder(BorderFactory.createLineBorder(Color.yellow));
        caption.setHorizontalAlignment(JLabel.CENTER);
        caption.setFont(new Font("Tahoma",1,16));
        this.add(caption);
    }
    
}
