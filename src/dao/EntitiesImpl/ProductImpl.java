/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao.EntitiesImpl;

import SingletonConnection.SingletonConnection;
import dao.IProduct;
import entity.Categorie;
import entity.Produit;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.Query;

/**
 *
 * @author TontonLaForce
 */
public class ProductImpl implements IProduct {
    EntityManager em = SingletonConnection.getConnection();
    
    @Override
    public List<Produit> readProduct() {
        return em.createQuery("select p from Produit p").getResultList();
    }

    @Override
    public List<String> findProductName() {
        return em.createQuery("select p.nomPro from Produit p").getResultList();
    }

    @Override
    public List<String> findProductCode() {
        return em.createQuery("select p.codePro from Produit p").getResultList();
    }

    @Override
    public boolean save(Produit produit) {
        em.getTransaction().begin();
        em.persist(produit);
        em.flush();
        em.getTransaction().commit();
        return true; // le produit a ete ajoutee dans la base de donnee
    }

    @Override
    public boolean update(Produit produit) {
        em.getTransaction().begin();
        em.merge(produit);
        em.flush();
        em.getTransaction().commit();
        return true;
    } 

    @Override
    public List<Produit> findProductByCategory(Categorie categorie) {
        return (List<Produit>) em.createQuery("select p from Produit p where p.categorieIdcat=:param").setParameter("param" , categorie.getIdCat());
    }

    @Override
    public List<Produit> readProductperCategorie(long categoryCode) {
        List<Produit> productPerCategory = null ;
        try{
            Query query = em.createQuery("select p from Produit p where p.categorieIdcat=:categoryCode");
            query.setParameter("categoryCode" , (int)categoryCode );
            productPerCategory = (List<Produit>)query.getResultList();
        }catch(IllegalStateException e){
            SingletonConnection.getEntityTransaction().rollback();
        }
        return productPerCategory ;
    }
    
    @Override
    public int findMaxId(){
        return ((int)em.createQuery("select max(p.codePro) from Produit p").getSingleResult() + 1);
    }

    @Override
    public boolean delete(Produit produit) {
        em.getTransaction().begin();
        int result = em.createQuery("DELETE FROM Produit p WHERE p.codePro=:codePro").setParameter("codePro",produit.getCodePro()).executeUpdate();
        em.flush();
        em.getTransaction().commit();
        
        return (result>0);
    }

    @Override
    public Produit findByCode(int code) {
        try{
            return (Produit) em.createQuery("select p from Produit p where p.codePro=:param").setParameter("param" , code).getSingleResult();
        }catch(Exception e){
            
        }
        return null;
    }
    
    
    
}
