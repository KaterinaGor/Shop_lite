
package keeper;

import entity.Client;
import entity.History;
import entity.Shoes;
import entity.Shop;
import interfaces.Keeping;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

public class BaseKeeper implements Keeping{
    private EntityManagerFactory emf = Persistence.createEntityManagerFactory("Shop_litePU");
    private EntityManager em = emf.createEntityManager();
    private EntityTransaction tx = em.getTransaction();

    @Override
    public void saveShoes(List<Shoes> shoes) {
        tx.begin();
        for (int i=0; i<shoes.size(); i++){
            if(shoes.get(i).getId()==null){
                em.persist(shoes.get(i));
            }
        }
        tx.commit();
    }

    @Override
    public List<Shoes> loadShoes() {
        List<Shoes> shoes = null;
        try{
            shoes = em.createQuery("SELECT shoes from Shoes shoes")
                    .getResultList();            
        }catch (Exception e){
            shoes = new ArrayList<>();
        }
        return shoes;
    }

    @Override
    public void saveClient(List<Client> client) {
        tx.begin();
        for (int i=0; i<client.size(); i++){
            if(client.get(i).getId()==null){
                em.persist(client.get(i));
            }
        }
        tx.commit();
    }

    @Override
    public List<Client> loadClient() {
        List<Client> client = null;
        try{
            client = em.createQuery("SELECT client FROM Client client").getResultList();            
        }catch (Exception e){
            client = new ArrayList<>();
        }
        return client;
    }

    @Override
    public void saveHistory(List<History> history) {
        tx.begin();
        for (int i=0; i<history.size(); i++){
            if(history.get(i).getId()==null){
                em.persist(history.get(i));
            }
        }
        tx.commit();
    }

    @Override
    public List<History> loadHistory() {
        List<History> history = null;
        try{
            history = em.createQuery("SELECT history FROM History history")
                    .getResultList();            
        }catch (Exception e){
            history = new ArrayList<>();
        }
        return history;
    }

    @Override
    public void saveShop(List<Shop> shop) {
        tx.begin();
        for (int i=0; i<shop.size(); i++){
            if(shop.get(i).getId()==null){
                em.persist(shop.get(i));
            }
        }
        tx.commit();
    }

    @Override
    public List<Shop> loadShop() {
        List<Shop> shop = null;
        try{
            shop = em.createQuery("SELECT shop FROM Shop shop")
                    .getResultList();            
        }catch (Exception e){
            shop = new ArrayList<>();
        }
        return shop;
    }
}
