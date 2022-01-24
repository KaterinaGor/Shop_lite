
package app.mycomponents.director;

import app.GuiApp;
import app.mycomponents.ButtonComponent;
import app.mycomponents.CaptionComponent;
import app.mycomponents.EditorComponent;
import app.mycomponents.InfoComponent;
import entity.Client;
import entity.Role;
import entity.User;
import facade.ClientFacade;
import facade.RoleFacade;
import facade.UserFacade;
import facade.UserRolesFacade;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JPanel;

public class TabAddClientComponents extends JPanel{
    private CaptionComponent captionComponent;
    private InfoComponent infoComponent;
    
    private EditorComponent nameComponent;
    private EditorComponent accountComponent;
    private EditorComponent moneyComponent;
    
    private ButtonComponent buttonComponent;
    private EditorComponent loginComponent;
    private EditorComponent passwordComponent;
    
    public TabAddClientComponents(int widthPanel) {
        setPreferredSize(new Dimension(GuiApp.WITH_WINDOWS-5,GuiApp.HEIGHT_WINDOWS));
        setMinimumSize(getPreferredSize());
        setMaximumSize(getPreferredSize());
        initComponents(widthPanel);
    }

    private void initComponents(int widthPanel) {
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        this.add(Box.createRigidArea(new Dimension(0,15)));
        captionComponent = new CaptionComponent("Регистрация нового клиента", widthPanel, 31);
        this.add(captionComponent); 
        infoComponent = new InfoComponent("", widthPanel, 31);
        this.add(infoComponent);
        this.add(Box.createRigidArea(new Dimension(0,10)));
        nameComponent = new EditorComponent("Имя", widthPanel, 31, 300);
        this.add(nameComponent);
        accountComponent = new EditorComponent("Номер счета", widthPanel, 31, 240);
        this.add(accountComponent);
        buttonComponent = new ButtonComponent("Добавить клиента", widthPanel, 31, 350, 150);
        loginComponent = new EditorComponent("Логин", widthPanel, 31, 200);
        this.add(loginComponent);
        passwordComponent = new EditorComponent("Пароль", widthPanel, 31, 200);
        this.add(passwordComponent);
        this.add(buttonComponent);
        buttonComponent.getButton().addActionListener(clickToButtonAddClient());
    }
    private ActionListener clickToButtonAddClient(){
        return new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent ae) {
                Client client = new Client();
                if(nameComponent.getEditor().getText().isEmpty()){
                    infoComponent.getInfo().setText("Введите имя");
                    return;
                }
                client.setName(nameComponent.getEditor().getText());         
                
                if(accountComponent.getEditor().getText().isEmpty()){
                    infoComponent.getInfo().setText("Введите номер счета");
                    return;
                } 
                client.setAccount(accountComponent.getEditor().getText());
                
                User newUser = new User();
                if(loginComponent.getEditor().getText().isEmpty()){
                   infoComponent.getInfo().setText("Введите логин");
                    return;
                } 
                newUser.setLogin(loginComponent.getEditor().getText());
                
                if(passwordComponent.getEditor().getText().isEmpty()){
                    infoComponent.getInfo().setText("Введите пароль");
                    return;
                } 
                newUser.setPassword(passwordComponent.getEditor().getText());
                
                ClientFacade clientFacade = new ClientFacade();
                clientFacade.create(client);
                newUser.setClient(client);
                
                UserFacade userFacade = new UserFacade();
                userFacade.create(newUser);
                UserRolesFacade userRolesFacade = new UserRolesFacade();
                userRolesFacade.setRole("CLIENT",newUser);
                try {
                    clientFacade.create(client);
                    infoComponent.getInfo().setText("Клиент успешно добавлен");
                    accountComponent.getEditor().setText("");   
                    nameComponent.getEditor().setText("");
                    loginComponent.getEditor().setText("");
                    passwordComponent.getEditor().setText("");
                } catch (Exception e) {
                    infoComponent.getInfo().setText("Клиента добавить не удалось");
                }
            }
        };
    }
    
}
