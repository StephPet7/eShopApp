/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;


import entity.Categorie;
import entity.Produit;

import java.util.List;

/**
 *
 * @author TontonLaForce
 */
public interface IProduct {
    public List<Produit> readProductperCategorie(long categoryCode);
    public List<Produit> readProduct();
    public List<String> findProductName();
    public List<String> findProductCode();
    public boolean save(Produit produit);
    public boolean update(Produit produit);
    public boolean delete(Produit produit);
    public List<Produit> findProductByCategory(Categorie categorie);
    public int findMaxId();
    public Produit findByCode(int code);
}
