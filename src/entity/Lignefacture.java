/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import java.io.Serializable;
import java.math.BigDecimal;
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
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Stephen
 */
@Entity
@Table(name = "lignefacture")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Lignefacture.findAll", query = "SELECT l FROM Lignefacture l")
    , @NamedQuery(name = "Lignefacture.findByIdLFac", query = "SELECT l FROM Lignefacture l WHERE l.idLFac = :idLFac")
    , @NamedQuery(name = "Lignefacture.findByPrixVente", query = "SELECT l FROM Lignefacture l WHERE l.prixVente = :prixVente")
    , @NamedQuery(name = "Lignefacture.findByQte", query = "SELECT l FROM Lignefacture l WHERE l.qte = :qte")
    , @NamedQuery(name = "Lignefacture.findByPrixAchat", query = "SELECT l FROM Lignefacture l WHERE l.prixAchat = :prixAchat")})
public class Lignefacture implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idLFac")
    private Integer idLFac;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Basic(optional = false)
    @Column(name = "prixVente")
    private BigDecimal prixVente;
    @Basic(optional = false)
    @Column(name = "qte")
    private BigDecimal qte;
    @Basic(optional = false)
    @Column(name = "prixAchat")
    private BigDecimal prixAchat;
    @JoinColumn(name = "idFac", referencedColumnName = "idFac")
    @ManyToOne(optional = false)
    private Facture idFac;
    @JoinColumn(name = "codePro", referencedColumnName = "codePro")
    @ManyToOne(optional = false)
    private Produit codePro;

    public Lignefacture() {
    }

    public String getCode(){
       return codePro.getCode();
    }
    
    public Lignefacture(Integer idLFac) {
        this.idLFac = idLFac;
    }

    public Lignefacture(Integer idLFac, BigDecimal prixVente, BigDecimal qte, BigDecimal prixAchat) {
        this.idLFac = idLFac;
        this.prixVente = prixVente;
        this.qte = qte;
        this.prixAchat = prixAchat;
    }

    public Integer getIdLFac() {
        return idLFac;
    }

    public void setIdLFac(Integer idLFac) {
        this.idLFac = idLFac;
    }

    public BigDecimal getPrixVente() {
        return prixVente;
    }

    public double getPrixU(){
        return prixVente.doubleValue();
    }
    
    public double getPrixT(){
        return prixVente.multiply(qte).doubleValue();
    }
    
    public void setPrixVente(BigDecimal prixVente) {
        this.prixVente = prixVente;
    }

    public BigDecimal getQte() {
        return qte;
    }

    public void setQte(BigDecimal qte) {
        this.qte = qte;
    }

    public BigDecimal getPrixAchat() {
        return prixAchat;
    }

    public void setPrixAchat(BigDecimal prixAchat) {
        this.prixAchat = prixAchat;
    }

    public Facture getIdFac() {
        return idFac;
    }

    public void setIdFac(Facture idFac) {
        this.idFac = idFac;
    }

    public Produit getCodePro() {
        return codePro;
    }

    public void setCodePro(Produit codePro) {
        this.codePro = codePro;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idLFac != null ? idLFac.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Lignefacture)) {
            return false;
        }
        Lignefacture other = (Lignefacture) object;
        if ((this.idLFac == null && other.idLFac != null) || (this.idLFac != null && !this.idLFac.equals(other.idLFac))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.Lignefacture[ idLFac=" + idLFac + " ]";
    }
    
}
