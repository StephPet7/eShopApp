/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao.EntitiesImpl;

import SingletonConnection.SingletonConnection;
import dao.IBill;
import entity.Facture;

import java.util.Date;
import java.util.List;
import javax.persistence.EntityManager;

/**
 *
 * @author TontonLaForce
 */
public class FactureImpl implements IBill {
    EntityManager em = SingletonConnection.getConnection();
    
    @Override
    public List<Facture> readBillPerDate(Date date) {
        return em.createQuery("select f from Facture f where f.dateFac= :date").setParameter("date", date).getResultList();
    }
    
    @Override
    public boolean saveFacture(Facture f) {
        em.getTransaction().begin();
        em.merge(f);
        em.flush();
        em.getTransaction().commit();
        return true ;
    }
    
}
