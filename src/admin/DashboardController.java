/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package admin;

import Utils.Internationalization;
import com.jfoenix.controls.JFXComboBox;
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
import javafx.stage.Stage;
import login.LoginController;

/**
 *
 * @author Isaac
 */
public class DashboardController implements Initializable{
    @FXML
    private Label user;
    
    @FXML
    private Label lien;

    @FXML
    private Label question;

    private Label magasinier;

    private Label caissier;

    @FXML
    private Label factures;

    private Label stats;

    @FXML
    private Label slogan;
    
    @FXML
    private JFXComboBox<String> langue;
    @FXML
    private Label lien1;
    @FXML
    private Label stock;
    @FXML
    private Pane suppliers;
    @FXML
    private Pane statistiques;
    @FXML
    private ImageView exit;

    ResourceBundle titres;
    @FXML
    private Label gestionnaires;
    
    public void loadResource(String language){
       titres =Internationalization.getBundle(language);
       setResource();
    }
    
    public void setResource(){
        slogan.setText(titres.getString("SLOGAN"));
        user.setText(titres.getString("USER"));
        lien1.setText(titres.getString("DASHBOARD"));
        question.setText(titres.getString("QUESTION"));
        langue.setPromptText(titres.getString("LANG"));
        //magasinier.setText(titres.getString("MAGASINIER"));
        //caissier.setText(titres.getString("CAISSIER"));
        //stats.setText(titres.getString("STATS"));
        factures.setText(titres.getString("FACTURES"));
        gestionnaires.setText(titres.getString("MANAGER"));
    }
    
    @FXML
    void logOut() {
        try {
             Stage tmp = (Stage)lien.getScene().getWindow();
             FXMLLoader loader=new FXMLLoader(getClass().getResource("/login/login.fxml"));
             Scene newScene = new Scene(loader.load());
             LoginController controller = loader.getController();
             controller.loadResource(langue.getValue());
             tmp.hide();
             tmp.setScene(newScene);
             tmp.show();
         } catch (IOException ex) {
             Logger.getLogger(DashboardController.class.getName()).log(Level.SEVERE, null, ex);
         }
    }
    
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Internationalization.initLanguage(langue);
        langue.valueProperty().addListener((observable, oldValue, newValue) -> {
            loadResource(newValue);
        });
    }

    @FXML
    private void loadGestionnaires() {
        try {
             Stage tmp = (Stage)lien.getScene().getWindow();
             FXMLLoader loader=new FXMLLoader(getClass().getResource("/admin/gestionnaire.fxml"));
             Scene newScene = new Scene(loader.load());
             GestionnairesController controller = loader.getController();
             controller.loadResource(langue.getValue());
             tmp.hide();
             tmp.setScene(newScene);
             tmp.show();
         } catch (IOException ex) {
             Logger.getLogger(GestionnairesController.class.getName()).log(Level.SEVERE, null, ex);
         }
    }

    @FXML
    private void loadStock(MouseEvent event) {
        try {
             Stage tmp = (Stage)lien.getScene().getWindow();
             FXMLLoader loader=new FXMLLoader(getClass().getResource("/admin/stock.fxml"));
             Scene newScene = new Scene(loader.load());
             StockController controller = loader.getController();
             controller.loadResource(langue.getValue());
             tmp.hide();
             tmp.setScene(newScene);
             tmp.show();
         } catch (IOException ex) {
             Logger.getLogger(StockController.class.getName()).log(Level.SEVERE, null, ex);
         }
    }

    @FXML
    private void loadFactures(MouseEvent event) {
        try {
             Stage tmp = (Stage)lien.getScene().getWindow();
             FXMLLoader loader=new FXMLLoader(getClass().getResource("/admin/factures.fxml"));
             Scene newScene = new Scene(loader.load());
             FacturesController controller = loader.getController();
             controller.loadResource(langue.getValue());
             tmp.hide();
             tmp.setScene(newScene);
             tmp.show();
         } catch (IOException ex) {
             Logger.getLogger(DashboardController.class.getName()).log(Level.SEVERE, null, ex);
         }
    }

    @FXML
    private void loadSuppliers(MouseEvent event) {
    }

    @FXML
    private void loadStats(MouseEvent event) {
    }
}
