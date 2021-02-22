/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import entity.Gestionnaire;
import entity.Gestionstock;
import java.util.List;

/**
 *
 * @author Stephen
 */
public interface IStock {
    public boolean loadOnBD(Gestionstock stock);
    public boolean deleteFromBD(List<Gestionstock> gestionnaires);
    public List<Gestionstock> readFromBD();
    public boolean addGestionStock(Gestionstock gestionstock);
}
