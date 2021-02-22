/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package magasinier.dashboard;

import magasinier.produit.ProduitsController;
import Utils.Internationalization;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.Initializable;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.chart.AreaChart;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.stage.Stage;
import login.LoginController;
import magasinier.categorie.CategoriesController;

/**
 * FXML Controller class
 *
 * @author Isaac
 */
public class DashboardController implements Initializable {

     @FXML
    private Label lien;

    @FXML
    private Label lien1;

    @FXML
    private Label slogan;

   /**********************        *     */

    @FXML
    private Label username;
    
    /*********************************************/

    @FXML
    private JFXButton dashboard;

    @FXML
    private JFXButton produits;

    @FXML
    private JFXButton categories;

    @FXML
    private ScrollPane chartPane;

    /********** Gestion de la langue *************/
    
    @FXML
    private JFXComboBox<String> langue;
    
    ResourceBundle titres;
   
    public void loadResource(String language){
        titres =Internationalization.getBundle(language);
        setResource();
    }
    
    public void setResource(){
        /************************************ A Compler ****************/
        lien.setText(titres.getString("MAGASINIER"));
        lien1.setText(titres.getString("DASHBOARD"));
        dashboard.setText(titres.getString("DASHBOARD"));
        produits.setText(titres.getString("PRODUITS"));
        categories.setText(titres.getString("CATEGORY"));
        
        refreshFlow.setText(titres.getString("REFRESH"));   
        
        refreshSales.setText(titres.getString("REFRESH"));   
        
        refreshProportionsCategorie.setText(titres.getString("REFRESH"));   
        
        refreshTypeProduit.setText(titres.getString("REFRESH"));   
        
        flowGraph.setTitle(titres.getString("FLOW_GRAPH_TITLE"));
        flowGraph.getXAxis().setLabel(titres.getString("FLOW_GRAPH_X"));
        flowGraph.getYAxis().setLabel(titres.getString("FLOW_GRAPH_Y"));
        
         differenceChart.setName(titres.getString("DIFFERENT_CHART"));
         entryChart.setName(titres.getString("ENTRY_CHART"));
         exitChart.setName(titres.getString("EXIT_CHART"));
         proportionsCategorie.setTitle(titres.getString("PROPORTIONS_CHART"));
         typeProduitGraph.getYAxis().setLabel(titres.getString("TYPE_PRODUCTS_GRAPH_TITLE"));
        typeProduitChart.setName(titres.getString("TYPE_PRODUCTS_Y"));
        
        salesGraph.getYAxis().setLabel(titres.getString("SALES_GRAPH_TITLE"));
        salesChart.setName(titres.getString("SALES_GRAPH_TITLE"));
        
        
    }

    /************************************************/
    @FXML
    void categories() {
        try {
             Stage tmp=(Stage) lien.getScene().getWindow();  
             FXMLLoader loader=new FXMLLoader(getClass().getResource("/magasinier/categorie/categories.fxml"));
             Scene newScene = new Scene(loader.load());
             CategoriesController controller = loader.getController();
             controller.loadResource(langue.getValue());
             tmp.setScene(newScene);
             tmp.hide();
             tmp.show();
             tmp.setMaximized(true);
         } catch (IOException ex) {
             Logger.getLogger(LoginController.class.getName()).log(Level.SEVERE, null, ex);
         }
    }

    @FXML
    void logOut() {
       loadInterface(false);
    }
    
    void loadInterface(boolean test){
        try {
             Stage tmp=(Stage) lien.getScene().getWindow();  
             FXMLLoader loader=new FXMLLoader(getClass().getResource(test?"/magasinier/produit/produits.fxml":"/login/login.fxml"));
             Scene newScene = new Scene(loader.load());
             if(test){
                 ProduitsController controller = loader.getController();
                 controller.loadResource(langue.getValue());
             }else{
                LoginController controller = loader.getController();
                controller.loadResource(langue.getValue());
             }
             tmp.setScene(newScene);
             tmp.hide();
             tmp.show();
             tmp.setMaximized(true);
         } catch (IOException ex) {
             Logger.getLogger(LoginController.class.getName()).log(Level.SEVERE, null, ex);
         }
    }
    
    @FXML
    void produits() {
        loadInterface(true);
    }
    
