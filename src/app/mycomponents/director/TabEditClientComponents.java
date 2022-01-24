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

public class TabEditClientComponents extends JPanel{
    private CaptionComponent captionComponent;
    private InfoComponent infoComponent;
    private ComboBoxModel comboBoxModel;
    private ComboBoxClientComponent comboBoxClientComponent;
    
    private EditorComponent nameComponent;
    private EditorComponent accountComponent;
    private EditorComponent moneyComponent;
    private ButtonComponent buttonComponent;
    private Client client;
    public TabEditClientComponents(int widthPanel) {
//        this.comboBoxModel = comboBoxModel;
        initComponents(widthPanel);
    }

    private void initComponents(int widthPanel) {
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        this.add(Box.createRigidArea(new Dimension(0,15)));
        captionComponent = new CaptionComponent("Изменение данных клиента", widthPanel, 31);
        this.add(captionComponent); 
        infoComponent = new InfoComponent("", widthPanel, 31);
        this.add(infoComponent);
        this.add(Box.createRigidArea(new Dimension(0,10)));
        nameComponent = new EditorComponent("Имя", widthPanel, 31, 300);
        accountComponent = new EditorComponent("Номер счета", widthPanel, 31, 200);
        moneyComponent = new EditorComponent("Денег на счету", widthPanel, 31, 200);
        comboBoxClientComponent = new ComboBoxClientComponent("CLIENTS", widthPanel, 30, 300);
//        comboBoxClientComponent.getComboBox().setModel(comboBoxModel);
        comboBoxClientComponent.getComboBox().setSelectedIndex(-1);
        comboBoxClientComponent.getComboBox().addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent ie) {
                client = (Client) ie.getItem();
                nameComponent.getEditor().setText(client.getName());
                accountComponent.getEditor().setText(client.getAccount());
                moneyComponent.getEditor().setText(((Integer)client.getMoney()).toString());
                
                
            }
        });
        this.add(comboBoxClientComponent);
        this.add(Box.createRigidArea(new Dimension(0,10)));
        this.add(nameComponent);
        this.add(accountComponent);
        this.add(moneyComponent);
        this.add(Box.createRigidArea(new Dimension(0,10)));
        buttonComponent = new ButtonComponent("Изменить данные клиента", widthPanel, 35, widthPanel/3+5, 200);
        this.add(buttonComponent);
        buttonComponent.getButton().addActionListener(clickToButtonEditClient());
    }
    private ActionListener clickToButtonEditClient(){
        return new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent ae) {
                if(nameComponent.getEditor().getText().isEmpty()){
                    infoComponent.getInfo().setText("Введите имя");
                    return;
                }
                client.setName(nameComponent.getEditor().getText());
                
                
                if(accountComponent.getEditor().getText().isEmpty()){
                    infoComponent.getInfo().setText("Введите Номер счета");
                    return;
                } 
                client.setAccount(accountComponent.getEditor().getText());
                
                if(moneyComponent.getEditor().getText().isEmpty()){
                    infoComponent.getInfo().setText("Введите сумму денег");
                    return;
                } 
                client.setMoney(Integer.parseInt(moneyComponent.getEditor().getText()));
                
                ClientFacade clientFacade = new ClientFacade();
                
                try {
                    clientFacade.edit(client);
                    infoComponent.getInfo().setText("Данные клиента успешно изменены");
                    comboBoxClientComponent.getComboBox().setSelectedIndex(-1);
                    nameComponent.getEditor().setText("");
                    accountComponent.getEditor().setText("");
                    moneyComponent.getEditor().setText("");
                } catch (Exception e) {
                    infoComponent.getInfo().setText("Данные клиента изменить не удалось");
                }
            }
        };
    }

    public void addComboBoxModel() {
        nameComponent.getEditor().setText("");
        accountComponent.getEditor().setText("");
        infoComponent.getInfo().setText("");
        ClientFacade clientFacade = new ClientFacade();
        List<Client> clients = clientFacade.findAll();
        DefaultComboBoxModel<Client> defaultComboBoxModel = new DefaultComboBoxModel<>();
        for (Client client : clients) {
            defaultComboBoxModel.addElement(client);
        }
        comboBoxClientComponent.getComboBox().setModel(defaultComboBoxModel);
        comboBoxClientComponent.getComboBox().setSelectedIndex(-1);
        accountComponent.getEditor().setText("");
        nameComponent.getEditor().setText("");
        moneyComponent.getEditor().setText("");
    }
    
}
