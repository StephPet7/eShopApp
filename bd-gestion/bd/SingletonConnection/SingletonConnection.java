/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bd.SingletonConnection;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

/**
 *
 * @author TontonLaForce
 */
public class SingletonConnection {
    private static EntityManager em ;
    static{
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("projet_eShopPU"); 
        em = emf.createEntityManager();
    }
    public static EntityManager getConnection(){
        return em ;
    }
    public static EntityTransaction getEntityTransaction(){
        return em.getTransaction();
    }
    
}
