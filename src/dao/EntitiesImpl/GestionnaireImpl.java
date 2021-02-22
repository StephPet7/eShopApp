package dao.EntitiesImpl;

import SingletonConnection.SingletonConnection;
import dao.IGestionnaire;
import entity.Gestionnaire;
import javax.persistence.EntityManager;
import java.util.List;
import javax.persistence.NoResultException;

public class GestionnaireImpl implements IGestionnaire {
    EntityManager em = SingletonConnection.getConnection();
    @Override
    public boolean loadOnBD(Gestionnaire gestionnaire) {
           em.getTransaction().begin();
           boolean flag = em.merge(gestionnaire)!=null;
           em.getTransaction().commit();
           return flag;
    }

    @Override
    public boolean deleteFromBD(List<Gestionnaire> gestionnaires) {
        for (Gestionnaire gestionnaire : gestionnaires){
            if( em.find(Gestionnaire.class , gestionnaire.getidGest())!=null ) {
                em.getTransaction().begin();
                em.remove(gestionnaire);
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
    public Gestionnaire findGestionnaire(String nom) {
        try{
            return (Gestionnaire) em.createQuery("select g from Gestionnaire g where g.login=:nom").setParameter("nom" , nom ).getSingleResult();
        }catch(NoResultException e){
            Utils.ControllerUtils.print(" No Gestionnaire Found");
        }
        return null;
    }

    @Override
    public List<Gestionnaire> readFromBD() {
        return em.createQuery("select g from Gestionnaire g").getResultList();
    }
}
