
package app.mycomponents;

import entity.Shop;
import entity.History;
import entity.Client;
import facade.HistoryFacade;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.util.List;
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

public class ListHistoriesComponent extends JPanel{
    private JLabel caption;
    private JList<History> list;

    public ListHistoriesComponent(String text, int widthWindow,int heightPanel, int listWidth) {
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
        caption.setAlignmentY(TOP_ALIGNMENT);//setVerticalAlignment(JLabel.TOP);
        caption.setFont(new Font("Tahoma",0,12));
        this.add(caption);
        this.add(Box.createRigidArea(new Dimension(5, 0)));
        list = new JList<>();
        list.setModel(getListModel());
        list.setCellRenderer(createListHistoriesRenderer());
        list.setSelectionMode (ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);;
        list.setLayoutOrientation (JList.HEIGHT);
        
        JScrollPane scrollPane = new JScrollPane(list);
        scrollPane.setPreferredSize(new Dimension(listWidth, 120));
        scrollPane.setMaximumSize(scrollPane.getPreferredSize());
        scrollPane.setMinimumSize(scrollPane.getPreferredSize());
        scrollPane.setAlignmentX(LEFT_ALIGNMENT);
        scrollPane.setAlignmentY(TOP_ALIGNMENT);
        this.add(scrollPane);
    }
    /**
     * Метод возвращает модель со списком доступных для выдачи книг
     * @return объект DefaultListModel
     */
    public ListModel<History> getListModel(){
       return getListModel(null);
    }
    /**
     * Метод возвращает модель со списком книг из базы данных
     * @param allBooks true все книги, false - только доступные к выдаче
     * @return объект DefaultListModel
     */
    public ListModel<History> getListModel(Client client) {
        if(client == null){
            return new DefaultListModel<>();
        }
        HistoryFacade historyFacade = new HistoryFacade();
        List<History> histories=null;
        histories = historyFacade.findAll(client);
        DefaultListModel<History> defaultListModel = new DefaultListModel<>();
        for (History history : histories) {
            defaultListModel.addElement(history);
        }
        return defaultListModel;
    }

    private ListCellRenderer<? super History> createListHistoriesRenderer() {
      return new DefaultListCellRenderer(){
        private final Color background = new Color(0, 100, 255, 15);
        private final Color defaultBackground = (Color) UIManager.get("List.background");
        @Override
        public Component getListCellRendererComponent(JList<?> list, Object value, int index,
                                                        boolean isSelected, boolean cellHasFocus){
          Component component = super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
          if(component instanceof JLabel){
              JLabel label = (JLabel) component;
              History history = (History) value;
              StringBuilder sb = new StringBuilder();
              
              label.setText(String.format("%d. %s. %s %d."
                          ,history.getShoes().getId()
                          ,history.getShoes().getName()
                          ,sb.toString()
                          ,history.getShoes().getPrice()
                          ,history.getShoes().getQuantity()
              ));
              
              if(!isSelected){
                  label.setBackground(index % 2 == 0 ? background : defaultBackground);
              }
          }
          return component;
        }
      }; 
    }

    public JList<History> getJList() {
        return list;
    }
    
}
