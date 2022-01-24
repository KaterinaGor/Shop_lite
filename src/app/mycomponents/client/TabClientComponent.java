
package app.mycomponents.client;

import app.mycomponents.ButtonComponent;
import app.mycomponents.CaptionComponent;
import app.mycomponents.ComboBoxClientComponent;
import app.mycomponents.InfoComponent;
import entity.Client;
import static java.awt.Component.CENTER_ALIGNMENT;
import java.awt.Dimension;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class TabClientComponent extends JPanel{
    private CaptionComponent captionComponent;
    private InfoComponent infoComponent;
    private ComboBoxClientComponent comboBoxClientComponent;
    private TabTakeOnShoesComponents tabTakeOnShoesComponents;
    
    private ButtonComponent buttonComponent;
    private Client client;
    public TabClientComponent(int widthPanel) {
        initComponents(widthPanel);
    }

    private void initComponents(int widthPanel) {
        this.setPreferredSize(new Dimension(widthPanel,450));
        this.setMinimumSize(this.getPreferredSize());
        this.setMaximumSize(this.getPreferredSize());
        JTabbedPane tabClientTabbed = new JTabbedPane();
        tabClientTabbed.setPreferredSize(new Dimension(widthPanel-17,450));
        tabClientTabbed.setMinimumSize(tabClientTabbed.getPreferredSize());
        tabClientTabbed.setMaximumSize(tabClientTabbed.getPreferredSize());
        tabClientTabbed.setAlignmentX(CENTER_ALIGNMENT);
        tabTakeOnShoesComponents = new TabTakeOnShoesComponents(widthPanel);
        tabClientTabbed.addTab("Купить товар", tabTakeOnShoesComponents);
        
        this.add(tabClientTabbed);
        tabClientTabbed.addChangeListener(new ChangeListener(){
            @Override
            public void stateChanged(ChangeEvent ce) {
                if(tabClientTabbed.indexOfTab("Купить товар")>0){
                    tabTakeOnShoesComponents.addComboBoxModel();
                }
                    
            }
            
        });
    }
    
}
