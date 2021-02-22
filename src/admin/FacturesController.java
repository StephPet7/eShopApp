/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package admin;

import Utils.Internationalization;
import static admin.GestionnairesController.listGestionnaires;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import java.io.IOException;
import java.net.URL;
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
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Stephen
 */
public class FacturesController implements Initializable {

    @FXML
    private Label lien;
    @FXML
    private Label lien1;
    @FXML
    private Label lien2;
    @FXML
    private Label slogan;
    @FXML
    private Label username;
    @FXML
    private JFXComboBox<String> langue;
    @FXML
    private JFXButton dashboard;
    @FXML
    private JFXButton employes;
    @FXML
    private JFXButton facture;
    @FXML
    private JFXButton stock;
    @FXML
    private JFXButton refresh;
    @FXML
    private JFXButton print;
    @FXML
    private TextField searchTextField;
    @FXML
    private JFXButton searchButton;
    @FXML
    private TableView<Facture> tableView;
    @FXML
    private TableColumn<Facture, String> codeFactureCol;
    @FXML
    private TableColumn<Facture, String> dateCol;
    @FXML
    private TableColumn<Facture, String> codePaiementCol;
    @FXML
    private TableColumn<Facture, Double> remiseCol;
    @FXML
    private TableColumn<Facture, Double> montantCol;
    @FXML
    private TableColumn<Facture, String> modePaiementCol;
    @FXML
    private TableColumn<Facture, String> caissiereCol;
    @FXML
    private TableColumn<Facture, String> clientCol;
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
    private JFXButton stopSearchButton;
    @FXML
    private Pane userPopup;
    @FXML
    private Pagination pagination;
    
    //*******************************************
    
    ResourceBundle titres;
    ObservableList<Facture> elements = FXCollections.observableArrayList();
    static ObservableList<Facture> listFactures = FXCollections.observableArrayList();
    FilteredList<Facture> filteredData;
    SortedList<Facture> sortedData;
    
    

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
        
        userPopup.setVisible(false);
        //flecheUserPopup.setVisible(false);
        
