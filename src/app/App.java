
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
            System.out.println("�������� ������");
            System.out.println("0: ��������� ���������");
            System.out.println("1: �������� ����� (addShoes)");
            System.out.println("2: ������ �������(printShoes)");
            System.out.println("3: �������� ����������(addClient)");
            System.out.println("4: ������ �����������(printClient)");
            System.out.println("5: ������ �����(addHistory)");
            System.out.println("6: ������� �������(printHistory)");
            
            System.out.println("7: ����� ��������(shopMoney)");
            System.out.println("8: �������� ����� �� ����(addMoneyToAccount)");
            System.out.println("9: ������������� ����� (updateShoes)");
            System.out.println("10: ������������� ������� (updateClient)");
                
            int task = getNumber();
            switch(task){
                case 0: 
                    repeat ="q";
                    System.out.println("���������� ���������");
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
                    System.out.println("�������� ����� �� ������ �����");
                    break;
            }
        }while("y".equals(repeat));
    }
    
    // ------------------- ADD ---------------------------
    
    private void addShoes(){
        System.out.println("���������� ����� ������ �����");
        if(quit()) return;
        Shoes shoes = new Shoes();
        System.out.print("�������� ������: ");
        shoes.setName(scanner.nextLine());
        System.out.print("���������: ");
        shoes.setPrice(getNumber()*100);
        System.out.print("���������� �� ������: ");
        shoes.setQuantity(getNumber());
        
        shoesFacade.create(shoes);
        /*
        this.shoes.add(shoes);
        keeper.saveShoes(this.shoes);
        */
    }
    
    private void addClient(){
        System.out.println("���������� ������ ����������");
        if(quit()) return;
        Client client = new Client();
        System.out.print("��� ����������: ");
        client.setName(scanner.nextLine());
        System.out.print("����� ����� ����������: ");
        client.setAccount(scanner.nextLine());
        System.out.print("������ ����������: ");
        client.setMoney(getNumber()*100);
        
        clientFacade.create(client);
        /*
        this.client.add(client);
        keeper.saveClient(this.client);
        */
    }
    
    private void addHistory(){
        System.out.println("������� ������");
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
        System.out.print("����� ����������: ");
        int clientNumber = insertNumber(setNumbersClient);
        //history.setClient(client.get(clientNumber-1));
        history.setClient(clientFacade.find((long)clientNumber));
        
        //2
        Set <Integer> setNumbersShoes = printShoes();
        if(setNumbersShoes.isEmpty()){
            return;}
        System.out.print("����� ������: ");
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
            System.out.println("������ �����-������� ������ �������");
        }
        else{
            System.out.println("������! ���� �� ������� ����� �� ����� �������, ���� �� ������ ���������� ��������� ������"); 
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
        System.out.println("������ �����:");
        List<Shoes> shoes = shoesFacade.findAll();
        
        for (int i = 0; i < shoes.size(); i++) {
            if (shoes.get(i)!= null && shoes.get(i).getQuantity()>0){
                System.out.println((i+1)+ " " + shoes.get(i).toString());
                setNumbersShoes.add(i+1);
            }else if(shoes.get(i)!= null){
                System.out.println("%d. %s ��� � �������.");
            }
        }
        return setNumbersShoes;
    }
    
    private Set<Integer> printClient(){
        Set<Integer> setNumbersClient = new HashSet<>();
        System.out.println("������ �����������");
        List<Client> client = clientFacade.findAll();
        
        for (int i = 0; i < client.size(); i++) {
            if (client.get(i)!=null){
                System.out.printf("%d. %s%n", (i+1), client.get(i).toString()); 
                setNumbersClient.add(i+1);
            }
        }
        if(setNumbersClient.isEmpty()){
            System.out.println("������ ����������� ����");
        }
        return setNumbersClient;
    }
    
    private Set<Integer> printHistory(){
        System.out.println("������� �������");
        Set<Integer> setNumbersHistory = new HashSet<>();
        List<History> history = historyFacade.findAll();
        
        for (int i = 0; i < history.size(); i++) {
            if (history.get(i)!=null){
                System.out.printf("%d. ������ %s ����� %s, ���������: %d%n",
                            (i+1),
                            history.get(i).getShoes().getName(), 
                            history.get(i).getClient().getName(),
                            history.get(i).getPrice()/100);
                setNumbersHistory.add(i+1);
            }
        }
        if(setNumbersHistory.isEmpty()){
            System.out.println("������ ������� ����");
        }
        return setNumbersHistory;
    }
    
   
    //------------- MONEY --------------------------
    
    private Set<Integer> shopMoney(){
        Set <Integer> setNumbersHistory = new HashSet<>();
        System.out.print("����������� ����� ��������. ������� �����: ");
        List<History> history = historyFacade.findAll();
        
        int monthMoney = getNumber();
        System.out.print("������� ���: ");
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
            System.out.println("������ ������� ����");
        }else{
            System.out.println("����� �������� �� " + monthMoney + "." + yearMoney + " ��������: " + money/100);
        }
        return setNumbersHistory;
    }
    
    private void addMoneyToAccount(){
        System.out.println("���������� ����� �� ���� ����������");
        if(quit()) return;
        
        Set<Integer> setNumbersClient = printClient();
        if(setNumbersClient.isEmpty()){
            return;}
        
        System.out.print("����� ���������� ��� ��������������: ");
        int clientNumber = insertNumber(setNumbersClient);
        Client client = clientFacade.find((long)clientNumber);
        System.out.print("������� ����� ����� ��� ���������� �� ����: ");
        int moneyToAdd = scanner.nextInt(); scanner.nextLine();
        client.setMoney(client.getMoney() + moneyToAdd * 100);
        
        clientFacade.edit(client);
        
        //keeper.saveClient(client);
    }
    
    //-------------------- UPDATE ---------------------------------
    
    private void updateShoes(){
        System.out.println("�������������� ������");
        Set <Integer> setNumbersShoes = printShoes();
        if(setNumbersShoes.isEmpty()){
            return;}
        System.out.print("������� ����� ������: ");
        int shoesNumber = insertNumber(setNumbersShoes);
        Shoes shoes = shoesFacade.find((long)shoesNumber);
        
        System.out.println("������������� ��������:" + shoes.getName());
        System.out.print("y/n: ");
        String answer = scanner.nextLine();
        if("y".equals(answer)){
            System.out.print("������� ����� ��������: ");
            shoes.setName(scanner.nextLine());
        }
        System.out.println("������������� ����:" + shoes.getPrice()/100);
        System.out.print("y/n: ");
        answer = scanner.nextLine();
        if("y".equals(answer)){
            System.out.print("������� ����� ����: ");
            shoes.setPrice(getNumber()*100);
        }
        System.out.println("������ �������� ���������� �����������? ������ �� ������: " + shoes.getQuantity());
        System.out.print("y/n: ");
        answer = scanner.nextLine();
        if("y".equals(answer)){
            System.out.print("������� ����� ����������: ");
            int newQuantity;
            do{
                newQuantity = getNumber();
                if(newQuantity >= 0){
                    break;
                }
                System.out.println("���������� ��� ���");
            }
            while(true);
            shoes.setQuantity(newQuantity);
        }
        
        shoesFacade.edit(shoes);
        
        //keeper.saveShoes(shoes);  
    }

    private void updateClient(){
        System.out.println("�������������� �������");
        if(quit()) return;
        Set <Integer> setNumbersClient = printClient();
        if(setNumbersClient.isEmpty()){
            return;
        }
        System.out.print("������� ����� �������: ");
        int clientNumber = insertNumber(setNumbersClient);
        Client client = clientFacade.find((long)clientNumber);
        
        System.out.println("������������� ���:" + client.getName());
        System.out.print("y/n: ");
        String answer = scanner.nextLine();
        if("y".equals(answer)){
            System.out.print("������� ����� ���: ");
            client.setName(scanner.nextLine());
        }
        System.out.println("������������� ����� �����:" + client.getAccount());
        System.out.print("y/n: ");
        answer = scanner.nextLine();
        if("y".equals(answer)){
            System.out.print("������� ����� ����� �����: ");
            client.setAccount(scanner.nextLine());
        }
        System.out.println("������������� ���������� ����� �� �����:" + client.getMoney()/100);
        System.out.print("y/n: ");
        answer = scanner.nextLine();
        if("y".equals(answer)){
            System.out.print("������� �����: ");
            int newMoney;
            do{
                newMoney = getNumber();
                if(newMoney >= 0){
                    break;
                }
                System.out.println("���������� ��� ���");
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
                 System.out.println("���������� ��� ���");
            }
        }while(true);
    }
    
    private int insertNumber(Set<Integer> setNumbers){
        do{
            int historyNumber = getNumber();
            if (setNumbers.contains(historyNumber)){
                return historyNumber; 
            }
            System.out.println("���������� ��� ���");
        }while(true);  
    }
    
    private boolean quit(){
        System.out.println("����� ��������� �������� ������� \"q\", ��� ����������� ����� ������ ������");
        String quit = scanner.nextLine();
        if("q".equals(quit)) return true;
      return false;
    }
    
}
