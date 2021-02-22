package caissier.controller;

import entity.Categorie;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import caissier.model.*;

import java.io.IOException;
import java.net.URL;
import java.util.Locale;
import java.util.ResourceBundle;

public class CategoryController implements Initializable {

    @FXML
    private Pane apercuPane;

    @FXML
    private Label nom;

    @FXML
    private Label code;

    @FXML
    private Label productsLabel;

    @FXML
    private Label labelQty;

    @FXML
    private Label labelQtyNum;

    @FXML
    private ImageView apercuImg;

    @FXML
    void showProducts(MouseEvent event) throws IOException {
        CategoriesController.loadProdFromCat = true;
        ProductsController.catCode = Long.valueOf(code.getText());
        loadPage("/caissier/view/pages/products.fxml", I18N.getLocale());
    }

    public CategoriesController parent;
    Categorie cat;

    public void setParent(CategoriesController parent){
        this.parent = parent;
    }

    public void loadData(int i){
        if(parent.categorySortedList.size() > i){
            cat = parent.categorySortedList.get(i);
            this.nom.setText(cat.getNomCat());
            this.code.setText(cat.getIdCat().toString());
            // should also set the qty
        }
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    private void loadPage(String path, Locale locale) throws IOException {
        Eshop.bundle = ResourceBundle.getBundle("caissier/language/Bundle", locale);
        Parent root = FXMLLoader.load(getClass().getResource(path), Eshop.bundle);
        Eshop.primaryStage.getScene().setRoot(root);
    }

    @FXML
    void catPressed(MouseEvent event) {
        parent.setDetails(cat);
    }
}
