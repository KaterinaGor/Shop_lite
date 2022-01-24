
package app.mycomponents.manager;

import app.mycomponents.ButtonComponent;
import app.mycomponents.CaptionComponent;
import app.mycomponents.EditorComponent;
import app.mycomponents.InfoComponent;

import entity.Shoes;
import facade.ShoesFacade;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JPanel;

public class TabEditShoesComponent extends JPanel{
    private CaptionComponent captionComponent;
    private InfoComponent infoComponent;
    private EditorComponent shoesNameComponent;
    private EditorComponent priceComponent;
    private EditorComponent quantityComponent;
    private ButtonComponent buttonComponent;
    public TabEditShoesComponent(int widthWindow) {
        initComponents(widthWindow);
    }

    private void initComponents(int widthWindow) {
        this.setPreferredSize(new Dimension(widthWindow,450));
        this.setMinimumSize(this.getPreferredSize());
        this.setMaximumSize(this.getPreferredSize());
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        this.add(Box.createRigidArea(new Dimension(0,15)));
        captionComponent = new CaptionComponent("Редактирование товара", widthWindow, 31);
        this.add(captionComponent);
        infoComponent = new InfoComponent("", widthWindow, 31);
        this.add(infoComponent);
        this.add(Box.createRigidArea(new Dimension(0,10)));
        shoesNameComponent = new EditorComponent("Название товара", widthWindow, 31, 300);
        this.add(shoesNameComponent);
        priceComponent = new EditorComponent("Стоимость", widthWindow, 31, 100);
        this.add(priceComponent);
        quantityComponent = new EditorComponent("Количество", widthWindow, 31, 50);
        this.add(quantityComponent);
        buttonComponent = new ButtonComponent("Добавить товар", widthWindow, 31, 350, 150);
        this.add(buttonComponent);
        buttonComponent.getButton().addActionListener(clickToButtonAddShoes());
    }
    private ActionListener clickToButtonAddShoes(){
        return new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent ae) {
                /**
                 * Создать объект book
                 * Инициировать поля книги используя элементы компотентов
                 * Добавить книгу в базу данных
                 * Сообзить пользователю о результате 
                 * если true очистить редакторы компонентов
                 */
                Shoes shoes = new Shoes();
                if(shoesNameComponent.getEditor().getText().isEmpty()){
                    infoComponent.getInfo().setText("Введите название");
                    return;
                }
                shoes.setName(shoesNameComponent.getEditor().getText());
                try {
                    shoes.setPrice(Integer.parseInt(priceComponent.getEditor().getText()));
                } catch (Exception e) {
                    infoComponent.getInfo().setText("Стоимость");
                    return;
                }
                try {
                    shoes.setQuantity(Integer.parseInt(quantityComponent.getEditor().getText()));
                    
                } catch (Exception e) {
                    infoComponent.getInfo().setText("Количество");
                    return;
                }
                
                ShoesFacade shoesFacade = new ShoesFacade();
                try {
                    shoesFacade.create(shoes);
                    infoComponent.getInfo().setText("Товар успешно добавлен");
                    quantityComponent.getEditor().setText("");
                    priceComponent.getEditor().setText("");
                    shoesNameComponent.getEditor().setText("");
                } catch (Exception e) {
                    infoComponent.getInfo().setText("Товар добавить не удалось");
                }
            }
        };
    }
    
}
