/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package caissier.controller;

import entity.Produit;
import dao.EntitiesImpl.ProductImpl;
import dao.IProduct;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
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
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import caissier.model.*;
import Utils.ControllerUtils;

import java.io.IOException;
import java.net.URL;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * FXML Controller class
 *
 * @author Shaquillo
 */
public class ProductsController extends CommonController implements Initializable {

    @FXML
    private Label labelUsername;

    @FXML
    private JFXButton buttonDisconnect;

    @FXML
    private JFXButton buttonFactorisation;

    @FXML
    private JFXButton buttonCategories;

    @FXML
    private JFXButton buttonCllient;

    @FXML
    private TextField textfieldSearch;

    @FXML
    private Button buttonSearch;

    @FXML
    private HBox headerHbox;

    @FXML
    private HBox headerHboxCat;
    
    @FXML
    private JFXComboBox<String> comboBoxSearchBy;

    @FXML
    private JFXComboBox<String> comboBoxLanguage;

    @FXML
    private Label username;

    @FXML
    private Label name;

    @FXML
    private Pagination pagination;

    @FXML
    private Label qty;

    @FXML
    private Label category;

    @FXML
    private Label supplier;

    @FXML
    private Label dateIns;

    @FXML
    private VBox productContainer;

    @FXML
    private ScrollPane scrPane;

    @FXML
    private ImageView productImg;


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

        verifyFromCat();

        manageLanguage(rb);
        
        initSearchByText();
        
        loadProducts();

        try {
            initPagination();
        } catch (IOException e) {
            e.printStackTrace();
        }
        if(listProducts.size()>0) setDetails(listProducts.get(0));

        // changing search image button
        
        putEndSearchIm(textfieldSearch, buttonSearch);
    }    
    
    @FXML
    void loadCategoriesPage(ActionEvent event) throws IOException {
        loadPage("/caissier/view/pages/categories.fxml", I18N.getLocale());
        CategoriesController.loadProdFromCat = false;
    }

    @FXML
    void loadClientPage(ActionEvent event) throws IOException {
        loadPage("/caissier/view/pages/clients.fxml", I18N.getLocale());
        CategoriesController.loadProdFromCat = false;
    }

    @FXML
    void loadFactorisationPage(ActionEvent event) throws IOException {
        loadPage("/caissier/view/pages/dailyBill.fxml", I18N.getLocale());
        CategoriesController.loadProdFromCat = false;
    }

    @FXML
    void logout(MouseEvent event) {

        CategoriesController.loadProdFromCat = false;
    }

    @FXML
    void clearSearch(ActionEvent event) {
        putSearchIm(textfieldSearch, buttonSearch);
    }
    
    private void verifyFromCat(){
        if(CategoriesController.loadProdFromCat == true){
            headerHbox.setVisible(false);
            headerHboxCat.setVisible(true);
        }
    }

    private void manageLanguage(ResourceBundle rb){

        // changing and managing language
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
                loadPage("/caissier/view/pages/products.fxml", I18N.getLocale());
            } catch (IOException ex) {
                Logger.getLogger(DailyBillController.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
    }

    private void initSearchByText(){
        // changing and managing of language
        comboBoxSearchBy.getItems().addAll(I18N.get("column.code"), I18N.get("column.name"), I18N.get("column.qty"), I18N.get("column.sellingPrice"), I18N.get("column.category"),I18N.get("column.supplier"),I18N.get("column.dateInsertion"), I18N.get("column.description"));
        comboBoxSearchBy.getSelectionModel().selectFirst();
    }

    //load products from database
    private ObservableList<Produit> listProducts = FXCollections.observableArrayList();
    private IProduct productFunc;
    private FilteredList<Produit> productFilteredList;
    public static SortedList<Produit> productSortedList;
    public static long catCode = -1;

    public void loadProducts(){

        productFunc = new ProductImpl();
        if(CategoriesController.loadProdFromCat){
            listProducts.addAll(productFunc.readProductperCategorie(catCode));
        } else {
            listProducts.addAll(productFunc.readProduct());
        }

        productFilteredList = new FilteredList<>(listProducts, p->true);

        textfieldSearch.textProperty().addListener((ObservableValue<? extends String> obs, String oldValue, String newValue) -> {
            productFilteredList.setPredicate(product -> {
                if(newValue == null || newValue.isEmpty()) return true;

                String lowerCaseFilter = newValue.toLowerCase();
                int i = comboBoxSearchBy.getItems().indexOf(comboBoxSearchBy.getValue());

                switch(i){
                    case 0:
                        if(product.getCodePro().toString().contains(lowerCaseFilter)) return true;
                        break;
                    case 1:
                        if(product.getNomPro().toLowerCase().contains(lowerCaseFilter)) return true;
                        break;
                    case 2:
                        if(product.getQte().toString().toLowerCase().contains(lowerCaseFilter)) return true;
                        break;
                    case 3:
                        if(product.getPrixVente().toString().contains(lowerCaseFilter)) return true;
                        break;
                    case 4:
                        if(product.getCategorieidCat().getNomCat().toLowerCase().contains(lowerCaseFilter)) return true;
                        break;
                    case 5:
                        if(product.getCodeFour().toLowerCase().contains(lowerCaseFilter)) return true;
                        break;
                    case 6:
                        if(product.getDateInsertion().toString().contains(lowerCaseFilter)) return true;
                        break;
                    case 7:
                        if(product.getDescription().toLowerCase().contains(lowerCaseFilter)) return true;
                        break;
                    default:
                        break;
                }
                return false;
            });
        });

        productSortedList = new SortedList<>(productFilteredList);

        productSortedList.addListener(new InvalidationListener() {
            @Override
            public void invalidated(Observable observable) {
                try {
                    initPagination();
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        });

    }

    private void initPagination() throws IOException {
        int n = productSortedList.size();
        int tmp = (int) Math.ceil(n<=5?1:n/5.0);
        pagination.setPageCount(tmp);
        pagination.setCurrentPageIndex(0);
        pagination.setMaxPageIndicatorCount(tmp);
        productContainer.getChildren().clear();

        int f = 5;
        if(f > productSortedList.size()) f = productSortedList.size();

        for (int i = 0; i < f; i++) {
            productContainer.getChildren().add(loadProduct(i));
        }

        pagination.setPageFactory(param -> {
            productContainer.getChildren().clear();

            int fin = 5*param+5;
            if(fin > productSortedList.size()) fin = productSortedList.size();

            for (int i = 5*param; i < fin; i++) {
                productContainer.getChildren().add(loadProduct(i));
            }
            return this.productContainer;
        });

    }



    private Pane loadProduct(int i){
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/caissier/view/pages/elements.fxml"), Eshop.bundle);
        Pane p = null;
        try {
            p = loader.load();
            ElementsController prodElement = loader.getController();
            prodElement.setParent(this);
            prodElement.loadData(i);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return p;
    }

    public void setDetails(Produit prod){
        name.setText(prod.getNomPro());
        qty.setText(prod.getQte().toString());
        category.setText(prod.getCategorieidCat().getNomCat());
        supplier.setText(prod.getCodeFour());
        dateIns.setText(prod.getDateInsertion().toString());
    }
    
}
