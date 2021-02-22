/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package admin;

import Utils.ControllerUtils;
import Utils.Internationalization;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import entity.Gestionnaire;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.Pagination;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Stephen
 */
public class ViewGestionnaireController implements Initializable {

    @FXML
    private Pagination pagination;
    @FXML
    private Pane apercuPane;
    @FXML
    private Label matricule;
    @FXML
    private Label type;
    @FXML
    private Label email;
    @FXML
    private Label contact;
    @FXML
    private Label adresse;
    @FXML
    private Label login;
    @FXML
    private Label password;
    @FXML
    private ImageView apercuImg;
    @FXML
    private Label nom;
    @FXML
    private JFXButton modifier;
    @FXML
    private FontAwesomeIconView close;
    @FXML
    private JFXComboBox<String> langue;
    
    ResourceBundle titres;
    private ObservableList<Gestionnaire> items;
    int index;
    int tableViewPagination;
    @FXML
    private Label actif;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        titres = Internationalization.initLanguage(langue);
        langue.valueProperty().addListener((observable, oldValue, newValue) -> {
            loadResource(newValue);
        });
    }    

    @FXML
    private void loadEditGestionnaire() {
        try {
             Stage tmp = new Stage();
             //tmp.initStyle(StageStyle.UNDECORATED);
             tmp.initModality(Modality.APPLICATION_MODAL);
             FXMLLoader loader=new FXMLLoader(getClass().getResource("/admin/editGestionnaire.fxml"));
             
             Pane pane = (StackPane)loader.load();
             EditGestionnaireController con = loader.getController();
             con.setItems(items);
             con.loadData(index);
             
             Scene newScene = new Scene(pane);
             tmp.setScene(newScene);             
             /*tmp.setWidth(453);
             tmp.setHeight(555);*/
             tmp.setResizable(false);             
             tmp.showAndWait();
             ControllerUtils.launchTransition(pane);
         } catch (IOException ex) {
             Logger.getLogger(EditGestionnaireController.class.getName()).log(Level.SEVERE, null, ex);
         }
        loadData(index);
    }

    
    @FXML
    private void close() {
        Stage tmp = (Stage) langue.getScene().getWindow();
        tmp.close();
    }
    
    public void loadResource(String language){
       titres =Internationalization.getBundle(language);
       setResource();
    }
    
    public void setResource(){
    /*    slogan.setText(titres.getString("SLOGAN"));
        user.setText(titres.getString("USER"));
        lien.setText(titres.getString("DASHBOARD"));
        question.setText(titres.getString("QUESTION"));
        langue.setPromptText(titres.getString("LANG"));
        magasinier.setText(titres.getString("MAGASINIER"));
        caissier.setText(titres.getString("CAISSIER"));
        stats.setText(titres.getString("STATS"));
        factures.setText(titres.getString("FACTURES"));*/
        modifier.setText(titres.getString("MODIFY"));
    }
    
    public void loadData(int i){
        Gestionnaire e = items.get(i);
        nom.setText(e.getNom());
        matricule.setText(e.getMatricule());
        type.setText(e.getType());
        email.setText(e.getEmail());
        contact.setText(e.getContact());
        adresse.setText(e.getAdresse());
        login.setText(e.getLogin());
        password.setText(e.getPassword());
        actif.setText(e.getActif()? "Actif" : "Inactif");
    }
    
    public void setup(ObservableList<Gestionnaire> list, int selectedIndex, int page){
        this.items = list;
        index = selectedIndex;
        tableViewPagination = page;
        pagination.setPageCount(list.size());
        pagination.setCurrentPageIndex(selectedIndex);
        pagination.setMaxPageIndicatorCount(5);
        pagination.setPageFactory((param) -> {
            loadData(param);
            index = param;
            return this.apercuPane;
        });
        loadData(selectedIndex);
        
    }

}
