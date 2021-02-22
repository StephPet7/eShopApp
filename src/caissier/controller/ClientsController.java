/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package caissier.controller;

import entity.Client;
import dao.EntitiesImpl.ClientImpl;
import dao.IClient;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import caissier.model.I18N;
import Utils.ControllerUtils;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * FXML Controller class
 *
 * @author Shaquillo
 */
public class ClientsController extends CommonController implements Initializable {
    
    @FXML
    private Label labelUsername;

    @FXML
    private JFXButton buttonDisconnect;

    @FXML
    private JFXButton buttonFactorisation;

    @FXML
    private JFXButton buttonCategories;

    @FXML
    private JFXButton buttonProduct;

    @FXML
    private TableView<Client> table;
    
    @FXML
    private TableColumn<Client, Integer> columnCode;

    @FXML
    private TableColumn<Client, String> columnName;

    @FXML
    private TableColumn<Client, String> columnTel;

    @FXML
    private TableColumn<Client, String> columnAdresse;

    @FXML
    private TableColumn<Client, Integer> columnBonus;

    @FXML
    private JFXButton buttonEdit;

    @FXML
    private TextField textfieldSearch;

    @FXML
    private Button buttonSearch;

    @FXML
    private JFXButton buttonAdd;

    @FXML
    private JFXButton buttonDelete;
    
    @FXML
    private JFXComboBox<String> comboBoxSearchBy;

    @FXML
    private JFXComboBox<String> comboBoxLanguage;

    @FXML
    private Pagination pagination;

    //User Popup

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

    //end user popup

    // Tableview Popup
    @FXML
    private Pane popup;

    @FXML
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
    public void modifyPopup(ActionEvent event) throws IOException {
        editClient(event);
        hidePopup(null);
    }

    @FXML
    public void deletePopup(ActionEvent event){
        deleteClient(event);
        hidePopup(null);
    }

    //End table popup

    @FXML
    public void tableViewClicked(MouseEvent e){
        if(e.isPopupTrigger()) showPopup(e);

        /*if(e.getClickCount()>2){
            if(!tableView.getSelectionModel().getSelectedIndices().isEmpty())
                launchEditProduit(tableView.getSelectionModel().getSelectedItems().get(0));
        }*/
    }
    
    public static boolean editClientVerifier = false;
    public static ArrayList<String> valuesToEdit = new ArrayList<String>();
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        manageLanguage(rb);

        initSearchByfield();
        
        manageTables(rb);
        
        // changing search image button
        
