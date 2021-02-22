package bd.esseai;

import bd.SingletonConnection.SingletonConnection;
import bd.dao.EntitiesImpl.CategoryImpl;
import bd.dao.EntitiesImpl.ProductImpl;
import bd.dao.ICategory;
import bd.dao.IProduct;
import bd.entity.Categorie;
import bd.entity.Produit;

import javax.persistence.EntityManager;
import java.math.BigDecimal;
import java.math.BigInteger;

public class testDao {
    public static void main(String[] args){
        ICategory iCategory = new CategoryImpl();
        IProduct iProduct = new ProductImpl();
        EntityManager em = SingletonConnection.getConnection();
        Produit p = new Produit( 6 , "parle-ST" , new BigDecimal(100), new BigDecimal(100) , new BigDecimal(100) , 3 , "17P1431");
        Categorie c = new Categorie(5 , "");
        em.getTransaction().begin();
//        if ( !iCategory.save(c)){
//            System.out.println("Categorie existe deja dans la base de donnée");
//        }else{
//            System.out.println("Categorie ajoutee");
//        }

//        if ( !iCategory.update(c)){
//            System.out.println("Categorie n'existe pas dans la base de donnée");
//        }else{
//            System.out.println("Categorie mis a jour");
//        }

//        if ( !iProduct.save(p)){
//            System.out.println("Produit existe deja dans la base de donnée");
//        }else{
//            System.out.println("Produit ajoutee ");
//        }

        if ( !iProduct.update(p)){
            System.out.println("le produit n'existe pas dans la BD ou le nouveau produit est a une propriete similaire a un un produit de la bd");
        }else{
            System.out.println("Produit mis a jour  ");
        }
        for( String i : iCategory.findCategoryName()){
            System.out.println( i );
        }

        em.getTransaction().commit();
       
    }
}
