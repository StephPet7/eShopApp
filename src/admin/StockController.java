/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package admin;

import Utils.Internationalization;
import static admin.FacturesController.listFactures;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import dao.EntitiesImpl.GestionStockImpl;
import entity.Gestionstock;
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
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;


/**
 * FXML Controller class
 *
 * @author Stephen
 */
public class StockController implements Initializable {

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
    private ImageView exit;
    @FXML
    private JFXButton dashboard;
    @FXML
    private JFXButton facture;
    @FXML
    private JFXButton stock;
    @FXML
    private JFXButton refresh;
    @FXML
    private TextField searchTextField;
    @FXML
    private JFXButton searchButton;
    @FXML
    private TableView<Gestionstock> tableView;
    @FXML
    private TableColumn<Gestionstock, String> codeStockCol;
    @FXML
    private TableColumn<Gestionstock, String> qteCol;
    @FXML
    private TableColumn<Gestionstock, String> dateCol;
    @FXML
    private TableColumn<Gestionstock, String> operationCol;
    @FXML
    private TableColumn<Gestionstock, String> gestionnaireCol;
    @FXML
    private TableColumn<Gestionstock, String> produitCol;
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
    private JFXButton gestionnaire;
    @FXML
    private JFXButton apercu;
    @FXML
    private Pane userPopup;
    @FXML
    private Label lien2;
    @FXML
    private Pagination pagination;
    
    
    //************************************
    
    ResourceBundle titres;
    ObservableList<Gestionstock> elements = FXCollections.observableArrayList();
    static ObservableList<Gestionstock> listStock = FXCollections.observableArrayList();
    FilteredList<Gestionstock> filteredData;
    SortedList<Gestionstock> sortedData;
    GestionStockImpl impl = new GestionStockImpl();
    

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
    private void showUserPopup(MouseEvent event) {
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
    private void loadFactures() {
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
    
    
    public void initializeTableView(){
        //code Column
         codeStockCol.setCellValueFactory(new PropertyValueFactory<>("idStock"));
        
        //quantite Column
        qteCol.setCellValueFactory(new PropertyValueFactory<>("qte"));
        
        //type Column
        dateCol.setCellValueFactory(new PropertyValueFactory<>("dateStock"));
        
        //email Column
        operationCol.setCellValueFactory(new PropertyValueFactory<>("operation"));
        
        //contact Column
        gestionnaireCol.setCellValueFactory(new PropertyValueFactory<>("idGest"));
       
        //adresse column
        produitCol.setCellValueFactory(new PropertyValueFactory<>("codePro"));

        
        /*TableView.TableViewSelectionModel model = tableView.getSelectionModel();
        model.setSelectionMode(SelectionMode.MULTIPLE);//On peut selectionner plusieurs entrees a la fois
        model.selectedItemProperty().addListener((ObservableValue obs, Object oldSelection, Object newSelection) -> {
            modify.setDisable((newSelection == null));
            delete.setDisable((newSelection == null));
        });*/
         
        tableView.setPlaceholder(new Label(titres.getString("STOCK_PLACE_HOLDER")));
        
        loadStockData();
    }
    
    
    public List<Gestionstock> readFromBD() {
        // Lire les employes de la BD et les mettre dans l'observableList "element"
        return impl.readFromBD();
    }

    @FXML
    private void loadStockData() {
        // Lire les éléments dans la BD et remplire dans l'observableList elements
        listStock = FXCollections.observableArrayList(readFromBD());
        
        //----------------------- Alternative -----------------------------------
       /* listStock.add(new Gestionstock("909001", 15, "06-05-2020", "Ajout", "55501", "888802"));
        listStock.add(new Gestionstock("909015", 03, "16-01-2020", "Ajout", "55556", "888856"));
        listStock.add(new Gestionstock("909023", 06, "18-03-2020", "Retrait", "55524", "888889"));
        
        listStock.add(new Gestionstock("909028", 16, "26-04-2020", "Ajout", "55513", "888842"));
        listStock.add(new Gestionstock("909032", 12, "07-06-2020", "Retrait", "55523", "888863"));
        listStock.add(new Gestionstock("909055", 25, "14-02-2020", "Retrait", "55536", "888822"));
        
        listStock.add(new Gestionstock("909013", 04, "05-02-2020", "Ajout", "55519", "888856"));
        listStock.add(new Gestionstock("909026", 11, "30-03-2020", "Retrait", "55508", "888846"));
        listStock.add(new Gestionstock("909054", 36, "09-05-2020", "Ajout", "55545", "888845"));
        
        listStock.add(new Gestionstock("909008", 24, "01-05-2020", "Ajout", "55532", "888864"));
        listStock.add(new Gestionstock("909033", 27, "21-01-2020", "Retrait", "55514", "888807"));
        listStock.add(new Gestionstock("909005", 10, "15-04-2020", "Retrait", "55520", "888825"));
        /*fournisseursUtils.list().forEach((e)->{
            listSuppliers.add(e);
        });*/
        //Wrap the ObservableList in a FilteredList (initially display all data).
        filteredData = new FilteredList<>(listStock,p->true);
        
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
                        if(person.getIdStock().toLowerCase().contains(lowerCaseFilter)) return true;
                        break;
                    case 1:
                        if(person.getQte().toLowerCase().contains(lowerCaseFilter)) return true;
                        break;
                    case 2:
                        if(person.getDateStock().toLowerCase().contains(lowerCaseFilter)) return true;
                        break;
                    case 3:
                        if(person.getOperation().toLowerCase().contains(lowerCaseFilter)) return true;
                        break;
                    case 4:
                        if(person.getIdGest().toLowerCase().contains(lowerCaseFilter)) return true;
                        break;
                    case 5:
                        if(person.getCodePro().toLowerCase().contains(lowerCaseFilter)) return true;
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
        searchColumn.getItems().addAll("CodeStock",  "Quanttité",
                                                "Date",
                                                "Opération",
                                                titres.getString("MANAGER"),
                                                titres.getString("PRODUITS"));
        searchColumn.setValue("CodeStock");
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
    
    
    //***********************************************
    
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
    private void apercu(ActionEvent event) {
    }
}
