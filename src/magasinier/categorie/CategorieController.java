/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package magasinier.categorie;

import entity.Categorie;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import magasinier.produit.ProduitsController;

public class CategorieController implements Initializable {

    @FXML
    private Pane apercuPane;

    @FXML
    private Label nom;

    @FXML
    private Label code;

    @FXML
    private VBox listeProduits;

    @FXML
    private ImageView apercuImg;

    CategoriesController parent;    
    
    Categorie data;
    
    public void setParent(CategoriesController p){
        this.parent = p;
    }
    
    
    @FXML
    void close(MouseEvent event) {
        parent.removeCategorie(apercuPane,data);
    }

    @FXML
    void edit(MouseEvent event) {
        
    }

    @FXML
    void view(MouseEvent event) {
        Stage tmp = new Stage();
             
        FXMLLoader loader=new FXMLLoader(getClass().getResource("/magasinier/categorie/viewCategories.fxml"));

        try{ 
        Pane pane = (Pane)loader.load();
        ViewCategoriesController con = loader.getController();
        con.loadData(data);
        Scene newScene = new Scene(pane);
        tmp.setScene(newScene);   
        tmp.setResizable(false);
        tmp.showAndWait();
        } catch (IOException ex) {
        Logger.getLogger(ProduitsController.class.getName()).log(Level.SEVERE, null, ex);
    }
    }

    public void loadData(int i){
        if(parent.sortedData.size()>i){
            data = parent.sortedData.get(i);
            this.nom.setText(data.getNomCat());
            this.code.setText(data.getCode());
            //listeProduits.getChildren().addAll(new Label(data.getProduitList().get(0).getNomPro()),new Label(data.getProduitList().get(1).getNomPro()));
        }
    }
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        
    }

}
