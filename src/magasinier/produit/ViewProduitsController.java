/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package magasinier.produit;

/**
 *
 * @author Isaac
 */

import Utils.ControllerUtils;
import com.jfoenix.controls.JFXButton;
import entity.Produit;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.Observable;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.Pagination;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class ViewProduitsController implements Initializable{
    
    private ImageView apercuImg;

    @FXML
    private Label nom;

    @FXML
    private Label code;

    @FXML
    private Label prix;

    @FXML
    private Label quantite;

    @FXML
    private Label categorie;
    
    @FXML
    private Pane apercuPane;
    
    @FXML
    private Pagination pagination;
    
    ObservableList<Produit> items ;
    
     @FXML
    private JFXButton modify;

    @FXML
    void modify(){
        try {
             Stage tmp = new Stage();
             tmp.initModality(Modality.APPLICATION_MODAL);
             FXMLLoader loader=new FXMLLoader(getClass().getResource("/magasinier/produit/editProduits.fxml"));
             
             Pane pane = (StackPane)loader.load();
             EditProduitsController con = loader.getController();
             con.setItems(items);
             con.loadData(pagination.getCurrentPageIndex());
             
             Scene newScene = new Scene(pane);
             tmp.setScene(newScene);
             tmp.setResizable(false);             
             tmp.showAndWait();
             ControllerUtils.launchTransition(pane);
         } catch (IOException ex) {
             Logger.getLogger(ProduitsController.class.getName()).log(Level.SEVERE, null, ex);
         }
        loadData(pagination.getCurrentPageIndex());
    }
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        
    }
    
    @FXML void close(){
        Stage tmp = (Stage)nom.getScene().getWindow();
        tmp.close();
    }

    public void loadData(int i){
        Produit p = items.get(i);
        nom.setText(p.getNomPro());
        code.setText(p.getCode());
        prix.setText(""+p.getPrixVente());
        quantite.setText(""+p.getQte());
        categorie.setText(p.getCategorie());
    }
    
    public void setup(ObservableList<Produit> list, int selectedIndex){
        this.items = list;
        pagination.setPageCount(list.size());
        pagination.setCurrentPageIndex(selectedIndex);
        pagination.setMaxPageIndicatorCount(5);
        pagination.setPageFactory((param) -> {
            loadData(param);
            return this.apercuPane;
        });
        loadData(selectedIndex);
        this.items.addListener((Observable c) -> {
            loadData(pagination.getCurrentPageIndex());
        });
    }
}
