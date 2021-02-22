/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package magasinier.produit;

import Utils.ControllerUtils;
import static Utils.ControllerUtils.print;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.Initializable;
import javafx.stage.Stage;


import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.controls.JFXToggleButton;
import dao.EntitiesImpl.CategoryImpl;
import dao.EntitiesImpl.GestionStockImpl;
import dao.EntitiesImpl.ProductImpl;
import entity.Gestionstock;
import entity.Produit;
import java.math.BigDecimal;
import java.util.Date;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Calendar;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import login.LoginController;

/**
 * FXML Controller class
 *
 * @author Isaac
 */
public class EditProduitsController implements Initializable {

    /**
     * Initializes the controller class.
     */
    
    @FXML
    private Pane rootPane;
     @FXML
    private Label lien;

    @FXML
    private Label lien1;

    @FXML
    private Label lien11;
    
    @FXML
    private Label title;

    @FXML
    private JFXTextArea description;

    @FXML
    private JFXButton valider;

    @FXML
    private JFXButton annuler;

    @FXML
    private JFXTextField code;

    @FXML
    private JFXTextField nom;

    @FXML
    private JFXTextField quantite;

    @FXML
    private JFXTextField fournisseur;

    @FXML
    private JFXToggleButton actif;

    @FXML
    private JFXTextField prixA;

    @FXML
    private JFXTextField prixV;

    @FXML
    private JFXDatePicker date;

    @FXML
    private JFXComboBox<String> categorie;

    CategoryImpl catImpl =  new CategoryImpl();
    ProductImpl proImpl = new ProductImpl();
    GestionStockImpl stockImpl = new GestionStockImpl();
    
    @FXML
    void annuler() {
        close();
    }
    Calendar c =Calendar.getInstance();
    @FXML
    void valider() {        
        Produit p = null;
        Gestionstock stock = null;
        //Changement de la categorie du produit
        if(index == -1){
/***1 ************Enregistrer ce produit dans la BD *****************/
           p = new Produit( 0,
                            nom.getText(),
                            BigDecimal.valueOf(Double.valueOf(prixV.getText())),
                            BigDecimal.valueOf(Double.valueOf(prixA.getText())),
                            BigDecimal.valueOf(0),
                            description.getText(),
                            fournisseur.getText().replace("-",""),
                            c.getTime(), 
                            ControllerUtils.getDate(date.getValue()), 
                            true,
                            actif.isSelected());
           p.setCategorieidCat(catImpl.findCategoryByName( categorie.getValue() ));
           
           if(proImpl.save(p)){
               sortedData.add(p);
               System.out.println("Added "+p.getCategorieidCat());
               Utils.ControllerUtils.showNotification("Eclipse Link - JPA[MySql Connection]","Product Added successfuly: [id ="+p.getCode()+"]",false,Pos.BOTTOM_RIGHT,5);
           }else{
               System.out.println("Add failed "+p.getCategorieidCat());
               Utils.ControllerUtils.showNotification("Eclipse Link - JPA[MySql Connection]","Adding Product failed",false,Pos.BOTTOM_RIGHT,5);
           }
           
        }else{
               p = sortedData.get(index);
               BigDecimal qte = p.getQte();
               
               getData(p);
               
               qte = qte.subtract(p.getQte());
               int operation = qte.compareTo(BigDecimal.ZERO);
               if(operation == -1){ //Cas d'un retrait
                   stock = new Gestionstock(0,
                                            qte.abs(),
                                            c.getTime(),
                                            false);
               }else if(operation == 1){ //Cas d'un ajout
                    stock = new Gestionstock(0,
                                            qte,
                                            c.getTime(),
                                            true);
               }else{ }
               
               if(stock != null){
                   stock.setCodePro(p);
                   stock.setIdGest(LoginController.gestionnaire);
                   if(stockImpl.addGestionStock(stock)){
                       print(" Modifications saved on B.D[Getionstock]");
                   }
               }
/***2 ********** Modifier ce produit dans la BD *********************/
                if(proImpl.update(p)){
                    sortedData.set(index,p);
                    System.out.println("Updated "+p.getCategorieidCat());
                    ControllerUtils.showNotification("Eclipse Link - JPA[MySql Connection]","Product Updated successfuly: [id ="+p.getCode()+"]",false,Pos.BOTTOM_RIGHT,5);
                }else {
                    ControllerUtils.showNotification("Eclipse Link - JPA[MySql Connection]","Updating Product successfuly: [id ="+p.getCode()+"]",false,Pos.BOTTOM_RIGHT,5);
                    System.out.println("Update failed "+p.getCategorieidCat());
                }
        }
        close();
    }   
    
