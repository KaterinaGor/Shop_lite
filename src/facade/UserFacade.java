/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package facade;

import entity.Client;
import entity.User;
import javax.persistence.EntityManager;
import tools.Singleton;

/**
 *
 * @author 37255
 */
public class UserFacade extends AbstractFacade<User>{
    private EntityManager em;
    public UserFacade() {
        super(User.class);
        Singleton singleton = Singleton.getInstance();
        em = singleton.getEntityManager();
    }

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }
    public User find(String login){
        try {
            return (User) em.createQuery("SELECT u FROM User u WHERE u.login = :login")
                    .setParameter("login", login)
                    .getSingleResult();
        } catch (Exception e) {
            return null;
        }
    }
    
}
