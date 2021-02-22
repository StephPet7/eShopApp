/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package caissier;

import Utils.ControllerUtils;
import Utils.Internationalization;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.controls.JFXToggleButton;
import dao.EntitiesImpl.ClientImpl;
import dao.EntitiesImpl.FactureImpl;
import dao.EntitiesImpl.LigneFactureImpl;
import dao.EntitiesImpl.ProductImpl;
import entity.Client;
import entity.Facture;
import entity.Lignefacture;
import entity.Produit;
import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;  
import java.time.LocalDateTime; 
import java.util.ArrayList;
import java.util.Calendar;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import login.LoginController;
import resources.Ticket_de_caisse.Ticket;

public class FacturationController implements Initializable {

    @FXML
    private Label lien;

    @FXML
    private Label slogan;

    @FXML
    private Label username;

    @FXML
    private JFXButton facturation;

    @FXML
    private JFXButton produits;

    @FXML
    private JFXButton categories;

    @FXML
    private JFXButton clients;

    @FXML
    private JFXTextField idClient;

    @FXML
    private JFXTextField codeProduit;

    @FXML
    private JFXTextField remise;

    @FXML
    private Label stock;

    @FXML
    private JFXTextField quantite;

    @FXML
    private JFXToggleButton eMoney;

    @FXML
    private ImageView add;
    
    @FXML
    private ImageView productImg;

    @FXML
    private Label total;

    @FXML
    private Label net;

    @FXML
    private Label reliquat;

    @FXML
    private JFXTextField montantRemi;

    @FXML
    private JFXButton valider;

    @FXML
    private JFXButton apercu;

    @FXML
    private JFXButton annuler;

    @FXML
    private JFXButton del;

    @FXML
    private JFXButton recettes;
    

    @FXML
    private TableView<Lignefacture> panier;
    
    @FXML
    private TableColumn<Lignefacture, String> codeCol;

    @FXML
    private TableColumn<Lignefacture, BigDecimal> qteCol;

    @FXML
    private TableColumn<Lignefacture, Double> prixUCol;

    @FXML
    private TableColumn<Lignefacture, Double> prixTCol;

    
    @FXML
    private Text nomP;

    @FXML
    private Text prixP;

    @FXML
    private Text stateP;

    @FXML
    private Text dateP;

    @FXML
    private Text categorieP;
    
    
    @FXML
    private JFXComboBox<String> langue;

    ObservableList<Lignefacture> listAchats = FXCollections.observableArrayList();
    
    double tmp,tmp2,tmp3;
    Lignefacture tmpLigneFac=null;
    double totalValue,montantValue;
     
    Produit p;
    ProductImpl pImpl = new ProductImpl();
    FactureImpl factureImpl = new FactureImpl();
    Client client = null;
    ClientImpl clientImpl  = new ClientImpl();
    LigneFactureImpl ligneFacImpl = new LigneFactureImpl();
    
    Calendar c =Calendar.getInstance();    
    DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
    LocalDateTime now;
    
    @FXML
    void add(MouseEvent event) {
        
        if(!quantite.getText().equals("") && !prixP.getText().equals("NaN")){
            tmp = Double.valueOf(quantite.getText().replace(" ",""));        
            
            System.out.println(">new Achats: ["+codeProduit.getText()+", "+tmp+", "+tmp2+", "+tmp*tmp2+"]");
            boolean flag = false;
            for(Lignefacture a:listAchats){
                if( a.getCode().equals(codeProduit.getText())){
                    a.setQte( a.getQte().add( BigDecimal.valueOf(tmp) ) );
                    flag= true;
                    break;
                }
            }
            if(!flag){
                tmpLigneFac = new Lignefacture(0,p.getPrixVente(),BigDecimal.valueOf(tmp),p.getPrixAchat());
                tmpLigneFac.setCodePro(p);
                listAchats.add(tmpLigneFac);
            }
            stock.setText( ""+(Integer.valueOf(stock.getText().replace(" ","")) - Integer.valueOf(quantite.getText().replace(" ",""))));
        }
    }

