/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package app.mycomponents.manager;

import app.mycomponents.client.*;
import app.mycomponents.ButtonComponent;
import app.mycomponents.CaptionComponent;
import app.mycomponents.ComboBoxClientComponent;
import app.mycomponents.InfoComponent;
import app.mycomponents.client.TabTakeOnShoesComponents;

import entity.Client;
import facade.ClientFacade;
import static java.awt.Component.CENTER_ALIGNMENT;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.List;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ComboBoxModel;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class TabManagerComponent extends JPanel{
    private CaptionComponent captionComponent;
    private InfoComponent infoComponent;
    private TabAddShoesComponent tabAddShoesComponent;
    private TabEditShoesComponent tabEditShoesComponent;
   
    private ButtonComponent buttonComponent;
    public TabManagerComponent(int widthPanel) {
        initComponents(widthPanel);
    }

    private void initComponents(int widthPanel) {
        this.setPreferredSize(new Dimension(widthPanel,450));
        this.setMinimumSize(this.getPreferredSize());
        this.setMaximumSize(this.getPreferredSize());
        JTabbedPane tabManagerTabbed = new JTabbedPane();
        tabManagerTabbed.setPreferredSize(new Dimension(widthPanel-17,450));
        tabManagerTabbed.setMinimumSize(tabManagerTabbed.getPreferredSize());
        tabManagerTabbed.setMaximumSize(tabManagerTabbed.getPreferredSize());
        tabManagerTabbed.setAlignmentX(CENTER_ALIGNMENT);
        
        tabAddShoesComponent = new TabAddShoesComponent(widthPanel);
        tabManagerTabbed.addTab("Добавить новый товар", tabAddShoesComponent);
        
        tabEditShoesComponent = new TabEditShoesComponent(widthPanel);
        tabManagerTabbed.addTab("Редактировать", tabEditShoesComponent);
        
        this.add(tabManagerTabbed);
        tabManagerTabbed.addChangeListener(new ChangeListener(){
            @Override
            public void stateChanged(ChangeEvent ce) {
                infoComponent.getInfo().setText("");
            }
        });
    }
    
}
