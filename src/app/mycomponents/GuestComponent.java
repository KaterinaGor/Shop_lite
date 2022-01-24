
package app.mycomponents;

import app.GuiApp;
import java.awt.Dimension;
import javax.swing.BoxLayout;
import javax.swing.JPanel;

public class GuestComponent extends JPanel{
    private ListShoesComponent listShoesComponent;
   
    public GuestComponent() {
        setPreferredSize(new Dimension(GuiApp.WITH_WINDOWS,GuiApp.HEIGHT_WINDOWS-100));
        setMinimumSize(getPreferredSize());
        setMaximumSize(getPreferredSize());
        initComponents();
    }

    private void initComponents() {
//        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        listShoesComponent = new ListShoesComponent(true,"Список товаров магазина", GuiApp.WITH_WINDOWS,GuiApp.HEIGHT_WINDOWS-100,GuiApp.WITH_WINDOWS-20);
//        listBooksComponent.getJList().setModel(listBooksComponent.getListModel(true));
//        listBooksComponent.getJList().setCellRenderer(listBooksComponent.createListBooksRenderer());
        this.add(listShoesComponent);
        
        
    }

    public ListShoesComponent getListShoesComponent() {
        return listShoesComponent;
    }

    
}
