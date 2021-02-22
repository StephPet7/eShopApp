/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package magasinier.produit;

import magasinier.categorie.CategoriesController;
import Utils.Internationalization;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.Initializable;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import Utils.ControllerUtils;
import dao.EntitiesImpl.ProductImpl;
import entity.Produit;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.Pagination;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import login.LoginController;
import magasinier.dashboard.DashboardController;

/**
 * FXML Controller class
 *
 * @author Isaac
 */
public class ProduitsController implements Initializable {

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
    
    ProductImpl proImpl = new ProductImpl();
    
    /************ Pagination ************/
    
    @FXML
    private Pagination pagination;
    
     void initPagination(){
        if(sortedData.isEmpty()) {
            pagination.setPageCount(0);
            pagination.setCurrentPageIndex(0);
            pagination.setMaxPageIndicatorCount(0);
            tableView.getItems().clear();
            return;
        }
        int tmp =  (int)Math.ceil(sortedData.size()/5.0);
        pagination.setPageCount(tmp);
        pagination.setCurrentPageIndex(0);
        pagination.setMaxPageIndicatorCount(tmp);
        tableView.getItems().clear();

        int f = 5;
        if(f >= sortedData.size()) f = sortedData.size();

        tableView.getItems().addAll(sortedData.subList(0, f));

        pagination.setPageFactory((param) -> {
        tableView.getItems().clear();

        int fin = 5*param+5;
        if(fin >= sortedData.size()) fin = sortedData.size();

        if(sortedData.size()>5*param) tableView.getItems().addAll(sortedData.subList(5*param, fin));

        return this.tableView;
    });
    }
    
    
    /******************** User popup ********************/
    
    @FXML
    private Pane userPopup;
    @FXML
    private JFXButton historique;
    
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
    
    /********************** Tableview Popup *************************/
    
    @FXML
    private Pane popup;
    
    public void showPopup(MouseEvent e){
        popup.relocate(e.getSceneX(), e.getSceneY()+3);
        popup.setVisible(true);
        ControllerUtils.openTransition(popup);
    }
    
    @FXML
    public void hidePopup(MouseEvent e){
        ControllerUtils.closeTransition(popup);
        popup.setVisible(false);
    }
    
    @FXML
    public void modifyPopup(){
        modify();
        hidePopup(null);
    }
    
    @FXML
    public void afficherPopup(){
        afficher();
        hidePopup(null);
    }
    
    @FXML
    public void deletePopup(){
        delete();
        hidePopup(null);
    }
    
    /************************** /******************************/ 
    @FXML
    private JFXButton delete;
    @FXML
    void delete() {
            
         ObservableList<Produit> items = tableView.getSelectionModel().getSelectedItems();
         if(items == null)
         {
             //message d'erreur "Pas d'elements selectionnes
         }else{
              //**supprimer l'enregistrement selectionne
             //**Retirer a la tableView
             items.forEach((data) -> {
                // fournisseursUtils.delete(data); //
                if(proImpl.delete(data)){
                    System.out.println("Deleted :" + data);
                    Utils.ControllerUtils.showNotification("Eclipse Link - JPA[MySql Connection]","Deleting complete successfuly",false,Pos.BOTTOM_RIGHT,30);
                    tableView.getItems().remove(data);
                    listProduits.remove(data);
                }else{
                    System.out.println("Deleting failed :" + data);                    
                    Utils.ControllerUtils.showNotification("Eclipse Link - JPA[MySql Connection]","Deleting failed",true,Pos.BOTTOM_RIGHT,30);
                }
             });
             pagination.setCurrentPageIndex(0);
         }
    }    
    /****************************** /*************************************/

    @FXML
    void add() {
        launchEditProduit(-1);
    }
    
    @FXML
    void modify() {
        launchEditProduit(listProduits.indexOf(tableView.getSelectionModel().getSelectedItem()) );
        initPagination();
    }
    
    void afficher(){
        launchAfficherProduit(listProduits.indexOf(tableView.getSelectionModel().getSelectedItem()));
    }
    
