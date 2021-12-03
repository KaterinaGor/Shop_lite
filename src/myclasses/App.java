
package myclasses;

import entity.Client;
import entity.History;
import entity.Shoes;
import entity.Shop;
import interfaces.Keeping;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashSet;
import java.util.List;
import java.util.Scanner;
import java.util.Set;
import java.util.TimeZone;
import keeper.FileKeeper;
import keeper.BaseKeeper;


public class App {
    public static boolean isBase;
    private Scanner scanner = new Scanner(System.in);
    private Calendar cal = Calendar.getInstance(TimeZone.getTimeZone("Europe/Tallinn"));
    private List <Shoes> shoes = new ArrayList<>();
    private List <Client> client = new ArrayList<>();
    private List <Shop> shop = new ArrayList<>();
    private List <History> history = new ArrayList<>();
    private Keeping keeper;
    
        
    public App(){
        if(App.isBase){
            keeper = new BaseKeeper();
        }else{
            keeper = new FileKeeper();
        }
        shoes = keeper.loadShoes();
        client = keeper.loadClient();
        shop = keeper.loadShop();
        history = keeper.loadHistory();
    }
    public void run(){
        String repeat = "y";
        do{
            System.out.println("Выберите задачу");
            System.out.println("0: Завершить программу");
            System.out.println("1: Добавить товар (addShoes)");
            System.out.println("2: Список товаров(printShoes)");
            System.out.println("3: Добавить покупателя(addClient)");
            System.out.println("4: Список покупателей(printClient)");
            System.out.println("5: Купить товар(addHistory)");
            System.out.println("6: История покупок(printHistory)");
            
            System.out.println("7: Доход магазина(AllMoney)");
            System.out.println("8: Добавить денег на счет(addMoneyToAccount)");
            System.out.println("9: Редактировать товар (updateShoes)");
            System.out.println("10: Редактировать клиента (updateClient)");
                
            int task = getNumber();
            switch(task){
                case 0: 
                    repeat ="q";
                    System.out.println("Завершение программы");
                    break;
                
                case 1: 
                    addShoes();
                    break;
                    
                case 2:
                    printShoes();
                    break;
                
                case 3:
                    addClient();
                    break;
                
                case 4:
                    printClient();
                    break;
                    
                case 5:
                    addHistory();
                    break;
                
                case 6:
                    printHistory();
                    break;
                    
                case 7:
                    shopMoney();
                    break;
                    
                case 8:
                    addMoneyToAccount();
                    break;
                    
                case 9:
                    updateShoes();
                    break;
                
                case 10:
                    updateClient();
                    break;
            }
        }while("y".equals(repeat));
    }
    
    // ------------------- ADD ---------------------------
    
    private void addShoes(){
        System.out.println("Добавление новой модели обуви");
        if(quit()) return;
        Shoes shoes = new Shoes();
        System.out.print("Название модели: ");
        shoes.setName(scanner.nextLine());
        System.out.print("Стоимость: ");
        shoes.setPrice(getNumber()*100);
        System.out.print("Количество на складе: ");
        shoes.setQuantity(getNumber());
        
        this.shoes.add(shoes);
        keeper.saveShoes(this.shoes);
    }
    
    private void addClient(){
        System.out.println("Добавление нового покупателя");
        if(quit()) return;
        Client client = new Client();
        System.out.print("Имя покупателя: ");
        client.setName(scanner.nextLine());
        System.out.print("Номер счета покупателя: ");
        client.setAccount(scanner.nextLine());
        System.out.print("Деньги покупателя: ");
        client.setMoney(getNumber()*100);
        this.client.add(client);
        keeper.saveClient(this.client);
    }
    
