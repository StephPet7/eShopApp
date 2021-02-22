package caissier.controller;

import entity.Produit;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;

import java.net.URL;
import java.util.ResourceBundle;

public class ElementsController implements Initializable {

    @FXML
    private ImageView img;

    @FXML
    private Label nom;

    @FXML
    private Label code;

    @FXML
    private Label description;

    ProductsController parent;
    Produit prod;

    public void setParent(ProductsController parent){
        this.parent = parent;
    }

    public void loadData(int i){
        if(parent.productSortedList.size() > i){
            prod = parent.productSortedList.get(i);
            this.nom.setText(prod.getNomPro());
            this.code.setText(prod.getCodePro().toString());
            this.description.setText(prod.getDescription());
        }
    }

    @FXML
    void elementPressed(ActionEvent event) {
        parent.setDetails(prod);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
}