    private void launchAfficherProduit(int i) {
        Stage tmp = new Stage();
             
             FXMLLoader loader=new FXMLLoader(getClass().getResource("/magasinier/produit/viewProduits.fxml"));
             
             try{ 
             Pane pane = (Pane)loader.load();
             ViewProduitsController con = loader.getController();
             con.setup(listProduits, i);
             Scene newScene = new Scene(pane);
             newScene.setFill(Color.TRANSPARENT);
             tmp.setScene(newScene);   
             tmp.setResizable(false);             
             
             tmp.initStyle(StageStyle.TRANSPARENT);
             tmp.initModality(Modality.APPLICATION_MODAL);
             tmp.showAndWait();
             } catch (IOException ex) {
             Logger.getLogger(ProduitsController.class.getName()).log(Level.SEVERE, null, ex);
         }
    }
    
    /* ***************************table view ****************/
    @FXML
    private TableView<Produit> tableView;

    @FXML
    private TableColumn<Produit, String> codeCol;

    @FXML
    private TableColumn<Produit, String> nomCol;

    @FXML
    private TableColumn<Produit, Double> prixCol;

    @FXML
    private TableColumn<Produit, Double> qteCol;

    @FXML
    private TableColumn<Produit, String> dateCol;

    @FXML
    private TableColumn<Produit, String> etatCol;

    @FXML
    private TableColumn<Produit, String> categorieCol;
    
    static ObservableList<Produit> listProduits = FXCollections.observableArrayList();
    
    FilteredList<Produit> filteredData;
    
    SortedList<Produit> sortedData ;
    
    int selectedSearchColumn = 1;/* 1 => Code
                                    2 => Nom
                                    3 => prixVente
                                    4 => quantite
                                    5 => date
                                    6 => etat
                                    7 => categorie
                                 */
    
    ArrayList<String> listColumnNames = new ArrayList<>();
    
    public void initializeTableView(){
        //id Column
        codeCol.setCellValueFactory(new PropertyValueFactory<>("code"));
        
//name Column
        nomCol.setText(titres.getString("NAME"));
        nomCol.setCellValueFactory(new PropertyValueFactory<>("nomPro"));
        //address Column
        
       prixCol.setText(titres.getString("PRICE_VENTE"));
        prixCol.setCellValueFactory(new PropertyValueFactory<>("prixVente"));
        
        //tel Column
        
        qteCol.setText(titres.getString("QUANTITY"));
        qteCol.setCellValueFactory(new PropertyValueFactory<>("qte"));
       
        //email column
        
        dateCol.setText(titres.getString("DATE_PEREMPTION"));
        dateCol.setCellValueFactory(new PropertyValueFactory<>("date"));

        
        etatCol.setText(titres.getString("FRACTION"));
        etatCol.setCellValueFactory(new PropertyValueFactory<>("etat"));    
 
        categorieCol.setCellValueFactory(new PropertyValueFactory<>("categorie"));
        categorieCol.setText(titres.getString("CATEGORY"));
        
        TableView.TableViewSelectionModel model = tableView.getSelectionModel();
        model.setSelectionMode(SelectionMode.MULTIPLE);//On peut selectionner plusieurs entrees a la fois
        model.selectedItemProperty().addListener((ObservableValue obs, Object oldSelection, Object newSelection) -> {
            modify.setDisable((newSelection == null));
            delete.setDisable((newSelection == null));
        });
         
        tableView.setPlaceholder(new Label(titres.getString("Produits_PlaceHolder")));
        
        loadSuppliers();
    }
    
     /** Lire la BD et charger tous les fournisseurs dans la tableView i.e. Les ajouter dans l'ArrayList listSuppliers*/
    
    ObservableList<Produit> elements = FXCollections.observableArrayList();
    ProductImpl productImpl = new ProductImpl();
    
