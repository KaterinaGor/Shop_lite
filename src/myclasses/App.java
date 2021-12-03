
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
            System.out.println("�������� ������");
            System.out.println("0: ��������� ���������");
            System.out.println("1: �������� ����� (addShoes)");
            System.out.println("2: ������ �������(printShoes)");
            System.out.println("3: �������� ����������(addClient)");
            System.out.println("4: ������ �����������(printClient)");
            System.out.println("5: ������ �����(addHistory)");
            System.out.println("6: ������� �������(printHistory)");
            
            System.out.println("7: ����� ��������(AllMoney)");
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
        
        this.shoes.add(shoes);
        keeper.saveShoes(this.shoes);
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
        this.client.add(client);
        keeper.saveClient(this.client);
    }
    
    private void addHistory(){
        System.out.println("������� ������");
        if(quit()) return;
        
        History history = new History();
        
        //1
        Set<Integer> setNumbersClient = printClient();
        if(setNumbersClient.isEmpty()){
            return;} 
        System.out.print("����� ����������: ");
        int clientNumber = insertNumber(setNumbersClient);
        history.setClient(client.get(clientNumber-1));
        
        //2
        Set <Integer> setNumbersShoes = printShoes();
        if(setNumbersShoes.isEmpty()){
            return;}
        System.out.print("����� ������: ");
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
            System.out.println("������ �����-������� ������ �������");
        }
        else{
            System.out.println("������! ���� �� ������� ����� �� ����� �������, ���� �� ������ ���������� ��������� ������"); 
        }

        this.history.add(history);
        keeper.saveHistory(this.history);
        
        keeper.saveClient(client);
        keeper.saveShoes(shoes);
    }
    
    //----------------- PRINT ------------------------------------------

    private Set<Integer> printShoes(){
        Set <Integer> setNumbersShoes = new HashSet();
        System.out.println("������ ������� �����:");
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
        Set<Integer> setNumbersClient = new HashSet();
         System.out.println("������ �����������");
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
        Set<Integer> setNumbersHistory = new HashSet();
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
        Set <Integer> setNumbersHistory = new HashSet();
        System.out.print("����������� ����� ��������. ������� �����: ");
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
        
        System.out.print("����� ����������: ");
        int clientNumber = insertNumber(setNumbersClient);
        System.out.print("������� ����� ����� ��� ���������� �� ����: ");
        int moneyToAdd = scanner.nextInt(); scanner.nextLine();
        client.get(clientNumber-1).setMoney(client.get(clientNumber-1).getMoney() + moneyToAdd * 100);
        keeper.saveClient(client);
    }
    
    //-------------------- UPDATE ---------------------------------
    
    private void updateShoes(){
        System.out.println("�������������� ������");
        Set <Integer> setNumbersShoes = printShoes();
        if(setNumbersShoes.isEmpty()){
            return;}
        System.out.print("������� ����� ������: ");
        int shoesNumber = insertNumber(setNumbersShoes);
        System.out.println("������������� ��������:" + shoes.get(shoesNumber-1).getName());
        System.out.print("y/n: ");
        String answer = scanner.nextLine();
        if("y".equals(answer)){
            System.out.print("������� ����� ��������: ");
            shoes.get(shoesNumber-1).setName(scanner.nextLine());
        }
        System.out.println("������������� ����:" + shoes.get(shoesNumber-1).getPrice()/100);
        System.out.print("y/n: ");
        answer = scanner.nextLine();
        if("y".equals(answer)){
            System.out.print("������� ����� ����: ");
            shoes.get(shoesNumber-1).setPrice(getNumber()*100);
        }
        System.out.println("������ �������� ���������� �����������? ������ �� ������: " + shoes.get(shoesNumber-1).getQuantity());
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
            shoes.get(shoesNumber-1).setQuantity(newQuantity);
        }
        keeper.saveShoes(shoes);  
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
        System.out.println("������������� ���:" + client.get(clientNumber-1).getName());
        System.out.print("y/n: ");
        String answer = scanner.nextLine();
        if("y".equals(answer)){
            System.out.print("������� ����� ���: ");
            client.get(clientNumber-1).setName(scanner.nextLine());
        }
        System.out.println("������������� ����� �����:" + client.get(clientNumber-1).getAccount());
        System.out.print("y/n: ");
        answer = scanner.nextLine();
        if("y".equals(answer)){
            System.out.print("������� ����� ����� �����: ");
            client.get(clientNumber-1).setAccount(scanner.nextLine());
        }
        System.out.println("������������� ���������� ����� �� �����:" + client.get(clientNumber-1).getMoney()/100);
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