    public void getData(Produit p){
        p.setNomPro(nom.getText());
        p.setPrixAchat(BigDecimal.valueOf(Double.valueOf(prixA.getText())) );
        p.setPrixVente(BigDecimal.valueOf(Double.valueOf(prixV.getText())) );
        p.setQte(BigDecimal.valueOf(Double.valueOf(quantite.getText())));
        p.setDescription(description.getText());
        p.setCodeFour(fournisseur.getText().replace("-",""));
        p.setDatePeremtion( Date.from(date.getValue().atStartOfDay(ZoneId.systemDefault()).toInstant()) );
        p.setFraction(actif.isSelected());   
        p.setCategorieidCat(catImpl.findCategoryByName(categorie.getValue()));
    }
    
    
    public void loadCategories(){
/***3 ******************** Charger les noms de categories dans categorie.getItems *********/
        categorie.getItems().addAll(catImpl.findCategoryName());
    }
    
    public void setType(Boolean b){
        if(b){
            lien11.setText("Ajouter un Produit");
            title.setText("Ajouter un Produit");
            quantite.setDisable(true);
            quantite.setText("0");
        }else{
            
            
        }
    }
    
    ObservableList<Produit> sortedData ;
    int index = -1;
    
    public void setItems(ObservableList<Produit> items){
        sortedData = items;
    }
    
    public void loadData(int i){
        index = i;
        Produit p = sortedData.get(i);
        code.setText(p.getCode());
        nom.setText(p.getNomPro());
        quantite.setText(String.valueOf( p.getQte() ) );
        fournisseur.setText(Utils.ControllerUtils.castId(Integer.valueOf(p.getCodeFour())) );
        prixA.setText( String.valueOf( p.getPrixAchat() ) );
        prixV.setText( String.valueOf( p.getPrixVente() ) );
        date.setValue( ControllerUtils.getLocalDate(p.getDatePeremtion()) );
        description.setText(p.getDescription());
        actif.setSelected(p.getFraction());
        categorie.setValue(p.getCategorieidCat().getNomCat());
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO;
        
        /********** Initialise Apercu ************/
        apercuNom.textProperty().bindBidirectional(nom.textProperty());
        apercuCode.textProperty().bindBidirectional(code.textProperty());
        apercuPrix.textProperty().bindBidirectional(prixV.textProperty());
        
        apercuCategorie.textProperty().bindBidirectional(categorie.valueProperty());
        
        date.setValue(LocalDate.now());
        valider.setDisable(false);
        //changeLocaleOfDate();
        loadCategories();
        
        code.setDisable(true);
        code.setText(ControllerUtils.castId(proImpl.findMaxId()));
        actif.setText("Fractionable");
        
        ControllerUtils.changeLocaleOfDate(date);
    }    
    
    @FXML void close(){
        Stage tmp = (Stage)lien.getScene().getWindow();
        tmp.close();
    }
    
    
    /********************** Apercu *************/
    
    @FXML
    private ImageView apercuImg;

    @FXML
    private Label apercuNom;

    @FXML
    private Label apercuCode;

    @FXML
    private Label apercuPrix;

    @FXML
    private Label apercuCategorie;

    @FXML
    private JFXButton editImg;
    
    @FXML
    public void editImg(){
        
    }
}
