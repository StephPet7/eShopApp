/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package caissier.controller;

import java.math.BigDecimal;
import java.net.URL;
import java.sql.Date;
import java.util.ResourceBundle;

import entity.Facture;
import dao.EntitiesImpl.FactureImpl;
import dao.IBill;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXDatePicker;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.util.StringConverter;
import caissier.model.I18N;
import Utils.ControllerUtils;

/**
 *
 * @author Shaquillo
 */
public class DailyBillController extends CommonController implements Initializable {

    @FXML
    private BorderPane borderPane;

    @FXML
    private Label labelUsername;

    @FXML
    private JFXButton buttonDisconnect;

    @FXML
    private JFXButton buttonFactorisation;

    @FXML
    private JFXButton buttonCategory;

    @FXML
    private JFXButton buttonProduct;

    @FXML
    private JFXButton buttonCllient;

    @FXML
    private TableView<Facture> table;

    @FXML
    private TableColumn<Facture, String> columnClientName;

    @FXML
    private TableColumn<Facture, String> columnPaymentCode;

    @FXML
    private TableColumn<Facture, BigDecimal> columnDiscount;

    @FXML
    private TableColumn<Facture, BigDecimal> columnTotalPrice;

    @FXML
    private JFXDatePicker facturationDate;

    @FXML
    private JFXComboBox<String> comboBoxSearchBy;
    
    @FXML
    private TextField textfieldSearch;

    @FXML
    private Button buttonSearch;

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
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {

        // setting date format
        setDateFormatandDefaultDate();

        initSearchByText();
        
        manageLanguage(rb);
        
        manageTable(rb);
        
        // changing search image button
        
        putEndSearchIm(textfieldSearch, buttonSearch);
    }    
    
    @FXML
    void loadCategoriesPage(ActionEvent event) throws IOException {
        loadPage("/caissier/view/pages/categories.fxml", I18N.getLocale());
    }

    @FXML
    void loadClientPage(ActionEvent event) throws IOException {
        loadPage("/caissier/view/pages/clients.fxml", I18N.getLocale());
    }

    @FXML
    void loadFactorisationPage(ActionEvent event) throws IOException {
        
    }

    @FXML
    void loadProductPage(ActionEvent event) throws IOException {
        loadPage("/caissier/view/pages/products.fxml", I18N.getLocale());
    }

    @FXML
    void clearSearch(ActionEvent event) {
        putSearchIm(textfieldSearch, buttonSearch);
    }
    
    @FXML
    void logout(ActionEvent event) {

    }

    private void setDateFormatandDefaultDate(){

        //setting date format
        facturationDate.setConverter(new StringConverter<LocalDate>(){
            private DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

            @Override
            public String toString(LocalDate localDate){
                if(localDate == null)
                    return "";
                return dateTimeFormatter.format(localDate);
            }

            @Override
            public LocalDate fromString(String dateString){
                if(dateString == null || dateString.trim().isEmpty()){
                    return null;
                }
                return LocalDate.parse(dateString, dateTimeFormatter);
            }
        });
        facturationDate.setValue(LocalDate.now());
    }

    private void manageTable(ResourceBundle rb){
        // managing table

        columnClientName.setCellValueFactory(new PropertyValueFactory<>("clientName"));
        columnPaymentCode.setCellValueFactory(new PropertyValueFactory<>("codePaiement"));
        columnDiscount.setCellValueFactory(new PropertyValueFactory<>("remise"));
        columnTotalPrice.setCellValueFactory(new PropertyValueFactory<>("montant"));

        TableView.TableViewSelectionModel model = table.getSelectionModel();
        model.setSelectionMode(SelectionMode.MULTIPLE);

        table.setPlaceholder(new Label(rb.getString("tablePlaceholder.dailyBill")));

        loadFactures();

        initPagination();
    }

    private void manageLanguage(ResourceBundle rb){
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
                loadPage("/caissier/view/pages/dailyBill.fxml", I18N.getLocale());
            } catch (IOException ex) {
                Logger.getLogger(DailyBillController.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
    }

    private void initSearchByText(){
        comboBoxSearchBy.getItems().addAll(I18N.get("column.clientName"), I18N.get("column.paymentCode"), I18N.get("column.discount"), I18N.get("column.totalPrice"));
        comboBoxSearchBy.getSelectionModel().selectFirst();
    }

    //load products from database
    private ObservableList<Facture> listBill = FXCollections.observableArrayList();
    private ObservableList<Facture> tempListBill = FXCollections.observableArrayList();
    private IBill billFunc;
    private FilteredList<Facture> billFilteredList;
    private SortedList<Facture> billSortedList;

    public void loadFactures(){
        table.setItems(null);
        billFunc = new FactureImpl();
        listBill.clear();
        listBill.addAll(billFunc.readBillPerDate(Date.valueOf(facturationDate.getValue())));
        tempListBill.addAll(listBill);

        billFilteredList = new FilteredList<>(tempListBill, p->true);

        textfieldSearch.textProperty().addListener((ObservableValue<? extends String> obs, String oldValue, String newValue) -> {
            billFilteredList.setPredicate(bill -> {
                if(newValue == null || newValue.isEmpty()) return true;

                String lowerCaseFilter = newValue.toLowerCase();
                int i = comboBoxSearchBy.getItems().indexOf(comboBoxSearchBy.getValue());

                switch(i){
                    case 0:
                        if(bill.getClientName().toLowerCase().contains(lowerCaseFilter)) return true;
                        break;
                    case 1:
                        if(bill.getCodePaiement().toLowerCase().contains(lowerCaseFilter)) return true;
                        break;
                    case 2:
                        if(bill.getRemise().toString().toLowerCase().contains(lowerCaseFilter)) return true;
                        break;
                    case 3:
                        if(bill.getMontant().toString().contains(lowerCaseFilter)) return true;
                        break;
                    default:
                        break;
                }
                return false;
            });
        });

        billSortedList = new SortedList<>(billFilteredList);

        billSortedList.comparatorProperty().bind(table.comparatorProperty());

        table.setItems(billSortedList);

    }

    private void loadBillforgivenDate(){
        facturationDate.valueProperty().addListener((obs, oldValue, newValue)->{
            if(newValue != null){
                listBill.clear();
                listBill.addAll(billFunc.readBillPerDate(Date.valueOf(newValue)));
            }
        });
    }

    private void internationalisation(){

    }

    private void initPagination(){
        int n = listBill.size();
        int tmp = (int) Math.ceil(n <=50 ? 1: n/50.0);
        pagination.setPageCount(tmp);
        pagination.setCurrentPageIndex(0);
        pagination.setMaxPageIndicatorCount(tmp);

        int f = 50;
        if(f > listBill.size()) f = listBill.size();

        setProduct(0,f-1);

        pagination.setPageFactory(param -> {

            int fin = (50*param+50);
            if(fin > listBill.size()) fin = listBill.size();

            setProduct(50*param, fin-1);
            return this.table;
        });
    }

    private void setProduct(int i, int j){
        tempListBill.clear();
        for(int k = i; k <= j; k++){
            tempListBill.add(listBill.get(k));
        }
    }
}