    private void addHistory(){
        System.out.println("Продажа товара");
        if(quit()) return;
        
        History history = new History();
        
        //1
        Set<Integer> setNumbersClient = printClient();
        if(setNumbersClient.isEmpty()){
            return;} 
        System.out.print("Номер покупателя: ");
        int clientNumber = insertNumber(setNumbersClient);
        history.setClient(client.get(clientNumber-1));
        
        //2
        Set <Integer> setNumbersShoes = printShoes();
        if(setNumbersShoes.isEmpty()){
            return;}
        System.out.print("Номер модели: ");
        int shoesNumber = insertNumber(setNumbersShoes);
        history.setShoes(shoes.get(shoesNumber-1));


        if(client.get(clientNumber-1).getMoney() > shoes.get(shoesNumber-1).getPrice() && shoes.get(shoesNumber-1).getQuantity() > 0){
            history.setClient(client.get(clientNumber-1));
            history.setShoes(shoes.get(shoesNumber-1));
            history.setPrice(shoes.get(shoesNumber-1).getPrice());
            client.get(clientNumber-1).setMoney(client.get(clientNumber-1).getMoney() - shoes.get(shoesNumber-1).getPrice()); // minus dengi
            shoes.get(shoesNumber-1).setQuantity(shoes.get(shoesNumber-1).getQuantity()-1); // minus kolichestvo
            //shop.get(0).setMoney(shop.get(0).getMoney() + shoes.get(shoesNumber-1).getPrice()); // plus dengi
            history.setMonth(cal.get(Calendar.MONTH));
            history.setYear(cal.get(Calendar.YEAR));
            System.out.println("Сделка купли-продажи прошла успешно");
        }
        else{
            System.out.println("Ошибка! Либо не хватает денег на счету клиента, либо на складе отсутсвует выбранная модель"); 
        }

        this.history.add(history);
        keeper.saveHistory(this.history);
        
        keeper.saveClient(client);
        keeper.saveShoes(shoes);
    }
    
    //----------------- PRINT ------------------------------------------

    private Set<Integer> printShoes(){
        Set <Integer> setNumbersShoes = new HashSet();
        System.out.println("Список моделей обуви:");
        for (int i = 0; i < shoes.size(); i++) {
            if (shoes.get(i)!= null && shoes.get(i).getQuantity()>0){
                System.out.println((i+1)+ " " + shoes.get(i).toString());
                setNumbersShoes.add(i+1);
            }else if(shoes.get(i)!= null){
                System.out.println("%d. %s нет в наличии.");
            }
        }
        return setNumbersShoes;
    }
    
    private Set<Integer> printClient(){
        Set<Integer> setNumbersClient = new HashSet();
         System.out.println("Список покупателей");
        for (int i = 0; i < client.size(); i++) {
            if (client.get(i)!=null){
                System.out.printf("%d. %s%n", (i+1), client.get(i).toString()); 
                setNumbersClient.add(i+1);
            }
        }
        if(setNumbersClient.isEmpty()){
            System.out.println("Список покупателей пуст");
        }
        return setNumbersClient;
    }
    
    private Set<Integer> printHistory(){
        System.out.println("История покупок");
        Set<Integer> setNumbersHistory = new HashSet();
        for (int i = 0; i < history.size(); i++) {
            if (history.get(i)!=null){
                System.out.printf("%d. Модель %s купил %s, стоимость: %d%n",
                            (i+1),
                            history.get(i).getShoes().getName(), 
                            history.get(i).getClient().getName(),
                            history.get(i).getPrice()/100);
                setNumbersHistory.add(i+1);
            }
        }
        if(setNumbersHistory.isEmpty()){
            System.out.println("Список покупок пуст");
        }
        return setNumbersHistory;
    }
    
   
    //------------- MONEY --------------------------
    
    private Set<Integer> shopMoney(){
        Set <Integer> setNumbersHistory = new HashSet();
        System.out.print("Распечатать доход магазина. Введите месяц: ");
        int monthMoney = getNumber();
        System.out.print("Введите год: ");
        int yearMoney = getNumber();
        int money = 0;
        for (int i = 0; i < history.size(); i++) {
            if (history.get(i)!= null && 
                history.get(i).getMonth()+1 == monthMoney && 
                history.get(i).getYear() == yearMoney){
                    money = money + history.get(i).getPrice();
                    setNumbersHistory.add(i+1);
            }
        }
        if(setNumbersHistory.isEmpty()){
            System.out.println("Список покупок пуст");
        }else{
            System.out.println("Доход магазина за " + monthMoney + "." + yearMoney + " составил: " + money/100);
        }
        return setNumbersHistory;
    }
    
