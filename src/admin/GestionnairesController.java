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
import dao.EntitiesImpl.GestionnaireImpl;
import entity.Gestionnaire;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
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
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.Pagination;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 * FXML Controller class
 *
 * @author Stephen
 */
public class GestionnairesController implements Initializable {

    @FXML
    private Label lien;
    @FXML
    private Label lien1;
    @FXML
    private Label slogan;
    @FXML
    private Label username;
    @FXML
    private JFXComboBox<String> langue;
    @FXML
    private JFXButton dashboard;
    @FXML
    private JFXButton gestionnaire;
    @FXML
    private JFXButton factures;
    @FXML
    private JFXButton stock;
    @FXML
    private JFXButton refresh;
    @FXML
    private TextField searchTextField;
    @FXML
    private JFXButton searchButton;
    @FXML
    private TableView<Gestionnaire> tableView;
    @FXML
    private TableColumn<Gestionnaire, String> matriculeCol;
    @FXML
    private TableColumn<Gestionnaire, String> nomCol;
    @FXML
    private TableColumn<Gestionnaire, String> typeCol;
    @FXML
    private TableColumn<Gestionnaire, String> emailCol;
    @FXML
    private TableColumn<Gestionnaire, String> contactCol;
    @FXML
    private TableColumn<Gestionnaire, String> adresseCol;
    @FXML
    private TableColumn<Gestionnaire, String> loginCol;
    @FXML
    private TableColumn<Gestionnaire, String> passwordCol;
    @FXML
    private TableColumn<Gestionnaire, String> actifCol;
    @FXML
    private Label searchBy;
    @FXML
    private JFXComboBox<String> searchColumn;
    @FXML
    private JFXButton add;
    @FXML
    private JFXButton modify;
    @FXML
    private JFXButton delete;
    @FXML
    private JFXButton apercu;
    @FXML
    private JFXButton stopSearchButton;
    @FXML
    private Pane popup;
    @FXML
    private Pane userPopup;
    @FXML
    private Label lien2;
    @FXML
    private ImageView flecheUserPopup;
    @FXML
    private Pagination pagination;
    
    
    //*********************************************
    ResourceBundle titres;
    static ObservableList<Gestionnaire> listGestionnaires = FXCollections.observableArrayList();
    SortedList<Gestionnaire> sortedData ;
    ObservableList<Gestionnaire> elements = FXCollections.observableArrayList();
    FilteredList<Gestionnaire> filteredData;
    GestionnaireImpl impl = new GestionnaireImpl();
    
    
    

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
        
        popup.setVisible(false);
        userPopup.setVisible(false);
        flecheUserPopup.setVisible(false);
        
