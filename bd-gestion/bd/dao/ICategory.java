/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bd.dao;


import bd.entity.Categorie;

import java.util.List;

/**
 *
 * @author TontonLaForce
 */
public interface ICategory {
    public List<Categorie> readCategory();
    public boolean save(Categorie categorie);
    public boolean update(Categorie categorie);
    public List<String> findCategoryName();
    
}
