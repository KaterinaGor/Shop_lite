
package app.mycomponents;

import java.awt.Dimension;
import java.awt.Font;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class ButtonComponent extends JPanel{
    private JLabel caption;
    private JButton button;
    /**
     * Компонент кнопки
     * @param text текст в кнопке
     * @param widthWindow ширина панели в которой находится кнопка
     * @param heightPanel высота панели в которой находится кнопка
     * @param left отступ слева от кнопки
     * @param buttonWidth ширина кнопки
     */
    public ButtonComponent(String text, int widthWindow,int heightPanel,int left, int buttonWidth) {
        initComponents(text, widthWindow, heightPanel,left, buttonWidth);
    }

    private void initComponents(String text, int widthWindow, int heightPanel,int left, int buttonWidth) {
        this.setPreferredSize(new Dimension(widthWindow,heightPanel));
        this.setMinimumSize(this.getPreferredSize());
        this.setMaximumSize(this.getPreferredSize());
        this.setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
//        this.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        caption = new JLabel("");
        caption.setPreferredSize(new Dimension(left,heightPanel));
        caption.setMinimumSize(caption.getPreferredSize());
        caption.setMaximumSize(caption.getPreferredSize());
//        caption.setBorder(BorderFactory.createLineBorder(Color.yellow));
        caption.setHorizontalAlignment(JLabel.RIGHT);
        caption.setFont(new Font("Tahoma",0,12));
        this.add(caption);
        button = new JButton(text);
        button.setPreferredSize(new Dimension(buttonWidth, 27));
        button.setMaximumSize(button.getPreferredSize());
        button.setMinimumSize(button.getPreferredSize());
        this.add(button);
    }

    public JButton getButton() {
        return button;
    }
}
