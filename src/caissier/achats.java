/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package caissier;

/**
 *
 * @author Isaac
 */
public class achats {
    private String code;
    private double qte;
    private double prixU;
    private double prixT;

    public achats(String code, double qte, double prixU, double prixT) {
        this.code = code;
        this.qte = qte;
        this.prixU = prixU;
        this.prixT = prixT;
    }
    
    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public double getQte() {
        return qte;
    }

    public void setQte(double qte) {
        this.qte = qte;
    }

    public double getPrixU() {
        return prixU;
    }

    public void setPrixU(double prixU) {
        this.prixU = prixU;
    }

    public double getPrixT() {
        return prixT;
    }

    public void setPrixT(double prixT) {
        this.prixT = prixT;
    }
    
    
            
}
