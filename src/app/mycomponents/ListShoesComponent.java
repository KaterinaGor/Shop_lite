/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package app.mycomponents;

import app.GuiApp;
import entity.Shop;
import entity.Shoes;
import facade.ShoesFacade;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.util.List;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.DefaultListCellRenderer;
import javax.swing.DefaultListModel;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ListCellRenderer;
import javax.swing.ListModel;
import javax.swing.ListSelectionModel;
import javax.swing.UIManager;

public class ListShoesComponent extends JPanel{
    private JLabel caption;
    private JList<Shoes> list;
  

    public ListShoesComponent(String text, int widthWindow,int heightPanel, int listWidth) {
        initComponents(false, text, widthWindow, heightPanel, listWidth);
    }

    public ListShoesComponent(boolean guest,String text, int widthWindow, int heightPanel, int listWidth) {
        
        this.initComponents(guest, text, widthWindow, heightPanel, listWidth);
    }
    private void initComponents(boolean guest, String text, int widthWindow, int heightPanel, int listWidth) {
        this.setPreferredSize(new Dimension(widthWindow-25,heightPanel));
        this.setMinimumSize(this.getPreferredSize());
        this.setMaximumSize(this.getPreferredSize());
        if(guest){
            this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
//        this.setBorder(BorderFactory.createLineBorder(Color.BLACK));
            caption = new JLabel(text);
            caption.setPreferredSize(new Dimension(widthWindow,37));
            caption.setMinimumSize(caption.getPreferredSize());
            caption.setMaximumSize(caption.getPreferredSize());
    //        caption.setBorder(BorderFactory.createLineBorder(Color.yellow));
            caption.setHorizontalAlignment(JLabel.LEFT);
            caption.setAlignmentY(TOP_ALIGNMENT);//setVerticalAlignment(JLabel.TOP);
            caption.setFont(new Font("Tahoma",0,12));
            this.add(caption);
            this.add(Box.createRigidArea(new Dimension(5, 0)));
            list = new JList<>();
            list.setModel(getListModel());
            list.setCellRenderer(createListShoesRenderer());
            list.setSelectionMode (ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
            list.setLayoutOrientation (JList.HEIGHT);
            JScrollPane scrollPane = new JScrollPane(list);
            scrollPane.setPreferredSize(new Dimension(GuiApp.WITH_WINDOWS-25, heightPanel));
            scrollPane.setMaximumSize(scrollPane.getPreferredSize());
            scrollPane.setMinimumSize(scrollPane.getPreferredSize());
            scrollPane.setAlignmentX(LEFT_ALIGNMENT);
            scrollPane.setAlignmentY(TOP_ALIGNMENT);
            this.add(scrollPane);
            
        }else{
            this.setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
//        this.setBorder(BorderFactory.createLineBorder(Color.BLACK));
            caption = new JLabel(text);
            caption.setPreferredSize(new Dimension(widthWindow/3,27));
            caption.setMinimumSize(caption.getPreferredSize());
            caption.setMaximumSize(caption.getPreferredSize());
    //        caption.setBorder(BorderFactory.createLineBorder(Color.yellow));
            caption.setHorizontalAlignment(JLabel.RIGHT);
            caption.setAlignmentY(TOP_ALIGNMENT);//setVerticalAlignment(JLabel.TOP);
            caption.setFont(new Font("Tahoma",0,12));
            this.add(caption);
        
            this.add(Box.createRigidArea(new Dimension(5, 0)));
            list = new JList<>();
            list.setModel(getListModel());
            list.setCellRenderer(createListShoesRenderer());
            list.setSelectionMode (ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
            list.setLayoutOrientation (JList.HEIGHT);
       

            JScrollPane scrollPane = new JScrollPane(list);
            scrollPane.setPreferredSize(new Dimension(GuiApp.WITH_WINDOWS-25, 120));
            scrollPane.setMaximumSize(scrollPane.getPreferredSize());
            scrollPane.setMinimumSize(scrollPane.getPreferredSize());
            scrollPane.setAlignmentX(LEFT_ALIGNMENT);
            scrollPane.setAlignmentY(TOP_ALIGNMENT);
            this.add(scrollPane);
        }
    }
    /**
     * Метод возвращает модель со списком доступных для выдачи книг
     * @return объект DefaultListModel
     */
    public ListModel<Shoes> getListModel(){
       return getListModel(true);
    }
    /**
     * Метод возвращает модель со списком книг из базы данных
     * @param allBooks true все книги, false - только доступные к выдаче
     * @return объект DefaultListModel
     */
    public ListModel<Shoes> getListModel(boolean allShoes) {
        ShoesFacade shoesFacade = new ShoesFacade();
        List<Shoes> shoes = null;
        if(allShoes){
            shoes = shoesFacade.findAll();
        }
        DefaultListModel<Shoes> defaultListModel = new DefaultListModel<>();
        for (Shoes shoe : shoes) {
            defaultListModel.addElement(shoe);
        }
        return defaultListModel;
    }

    public  ListCellRenderer<? super Shoes> createListShoesRenderer() {
      return new DefaultListCellRenderer(){
        private final Color background = new Color(0, 100, 255, 15);
        private final Color defaultBackground = (Color) UIManager.get("List.background");
        @Override
        public Component getListCellRendererComponent(JList<?> list, Object value, int index,
                                                        boolean isSelected, boolean cellHasFocus){
          Component component = super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
          if(component instanceof JLabel){
              JLabel label = (JLabel) component;
              Shoes shoes = (Shoes) value;
              StringBuilder sb = new StringBuilder();
              
              if(shoes.getQuantity() > 0){
                  label.setText(String.format("%d. %s. %s %d. В наличии %d"
                          ,shoes.getId()
                          ,shoes.getName()
                          ,sb.toString()
                          ,shoes.getPrice()
                          ,shoes.getQuantity()
                  ));
              }else{
                  label.setText(String.format("%d. %s. %s %d. Нет в наличии"
                          ,shoes.getId()
                          ,shoes.getName()
                          ,sb.toString()
                          ,shoes.getPrice()
                          ,shoes.getQuantity()
                  ));
                  label.setForeground(Color.RED);
              }
              if(!isSelected){
                  label.setBackground(index % 2 == 0 ? background : defaultBackground);
              }
          }
          return component;
        }
      }; 
    }

    public JList<Shoes> getJList() {
        return list;
    }
    
}
