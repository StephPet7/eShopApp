package bd.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

@Entity
@Table(name = "fournisseur")
public class Fournisseur implements Serializable {
    private static final long serialVersionUID = 1L;

    @Column(name = "adresse")
    private String adresse;

    @Id
    @Column(insertable = false, name = "idFournisseur", nullable = false)
    private Integer idFournisseur;

    @Column(name = "nom", nullable = false)
    private String nom;

    @Column(name = "tel")
    private String tel;

    public Fournisseur() {
    }

    public String getAdresse() {
        return adresse;
    }

    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }

    public Integer getIdFournisseur() {
        return idFournisseur;
    }

    public void setIdFournisseur(Integer idFournisseur) {
        this.idFournisseur = idFournisseur;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String toString() {
      return "Fournisseur{adresse=" + adresse + 
        ", idFournisseur=" + idFournisseur + 
        ", nom=" + nom + 
        ", tel=" + tel + 
        "}";
    }
}