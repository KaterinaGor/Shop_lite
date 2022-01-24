
package app.mycomponents;

import java.awt.Dimension;
import java.awt.Font;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class EditorComponent extends JPanel{
    
    private JLabel caption;
    private JTextField editor;
    
    public EditorComponent(String text, int widthWindow,int heightPanel, int editorWidth) {
        initComponents(text, widthWindow, heightPanel, 0, editorWidth);
    }
    /**
     * Компонент состоит из панели, на которой распологаются в строчку метка и текстовый редактор
     * @param text - текст слева от редактора
     * @param widthWindow - ширина компонента, обычно равна ширине окна
     * @param heightPanel - высота панели редактора
     * @param left - отступ от левого края окна до редактора. Ширина лейбела.
     * @param editorWidth - ширина редактора
     */
    public EditorComponent(String text, int widthWindow,int heightPanel,int left, int editorWidth) {
        initComponents(text, widthWindow, heightPanel,left, editorWidth);
    }

    private void initComponents(String text, int widthWindow, int heightPanel,int left, int editorWidth) {
        this.setPreferredSize(new Dimension(widthWindow,heightPanel));
        this.setMinimumSize(this.getPreferredSize());
        this.setMaximumSize(this.getPreferredSize());
        this.setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
//        this.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        caption = new JLabel(text);
        if(left > 0){
            caption.setPreferredSize(new Dimension(left,heightPanel));
        }else{
            caption.setPreferredSize(new Dimension(widthWindow/3,heightPanel));
        }
        caption.setMinimumSize(caption.getPreferredSize());
        caption.setMaximumSize(caption.getPreferredSize());
//        caption.setBorder(BorderFactory.createLineBorder(Color.yellow));
        caption.setHorizontalAlignment(JLabel.RIGHT);
        caption.setFont(new Font("Tahoma",0,12));
        this.add(caption);
        this.add(Box.createRigidArea(new Dimension(5, 0)));
        editor = new JTextField();
        editor.setPreferredSize(new Dimension(editorWidth, 27));
        editor.setMaximumSize(editor.getPreferredSize());
        editor.setMinimumSize(editor.getPreferredSize());
        this.add(editor);
    }

    public JLabel getCaption() {
        return caption;
    }

    public JTextField getEditor() {
        return editor;
    }
    
}
