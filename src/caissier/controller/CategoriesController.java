/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package caissier.controller;

import entity.Categorie;
import dao.EntitiesImpl.CategoryImpl;
import dao.ICategory;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextArea;
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
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.Pane;
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
public class CategoriesController extends CommonController implements Initializable {
    
    public static boolean loadProdFromCat = false;
    
    @FXML
    private Label labelUsername;

    @FXML
    private JFXButton buttonDisconnect;
    
    @FXML
    private JFXButton buttonFactorisation;

    @FXML
    private JFXButton buttonProduct;

    @FXML
    private JFXButton buttonCllient;

    @FXML
    private JFXButton buttonProducts;

    @FXML
    private TextField textfieldSearch;

    @FXML
    private Button buttonSearch;
    
    @FXML
    private JFXComboBox<String> comboBoxSearchBy;

    @FXML
    private JFXComboBox<String> comboBoxLanguage;

    @FXML
    private Pagination pagination;

    @FXML
    private FlowPane categoryContainer;

    @FXML
    private ScrollPane scrPane;

    @FXML
    private JFXTextArea description;

    @FXML
    private Label category;

    @FXML
    private ImageView catImg;


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
        
        manageLanguage(rb);
        
        initSearchByField();
        
        // changing search image button

        loadCategories();

        try {
            initPagination();
        } catch (IOException e) {
            e.printStackTrace();
        }

        putEndSearchIm(textfieldSearch, buttonSearch);
    }  
    
    @FXML
    void loadClientPage(ActionEvent event) throws IOException {
        loadPage("/caissier/view/pages/clients.fxml", I18N.getLocale());
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
    void clearSearch(ActionEvent event) {
        putSearchIm(textfieldSearch, buttonSearch);
    }
    
    @FXML
    void logout(ActionEvent event) {

    }

    private void manageLanguage(ResourceBundle rb){
        // Adding and managing change of language

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
                loadPage("/caissier/view/pages/categories.fxml", I18N.getLocale());
            } catch (IOException ex) {
                Logger.getLogger(DailyBillController.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
    }

    private void initSearchByField(){
        // changing and managing of language
        comboBoxSearchBy.getItems().addAll(I18N.get("column.code"), I18N.get("column.name"), I18N.get("column.qty"));
        comboBoxSearchBy.getSelectionModel().selectFirst();
    }

    //load products from database
    private ObservableList<Categorie> listCategories = FXCollections.observableArrayList();
    private ICategory categoryFunc;
    private FilteredList<Categorie> categoryFilteredList;
    public SortedList<Categorie> categorySortedList;

    public void loadCategories(){
        categoryFunc = new CategoryImpl();
        listCategories.addAll(categoryFunc.readCategory());

        categoryFilteredList = new FilteredList<>(listCategories, p->true);

        textfieldSearch.textProperty().addListener((ObservableValue<? extends String> obs, String oldValue, String newValue) -> {
            categoryFilteredList.setPredicate(categorie -> {
                if(newValue == null || newValue.isEmpty()) return true;

                String lowerCaseFilter = newValue.toLowerCase();
                int i = comboBoxSearchBy.getItems().indexOf(comboBoxSearchBy.getValue());

                switch(i){
                    case 0:
                        if(categorie.getIdCat().toString().contains(lowerCaseFilter)) return true;
                        break;
                    case 1:
                        if(categorie.getNomCat().toLowerCase().contains(lowerCaseFilter)) return true;
                        break;
                    default:
                        break;
                }
                return false;
            });
        });

        categorySortedList = new SortedList<>(categoryFilteredList);

        categorySortedList.addListener(new InvalidationListener() {
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
        int tmp = (int) Math.ceil(categorySortedList.size()/4.0);
        pagination.setPageCount(tmp);
        pagination.setCurrentPageIndex(0);
        pagination.setMaxPageIndicatorCount(tmp);
        categoryContainer.getChildren().clear();

        int f = 4;
        if(f > categorySortedList.size()) f = categorySortedList.size();

        for (int i = 0; i < f; i++) {
            categoryContainer.getChildren().add(loadCategory(i));
        }

        pagination.setPageFactory(param -> {
            categoryContainer.getChildren().clear();

            int fin = 4*param+4;
            if(fin > categorySortedList.size()) fin = categorySortedList.size();

            for (int i = 4*param; i < fin; i++) {
                try {
                    categoryContainer.getChildren().add(loadCategory(i));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            return this.scrPane;
        });

    }

    private Pane loadCategory(int i) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/caissier/view/pages/category.fxml"), Eshop.bundle);
        Pane p = loader.load();
        CategoryController catCon = loader.getController();
        catCon.setParent(this);
        catCon.loadData(i);
        return p;
    }

    public void setDetails(Categorie cat){
        category.setText(cat.getNomCat());
        description.setText(cat.getDesc(cat));
    }
}
