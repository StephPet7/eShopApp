/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package magasinier.categorie;

import Utils.Internationalization;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.Initializable;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import Utils.ControllerUtils;
import dao.EntitiesImpl.CategoryImpl;
import entity.Categorie;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.Pagination;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import login.LoginController;
import magasinier.dashboard.DashboardController;
import magasinier.produit.ProduitsController;

/**
 * FXML Controller class
 *
 * @author Isaac
 */
public class CategoriesController implements Initializable {

     @FXML
    private Label lien;

    @FXML
    private Label lien1;

    @FXML
    private Label slogan;
    /**********************         *                     */

    @FXML
    private Label username;

    @FXML
    private Label nameProfil;

    @FXML
    private Label idProfil;

    @FXML
    private JFXButton showProfil;
    
    /*********************************************/
    @FXML
    private JFXButton dashboard;

    @FXML
    private JFXButton produits;

    @FXML
    private JFXButton categories;
    
    @FXML
    private JFXButton print;

    @FXML
    private JFXButton refresh;
    
    @FXML
    private JFXButton add;

    @FXML
    private JFXButton modify; 
    
    @FXML
    private Pagination pagination;
    
    @FXML
    private FlowPane flowPane;
    
    /******************** User popup ********************/
    
    @FXML
    private Pane userPopup;
    
    @FXML
    public void hideUserPopup(MouseEvent e){
        ControllerUtils.closeTransition(userPopup);
        userPopup.setVisible(false);
    }
    
    @FXML
    public void showUserPopup(MouseEvent e){        
        userPopup.setVisible(true);
        ControllerUtils.openTransition(userPopup);
    }
    /******************************************************/
    
    @FXML
    private JFXButton delete;
    @FXML
    void delete() {
        
    }    
    /****************************** /*************************************/

    @FXML
    void add() {
       //launchEditProduit(-1);
    }
    
    @FXML
    void modify() {
       // launchEditProduit(tableView.getSelectionModel().getSelectedIndex());
    }
    
    void afficher(){
        //launchAfficherProduit(tableView.getSelectionModel().getSelectedIndex());
    }
    
    private void launchAfficherProduit(int i) {
        /*Stage tmp = new Stage();
             
             FXMLLoader loader=new FXMLLoader(getClass().getResource("/magasinier/viewProduits.fxml"));
             
             try{ 
             Pane pane = (Pane)loader.load();
             ViewProduitsController con = loader.getController();
             con.setup(tableView.getItems(), i);
             Scene newScene = new Scene(pane);
             newScene.setFill(Color.TRANSPARENT);
             tmp.setScene(newScene);   
             tmp.setResizable(false);             
             
             tmp.initStyle(StageStyle.TRANSPARENT);
             tmp.initModality(Modality.APPLICATION_MODAL);
             tmp.showAndWait();
             } catch (IOException ex) {
             Logger.getLogger(ProduitsController.class.getName()).log(Level.SEVERE, null, ex);
         }*/
    }    
    
    static ObservableList<Categorie> listCategorie;
    
    public FilteredList<Categorie> filteredData;
    
    public SortedList<Categorie> sortedData ;
    
    
    CategoryImpl impl = new CategoryImpl();
    
