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
import com.jfoenix.controls.JFXTextField;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import de.jensd.fx.glyphs.materialdesignicons.MaterialDesignIconView;
import javafx.beans.binding.StringBinding;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import caissier.model.I18N;

import static caissier.controller.ClientsController.editClientVerifier;
import static caissier.controller.ClientsController.listClients;

/**
 * FXML Controller class
 *
 * @author Shaquillo
 */
public class AddClientController implements Initializable {

     @FXML
    private Label alertLabel;

    @FXML
    private JFXTextField nameTF;

    @FXML
    private JFXTextField telTF;

    @FXML
    private JFXTextField adresseTF;

    @FXML
    private JFXButton addButton;

    @FXML
    private JFXButton cancelButton;

    @FXML
    private Label headingLabel;

    @FXML
    private MaterialDesignIconView headingImg;
    
    @FXML
    private JFXComboBox<String> telIndex;

    @FXML
    void add(ActionEvent event) throws IOException {
        String name = nameTF.getText(), tel = telTF.getText(), adresse = adresseTF.getText();
        boolean flag = false;
        if(name.isEmpty() || tel.isEmpty() || adresse.isEmpty()){
            alertLabel.setText(I18N.get("label.fillAll"));
        } else{
            // add to the db.
            IClient clientFunc = new ClientImpl();

            if(editClientVerifier){
                Client c = clientList.get(index);
                if(!name.equals(c.getNom())){
                    c.setNom(name);
                    flag = true;
                }
                if(!tel.equals(c.getTel())){
                    c.setTel(tel);
                    flag = true;
                }
                if(!adresse.equals(c.getAdresse())){
                    c.setAdresse(adresse);
                    flag = true;
                }
                if(flag){
                    clientFunc.updateClient(c.getIdClient(), c);
                }
            }else {
                IClient addClient = new ClientImpl();
                addClient.createClient(nameTF.getText(), /*telIndex +*/ telTF.getText(), adresseTF.getText());
                nameTF.setText("");
                telTF.setText("");
                adresseTF.setText("");
            }
            listClients.clear();
            listClients.addAll(clientFunc.readClient());
        }

    }

    @FXML
    void cancel(ActionEvent event) {
        nameTF.setText("");
        telTF.setText("");
        adresseTF.setText("");
    }

    private ObservableList<Client> clientList;

    public void setClientList(ObservableList<Client> clientList){
        this.clientList = clientList;
    }

    private int index;

    public void loadFields(int i){
        index = i;
        Client c = clientList.get(i);
        nameTF.setText(c.getNom());
        telTF.setText(c.getTel());
        adresseTF.setText(c.getAdresse());
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        labelManagement();

        setAMButton();

        StringBinding strb = I18N.createStringBinding("button.add");
        
        //set telepnone prefixes
        telIndex.getItems().addAll("+237", "+234", "+1", "+44", "+33", "+86");
        telIndex.getSelectionModel().selectFirst();
    }

    private void setAMButton(){
        if(editClientVerifier){
            addButton.setText(I18N.get("button.edit"));
            headingLabel.setText(I18N.get("label.modifyClient"));
            editClientVerifier = false;
        }
    }

    private void labelManagement(){
        nameTF.textProperty().addListener((ObservableValue<? extends String> obs, String oldValue, String newValue) -> {
            if(!newValue.isEmpty()) alertLabel.setText("");
        });
        telTF.textProperty().addListener((ObservableValue<? extends String> obs, String oldValue, String newValue) -> {
            if(!newValue.isEmpty()) alertLabel.setText("");
        });
        adresseTF.textProperty().addListener((ObservableValue<? extends String> obs, String oldValue, String newValue) -> {
            if(!newValue.isEmpty()) alertLabel.setText("");
        });
    }

    
}
