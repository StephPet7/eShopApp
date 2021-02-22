/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package caissier.controller;

import java.io.IOException;
import java.util.Locale;
import java.util.ResourceBundle;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import caissier.model.Eshop;

/**
 *
 * @author Shaquillo
 */
public abstract class CommonController {
    
    protected Image clearSearch = new Image(getClass().getResourceAsStream("/resources/img/clear_search.png"));
    protected Image searchIm = new Image(getClass().getResourceAsStream("/resources/img/search.png"));
    
    protected void loadPage(String path, Locale locale) throws IOException{
        Eshop.bundle = ResourceBundle.getBundle("caissier/language/Bundle", locale);
        Parent root = FXMLLoader.load(getClass().getResource(path), Eshop.bundle);
        Eshop.primaryStage.getScene().setRoot(root);
    }
    
    protected void putEndSearchIm(TextField textfieldSearch, Button buttonSearch){
        textfieldSearch.textProperty().addListener((obs, oldValue, newValue) -> {
            if(newValue != null || !newValue.isEmpty()){
                buttonSearch.setGraphic(new ImageView(clearSearch));
            } else{
                buttonSearch.setGraphic(new ImageView(searchIm));
            }
        });
    }
    
    protected void putSearchIm(TextField textfieldSearch, Button buttonSearch){
        textfieldSearch.setText("");
        buttonSearch.setGraphic(null);
        buttonSearch.setGraphic(new ImageView(searchIm));
    }

}
