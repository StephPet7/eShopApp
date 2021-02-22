/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package admin;

import java.util.Date;

/**
 *
 * @author Stephen
 */
public class Facture {
    private String codeFacture;
    private String date;
    private String codePaiement;
    private double remise;
    private double montant;
    private String modePaiement;
    private String idCaissiere;
    private String idClient;

    public Facture(String codeFacture, String date, String codePaiement, double remise, double montant, String modePaiement, String idCaissiere, String idClient) {
        this.codeFacture = codeFacture;
        this.date = date;
        this.codePaiement = codePaiement;
        this.remise = remise;
        this.montant = montant;
        this.modePaiement = modePaiement;
        this.idCaissiere = idCaissiere;
        this.idClient = idClient;
    }

    public String getCodeFacture() {
        return codeFacture;
    }

    public void setCodeFacture(String codeFacture) {
        this.codeFacture = codeFacture;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getCodePaiement() {
        return codePaiement;
    }

    public void setCodePaiement(String codePaiement) {
        this.codePaiement = codePaiement;
    }

    public double getRemise() {
        return remise;
    }

    public void setRemise(double remise) {
        this.remise = remise;
    }

    public double getMontant() {
        return montant;
    }

    public void setMontant(double montant) {
        this.montant = montant;
    }

    public String getModePaiement() {
        return modePaiement;
    }

    public void setModePaiement(String modePaiement) {
        this.modePaiement = modePaiement;
    }

    public String getIdCaissiere() {
        return idCaissiere;
    }

    public void setIdCaissiere(String idCaissiere) {
        this.idCaissiere = idCaissiere;
    }

    public String getIdClient() {
        return idClient;
    }

    public void setIdClient(String idClient) {
        this.idClient = idClient;
    }
    
}