        initSearch();
        initializeTableView();
        modify.setDisable(true);
        delete.setDisable(true);
    }    

    @FXML
    private void showUserPopup() {
        if (userPopup.isVisible()) {
            userPopup.setVisible(false);
            //flecheUserPopup.setVisible(false);
        }
        else {
            userPopup.setVisible(true);
            //flecheUserPopup.setVisible(true);
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
    private void dashboard() {
        loadDashboard();
    }

    @FXML
    private void loadGestionnaires(ActionEvent event) {
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
    private void loadStock() {
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
    
    
    public void initializeTableView(){
        //matricule Column
         codeFactureCol.setCellValueFactory(new PropertyValueFactory<>("codeFacture"));
        
        //name Column
        dateCol.setCellValueFactory(new PropertyValueFactory<>("date"));
        
        //type Column
        codePaiementCol.setCellValueFactory(new PropertyValueFactory<>("codePaiement"));
        
        //email Column
        remiseCol.setCellValueFactory(new PropertyValueFactory<>("remise"));
        
        //contact Column
        montantCol.setCellValueFactory(new PropertyValueFactory<>("montant"));
       
        //adresse column
        modePaiementCol.setCellValueFactory(new PropertyValueFactory<>("modePaiement"));

        //login column
        caissiereCol.setCellValueFactory(new PropertyValueFactory<>("idCaissiere"));    
 
        //password Column
        clientCol.setCellValueFactory(new PropertyValueFactory<>("idClient"));
        
        TableView.TableViewSelectionModel model = tableView.getSelectionModel();
        /*model.setSelectionMode(SelectionMode.MULTIPLE);//On peut selectionner plusieurs entrees a la fois
        model.selectedItemProperty().addListener((ObservableValue obs, Object oldSelection, Object newSelection) -> {
            modify.setDisable((newSelection == null));
            delete.setDisable((newSelection == null));
        });*/
         
        tableView.setPlaceholder(new Label(titres.getString("FACTURE_PLACE_HOLDER")));
        
        refresh();
    }

    
    /****** READ_MONTHE **********/
    @FXML
    private void refresh() {
        // Lire les éléments dans la BD et remplire dans l'observableList elements
        
        
        //----------------------- Alternative -----------------------------------
        listFactures.add(new Facture("99901", "01-05-2020","000-01", 10, 5050.0, "Cash", "555024", "22201"));
        listFactures.add(new Facture("99902", "01-05-2020","000-05", 5, 3250.0, "Virement Banquaire", "555024", "222056"));
        listFactures.add(new Facture("99903", "03-05-2020","000-016", 7, 20750.0, "Cash", "555089", "222032"));
        
        listFactures.add(new Facture("99905", "01-03-2020","000-0123", 10, 2450.0, "Virement Banquaire", "555023", "2220663"));
        listFactures.add(new Facture("99904", "20-04-2020","000-89", 13, 13875.0, "Cash", "555024", "222033"));
        listFactures.add(new Facture("99909", "13-05-2020","000-26", 06, 3265.0, "Virement Banquaire", "555052", "222066"));
        
        listFactures.add(new Facture("99912", "01-04-2020","000-45", 6.3, 3450.0, "Cash", "555046", "2220653"));
        listFactures.add(new Facture("99914", "26-05-2020","000-016", 9.2, 63875.0, "Virement Banquaire", "555024", "222048"));
        listFactures.add(new Facture("99925", "01-03-2020","000-29", 6.4, 8665.0, "Cash", "555052", "222066"));
        
        listFactures.add(new Facture("99913", "10-04-2020","000-30", 2.1, 9450.0, "Cash", "555010", "2220622"));
        listFactures.add(new Facture("99915", "26-03-2020","000-046", 3.5, 563875.0, "Virement Banquaire", "555009", "222037"));
        listFactures.add(new Facture("99917", "17-03-2020","000-078", 4, 12265.0, "Virement Banquaire", "555013", "222043"));
        /*fournisseursUtils.list().forEach((e)->{
            listSuppliers.add(e);
        });*/
        //Wrap the ObservableList in a FilteredList (initially display all data).
        filteredData = new FilteredList<>(listFactures,p->true);
        
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
                        if(person.getCodeFacture().toLowerCase().contains(lowerCaseFilter)) return true;
                        break;
                    case 1:
                        if(person.getDate().toLowerCase().contains(lowerCaseFilter)) return true;
                        break;
                    case 2:
                        if(person.getCodePaiement().toLowerCase().contains(lowerCaseFilter)) return true;
                        break;
                    case 3:
                        if(String.valueOf(person.getRemise()).toLowerCase().contains(lowerCaseFilter)) return true;
                        break;
                    case 4:
                        if(String.valueOf(person.getMontant()).toLowerCase().contains(lowerCaseFilter)) return true;
                        break;
                    case 5:
                        if(person.getModePaiement().toLowerCase().contains(lowerCaseFilter)) return true;
                        break;
                    case 6:
                        if(person.getIdCaissiere().toLowerCase().contains(lowerCaseFilter)) return true;
                        break;
                    case 7:
                        if(person.getIdClient().toLowerCase().contains(lowerCaseFilter)) return true;
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

    void initSearch(){
        searchColumn.getItems().addAll("CodeFacture",  "Date",
                                                "CodePaiement",
                                                "Remise",
                                                "Montant",
                                                "ModePaiement",
                                                "Caissier", "Client");
        searchColumn.setValue("CodeFacture");
        initSearchPane();
        searchTextField.textProperty().addListener((observable, oldValue, newValue) -> {
            stopSearchButton.setVisible(!newValue.isEmpty());
            searchButton.setVisible(newValue.isEmpty());
        });
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
    private void print(ActionEvent event) {
    }

    @FXML
    private void tableViewClicked(MouseEvent event) {
    }

    @FXML
    private void initSearchPane() {
        searchTextField.clear();
        searchButton.setVisible(true);
        stopSearchButton.setVisible(false);
    }

    @FXML
    private void add(ActionEvent event) {
    }

    @FXML
    private void modify(ActionEvent event) {
    }

    @FXML
    private void delete(ActionEvent event) {
    }

    @FXML
    private void hideUserPopup(MouseEvent event) {
    }
    
    
    //********************************************
    
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
}
