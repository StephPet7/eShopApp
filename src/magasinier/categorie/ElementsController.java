/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package magasinier.categorie;

import entity.Produit;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
/**
 *
 * @author Isaac
 */
public class ElementsController implements Initializable{
    
    @FXML
    private HBox root;
    
    @FXML
    private ImageView img;

    @FXML
    private Label nom;

    @FXML
    private Label code;
    
    ArrayList<Produit> listProduits;
    
    @FXML
    void remove() {
        listProduits.remove(index);
        v.listProduits.getChildren().remove(this.root);
        v.initPagination();
    }
    
    ViewCategoriesController v;
    
    int index;
    
    public void loadData(ArrayList<Produit> a,int d,ViewCategoriesController  c){
        this.v = c;
        this.listProduits=a;
        this.index = d;
        nom.setText(a.get(index).getNomPro());
        code.setText(a.get(index).getCode()+" ("+a.get(index).getPrixVente()+" FCFA)");
        // img
    }
    
    
    
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        
    }
    
    
}
