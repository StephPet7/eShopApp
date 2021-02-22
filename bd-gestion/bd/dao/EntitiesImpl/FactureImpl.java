/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bd.dao.EntitiesImpl;

import bd.SingletonConnection.SingletonConnection;
import bd.dao.IBill;
import bd.entity.Facture;

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
    
}
