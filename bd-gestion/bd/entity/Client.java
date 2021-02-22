package bd.entity;

import javax.persistence.*;
import java.io.Serializable;

@Table(name = "client")
@Entity
public class Client implements Serializable {
    private static final long serialVersionUID = 1L;

    @Column(name = "actif", nullable = false)
    private boolean actif;

    @Column(name = "adresse")
    private String adresse;

    @Column(name = "bonus")
    private Integer bonus;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idClient", insertable = false, nullable = false)
    private Integer idClient;

    @Column(name = "nom", nullable = false)
    private String nom = "ND";

    @Column(name = "tel")
    private String tel;

    public Client() {
    }

    public Client(String nom, String tel, String adresse, Integer bonus, boolean actif) {
        this.nom = nom;
        this.tel = tel;
        this.adresse = adresse;
        this.bonus = bonus;
        this.actif = actif;
    }

    public boolean getActif() {
        return actif;
    }

    public void setActif(boolean actif) {
        this.actif = actif;
    }

    public String getAdresse() {
        return adresse;
    }

    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }

    public Integer getBonus() {
        return bonus;
    }

    public void setBonus(Integer bonus) {
        this.bonus = bonus;
    }

    public Integer getIdClient() {
        return idClient;
    }

    public void setIdClient(Integer idClient) {
        this.idClient = idClient;
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
      return "Client{actif=" + actif + 
        ", adresse=" + adresse + 
        ", bonus=" + bonus + 
        ", idClient=" + idClient + 
        ", nom=" + nom + 
        ", tel=" + tel + 
        "}";
    }
}