/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package app.mycomponents.director;

import app.mycomponents.ButtonComponent;
import app.mycomponents.CaptionComponent;
import app.mycomponents.ComboBoxClientComponent;
import app.mycomponents.EditorComponent;
import app.mycomponents.InfoComponent;
import entity.Client;
import facade.ClientFacade;
import java.awt.Dimension;
import java.util.List;
import javax.swing.ComboBoxModel;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class TabDirectorComponent extends JPanel{
    private CaptionComponent captionComponent;
    private InfoComponent infoComponent;
    
    private EditorComponent nameComponent;
    private EditorComponent accountComponent;
    
    private ButtonComponent buttonComponent;
    private ComboBoxModel comboBoxModel;
    private ComboBoxClientComponent comboBoxClientComponent;
    
    public TabDirectorComponent(int widthWindow) {
        setComboBoxModel();
        initComponents(widthWindow);
    }

    private void initComponents(int widthPanel) {
        this.setPreferredSize(new Dimension(widthPanel,450));
        this.setMinimumSize(this.getPreferredSize());
        this.setMaximumSize(this.getPreferredSize());
        JTabbedPane tabDirector = new JTabbedPane();
        tabDirector.setPreferredSize(new Dimension(widthPanel-17,450));
        tabDirector.setMinimumSize(tabDirector.getPreferredSize());
        tabDirector.setMaximumSize(tabDirector.getPreferredSize());
        tabDirector.setAlignmentX(CENTER_ALIGNMENT);
        TabAddClientComponents tabAddClientComponents = new TabAddClientComponents(widthPanel);
        tabDirector.addTab("Регистрация клиента", tabAddClientComponents);

        TabEditClientComponents tabEditClientComponents = new TabEditClientComponents(widthPanel);
        tabDirector.addTab("Изменить данные клиента", tabEditClientComponents);
        this.add(tabDirector);
        tabDirector.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent ce) {
                tabEditClientComponents.addComboBoxModel();
            }
        });
    }  
    private void setComboBoxModel(){
        ClientFacade clientFacade = new ClientFacade();
        List<Client> clients = clientFacade.findAll();
        DefaultComboBoxModel<Client> defaultComboBoxModel = new DefaultComboBoxModel<>();
        for (Client client : clients) {
            defaultComboBoxModel.addElement(client);
        }
        comboBoxModel = defaultComboBoxModel;
    }
    
}
