/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Stephen
 */
@Entity
@Table(name = "gestionnaire")
@XmlRootElement
/*@NamedQueries({
    @NamedQuery(name = "Gestionnaire.findAll", query = "SELECT e FROM Gestionnaire e")
    , @NamedQuery(name = "Gestionnaire.findByidGest", query = "SELECT e FROM Gestionnaire e WHERE e.idGest = :idGest")
    , @NamedQuery(name = "Gestionnaire.findByNomGest", query = "SELECT e FROM Gestionnaire e WHERE e.nomGest = :nomGest")
    , @NamedQuery(name = "Gestionnaire.findByTypeGest", query = "SELECT e FROM Gestionnaire e WHERE e.typeGest = :typeGest")
    , @NamedQuery(name = "Gestionnaire.findByEmail", query = "SELECT e FROM Gestionnaire e WHERE e.email = :email")
    , @NamedQuery(name = "Gestionnaire.findByContact", query = "SELECT e FROM Gestionnaire e WHERE e.contact = :contact")
    , @NamedQuery(name = "Gestionnaire.findByAdresse", query = "SELECT e FROM Gestionnaire e WHERE e.adresse = :adresse")
    , @NamedQuery(name = "Gestionnaire.findByLogin", query = "SELECT e FROM Gestionnaire e WHERE e.login = :login")
    , @NamedQuery(name = "Gestionnaire.findByPwd", query = "SELECT e FROM Gestionnaire e WHERE e.pwd = :pwd")
    , @NamedQuery(name = "Gestionnaire.findByActif", query = "SELECT e FROM Gestionnaire e WHERE e.actif = :actif")})*/
public class Gestionnaire implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idGest")
    private Integer idGest;
    @Transient
    private String matricule;
    @Basic(optional = false)
    @Column(name = "nomGest")
    private String nom;
    @Basic(optional = false)
    @Column(name = "typeGest")
    private String type;
    @Basic(optional = false)
    @Column(name = "email")
    private String email;
    @Basic(optional = false)
    @Column(name = "contact")
    private String contact;
    @Basic(optional = false)
    @Column(name = "adresse")
    private String adresse;
    @Basic(optional = false)
    @Column(name = "login")
    private String login;
    @Basic(optional = false)
    @Column(name = "pwd")
    private String password;
    @Basic(optional = false)
    @Column(name = "actif")
    private boolean actif;
    @Transient
    private String printActif;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idCaissiere")
    private List<Facture> factureList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idGest")
    private List<Gestionstock> gestionstockList;

    public Gestionnaire() {
    }

    public Gestionnaire(Integer idGest) {
        this.idGest = idGest;
    }

    public Gestionnaire(Integer idGest, String nomGest, String typeGest, String email, String contact, String adresse, String login, boolean actif) {
        this.idGest = idGest;
        this.matricule = idGest.toString().substring(0, 3) + "-" + idGest.toString().substring(3, 6);
        this.nom = nomGest;
        this.type = typeGest;
        this.email = email;
        this.contact = contact;
        this.adresse = adresse;
        this.login = login;
        this.password = idGest.toString().substring(idGest.toString().length()-2, idGest.toString().length()) + nom.substring(0, 2) + type.substring(0, 2);
        this.actif = actif;
        printActif = (actif) ? "Oui" : "Non";
    }

    public Integer getidGest() {
        return idGest;
    }

    public boolean isActif() {
        return actif;
    }

    public String getMatricule() {
       return idGest.toString().substring(0, 3) + "-" + idGest.toString().substring(3, 6);
    }

    public void setidGest(Integer idGest) {
        this.idGest = idGest;
        this.matricule = idGest.toString().substring(0, 3) + "-" + idGest.toString().substring(3, 6);
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nomGest) {
        this.nom = nomGest;
    }

    public String getType() {
        return type;
    }

    public void setType(String typeGest) {
        this.type = typeGest;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getAdresse() {
        return adresse;
    }

    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String pwd) {
        this.password = pwd;
    }

    public boolean getActif() {
        return actif;
    }

    public void setActif(boolean actif) {
        this.actif = actif;
        printActif = (actif) ? "Oui" : "Non";
    }

    public String getPrintActif() {
        return (actif) ? "Oui" : "Non";
    }

    @XmlTransient
    public List<Facture> getFactureList() {
        return factureList;
    }

    public void setFactureList(List<Facture> factureList) {
        this.factureList = factureList;
    }

    @XmlTransient
    public List<Gestionstock> getGestionstockList() {
        return gestionstockList;
    }

    public void setGestionstockList(List<Gestionstock> gestionstockList) {
        this.gestionstockList = gestionstockList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idGest != null ? idGest.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Gestionnaire)) {
            return false;
        }
        Gestionnaire other = (Gestionnaire) object;
        if ((this.idGest == null && other.idGest != null) || (this.idGest != null && !this.idGest.equals(other.idGest))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.Gestionnaire[ idGest=" + idGest + " ]";
    }
    
}