    public void loadSuppliers(){
        listProduits.clear();
        elements.clear();
        //Load Suppliers from Database
        listProduits.addAll(productImpl.readProduct());
        tableView.setItems(elements);
        
        //Wrap the ObservableList in a FilteredList (initially display all data).
        filteredData = new FilteredList<>(listProduits,p->true);
        
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
                        if(person.getNomPro().toLowerCase().contains(lowerCaseFilter)) return true;
                        break;
                    case 2:
                        if(String.valueOf(person.getPrixVente()).toLowerCase().contains(lowerCaseFilter)) return true;
                        break;
                    case 3:
                        if(String.valueOf(person.getQte()).toLowerCase().contains(lowerCaseFilter)) return true;
                        break;
                    case 4:
                        if(person.getDatePeremtion().toString().toLowerCase().contains(lowerCaseFilter)) return true;
                        break;
                    case 5:
                        if(person.getEtat().toLowerCase().contains(lowerCaseFilter)) return true;
                        break;
                    case 6:
                        if(person.getCategorie().toLowerCase().contains(lowerCaseFilter)) return true;
                        break;
                    default:
                        break;
                }
                return false;
            });
           initPagination();
        });
        
        // Wrap the FilteredList in a SortedList. 
        sortedData = new SortedList<>(filteredData);
        
        //Bind the SortedList comparator to the TableView comparator.
        sortedData.comparatorProperty().bind(tableView.comparatorProperty());
        
        initPagination();
    }
    
    void launchEditProduit(int p){
        try {
             Stage tmp = new Stage();
             //tmp.initStyle(StageStyle.UNDECORATED);
             tmp.initModality(Modality.APPLICATION_MODAL);
             FXMLLoader loader=new FXMLLoader(getClass().getResource("/magasinier/produit/editProduits.fxml"));
             
             Pane pane = (StackPane)loader.load();
             EditProduitsController con = loader.getController();
             con.setItems(listProduits);
             if(p!=-1){
                 con.loadData(p);
             }else{
                 con.setType(true);
             }
             Scene newScene = new Scene(pane);
             tmp.setScene(newScene);
             tmp.setResizable(false);             
             tmp.showAndWait();
             ControllerUtils.launchTransition(pane);
         } catch (IOException ex) {
             Logger.getLogger(ProduitsController.class.getName()).log(Level.SEVERE, null, ex);
         }
    }
    
    @FXML 
    public void tableViewClicked(MouseEvent e){
        if(e.isPopupTrigger()) showPopup(e);
        
        if(e.getClickCount()>2){
            if(!tableView.getSelectionModel().getSelectedIndices().isEmpty())
                launchEditProduit(tableView.getSelectionModel().getSelectedIndices().get(0));
        }
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
        searchColumn.getItems().addAll("Code",  titres.getString("NAME"),
                                                titres.getString("PRICE_VENTE"),
                                                titres.getString("QUANTITY"),
                                                titres.getString("DATE_PEREMPTION"),
                                                titres.getString("FRACTION"),
                                                titres.getString("CATEGORY"));
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
/*********************************************************/
    
    @FXML
    void categories() {
        try {
             Stage tmp=(Stage) lien.getScene().getWindow();  
             FXMLLoader loader=new FXMLLoader(getClass().getResource("/magasinier/categorie/categories.fxml"));
             Scene newScene = new Scene(loader.load());
             CategoriesController controller = loader.getController();
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
    void refresh() {
        loadSuppliers();
        pagination.setCurrentPageIndex(0);
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
        
        nomCol.setText(titres.getString("NAME"));
        
        prixCol.setText(titres.getString("PRICE_VENTE"));
        
        qteCol.setText(titres.getString("QUANTITY"));
        
        dateCol.setText(titres.getString("DATE_PEREMPTION"));
        
        etatCol.setText(titres.getString("FRACTION"));
        
        categorieCol.setText(titres.getString("CATEGORY"));        
        
        ((Label)tableView.getPlaceholder()).setText(titres.getString("Produits_PlaceHolder"));
        
        int i = searchColumn.getItems().indexOf(searchColumn.getValue());
        searchColumn.getItems().clear();
        searchColumn.getItems().addAll("Code",  titres.getString("NAME"),
                                                titres.getString("PRICE_VENTE"),
                                                titres.getString("QUANTITY"),
                                                titres.getString("DATE_PEREMPTION"),
                                                titres.getString("FRACTION"),
                                                titres.getString("CATEGORY"));
        searchColumn.setValue(searchColumn.getItems().get(i));        
    }

    /************************************************/

    /**
     * Initializes the controller class.
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        popup.setVisible(false);
        userPopup.setVisible(false);
        titres = Internationalization.initLanguage(langue);        
        ControllerUtils.setProfil( username,nameProfil,idProfil,showProfil);
        langue.valueProperty().addListener((observable, oldValue, newValue) -> {
            loadResource(newValue);
        });
        initSearch();
        initializeTableView();
        modify.setDisable(true);
        delete.setDisable(true);
    }
    
}
