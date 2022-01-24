/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package app.mycomponents;

import entity.Client;
import facade.ClientFacade;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.util.List;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ComboBoxModel;
import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListCellRenderer;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.ListCellRenderer;
import javax.swing.UIManager;

public class ComboBoxClientComponent extends JPanel{
    private JLabel caption;
    private JComboBox<Client> comboBox;

    public ComboBoxClientComponent(String text, int widthWindow,int heightPanel, int listWidth) {
        initComponents(text, widthWindow, heightPanel, listWidth);
    }

    private void initComponents(String text, int widthWindow, int heightPanel, int listWidth) {
        this.setPreferredSize(new Dimension(widthWindow,heightPanel));
        this.setMinimumSize(this.getPreferredSize());
        this.setMaximumSize(this.getPreferredSize());
        this.setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
//        this.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        caption = new JLabel(text);
        caption.setPreferredSize(new Dimension(widthWindow/3,27));
        caption.setMinimumSize(caption.getPreferredSize());
        caption.setMaximumSize(caption.getPreferredSize());
//        caption.setBorder(BorderFactory.createLineBorder(Color.yellow));
        caption.setHorizontalAlignment(JLabel.RIGHT);
        caption.setAlignmentY(CENTER_ALIGNMENT);//setVerticalAlignment(JLabel.TOP);
        caption.setFont(new Font("Tahoma",0,12));
        this.add(caption);
        this.add(Box.createRigidArea(new Dimension(5, 0)));
        comboBox = new JComboBox<>();
        comboBox.setPreferredSize(new Dimension(listWidth,27));
        comboBox.setMaximumSize(comboBox.getPreferredSize());
        comboBox.setMinimumSize(comboBox.getPreferredSize());
        //comboBox.setModel(getListModel());
        comboBox.setRenderer(createListAuthorsRenderer());
        comboBox.setMaximumRowCount(5);
       // comboBox.setSelectedIndex(-1);
        this.add(comboBox);
    }

    private ComboBoxModel<Client> getListModel() {
        ClientFacade clientFacade = new ClientFacade();
        List<Client> clients = clientFacade.findAll();
        DefaultComboBoxModel<Client> defaultComboBoxModel = new DefaultComboBoxModel<>();
        for (Client client : clients) {
            defaultComboBoxModel.addElement(client);
        }
        return defaultComboBoxModel;
    }

    private ListCellRenderer<? super Client> createListAuthorsRenderer() {
      return new DefaultListCellRenderer(){
        private final Color background = new Color(0, 100, 255, 15);
        private final Color defaultBackground = (Color) UIManager.get("List.background");
        @Override
        public Component getListCellRendererComponent(JList<?> list, Object value, int index,
                                                        boolean isSelected, boolean cellHasFocus){
          Component component = super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
          if(component instanceof JLabel){
              JLabel label = (JLabel) component;
              Client client = (Client) value;
              if(client == null) return component;
              label.setText(String.format("%d. %s %s. %d%n"
                      ,client.getId()
                      ,client.getName()
                      ,client.getAccount()
                      ,client.getMoney()
              ));
              if(!isSelected){
                  label.setBackground(index % 2 == 0 ? background : defaultBackground);
              }
          }
          return component;
        }
      }; 
    }

    public JComboBox<Client> getComboBox() {
        return comboBox;
    }
    
}