    public void loadCategoryData(){
        //Load Suppliers from Database
        listCategorie = FXCollections.observableArrayList(impl.readCategory());
       
       
        //Wrap the ObservableList in a FilteredList (initially display all data).
        filteredData = new FilteredList<>(listCategorie,p->true);
        
        //Set the filter Predicate whenever the filter changes.
        searchTextField.textProperty().addListener((ObservableValue<? extends String> observable, String oldValue, String newValue) -> {
            filteredData.setPredicate(person->{
                if(newValue == null || newValue.isEmpty()){ 
                    searchButton.setVisible(true);
                    stopSearchButton.setVisible(false);
                    return true;
                }
                
                searchButton.setVisible(false);
                stopSearchButton.setVisible(true);
                
                // Compare name of every suppliers with filter text.
                String lowerCaseFilter = newValue.toLowerCase();
                int i =searchColumn.getItems().indexOf(searchColumn.getValue());
                switch(i)
                {
                    case 0:
                        if(person.getCode().toLowerCase().contains(lowerCaseFilter)) return true;
                        break;
                    case 1:
                        if(person.getNomCat().toLowerCase().contains(lowerCaseFilter)) return true;
                        break;
                    default:
                        break;
                }
                return false;
            });        
        });
        
        // Wrap the FilteredList in a SortedList. 
        sortedData = new SortedList<>(filteredData);
        
        sortedData.addListener(new InvalidationListener() {
            @Override
            public void invalidated(Observable c) {
                initPagination();
            }
        });
    }
    
    void launchEditProduit(int p){
       /* try {
             Stage tmp = new Stage();
             //tmp.initStyle(StageStyle.UNDECORATED);
             tmp.initModality(Modality.APPLICATION_MODAL);
             FXMLLoader loader=new FXMLLoader(getClass().getResource("/magasinier/produit/editProduits.fxml"));
             
             Pane pane = (StackPane)loader.load();
             EditProduitsController con = loader.getController();
             con.setItems(listCategorie);
             if(p!=-1){
                 con.loadData(p);
             }else{
                 con.setType(true);
             }
             
             Scene newScene = new Scene(pane);
             tmp.setScene(newScene);             
             /*tmp.setWidth(453);
             //tmp.setHeight(555);
             tmp.setResizable(false);             
             tmp.showAndWait();
             ControllerUtils.launchTransition(pane);
         } catch (IOException ex) {
             Logger.getLogger(ProduitsController.class.getName()).log(Level.SEVERE, null, ex);
         }*/
    }
 /* *************************************************************** */ 
    
    /********* search option *********************/
    @FXML
    private TextField searchTextField;

    @FXML
    private JFXButton searchButton;
    
    @FXML
    private JFXButton stopSearchButton;
    
    @FXML
    private Label searchBy;

    @FXML
    private JFXComboBox<String> searchColumn;
    
    void initSearch(){
        searchColumn.getItems().addAll("Code",  titres.getString("NAME"));
        searchColumn.setValue("Code");
        initSearchPane();
        searchTextField.textProperty().addListener((observable, oldValue, newValue) -> {
            stopSearchButton.setVisible(!newValue.isEmpty());
            searchButton.setVisible(newValue.isEmpty());
        });
    }
    
    @FXML
    void initSearchPane(){
        searchTextField.clear();
        searchButton.setVisible(true);
        stopSearchButton.setVisible(false);
    }
/******************              ************************/
    
    @FXML
    void loadProduitsInterface() {
        try {
             Stage tmp=(Stage) lien.getScene().getWindow();  
             FXMLLoader loader=  new FXMLLoader(getClass().getResource("/magasinier/produit/produits.fxml"));
             Scene newScene = new Scene(loader.load());
             ProduitsController controller = loader.getController();
             controller.loadResource(langue.getValue());
             tmp.setScene(newScene);
             tmp.hide();
             tmp.show();
             tmp.setMaximized(true);
         } catch (IOException ex) {
             Logger.getLogger(LoginController.class.getName()).log(Level.SEVERE, null, ex);
         }
    }

    @FXML
    void logOut() {
        loadInterface(false);
    }

    @FXML
    void dashboard() {
        loadInterface(true);
    }

