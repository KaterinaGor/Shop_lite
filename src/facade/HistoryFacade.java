/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package facade;

import entity.Shoes;
import entity.History;
import entity.Client;
import java.util.List;
import javax.persistence.EntityManager;
import tools.Singleton;

public class HistoryFacade extends AbstractFacade<History>{
    
    private EntityManager em;
    
    public HistoryFacade() {
        super(History.class);
        Singleton singleton = Singleton.getInstance();
        em = singleton.getEntityManager();
    }

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }
    
    public History findHistoryBySoldShoes(Shoes shoes) {
        return (History) getEntityManager().createQuery(
                "SELECT history FROM History history WHERE history.shoes = :shoes AND history.year = null")
                .setParameter("shoes", shoes)
                .getSingleResult();
    }

    public List<History> findHistoryWithSoldShoes() {
        return getEntityManager().createQuery(
                "SELECT h FROM History h WHERE h.year = null"
        ).getResultList();
    }
    
    public List<History> findAll(Client client) {
        return getEntityManager().createQuery("SELECT h FROM History h WHERE h.client = :client AND h.year = null")
                .setParameter("CLIENT", client)
                .getResultList();
    }

    
}
