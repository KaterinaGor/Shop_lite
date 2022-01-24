
package app.mycomponents.client;

import app.GuiApp;
import app.mycomponents.ButtonComponent;
import app.mycomponents.ButtonComponent;
import app.mycomponents.CaptionComponent;
import app.mycomponents.CaptionComponent;
import app.mycomponents.ComboBoxClientComponent;
import app.mycomponents.ComboBoxClientComponent;
import app.mycomponents.InfoComponent;
import app.mycomponents.InfoComponent;
import app.mycomponents.ListShoesComponent;
import app.mycomponents.ListShoesComponent;
import entity.Shoes;
import entity.History;
import entity.Client;
import facade.ShoesFacade;
import facade.HistoryFacade;
import facade.ClientFacade;
import facade.ShopFacade;
import facade.UserRolesFacade;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ComboBoxModel;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JCheckBox;
import javax.swing.JPanel;

public class TabTakeOnShoesComponents extends JPanel{
    private boolean isClient = true;
    private CaptionComponent captionComponent;
    private InfoComponent infoComponent;
    private ComboBoxClientComponent comboBoxClientComponent;
    private ListShoesComponent listShoesComponent;
    private ButtonComponent buttonComponent;
    private ComboBoxModel comboBoxModel;
    private Client client;
    private HistoryFacade historyFacade = new HistoryFacade();
    private ShoesFacade shoesFacade = new ShoesFacade();
    private ClientFacade clientFacade = new ClientFacade();
    private ShopFacade shopFacade = new ShopFacade();
    private UserRolesFacade userRolesFacade = new UserRolesFacade();
    
    public TabTakeOnShoesComponents(int widthPanel) {
        setPreferredSize(new Dimension(GuiApp.WITH_WINDOWS-5,GuiApp.HEIGHT_WINDOWS));
        setMinimumSize(getPreferredSize());
        setMaximumSize(getPreferredSize());
        setComboBoxModel();
        initComponents(widthPanel);
    }
    private void initComponents(int widthPanel) {
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        this.add(Box.createRigidArea(new Dimension(0,15)));
        captionComponent = new CaptionComponent("Выбор товара", widthPanel, 31);
        this.add(captionComponent); 
        infoComponent = new InfoComponent("", widthPanel, 31);
        this.add(infoComponent);
        isClient = !userRolesFacade.isRole("MANAGER", GuiApp.user);
        if(!isClient){
            this.add(Box.createRigidArea(new Dimension(0,10)));
            comboBoxClientComponent = new ComboBoxClientComponent("CLIENTS", widthPanel, 30, 300);
            comboBoxClientComponent.getComboBox().setModel(comboBoxModel);
            comboBoxClientComponent.getComboBox().setSelectedIndex(-1);
            comboBoxClientComponent.getComboBox().addItemListener(new ItemListener() {
                @Override
                public void itemStateChanged(ItemEvent ie) {
                    client = (Client) ie.getItem();
                }
            });
            this.add(comboBoxClientComponent);
            this.add(Box.createRigidArea(new Dimension(0,10)));
        }
        JCheckBox checkBoxAllShoes = new JCheckBox("Показать все товары");
        this.add(checkBoxAllShoes);
        this.add(Box.createRigidArea(new Dimension(0,10)));
        listShoesComponent = new ListShoesComponent("SHOES", widthPanel, 120, 300);
        this.add(listShoesComponent);
        checkBoxAllShoes.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent ie) {
                if(ie.getStateChange() == ItemEvent.SELECTED){
                    listShoesComponent.getJList().setModel(listShoesComponent.getListModel(true));
                    buttonComponent.getButton().setEnabled(false);
                    listShoesComponent.getJList().setEnabled(false);
                }else{
                    listShoesComponent.getJList().setModel(listShoesComponent.getListModel(false));
                    buttonComponent.getButton().setEnabled(true);
                    listShoesComponent.getJList().setEnabled(true);
                }
            }
        });
        this.add(Box.createRigidArea(new Dimension(0,10)));
        buttonComponent = new ButtonComponent("Купить товар", widthPanel, 35, widthPanel/3+5, 300);
        this.add(buttonComponent);
        buttonComponent.getButton().addActionListener(clickToButtonTakeOnShoes());
    }
    private ActionListener clickToButtonTakeOnShoes(){
        return new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent ae) {
                if(!isClient){
                    if(comboBoxClientComponent.getComboBox().getSelectedIndex() == -1){
                        infoComponent.getInfo().setForeground(Color.RED);
                        infoComponent.getInfo().setText("Выберите клиента");
                        return;
                    }
                }
                List<Shoes> shoes = listShoesComponent.getJList().getSelectedValuesList();
                if(shoes.isEmpty()){
                    infoComponent.getInfo().setForeground(Color.RED);
                    infoComponent.getInfo().setText("Выберите товар");
                    return;
                }
                try {
                    for (Shoes shoe : shoes) {
                        History history = new History();
                        shoe.setQuantity(shoe.getQuantity()-1);
                        
                        shoesFacade.edit(shoe);
                        history.setShoes(shoe);
                        if(isClient){
                            history.setClient(GuiApp.user.getClient());
                        }else{
                            history.setClient(client);
                        }
                        
                        //?
                        history.setPrice(shoe.getPrice());
                        client.setMoney(client.getMoney() - shoe.getPrice()); // minus dengi
                        clientFacade.edit(client);
             
                        
                        Calendar c = new GregorianCalendar();
                        history.setMonth(c.get(Calendar.MONTH));
                        history.setYear(c.get(Calendar.YEAR));
                        
                        
                        historyFacade.create(history);
                        infoComponent.getInfo().setForeground(Color.BLUE);
                        infoComponent.getInfo().setText("Выбранный товар оплачен");
                        if(!isClient){
                            comboBoxClientComponent.getComboBox().setSelectedIndex(-1);
                        }
                        listShoesComponent.getJList().setModel(listShoesComponent.getListModel(false));
                        listShoesComponent.getJList().clearSelection();
                    }
                } catch (Exception e) {
                    infoComponent.getInfo().setForeground(Color.RED);
                    infoComponent.getInfo().setText("Выбранный товар выдать не удалось");
                }
            }
        };
    }
    private void setComboBoxModel(){
        ClientFacade clientFacade = new ClientFacade();
        List<Client> clients = clientFacade.findAll();
        DefaultComboBoxModel<Client> defaultComboBoxModel = new DefaultComboBoxModel<>();
        for (Client client : clients) {
            defaultComboBoxModel.addElement(client);
        }
        comboBoxModel=defaultComboBoxModel;
    }
    public void addComboBoxModel() {
        infoComponent.getInfo().setText("");
        setComboBoxModel();
        comboBoxClientComponent.getComboBox().setSelectedIndex(-1);
    }
    
}
