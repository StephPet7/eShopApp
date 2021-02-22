/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Isaac
 */
@Entity
@Table(name = "produit")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Produit.findAll", query = "SELECT p FROM Produit p")
    , @NamedQuery(name = "Produit.findByCodePro", query = "SELECT p FROM Produit p WHERE p.codePro = :codePro")
    , @NamedQuery(name = "Produit.findByNomPro", query = "SELECT p FROM Produit p WHERE p.nomPro = :nomPro")
    , @NamedQuery(name = "Produit.findByPrixVente", query = "SELECT p FROM Produit p WHERE p.prixVente = :prixVente")
    , @NamedQuery(name = "Produit.findByPrixAchat", query = "SELECT p FROM Produit p WHERE p.prixAchat = :prixAchat")
    , @NamedQuery(name = "Produit.findByQte", query = "SELECT p FROM Produit p WHERE p.qte = :qte")
    , @NamedQuery(name = "Produit.findByDescription", query = "SELECT p FROM Produit p WHERE p.description = :description")
    , @NamedQuery(name = "Produit.findByCodeFour", query = "SELECT p FROM Produit p WHERE p.codeFour = :codeFour")
    , @NamedQuery(name = "Produit.findByDateInsertion", query = "SELECT p FROM Produit p WHERE p.dateInsertion = :dateInsertion")
    , @NamedQuery(name = "Produit.findByDatePeremtion", query = "SELECT p FROM Produit p WHERE p.datePeremtion = :datePeremtion")
    , @NamedQuery(name = "Produit.findByActif", query = "SELECT p FROM Produit p WHERE p.actif = :actif")
    , @NamedQuery(name = "Produit.findByFraction", query = "SELECT p FROM Produit p WHERE p.fraction = :fraction")})
public class Produit implements Serializable {

    @JoinColumn(name = "categorie_idCat", referencedColumnName = "idCat")
    @ManyToOne(optional = false)
    private Categorie categorieidCat;

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "codePro")
    private Integer codePro;
    @Basic(optional = false)
    @Column(name = "nomPro")
    private String nomPro;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Basic(optional = false)
    @Column(name = "prixVente")
    private BigDecimal prixVente;
    @Basic(optional = false)
    @Column(name = "prixAchat")
    private BigDecimal prixAchat;
    @Basic(optional = false)
    @Column(name = "qte")
    private BigDecimal qte;
    @Basic(optional = false)
    @Column(name = "description")
    private String description;
    @Basic(optional = false)
    @Column(name = "codeFour")
    private String codeFour;
    @Basic(optional = false)
    @Column(name = "dateInsertion")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dateInsertion;
    @Basic(optional = false)
    @Column(name = "datePeremtion")
    @Temporal(TemporalType.TIMESTAMP)
    private Date datePeremtion;
    @Basic(optional = false)
    @Column(name = "actif")
    private boolean actif;
    @Basic(optional = false)
    @Column(name = "fraction")
    private boolean fraction;

    @Transient
    private String date;
    
    public Produit() {
    }

    public Produit(Integer codePro) {
        this.codePro = codePro;
    }

    public Produit(Integer codePro, String nomPro, BigDecimal prixVente, BigDecimal prixAchat, BigDecimal qte, String description, String codeFour, Date dateInsertion, Date datePeremtion, boolean actif, boolean fraction) {
        this.codePro = codePro;
        this.nomPro = nomPro;
        this.prixVente = prixVente;
        this.prixAchat = prixAchat;
        this.qte = qte;
        this.description = description;
        this.codeFour = codeFour;
        this.dateInsertion = dateInsertion;
        this.datePeremtion = datePeremtion;
        this.actif = actif;
        this.fraction = fraction;
    }

    public Integer getCodePro() {
        return codePro;
    }

    public void setCodePro(Integer codePro) {
        this.codePro = codePro;
    }    
    
    public String getCode(){
        return codePro.toString().substring(0, 3) + "-" + codePro.toString().substring(3, 6);        
    }

    public String getEtat(){
        return fraction?"enabled":"disabled";
    }
    
    public String getCategorie(){
        return categorieidCat.getIdCat().toString().substring(0, 3) + "-" + categorieidCat.getIdCat().toString().substring(3, 6);
    }
    
    public String getNomPro() {
        return nomPro;
    }

    public void setNomPro(String nomPro) {
        this.nomPro = nomPro;
    }

    public BigDecimal getPrixVente() {
        return prixVente;
    }

    public void setPrixVente(BigDecimal prixVente) {
        this.prixVente = prixVente;
    }

    public BigDecimal getPrixAchat() {
        return prixAchat;
    }

    public void setPrixAchat(BigDecimal prixAchat) {
        this.prixAchat = prixAchat;
    }

    public BigDecimal getQte() {
        return qte;
    }

    public void setQte(BigDecimal qte) {
        this.qte = qte;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCodeFour() {
        return codeFour;
    }

    public void setCodeFour(String codeFour) {
        this.codeFour = codeFour;
    }

    public Date getDateInsertion() {
        return dateInsertion;
    }

    public void setDateInsertion(Date dateInsertion) {
        this.dateInsertion = dateInsertion;
    }

    public Date getDatePeremtion() {
        return datePeremtion;
    }
    
    public String getDate(){
        return new SimpleDateFormat("dd-MM-yyyy").format(datePeremtion);
    }

    public void setDatePeremtion(Date datePeremtion) {
        this.datePeremtion = datePeremtion;
    }

    public boolean getActif() {
        return actif;
    }

    public void setActif(boolean actif) {
        this.actif = actif;
    }

    public boolean getFraction() {
        return fraction;
    }

    public void setFraction(boolean fraction) {
        this.fraction = fraction;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (codePro != null ? codePro.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Produit)) {
            return false;
        }
        Produit other = (Produit) object;
        if ((this.codePro == null && other.codePro != null) || (this.codePro != null && !this.codePro.equals(other.codePro))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.Produit[ codePro=" + codePro + " ]";
    }

    public Categorie getCategorieidCat() {
        return categorieidCat;
    }

    public void setCategorieidCat(Categorie categorieidCat) {
        this.categorieidCat = categorieidCat;
    }
    
}