        putEndSearchIm(textfieldSearch, buttonSearch);
    }    
    
    @FXML
    void AddClient(ActionEvent event) throws IOException {
        loadPage(-1);
    }

    @FXML
    void deleteClient(ActionEvent event) {
        Client c = listClients.get(table.getSelectionModel().getSelectedIndex());
        clientFunc = new ClientImpl();
        clientFunc.deleteClient(c.getIdClient());
        listClients.clear();
        listClients.addAll(clientFunc.readClient());
    }

    @FXML
    void editClient(ActionEvent event) throws IOException {
        editClientVerifier = true;
        loadPage(table.getSelectionModel().getSelectedIndex());
    }


    @FXML
    void loadCategoriesPage(ActionEvent event) throws IOException {
        loadPage("/caissier/view/pages/categories.fxml", I18N.getLocale());
    }

    @FXML
    void loadFactorisationPage(ActionEvent event) throws IOException {
        loadPage("/caissier/view/pages/dailyBill.fxml", I18N.getLocale());
    }

    @FXML
    void loadProductPage(ActionEvent event) throws IOException {
        loadPage("/caissier/view/pages/products.fxml", I18N.getLocale());
    }

    @FXML
    void logout(ActionEvent event) {

    }

    @FXML
    void clearSearch(ActionEvent event) {
        putSearchIm(textfieldSearch, buttonSearch);
    }
    
    public void loadPage(int i) throws IOException{
        Stage addClientStage = new Stage();
        FXMLLoader loader =new FXMLLoader(getClass().getResource("/caissier/view/pages/addClient.fxml"), ResourceBundle.getBundle("caissier/language/Bundle",I18N.getLocale()));

        Parent root = loader.load();

        AddClientController addClientController = loader.getController();

        Scene addClientScene = new Scene(root);
        
        addClientStage.setResizable(false);
        
        addClientStage.setScene(addClientScene);
        if(i == -1){
            addClientStage.setTitle("Add Client");
        } else {
            addClientController.setClientList(listClients);
            addClientController.loadFields(i);
            addClientStage.setTitle("Modify Client");
        }

        
        addClientStage.initModality(Modality.APPLICATION_MODAL);
        addClientStage.showAndWait();
    }

    private void manageTables(ResourceBundle rb){
        // managing table

        columnCode.setCellValueFactory(new PropertyValueFactory<>("idClient"));
        columnName.setCellValueFactory(new PropertyValueFactory<>("nom"));
        columnTel.setCellValueFactory(new PropertyValueFactory<>("tel"));
        columnAdresse.setCellValueFactory(new PropertyValueFactory<>("adresse"));
        columnBonus.setCellValueFactory(new PropertyValueFactory<>("bonus"));

        TableView.TableViewSelectionModel model = table.getSelectionModel();
        model.setSelectionMode(SelectionMode.SINGLE);
        model.selectedItemProperty().addListener((ObservableValue observable, Object oldSelection, Object newSelection) -> {
            buttonEdit.setDisable(model.getSelectedItems().isEmpty());
            buttonDelete.setDisable(model.getSelectedItems().isEmpty());
        });

        table.setPlaceholder(new Label(rb.getString("tablePlaceholder.client")));

        loadClients();

        initPagination();
    }

    private void initSearchByfield(){
        comboBoxSearchBy.getItems().addAll(I18N.get("column.code"), I18N.get("column.name"), I18N.get("column.tel"), I18N.get("column.adresse"), I18N.get("column.bonus"));
        comboBoxSearchBy.getSelectionModel().selectFirst();
    }

    private void manageLanguage(ResourceBundle rb){
        // Adding managing change of languagel

        comboBoxLanguage.getItems().addAll(I18N.get("language.english"),rb.getString("language.french" ));
        if(I18N.getLocale() == Locale.ENGLISH){
            comboBoxLanguage.getSelectionModel().selectFirst();
        } else {
            comboBoxLanguage.getSelectionModel().selectLast();
        }
        comboBoxLanguage.valueProperty().addListener((observable, oldValue, newValue) -> {
            if(newValue.equals(I18N.get("language.english"))){
                I18N.setLocale(Locale.ENGLISH);
            } else {
                I18N.setLocale(Locale.FRENCH);
            }
            try {
                loadPage("/caissier/view/pages/clients.fxml", I18N.getLocale());
            } catch (IOException ex) {
                Logger.getLogger(DailyBillController.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
    }

    //load products from database
    public static ObservableList<Client> listClients = FXCollections.observableArrayList();
    public static ObservableList<Client> tempListClients = FXCollections.observableArrayList();
    private IClient clientFunc;
    private FilteredList<Client> clientFilteredList;
    private SortedList<Client> clientSortedList;

    public void loadClients(){
        table.setItems(null);
        clientFunc = new ClientImpl();
        listClients.clear();
        listClients.addAll(clientFunc.readClient());
        tempListClients.addAll(listClients);

        clientFilteredList = new FilteredList<>(tempListClients, p->true);

        textfieldSearch.textProperty().addListener((ObservableValue<? extends String> obs, String oldValue, String newValue) -> {
            clientFilteredList.setPredicate(client -> {
                if(newValue == null || newValue.isEmpty()) {
                    putSearchIm(textfieldSearch, buttonSearch);
                    return true;
                }

                String lowerCaseFilter = newValue.toLowerCase();
                int i = comboBoxSearchBy.getItems().indexOf(comboBoxSearchBy.getValue());

                switch(i){
                    case 0:
                        if(client.getIdClient().toString().contains(lowerCaseFilter)) return true;
                        break;
                    case 1:
                        if(client.getNom().toLowerCase().contains(lowerCaseFilter)) return true;
                        break;
                    case 2:
                        if(client.getTel().toLowerCase().contains(lowerCaseFilter)) return true;
                        break;
                    case 3:
                        if(client.getAdresse().toLowerCase().contains(lowerCaseFilter)) return true;
                        break;
                    case 4:
                        if(client.getBonus().toString().toLowerCase().contains(lowerCaseFilter)) return true;
                        break;
                    default:
                        break;
                }
                return false;
            });
        });

        clientSortedList = new SortedList<>(clientFilteredList);

        clientSortedList.comparatorProperty().bind(table.comparatorProperty());

        table.setItems(clientSortedList);
    }

    private void initPagination(){
        int n = listClients.size();
        int tmp = (int) Math.ceil(n <=50 ? 1: n/50.0);
        pagination.setPageCount(tmp);
        pagination.setCurrentPageIndex(0);
        pagination.setMaxPageIndicatorCount(tmp);

        int f = 50;
        if(f > listClients.size()) f = listClients.size();

        setProduct(0,f-1);

        pagination.setPageFactory(param -> {

            int fin = (50*param+50);
            if(fin > listClients.size()) fin = listClients.size();

            setProduct(50*param, fin-1);
            return this.table;
        });
    }

    private void setProduct(int i, int j){
        tempListClients.clear();
        for(int k = i; k <= j; k++){
            tempListClients.add(listClients.get(k));
        }
    }

}
