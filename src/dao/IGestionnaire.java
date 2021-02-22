package dao;

import entity.Gestionnaire;

import java.util.List;

public interface IGestionnaire {

    public boolean loadOnBD(Gestionnaire gestionnaire);
    public boolean deleteFromBD(List<Gestionnaire> gestionnaires);
    public List<Gestionnaire> readFromBD();    
    public Gestionnaire findGestionnaire(String nom);
}