    void loadInterface(boolean test){
        try {
             Stage tmp = (Stage) lien.getScene().getWindow();
             FXMLLoader loader=new FXMLLoader(getClass().getResource(test?"/magasinier/dashboard/dashboard.fxml":"/login/login.fxml"));
            Scene newScene;
            newScene =  new Scene(loader.load());
             if(test){
                  DashboardController controller = loader.getController();
                 controller.loadResource(langue.getValue());
             }else{
                LoginController controller = loader.getController();
                controller.loadResource(langue.getValue());
             }
             tmp.setScene(newScene);
             tmp.sizeToScene();
             /*tmp.hide();
             tmp.show();*/
         } catch (IOException ex) {
             Logger.getLogger(LoginController.class.getName()).log(Level.SEVERE, null, ex);
         }
    }
    
    @FXML
    void print(ActionEvent event) {

    }

    @FXML
    void refresh(ActionEvent event) {

    }

    Pane loadCategorie(int i){
        try { 
             FXMLLoader loader=new FXMLLoader(getClass().getResource("/magasinier/categorie/categorie.fxml"));
             Pane p = loader.load();
             CategorieController controller = loader.getController();             
             controller.setParent(this);
             controller.loadData(i);
             return p;
         } catch (IOException ex) {
             Logger.getLogger(LoginController.class.getName()).log(Level.SEVERE, null, ex);
         }
        return null;
    }
    
    /********** Gestion de la langue *************/
    
    @FXML
    private JFXComboBox<String> langue;
    
    ResourceBundle titres;  
    
    public void loadResource(String language){
       titres =Internationalization.getBundle(language);
       setResource();
    }
    
    public void setResource(){
        /* ******** A Completer *************/
        lien.setText(titres.getString("MAGASINIER"));
        lien1.setText(titres.getString("PRODUITS"));
        dashboard.setText(titres.getString("DASHBOARD"));
        produits.setText(titres.getString("PRODUITS"));
        categories.setText(titres.getString("CATEGORY"));
        slogan.setText(titres.getString("SLOGAN"));
        
        
        add.setText(titres.getString("ADD")); 
        delete.setText(titres.getString("DELETE")); 
        refresh.setText(titres.getString("REFRESH")); 
        print.setText(titres.getString("PRINT"));
        modify.setText(titres.getString("MODIFY"));
        searchBy.setText(titres.getString("SEARCH_BY"));
        searchTextField.setPromptText(titres.getString("SEARCH_PRODUCTS"));
        int i = searchColumn.getItems().indexOf(searchColumn.getValue());
        searchColumn.getItems().clear();
        searchColumn.getItems().addAll("Code",  titres.getString("NAME"));
        searchColumn.setValue(searchColumn.getItems().get(i));
        
    }

    /**
     * @param cat*
     * @param c*********************************************/
    public void removeCategorie(Pane cat, Categorie c){
        listCategorie.remove(c);
        initPagination();
    }    
    
    /**
     * Initializes the controller class.
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        ControllerUtils.setProfil( username,nameProfil,idProfil,showProfil);
        userPopup.setVisible(false);
        titres = Internationalization.initLanguage(langue);
        langue.valueProperty().addListener((observable, oldValue, newValue) -> {
            loadResource(newValue);
        });
        
        loadCategoryData();
                
        initSearch();        
     
        initPagination();

        modify.setDisable(true);
        delete.setDisable(true);
    }
    
    void initPagination(){
        int tmp =  (int)Math.ceil(sortedData.size()/4.0);
        pagination.setPageCount(tmp);
        pagination.setCurrentPageIndex(0);
        pagination.setMaxPageIndicatorCount(tmp);
        flowPane.getChildren().clear();
                
        int f = 4;
        if(f >= sortedData.size()) f = sortedData.size();

        for(int i=0;i<f;i++){
            flowPane.getChildren().add(loadCategorie(i));
        }
        pagination.setPageFactory((param) -> {
            flowPane.getChildren().clear();

            int fin = 4*param+4;
            if(fin >= sortedData.size()) fin = sortedData.size();

            for(int i=4*param;i<fin;i++){
                flowPane.getChildren().add(loadCategorie(i));
            }

            return this.flowPane;
        });            
    }
}