    private void addMoneyToAccount(){
        System.out.println("Добавление денег на счёт покупателя");
        if(quit()) return;
        
        Set<Integer> setNumbersClient = printClient();
        if(setNumbersClient.isEmpty()){
            return;}
        
        System.out.print("Номер покупателя: ");
        int clientNumber = insertNumber(setNumbersClient);
        System.out.print("Введите сумму денег для добавления на счет: ");
        int moneyToAdd = scanner.nextInt(); scanner.nextLine();
        client.get(clientNumber-1).setMoney(client.get(clientNumber-1).getMoney() + moneyToAdd * 100);
        keeper.saveClient(client);
    }
    
    //-------------------- UPDATE ---------------------------------
    
    private void updateShoes(){
        System.out.println("Редактирование товара");
        Set <Integer> setNumbersShoes = printShoes();
        if(setNumbersShoes.isEmpty()){
            return;}
        System.out.print("Введите номер товара: ");
        int shoesNumber = insertNumber(setNumbersShoes);
        System.out.println("Редактировать название:" + shoes.get(shoesNumber-1).getName());
        System.out.print("y/n: ");
        String answer = scanner.nextLine();
        if("y".equals(answer)){
            System.out.print("Введите новое название: ");
            shoes.get(shoesNumber-1).setName(scanner.nextLine());
        }
        System.out.println("Редактировать цену:" + shoes.get(shoesNumber-1).getPrice()/100);
        System.out.print("y/n: ");
        answer = scanner.nextLine();
        if("y".equals(answer)){
            System.out.print("Введите новую цену: ");
            shoes.get(shoesNumber-1).setPrice(getNumber()*100);
        }
        System.out.println("Хотите изменить количество экземпляров? Сейчас на складе: " + shoes.get(shoesNumber-1).getQuantity());
        System.out.print("y/n: ");
        answer = scanner.nextLine();
        if("y".equals(answer)){
            System.out.print("Введите новое количество: ");
            int newQuantity;
            do{
                newQuantity = getNumber();
                if(newQuantity >= 0){
                    break;
                }
                System.out.println("Попробуйте еще раз");
            }
            while(true);
            shoes.get(shoesNumber-1).setQuantity(newQuantity);
        }
        keeper.saveShoes(shoes);  
    }

    private void updateClient(){
        System.out.println("Редактирование клиента");
        if(quit()) return;
        Set <Integer> setNumbersClient = printClient();
        if(setNumbersClient.isEmpty()){
            return;
        }
        System.out.print("Введите номер клиента: ");
        int clientNumber = insertNumber(setNumbersClient);
        System.out.println("Редактировать имя:" + client.get(clientNumber-1).getName());
        System.out.print("y/n: ");
        String answer = scanner.nextLine();
        if("y".equals(answer)){
            System.out.print("Введите новое имя: ");
            client.get(clientNumber-1).setName(scanner.nextLine());
        }
        System.out.println("Редактировать номер счета:" + client.get(clientNumber-1).getAccount());
        System.out.print("y/n: ");
        answer = scanner.nextLine();
        if("y".equals(answer)){
            System.out.print("Введите новый номер счета: ");
            client.get(clientNumber-1).setAccount(scanner.nextLine());
        }
        System.out.println("редактировать количество денег на счету:" + client.get(clientNumber-1).getMoney()/100);
        System.out.print("y/n: ");
        answer = scanner.nextLine();
        if("y".equals(answer)){
            System.out.print("Введите сумму: ");
            int newMoney;
            do{
                newMoney = getNumber();
                if(newMoney >= 0){
                    break;
                }
                System.out.println("Попробуйте еще раз");
            }while(true);
            client.get(clientNumber-1).setMoney(newMoney*100);
        }
        keeper.saveClient(client); 
    }
    
    
     //---------------- functional ---------------------
    
    private int getNumber(){
        do{
            try{
                String strNumber = scanner.nextLine();
                return Integer.parseInt(strNumber);
            }catch(Exception e){
                 System.out.println("Попробуйте еще раз");
            }
        }while(true);
    }
    
    private int insertNumber(Set<Integer> setNumbers){
        do{
            int historyNumber = getNumber();
            if (setNumbers.contains(historyNumber)){
                return historyNumber; 
            }
            System.out.println("Попробуйте еще раз");
        }while(true);  
    }
    
    private boolean quit(){
        System.out.println("Чтобы закончить операцию нажмите \"q\", для продолжения любой другой символ");
        String quit = scanner.nextLine();
        if("q".equals(quit)) return true;
      return false;
    }
}