    /**
     * Initializes the controller class.
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        titres = Internationalization.initLanguage(langue);
        
        
        Utils.ControllerUtils.setProfil(username,null,null,null);
        
        langue.valueProperty().addListener((observable, oldValue, newValue) -> {
            loadResource(newValue);
        });
        
        chartPane.widthProperty().addListener((observable, oldValue, newValue) -> {
            chartPane.setFitToWidth(newValue.intValue() >1000);
        });
        
         // flowGraph
                initFlowGraph();

       //proportionsCategorie Graph
                initProportionsCategorie();
                
    	//typeProduit Graph
                initTypeProduit();

    	//salesGraph
                initSales();        
    }    
    
    
    /********** Flow graph *************/
    
    @FXML
    private JFXButton refreshFlow;
    
    @FXML
    private AreaChart<String, Number> flowGraph;
    
    XYChart.Series<String,Number> entryChart = new XYChart.Series<>();
   
    XYChart.Series<String,Number> exitChart = new XYChart.Series<>();
    
    XYChart.Series<String,Number> differenceChart = new XYChart.Series<>();
    
    XYChart.Series<String,Number> salesChart = new XYChart.Series<>();
    
    @FXML
    void refreshFlow(ActionEvent event) {
        differenceChart.getData().clear();
        loadDifferenceData();
        
        entryChart.getData().clear();
        loadEntryData();
        
        exitChart.getData().clear();
        loadExitData();
    }
    
    void initFlowGraph(){
        flowGraph.setTitle(titres.getString("FLOW_GRAPH_TITLE"));
        flowGraph.getXAxis().setLabel(titres.getString("FLOW_GRAPH_X"));
        flowGraph.getYAxis().setLabel(titres.getString("FLOW_GRAPH_Y"));

        loadDifferenceData();
        flowGraph.getData().add(differenceChart);

        loadEntryData();
        flowGraph.getData().add(entryChart);

        loadExitData();
        flowGraph.getData().add(exitChart);
    }
    
    void loadDifferenceData(){
            differenceChart.setName(titres.getString("DIFFERENT_CHART"));
        /*// resultSet va contenir les jours et les differences du mois en cours
    		while(resultSet.next()){
    			differenceChart.getData.add(new XYChart.Data<Number, Number>(resultSet.getInt(),result.getDouble()));
    		}*/
                differenceChart.getData().add(new XYChart.Data<>("0",0));
                differenceChart.getData().add(new XYChart.Data<>("1",-11));
                differenceChart.getData().add(new XYChart.Data<>("2",-11));
                differenceChart.getData().add(new XYChart.Data<>("3",10));
                differenceChart.getData().add(new XYChart.Data<>("4",20));
                differenceChart.getData().add(new XYChart.Data<>("5",-3));
                differenceChart.getData().add(new XYChart.Data<>("6",10));
                differenceChart.getData().add(new XYChart.Data<>("7",20));
                differenceChart.getData().add(new XYChart.Data<>("8",13));
    }
    
    void loadEntryData(){
        entryChart.setName(titres.getString("ENTRY_CHART"));
        /*// resultSet va contenir les jours et les entree du mois en cours
    		while(resultSet.next()){
    			entryChart.getData.add(new XYChart.Data<Number, Number>(resultSet.getInt(),result.getDouble()));
    		}*/
                entryChart.getData().add(new XYChart.Data<>("0",2));
                entryChart.getData().add(new XYChart.Data<>("1",9));
                entryChart.getData().add(new XYChart.Data<>("2",11));
                entryChart.getData().add(new XYChart.Data<>("3",23));
                entryChart.getData().add(new XYChart.Data<>("4",20));
                entryChart.getData().add(new XYChart.Data<>("5",2));
                entryChart.getData().add(new XYChart.Data<>("6",25));
                entryChart.getData().add(new XYChart.Data<>("7",20));
                entryChart.getData().add(new XYChart.Data<>("8",20));
    }  
        
    public void loadExitData(){
        exitChart.setName(titres.getString("EXIT_CHART"));
        /*// resultSet va contenir les jours et les sortie du mois en cours
    		while(resultSet.next()){
    			exitChart.getData.add(new XYChart.Data<Number, Number>(resultSet.getInt(),result.getDouble()));
    		}*/
                exitChart.getData().add(new XYChart.Data<>("0",2));
                exitChart.getData().add(new XYChart.Data<>("1",20));
                exitChart.getData().add(new XYChart.Data<>("2",21));
                exitChart.getData().add(new XYChart.Data<>("3",13));
                exitChart.getData().add(new XYChart.Data<>("4",0));
                exitChart.getData().add(new XYChart.Data<>("5",5));
                exitChart.getData().add(new XYChart.Data<>("6",15));
                exitChart.getData().add(new XYChart.Data<>("7",0));
                exitChart.getData().add(new XYChart.Data<>("8",7));
    }
    
