

package facade;

import entity.Shop;
import javax.persistence.EntityManager;
import tools.Singleton;

public class ShopFacade extends AbstractFacade<Shop>{
   
    private EntityManager em;
    
    public ShopFacade() {
        super(Shop.class);
        Singleton singleton = Singleton.getInstance();
        em = singleton.getEntityManager();
    }

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }
    
}
