package dao.EntitiesImpl;

import SingletonConnection.SingletonConnection;
import dao.ILigneFacture;
import entity.Lignefacture;

import javax.persistence.EntityManager;
import java.util.List;

public class LigneFactureImpl implements ILigneFacture {
    EntityManager em = SingletonConnection.getConnection();
    @Override
    public boolean saveLigneFacture(List<Lignefacture> lignes) {
        em.getTransaction().begin();
        lignes.forEach((ligne) -> {
            em.merge( ligne );
        });
        em.getTransaction().commit();
        return true;
    }
}