    @FXML
    void annuler(ActionEvent event) {
        panier.getItems().clear();
        clear();
    }
    
    
    @FXML
    void apercu() {
        try {
            now = LocalDateTime.now();
            ArrayList<Lignefacture> ticketTmp = new ArrayList();
            listAchats.forEach((t) -> {
                ticketTmp.add(t);
            });
            Ticket t = new Ticket(ticketTmp,dtf.format(now),LoginController.gestionnaire.getMatricule(),Double.valueOf(montantRemi.getText().replace(" ","")) );
            t.buildTicket();
            Desktop.getDesktop().open(new File(t.fichierTicket));
        } catch (IOException ex) {
            Logger.getLogger(FacturationController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    void categories(ActionEvent event) {
        
    }

    @FXML
    void client() {

    }

    @FXML
    void del(ActionEvent event) { 
        panier.getSelectionModel().getSelectedItems().forEach((a)->{
            panier.getItems().remove(a);
        });
    }

    @FXML
    void logOut() {
        try {
             Stage tmpStage = (Stage)lien.getScene().getWindow();
             FXMLLoader loader=new FXMLLoader(getClass().getResource("/login/login.fxml"));
             Scene newScene = new Scene(loader.load());
             LoginController controller = loader.getController();
             controller.loadResource(langue.getValue());
             tmpStage.hide();
             tmpStage.setScene(newScene);
             tmpStage.show();
         } catch (IOException ex) {
             Logger.getLogger(FacturationController.class.getName()).log(Level.SEVERE, null, ex);
         }
    }

    @FXML
    void produits() {

    }

    @FXML
    void valider() {       
        double rem = 0.0;
        double totaux = 0.0;
        for(Lignefacture f:listAchats){
            totaux+= f.getPrixT();
        }
        try{
            rem = Double.valueOf(remise.getText().replace(" ",""));
        }catch(NumberFormatException e){
            ControllerUtils.print("Cannot Format Remise");
        }
        
        now = LocalDateTime.now(); 
        Facture facture = new Facture(0,
                                      ControllerUtils.getDate(LocalDate.now()),
                                      dtf.format(now),
                                      BigDecimal.valueOf(rem),
                                      BigDecimal.valueOf(totaux),
                                      eMoney.isSelected());
        facture.setIdClient(this.client);
        facture.setIdCaissiere(LoginController.gestionnaire);
        facture.setLignefactureList(listAchats.subList(0, listAchats.size()));
        
        factureImpl.saveFacture(facture);
        listAchats.forEach((Lignefacture t) -> {
            t.setIdFac(facture);
        });
        ligneFacImpl.saveLigneFacture(listAchats.subList(0, listAchats.size()));
        
        apercu();
        clear();
        panier.getItems().clear();
    }

    ResourceBundle titres;
    
    public void loadResource(String language){
        titres =Internationalization.getBundle(language);
        setResource();
    }
   
    public void setResource(){
        /*********************** ***********A Faire /*****************************/
    }
     
    void buildReliquat(){
         try{
                totalValue = Double.valueOf(net.getText());
                montantValue = Double.valueOf(montantRemi.getText().replace(" ",""));
                reliquat.setText(""+(montantValue - totalValue));
            }catch(NumberFormatException e){
                reliquat.setText("NaN");
            }
    }
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        
        net.textProperty().addListener((observable, oldValue, newValue) -> {
            buildReliquat();
        });
        
        idClient.textProperty().addListener((observable, oldValue, newValue) -> {
             // si une certaine condition, charge les infos relatives aux produits
             if(newValue.length() >=7 && newValue.contains("-")){
                 try{
                     client = clientImpl.getClientById(Integer.valueOf(newValue.replace("-","")));
                 }catch(NumberFormatException e){
                     client = null;
                 }
                 if(client==null){
                     ControllerUtils.showNotification("Eshop[Cassier]","Client[ id =" + newValue+" ] introuvable",true,Pos.BOTTOM_RIGHT,5);
                 }
                 
             }
        });
        
        listAchats.addListener((javafx.beans.Observable observable) -> {
            totalValue = 0.0;
            if(!listAchats.isEmpty()){
                listAchats.forEach((a) -> {
                    totalValue+= ( a.getPrixVente().multiply(a.getQte()) ).doubleValue();
                });
                try{
                    tmp3 = Double.valueOf(remise.getText().replace(" ",""));
                }catch(NumberFormatException e){
                    tmp3=0;
                }
                
                totalValue*=(1-tmp3/100);
                
                total.setText(String.format("%.2f",totalValue));
                net.setText(String.format("%.2f",totalValue));
            }else{
                total.setText("0.00");
                net.setText("0.00");
            }
        });
        
        montantRemi.textProperty().addListener((observable, oldValue, newValue) -> {
           buildReliquat();
        });
        
        titres = Internationalization.initLanguage(langue);
        langue.valueProperty().addListener((observable, oldValue, newValue) -> {
            loadResource(newValue);
        });
        
        initializeTableView();
        
        initializeProductInfos();
        
        del.setDisable(true);
        
        productImg.setImage(null);
   } 
    
   public void initializeLanguage(){
         langue.getItems().addAll("Francais","English");
        langue.setValue("English");
    }
   
   
   Label placeHolder = new Label("No products");
    
   public void initializeTableView(){
        codeCol.setCellValueFactory(new PropertyValueFactory<>("code"));
        
        qteCol.setCellValueFactory(new PropertyValueFactory<>("qte"));
        
        prixUCol.setCellValueFactory(new PropertyValueFactory<>("prixU"));
        
        prixTCol.setCellValueFactory(new PropertyValueFactory<>("prixT"));
        
        TableView.TableViewSelectionModel model = panier.getSelectionModel();
        model.setSelectionMode(SelectionMode.MULTIPLE);//On peut selectionner plusieurs entrees a la fois
        
        model.selectedItemProperty().addListener((ObservableValue obs, Object oldSelection, Object newSelection) -> {
            del.setDisable(model.getSelectedItems().isEmpty());
        });
         
        panier.setPlaceholder(placeHolder);
        
        panier.setItems(null);
        panier.setItems(listAchats);
    }
   
   public void initializeProductInfos(){
         codeProduit.textProperty().addListener((observable, oldValue, newValue) -> {
             // si une certaine condition, charge les infos relatives aux produits
             if(newValue.length() >=7 && newValue.contains("-")){
               try{
                    p  = pImpl.findByCode( Integer.valueOf(newValue.replace("-","")) );
                    if(p!=null){
                        nomP.setText(p.getNomPro());
                        prixP.setText(p.getPrixVente().toString());
                        stateP.setText("Bon");
                        dateP.setText(p.getDate());
                        categorieP.setText(p.getCategorie());

                        if(newValue.equals("300-001")) productImg.setImage(new Image("/resources/img/productPerime.png"));
                        else if(newValue.equals("300-002"))productImg.setImage(new Image("/resources/img/productPerimeP.png"));
                        else productImg.setImage(new Image("/resources/img/defaultProduct.png"));

                        c.setTime(p.getDatePeremtion());
                        stock.setText(p.getFraction()?p.getQte().toString():""+p.getQte().intValue());
                    }else{
                        ControllerUtils.print("No Produit Found for id = "+newValue);
                    }
               }catch( NumberFormatException e){
                   ControllerUtils.print("Code Produit Parsing Failed");
               }
             }else{
                 p=null;
                 clear();
             }
         });
     }   

   public void clear(){
        nomP.setText("");
        prixP.setText("");
        stateP.setText("");
        dateP.setText("");
        categorieP.setText("");
        productImg.setImage(null);
        stock.setText("");
   }
   
   @FXML
   public void loadApercu() {}
   
}