    /************************************************************/
    
    /* ***************proportionsCategorie**************** */
    @FXML
    private JFXButton refreshProportionsCategorie;

    @FXML
    private PieChart proportionsCategorie;

    ObservableList<PieChart.Data> proportionsCategorieChartData;
    
    void loadProportionsCategorie(){
        proportionsCategorieChartData.addAll(new PieChart.Data("Agro-Alimentaire", 3000),
                                            new PieChart.Data("Cosmetique", 2000),
                                            new PieChart.Data("Electro-Menager", 1400),
                                            new PieChart.Data("Autre", 900));
    }
    
    void initProportionsCategorie(){
         proportionsCategorie.setTitle(titres.getString("PROPORTIONS_CHART"));
         proportionsCategorieChartData = proportionsCategorie.getData();
                 
         loadProportionsCategorie();
    }
    
    @FXML
    void refreshProportionsCategorie() {
        proportionsCategorie.getData().clear();
        loadProportionsCategorie();
    }
    
    /********************************************************************/
    
    /* *********************** typeProduitGraph ******************** */
    @FXML
    private AreaChart<String, Number> typeProduitGraph;

    @FXML
    private JFXButton refreshTypeProduit;
    
    XYChart.Series<String,Number> typeProduitChart = new XYChart.Series<>();
    
    public void loadTypeProduitData(){
        /*// resultSet va contenir les jours et les  du mois en cours
    		while(resultSet.next()){
    			typeProduitChart.getData.add(new XYChart.Data<Number, Number>(resultSet.getInt(),result.getDouble()));
    		}*/
                typeProduitChart.getData().add(new XYChart.Data<>("Jan",25));
                typeProduitChart.getData().add(new XYChart.Data<>("Fev",30));
                typeProduitChart.getData().add(new XYChart.Data<>("Mar",30));
                typeProduitChart.getData().add(new XYChart.Data<>("Av",25));
                typeProduitChart.getData().add(new XYChart.Data<>("May",20));
                typeProduitChart.getData().add(new XYChart.Data<>("Jun",25));
                typeProduitChart.getData().add(new XYChart.Data<>("Jul",15));
                typeProduitChart.getData().add(new XYChart.Data<>("Au",20));
                typeProduitChart.getData().add(new XYChart.Data<>("Sep",17));
    }
    
    void initTypeProduit(){
        typeProduitGraph.getYAxis().setLabel(titres.getString("TYPE_PRODUCTS_GRAPH_TITLE"));
        typeProduitChart.setName(titres.getString("TYPE_PRODUCTS_Y"));

        loadTypeProduitData();
        typeProduitGraph.getData().add(typeProduitChart);
    }
    
    @FXML
    void refreshTypeProduit(ActionEvent event) {
        typeProduitChart.getData().clear();  
        loadTypeProduitData();
    }

    /********************************************************************/
    
    /*************** Sales Graph **********************/
    
    @FXML
    private Label date;

    @FXML
    private BarChart<String, Number> salesGraph;

    @FXML
    private JFXButton refreshSales;
    
    void initSales(){
        salesGraph.getYAxis().setLabel(titres.getString("SALES_GRAPH_TITLE"));
        salesChart.setName(titres.getString("SALES_GRAPH_TITLE"));

        loadSalesData();
        salesGraph.getData().add(salesChart);
    }
    
    public void loadSalesData(){
        /*// resultSet va contenir les jours et les ventes du mois en cours
    		while(resultSet.next()){
    			salesChart.getData.add(new XYChart.Data<Number, Number>(resultSet.getInt(),result.getDouble()));
    		}*/
                
                salesChart.getData().add(new XYChart.Data<>("Pate Alimentaire",400));
                salesChart.getData().add(new XYChart.Data<>("Aperitifs",500));
                salesChart.getData().add(new XYChart.Data<>("Poisson",200));
                salesChart.getData().add(new XYChart.Data<>("electro-Menager",80));
                salesChart.getData().add(new XYChart.Data<>("Produits Laitiers",250));
                salesChart.getData().add(new XYChart.Data<>("Vetements",100));
                salesChart.getData().add(new XYChart.Data<>("Fruits et Legumes",750));
                salesChart.getData().add(new XYChart.Data<>("Autres",300));
    }
    
    @FXML
    void refreshSales(ActionEvent event) {
        salesChart.getData().clear();
       loadSalesData();
    }
    
    
}
