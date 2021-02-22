/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bd.dao.EntitiesImpl;

import bd.SingletonConnection.SingletonConnection;
import bd.dao.ICategory;
import bd.entity.Categorie;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.Query;

/**
 *
 * @author TontonLaForce
 */
public class CategoryImpl implements ICategory {
    EntityManager em = SingletonConnection.getConnection();
    
    @Override
    public List<Categorie> readCategory() {
       return em.createQuery("select c from Categorie c ").getResultList();
    }

    @Override
    public boolean save(Categorie categorie) {

        try{
            List<Categorie> categories = em.createQuery("select c from Categorie c where c.idCat=:id or c.nomCat=:nom").setParameter("id" , categorie.getIdCat()).setParameter("nom" , categorie.getNomCat()).getResultList();
            if( !categories.isEmpty() ) return false ; //la categorie existe deja dans la BD
            em.persist(categorie);
        }catch(IllegalStateException e){
            SingletonConnection.getEntityTransaction().rollback();
        }

        return true; // la categorie a ete ajoutee dans la BD
    }

    @Override
    public boolean update(Categorie categorie) {
        List<Categorie> categories = em.createQuery("select c from Categorie c where c.idCat=:id ").setParameter("id" , categorie.getIdCat()).getResultList();
        if( categories.isEmpty()) return false ; // la categorie n'existe pas dans le BD
        em.merge(categorie);
        return true; // La categorie a ete bien mis a jour
    }

    @Override
    public List<String> findCategoryName() {
        return em.createQuery("select c.nomCat from Categorie c").getResultList();
    }

}
