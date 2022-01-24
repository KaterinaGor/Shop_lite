
package app;

import entity.Client;
import entity.Shoes;
import entity.Shop;
import entity.History;
import facade.ClientFacade;
import facade.ShoesFacade;
import facade.HistoryFacade;
import facade.ShopFacade;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashSet;
import java.util.List;
import java.util.Scanner;
import java.util.Set;

public class App {
    
    public static boolean isBase;
    private Scanner scanner = new Scanner(System.in);
//    private List<Shoes> shoes = new ArrayList<>();
//    private List<Client> client = new ArrayList<>();
//    private List<History> history = new ArrayList<>();
//    private List<Shop> shop = new ArrayList<>();
    private ShoesFacade shoesFacade = new ShoesFacade();
    private ClientFacade clientFacade = new ClientFacade();
    private ShopFacade  shopFacade = new ShopFacade();
    private HistoryFacade historyFacade = new HistoryFacade();


    public App() {
        
//        shoes = keeper.loadShoes();
//        shop = keeper.loadShop();
//        client = keeper.loadClient();
//        history = keeper.loadHistory();
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
            
            System.out.println("7: Доход магазина(shopMoney)");
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
                default:
                    System.out.println("Выберите номер из списка задач");
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
        
        shoesFacade.create(shoes);
        /*
        this.shoes.add(shoes);
        keeper.saveShoes(this.shoes);
        */
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
        
        clientFacade.create(client);
        /*
        this.client.add(client);
        keeper.saveClient(this.client);
        */
    }
    
    private void addHistory(){
        System.out.println("Продажа товара");
        if(quit()) return;
        
        History history = new History();
        
        //?
        Client client = new Client();
        Shoes shoes = new Shoes();
        Shop shop = new Shop();
        
        //1
        Set<Integer> setNumbersClient = printClient();
        if(setNumbersClient.isEmpty()){
            return;} 
        System.out.print("Номер покупателя: ");
        int clientNumber = insertNumber(setNumbersClient);
        //history.setClient(client.get(clientNumber-1));
        history.setClient(clientFacade.find((long)clientNumber));
        
        //2
        Set <Integer> setNumbersShoes = printShoes();
        if(setNumbersShoes.isEmpty()){
            return;}
        System.out.print("Номер модели: ");
        int shoesNumber = insertNumber(setNumbersShoes);
        history.setShoes(shoesFacade.find((long)shoesNumber));

        
        
        if (client.getMoney() > shoes.getPrice() && shoes.getQuantity() > 0){
            //history.setClient(client);
            //history.setShoes(shoes);
            history.setPrice(shoes.getPrice());
            client.setMoney(client.getMoney() - shoes.getPrice()); // minus dengi
            shoes.setQuantity(shoes.getQuantity()-1); // minus kolichestvo
            //shop.get(0).setMoney(shop.get(0).getMoney() + shoes.get(shoesNumber-1).getPrice()); // plus dengi
            
            Calendar cal = new GregorianCalendar();
            history.setMonth(cal.get(Calendar.MONTH));
            history.setYear(cal.get(Calendar.YEAR));
            System.out.println("Сделка купли-продажи прошла успешно");
        }
        else{
            System.out.println("Ошибка! Либо не хватает денег на счету клиента, либо на складе отсутсвует выбранная модель"); 
        }

        shoesFacade.edit(shoes);
        clientFacade.edit(client);
        shopFacade.edit(shop);
        
        historyFacade.create(history);
        
        /*
        this.history.add(history);
        keeper.saveHistory(this.history);
        
        keeper.saveClient(client);
        keeper.saveShoes(shoes);
        */
    }
    
    //----------------- PRINT ------------------------------------------

    private Set<Integer> printShoes(){
        Set <Integer> setNumbersShoes = new HashSet<>();
        System.out.println("Список обуви:");
        List<Shoes> shoes = shoesFacade.findAll();
        
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
        Set<Integer> setNumbersClient = new HashSet<>();
        System.out.println("Список покупателей");
        List<Client> client = clientFacade.findAll();
        
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
        Set<Integer> setNumbersHistory = new HashSet<>();
        List<History> history = historyFacade.findAll();
        
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
        Set <Integer> setNumbersHistory = new HashSet<>();
        System.out.print("Распечатать доход магазина. Введите месяц: ");
        List<History> history = historyFacade.findAll();
        
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
        
        System.out.print("Номер покупателя для редактирования: ");
        int clientNumber = insertNumber(setNumbersClient);
        Client client = clientFacade.find((long)clientNumber);
        System.out.print("Введите сумму денег для добавления на счет: ");
        int moneyToAdd = scanner.nextInt(); scanner.nextLine();
        client.setMoney(client.getMoney() + moneyToAdd * 100);
        
        clientFacade.edit(client);
        
        //keeper.saveClient(client);
    }
    
    //-------------------- UPDATE ---------------------------------
    
    private void updateShoes(){
        System.out.println("Редактирование товара");
        Set <Integer> setNumbersShoes = printShoes();
        if(setNumbersShoes.isEmpty()){
            return;}
        System.out.print("Введите номер товара: ");
        int shoesNumber = insertNumber(setNumbersShoes);
        Shoes shoes = shoesFacade.find((long)shoesNumber);
        
        System.out.println("Редактировать название:" + shoes.getName());
        System.out.print("y/n: ");
        String answer = scanner.nextLine();
        if("y".equals(answer)){
            System.out.print("Введите новое название: ");
            shoes.setName(scanner.nextLine());
        }
        System.out.println("Редактировать цену:" + shoes.getPrice()/100);
        System.out.print("y/n: ");
        answer = scanner.nextLine();
        if("y".equals(answer)){
            System.out.print("Введите новую цену: ");
            shoes.setPrice(getNumber()*100);
        }
        System.out.println("Хотите изменить количество экземпляров? Сейчас на складе: " + shoes.getQuantity());
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
            shoes.setQuantity(newQuantity);
        }
        
        shoesFacade.edit(shoes);
        
        //keeper.saveShoes(shoes);  
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
        Client client = clientFacade.find((long)clientNumber);
        
        System.out.println("Редактировать имя:" + client.getName());
        System.out.print("y/n: ");
        String answer = scanner.nextLine();
        if("y".equals(answer)){
            System.out.print("Введите новое имя: ");
            client.setName(scanner.nextLine());
        }
        System.out.println("Редактировать номер счета:" + client.getAccount());
        System.out.print("y/n: ");
        answer = scanner.nextLine();
        if("y".equals(answer)){
            System.out.print("Введите новый номер счета: ");
            client.setAccount(scanner.nextLine());
        }
        System.out.println("редактировать количество денег на счету:" + client.getMoney()/100);
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
            client.setMoney(newMoney*100);
        }
        
        clientFacade.edit(client);
        
        //keeper.saveClient(client); 
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
