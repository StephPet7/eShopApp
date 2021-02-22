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
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Isaac
 */
@Entity
@Table(name = "categorie")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Categorie.findAll", query = "SELECT c FROM Categorie c")
    , @NamedQuery(name = "Categorie.findByIdCat", query = "SELECT c FROM Categorie c WHERE c.idCat = :idCat")
    , @NamedQuery(name = "Categorie.findByNomCat", query = "SELECT c FROM Categorie c WHERE c.nomCat = :nomCat")})
public class Categorie implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idCat")
    private Integer idCat;
    @Basic(optional = false)
    @Column(name = "nomCat")
    private String nomCat;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "categorieidCat")
    private List<Produit> produitList;

    public Categorie() {
    }

    public Categorie(Integer idCat) {
        this.idCat = idCat;
    }

    public Categorie(Integer idCat, String nomCat) {
        this.idCat = idCat;
        this.nomCat = nomCat;
    }

    public Integer getIdCat() {
        return idCat;
    }

    public String getCode(){
        return idCat.toString().substring(0, 3) + "-" + idCat.toString().substring(3, 6);
    }
    
    public void setIdCat(Integer idCat) {
        this.idCat = idCat;
    }

    public String getNomCat() {
        return nomCat;
    }

    public void setNomCat(String nomCat) {
        this.nomCat = nomCat;
    }

    @XmlTransient
    public List<Produit> getProduitList() {
        return produitList;
    }

    public void setProduitList(List<Produit> produitList) {
        this.produitList = produitList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idCat != null ? idCat.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Categorie)) {
            return false;
        }
        Categorie other = (Categorie) object;
        if ((this.idCat == null && other.idCat != null) || (this.idCat != null && !this.idCat.equals(other.idCat))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.Categorie[ idCat=" + idCat + " ]";
    }

    public String getDesc(Categorie cat) {
        return "Description de la categorie";
    }
    
}
