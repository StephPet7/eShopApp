/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package login;

import static Utils.ControllerUtils.showNotification;
import Utils.Internationalization;
import caissier.FacturationController;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXCheckBox;

import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import dao.EntitiesImpl.GestionnaireImpl;
import entity.Gestionnaire;
import java.io.IOException;
import java.net.URL;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;

/**
 *
 * @author Isaac
 */
public class LoginController implements Initializable {
    
     @FXML
    private JFXTextField user;

    @FXML
    private JFXPasswordField pass;

    @FXML
    private JFXCheckBox asAdmin;

    @FXML
    private JFXButton login;

    @FXML
    private JFXButton forget;
    
    @FXML
    private JFXComboBox<String> langue;
     
    @FXML
    private Label welcome;

    @FXML
    private Label slogan;
    
    ResourceBundle titres;

    GestionnaireImpl gestImpl = new GestionnaireImpl();
    
    public static Gestionnaire gestionnaire;
    
    @FXML
    void logIn() {
        try{
            gestionnaire = gestImpl.findGestionnaire(user.getText());
            if(gestionnaire == null){
                showNotification("Eclipse Link - JPA[Login]","No Users Found with username = "+user.getText(),true,Pos.BOTTOM_RIGHT,5);
            }else if (gestionnaire.getPassword().equals(pass.getText())){
                Stage tmp = (Stage)slogan.getScene().getWindow();
                FXMLLoader loader=null;
                Scene newScene = null;
                if(asAdmin.isSelected()){
                    if(gestionnaire.getType().equals("ADMINISTRATEUR")){
                        loader = new FXMLLoader(getClass().getResource("/admin/dashboard.fxml"));
                        newScene = new Scene(loader.load());
                        admin.DashboardController controller= loader.getController();
                        controller.loadResource(langue.getValue());
                        tmp.setScene(newScene);             
                        tmp.hide();
                        tmp.show();
                        tmp.setMaximized(true);
                    }else{
                        showNotification("Eclipse Link - JPA[Login Privilege]",gestionnaire.getLogin() +" vous n'avez pas les privileges pour vous connectez en tant Administrateur",true,Pos.BOTTOM_RIGHT,5);
                    }
                }else{
                    if(gestionnaire.getType().equals("MAGASINIER")){
                        loader = new FXMLLoader(getClass().getResource("/magasinier/categorie/categories.fxml"));
                        newScene = new Scene(loader.load());
                        magasinier.categorie.CategoriesController controller= loader.getController();
                        controller.loadResource(langue.getValue());
                        tmp.setScene(newScene);             
                        tmp.hide();
                        tmp.show();
                        tmp.setMaximized(true);
                    }else if (gestionnaire.getType().equals("CAISSIER")){
                        loader = new FXMLLoader(getClass().getResource("/caissier/facturation.fxml"));
                        newScene = new Scene(loader.load());
                        FacturationController controller= loader.getController();
                        controller.loadResource(langue.getValue());
                        tmp.setScene(newScene);             
                        tmp.hide();
                        tmp.show();
                        tmp.setMaximized(true);
                    }
                }
            }else{
                showNotification("Eshop [Login]","Username/Password is wrong.\n Verify your informations and try again",true,Pos.BOTTOM_RIGHT,5);
            }
        } catch (IOException ex) {
            Logger.getLogger(LoginController.class.getName()).log(Level.SEVERE, null, ex);
        }        
    }

    @FXML
    void passwordForget(ActionEvent event) {
        showNotification("Eshop [Login]","Please contact an Administrator.",false,Pos.BOTTOM_RIGHT,5);
    }
    
    public void loadResource(String language){
        titres =Internationalization.getBundle(language);
        setResource();
    }
    
    public void setResource(){
        welcome.setText(titres.getString("WELCOME"));
        slogan.setText(titres.getString("SLOGAN"));
        user.setPromptText(titres.getString("USER"));
        pass.setPromptText(titres.getString("PASS"));
        langue.setPromptText(titres.getString("LANG"));
        forget.setText(titres.getString("PASSFORGET"));
        asAdmin.setText(titres.getString("LOGINASADMIN"));
        login.setText(titres.getString("LOG"));
        cgu.setText(titres.getString("CGU"));
        showNotification("Eshop [Login]","Connection à la B.D. établie",false,Pos.BOTTOM_RIGHT,5);
    }    
    
    @FXML
    private Label cgu;
    
     @FXML
    void loadCGU() {
        try {
             Stage tmp =new Stage();
             FXMLLoader loader = new FXMLLoader(getClass().getResource("/login/cgu.fxml")) ;
             Scene newScene = new Scene(loader.load());             
             tmp.setScene(newScene);
             tmp.centerOnScreen();
             tmp.setResizable(false);
             tmp.show();
         } catch (IOException ex) {
             Logger.getLogger(LoginController.class.getName()).log(Level.SEVERE, null, ex);
         }
    }
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
       
        titres = Internationalization.initLanguage(langue);
        langue.valueProperty().addListener((observable, oldValue, newValue) -> {
            loadResource(newValue);
        });
        gestionnaire = null;
    }
    
}