        initSearch();
        initializeTableView();
        modify.setDisable(true);
        delete.setDisable(true);
        apercu.setDisable(true);
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
    }

    @FXML
    private void showUserPopup() {
        if (userPopup.isVisible()) {
            userPopup.setVisible(false);
            flecheUserPopup.setVisible(false);
        }
        else {
            userPopup.setVisible(true);
            flecheUserPopup.setVisible(true);
        }
    }

    @FXML
    public void loadDashboard() {
        try {
             Stage tmp = (Stage)lien.getScene().getWindow();
             FXMLLoader loader=new FXMLLoader(getClass().getResource("/admin/dashboard.fxml"));
             Scene newScene = new Scene(loader.load());
             DashboardController controller = loader.getController();
             controller.loadResource(langue.getValue());
             tmp.hide();
             tmp.setScene(newScene);
             tmp.show();
         } catch (IOException ex) {
             Logger.getLogger(DashboardController.class.getName()).log(Level.SEVERE, null, ex);
         }
    }

    @FXML
    private void logOut() {
        loadDashboard();
    }

    @FXML
    private void dashboard(MouseEvent event) {
        loadDashboard();
    }

    @FXML
    private void loadFactures(ActionEvent event) {
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
             Logger.getLogger(FacturesController.class.getName()).log(Level.SEVERE, null, ex);
         }
    }

    @FXML
    private void loadStock(ActionEvent event) {
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
    
    void launchEditGestionnaire(int p){
        try {
             Stage tmp = new Stage();
             //tmp.initStyle(StageStyle.UNDECORATED);
             tmp.initModality(Modality.APPLICATION_MODAL);
             FXMLLoader loader=new FXMLLoader(getClass().getResource("/admin/editGestionnaire.fxml"));
             
             Pane pane = (StackPane)loader.load();
             EditGestionnaireController con = loader.getController();
             con.setItems(listGestionnaires);
             if(p!=-1){
                 con.loadData(p);
             }else{
                 con.setType(true);
                 con.initMatricule();
             }
             
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
    }

    @FXML
    private void add() {
        launchEditGestionnaire(-1);
        initPagination();
    }

    @FXML
    private void modify() {
        launchEditGestionnaire(listGestionnaires.indexOf((Gestionnaire)tableView.getSelectionModel().getSelectedItem()));
        initPagination();     
    }

    @FXML
    private void delete() {
        ObservableList<Gestionnaire> items;
         items = tableView.getSelectionModel().getSelectedItems();
         if(items == null)
         {
             //message d'erreur "Pas d'elements selectionnes
         }else{
              //**supprimer l'enregistrement selectionne
             //**Retirer a la tableView
             if(impl.deleteFromBD(items)) items.forEach(listGestionnaires::remove);
             initPagination();            
        }
    }
    
    @FXML
    private void refresh() {
        listGestionnaires = FXCollections.observableArrayList(readFromBD());
        initPagination();
    }


    
    @FXML
    private void loadViewGestionnaire() {
        Stage tmp = new Stage();
             
             FXMLLoader loader=new FXMLLoader(getClass().getResource("/admin/viewGestionnaire.fxml"));
             
             try{ 
             Pane pane = (Pane)loader.load();
             ViewGestionnaireController con = loader.getController();
             con.setup(tableView.getItems(), tableView.getSelectionModel().getSelectedIndex(), pagination.getCurrentPageIndex());
             Scene newScene = new Scene(pane);
             newScene.setFill(Color.TRANSPARENT);
             tmp.setScene(newScene);   
             tmp.setResizable(false);             
             
             tmp.initStyle(StageStyle.TRANSPARENT);
             tmp.initModality(Modality.APPLICATION_MODAL);
             tmp.showAndWait();
             } catch (IOException ex) {
             Logger.getLogger(GestionnairesController.class.getName()).log(Level.SEVERE, null, ex);
         }
        initPagination();
    }
    
    @FXML
    private void afficherPopup(ActionEvent event) {
    }

    @FXML
    private void modifyPopup(ActionEvent event) {
    }

    @FXML
    private void deletePopup(ActionEvent event) {
    }

    @FXML
    private void hidePopup(MouseEvent event) {
    }

    @FXML
    private void loadUserInfos(ActionEvent event) {
    }

    @FXML
    private void hideUserPopup(MouseEvent event) {
    }

    
    //**************************************
    
    void initSearch(){
        searchColumn.getItems().addAll(titres.getString("MAT"),  titres.getString("NAME"),
                                                "Type",
                                                "Email",
                                                "Contact",
                                                titres.getString("ADDRESS"),
                                                titres.getString("USERNAME"),
                                                titres.getString("PASS"),
                                                titres.getString("ACTIVE"));
        searchColumn.setValue(titres.getString("MAT"));
        initSearchPane();
        searchTextField.textProperty().addListener((observable, oldValue, newValue) -> {
            stopSearchButton.setVisible(!newValue.isEmpty());
            searchButton.setVisible(newValue.isEmpty());
        });
    }
    
    @FXML
    private void initSearchPane() {
        searchTextField.clear();
        searchButton.setVisible(true);
        stopSearchButton.setVisible(false);
    }
    
    public void initializeTableView(){
        //matricule Column
         matriculeCol.setCellValueFactory(new PropertyValueFactory<>("matricule"));
        
        //name Column
        nomCol.setCellValueFactory(new PropertyValueFactory<>("nom"));
        
        //type Column
        typeCol.setCellValueFactory(new PropertyValueFactory<>("type"));
        
        //email Column
        emailCol.setCellValueFactory(new PropertyValueFactory<>("email"));
        
        //contact Column
        contactCol.setCellValueFactory(new PropertyValueFactory<>("contact"));
       
        //adresse column
        adresseCol.setCellValueFactory(new PropertyValueFactory<>("adresse"));

        //login column
        loginCol.setCellValueFactory(new PropertyValueFactory<>("login"));    
 
        //password Column
        passwordCol.setCellValueFactory(new PropertyValueFactory<>("password"));
        
        //actif Column
        actifCol.setCellValueFactory(new PropertyValueFactory<>("printActif"));
        
        TableView.TableViewSelectionModel model = tableView.getSelectionModel();
        model.setSelectionMode(SelectionMode.MULTIPLE);//On peut selectionner plusieurs entrees a la fois
        model.selectedItemProperty().addListener((ObservableValue obs, Object oldSelection, Object newSelection) -> {
            modify.setDisable((newSelection == null));
            delete.setDisable((newSelection == null));
            apercu.setDisable((newSelection == null));
        });
         
        tableView.setPlaceholder(new Label(titres.getString("EMPLOYE_PLACE_HOLDER")));
        
        loadGestionnaireData();
    }
    
    public List<Gestionnaire> readFromBD() {
        // Lire les employes de la BD et les mettre dans l'observableList "element"
        return impl.readFromBD();
    }
 
    
    public void loadGestionnaireData(){        
        tableView.setItems(elements);
        listGestionnaires = FXCollections.observableArrayList(readFromBD());
/* 4 ******************** Lire les employes de la B.D.*/
        /*listGestionnaires.add(new Gestionnaire(555000,"NDEMA Isaac","Magasinier","bekolleisaac@gmail.com","694256354","Efoulan Club-France","IsaacNdema", true));
        listGestionnaires.add(new Gestionnaire("555-07","CHIMI Oreal","Admin","shaguilleyoukap@gmail.com","692567498","Damas","OrealChimi"));
        listGestionnaires.add(new Gestionnaire("555-24","NTANTAME Stephen","Caissier","stephenpetieu@gmail.com","695343499","Tam-Tam Garderie d'en haut","StephenPetieu"));
        
        listGestionnaires.add(new Gestionnaire("555-01","NDEMA Isaac","Magasinier","bekolleisaac@gmail.com","694256354","Efoulan Club-France","IsaacNdema"));
        listGestionnaires.add(new Gestionnaire("555-07","CHIMI Oreal","Admin","shaguilleyoukap@gmail.com","692567498","Damas","OrealChimi"));
        listGestionnaires.add(new Gestionnaire("555-24","NTANTAME Stephen","Caissier","stephenpetieu@gmail.com","695343499","Tam-Tam Garderie d'en haut","StephenPetieu"));
        
        listGestionnaires.add(new Gestionnaire("555-01","NDEMA Isaac","Magasinier","bekolleisaac@gmail.com","694256354","Efoulan Club-France","IsaacNdema"));
        listGestionnaires.add(new Gestionnaire("555-07","CHIMI Oreal","Admin","shaguilleyoukap@gmail.com","692567498","Damas","OrealChimi"));
        listGestionnaires.add(new Gestionnaire("555-24","NTANTAME Stephen","Caissier","stephenpetieu@gmail.com","695343499","Tam-Tam Garderie d'en haut","StephenPetieu"));*/
        /*fournisseursUtils.list().forEach((e)->{
            listSuppliers.add(e);
        });*/
        
        //Wrap the ObservableList in a FilteredList (initially display all data).
        filteredData = new FilteredList<>(listGestionnaires,p->true);
        
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
                        if(person.getMatricule().toLowerCase().contains(lowerCaseFilter)) return true;
                        break;
                    case 1:
                        if(person.getNom().toLowerCase().contains(lowerCaseFilter)) return true;
                        break;
                    case 2:
                        if(person.getType().toLowerCase().contains(lowerCaseFilter)) return true;
                        break;
                    case 3:
                        if(person.getEmail().toLowerCase().contains(lowerCaseFilter)) return true;
                        break;
                    case 4:
                        if(person.getContact().toLowerCase().contains(lowerCaseFilter)) return true;
                        break;
                    case 5:
                        if(person.getAdresse().toLowerCase().contains(lowerCaseFilter)) return true;
                        break;
                    case 6:
                        if(person.getLogin().toLowerCase().contains(lowerCaseFilter)) return true;
                        break;
                    case 7:
                        if(person.getPassword().toLowerCase().contains(lowerCaseFilter)) return true;
                        break;
                    case 8:
                        if(person.getPrintActif().toLowerCase().contains(lowerCaseFilter)) return true;
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
        
        //Add sorted (and filtered) data to the table.
        initPagination();
        
        /*//Sauvegarde le plus grand Id pour les ajouts futurs
        maxId = listSuppliers.get(listSuppliers.size()-1).getId();*/
    }
    
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
        pagination.setCurrentPageIndex(pagination.getCurrentPageIndex());
        pagination.setMaxPageIndicatorCount(tmp);
        tableView.getItems().clear();

        int f = 5;
        if(f >= sortedData.size()) { System.out.println("f>=taille");f = sortedData.size();}

        tableView.getItems().addAll(sortedData.subList(0, f));
        pagination.setPageFactory((param) -> {
        tableView.getItems().clear();
        int fin = 5*param+5;
        if(fin >= sortedData.size()) fin = sortedData.size();

        if(sortedData.size()>5*param) tableView.getItems().addAll(sortedData.subList(5*param, fin));

        return this.tableView;
        });            
   }

    @FXML
    private void tableViewClicked(MouseEvent event) {
    }

}
    
