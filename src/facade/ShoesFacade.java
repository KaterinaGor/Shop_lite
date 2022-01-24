
package facade;

import entity.Shoes;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import tools.Singleton;

public class ShoesFacade extends AbstractFacade<Shoes> {
    
    private EntityManager em;
    
    public ShoesFacade() {
        super(Shoes.class);
        Singleton singleton = Singleton.getInstance();
        em = singleton.getEntityManager();
    }

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }
    
    public List<Shoes> fingEnabledShoes() {
        try {
            return em.createQuery("SELECT shoes FROM Shoes WHERE quantity > 0")
                    .getResultList();
        } catch (Exception e) {
            return new ArrayList<>();
        }
        
    }
    
}
