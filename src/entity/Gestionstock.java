/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Stephen
 */
@Entity
@Table(name = "gestionstock")
@XmlRootElement
/*@NamedQueries({
    @NamedQuery(name = "Gestionstock.findAll", query = "SELECT g FROM Gestionstock g")
    , @NamedQuery(name = "Gestionstock.findByIdStock", query = "SELECT g FROM Gestionstock g WHERE g.idStock = :idStock")
    , @NamedQuery(name = "Gestionstock.findByQte", query = "SELECT g FROM Gestionstock g WHERE g.qte = :qte")
    , @NamedQuery(name = "Gestionstock.findByDateStock", query = "SELECT g FROM Gestionstock g WHERE g.dateStock = :dateStock")
    , @NamedQuery(name = "Gestionstock.findByOperation", query = "SELECT g FROM Gestionstock g WHERE g.operation = :operation")})*/
public class Gestionstock implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idStock")
    private Integer idStock;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Basic(optional = false)
    @Column(name = "qte")
    private BigDecimal qte;
    @Basic(optional = false)
    @Column(name = "dateStock")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dateStock;
    @Basic(optional = false)
    @Column(name = "operation")
    private boolean operation;
    @JoinColumn(name = "idGest", referencedColumnName = "idGest")
    @ManyToOne(optional = false)
    private Gestionnaire idGest;
    @JoinColumn(name = "codePro", referencedColumnName = "codePro")
    @ManyToOne(optional = false)
    private Produit codePro;

    public Gestionstock() {
    }

    public Gestionstock(Integer idStock) {
        this.idStock = idStock;
    }

    public Gestionstock(Integer idStock, BigDecimal qte, Date dateStock, boolean operation) {
        this.idStock = idStock;
        this.qte = qte;
        this.dateStock = dateStock;
        this.operation = operation;     // Retrait: 0       Ajout:1
    }

    public String getIdStock() {
        return idStock.toString().substring(0, 3) + "-" + idStock.toString().substring(3, 6);
    }

    public void setIdStock(Integer idStock) {
        this.idStock = idStock;
    }

    public String getQte() {
        return qte.toString();
    }

    public void setQte(BigDecimal qte) {
        this.qte = qte;
    }

    public String getDateStock() {
        return dateStock.toGMTString();
    }

    public void setDateStock(Date dateStock) {
        this.dateStock = dateStock;
    }

    public String getOperation() {
        return (operation)?"Ajout":"Retrait";
    }

    public void setOperation(boolean operation) {
        this.operation = operation;
    }

    public String getIdGest() {
        return idGest.getMatricule();
    }

    public void setIdGest(Gestionnaire idGest) {
        this.idGest = idGest;
    }

    public String getCodePro() {
        String code = codePro.getCodePro().toString();
        return code.substring(0, 3) + "-" + code.substring(3, 6);
    }

    public void setCodePro(Produit codePro) {
        this.codePro = codePro;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idStock != null ? idStock.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Gestionstock)) {
            return false;
        }
        Gestionstock other = (Gestionstock) object;
        if ((this.idStock == null && other.idStock != null) || (this.idStock != null && !this.idStock.equals(other.idStock))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.Gestionstock[ idStock=" + idStock + " ]";
    }
    
}
