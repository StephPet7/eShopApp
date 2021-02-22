/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao.EntitiesImpl;

import SingletonConnection.SingletonConnection;
import dao.IStock;
import entity.Gestionnaire;
import entity.Gestionstock;
import java.util.List;
import javax.persistence.EntityExistsException;
import javax.persistence.EntityManager;

/**
 *
 * @author Stephen
 */
public class GestionStockImpl implements IStock{
    EntityManager em = SingletonConnection.getConnection();
    @Override
    public boolean loadOnBD(Gestionstock stock) {
           em.getTransaction().begin();
           boolean flag = em.merge(stock)!=null;
           em.getTransaction().commit();
           return flag;
    }

    @Override
    public boolean deleteFromBD(List<Gestionstock> listStock) {
        for (Gestionstock stock : listStock){
            if( em.find(Gestionstock.class , stock.getIdStock())!=null ) {
                em.getTransaction().begin();
                em.remove(stock);
                em.getTransaction().commit();
            }
            else {
                System.out.println("Erreur Suppression");
                return false;
            }
        }
        return true;
    }
    
     @Override
    public boolean addGestionStock(Gestionstock gestionstock) {
        try{
            em.getTransaction().begin();
            em.persist(gestionstock);
            em.getTransaction().commit();
            return true ;
        }catch(EntityExistsException e){
            Utils.ControllerUtils.print(" Exception in GestionStockImpl : Entity Exists ");
        }        
        return false;
    }

    @Override
    public List<Gestionstock> readFromBD() {
        return em.createQuery("select g from Gestionstock g").getResultList();
    }
}
