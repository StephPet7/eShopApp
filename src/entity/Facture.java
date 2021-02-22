/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Stephen
 */
@Entity
@Table(name = "facture")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Facture.findAll", query = "SELECT f FROM Facture f")
    , @NamedQuery(name = "Facture.findByIdFac", query = "SELECT f FROM Facture f WHERE f.idFac = :idFac")
    , @NamedQuery(name = "Facture.findByDateFac", query = "SELECT f FROM Facture f WHERE f.dateFac = :dateFac")
    , @NamedQuery(name = "Facture.findByCodePaiement", query = "SELECT f FROM Facture f WHERE f.codePaiement = :codePaiement")
    , @NamedQuery(name = "Facture.findByRemise", query = "SELECT f FROM Facture f WHERE f.remise = :remise")
    , @NamedQuery(name = "Facture.findByMontant", query = "SELECT f FROM Facture f WHERE f.montant = :montant")
    , @NamedQuery(name = "Facture.findByModePaiement", query = "SELECT f FROM Facture f WHERE f.modePaiement = :modePaiement")})
public class Facture implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idFac")
    private Integer idFac;
    @Basic(optional = false)
    @Column(name = "dateFac")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dateFac;
    @Basic(optional = false)
    @Column(name = "codePaiement")
    private String codePaiement;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Basic(optional = false)
    @Column(name = "remise")
    private BigDecimal remise;
    @Basic(optional = false)
    @Column(name = "montant")
    private BigDecimal montant;
    @Basic(optional = false)
    @Column(name = "modePaiement")
    private boolean modePaiement;
    @JoinColumn(name = "idClient", referencedColumnName = "idClient")
    @ManyToOne(optional = false)
    private Client idClient;
    @JoinColumn(name = "idCaissiere", referencedColumnName = "idGest")
    @ManyToOne(optional = false)
    private Gestionnaire idCaissiere;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idFac")
    private List<Lignefacture> lignefactureList;

    public Facture() {
    }

    public Facture(Integer idFac) {
        this.idFac = idFac;
    }

    public Facture(Integer idFac, Date dateFac, String codePaiement, BigDecimal remise, BigDecimal montant, boolean modePaiement) {
        this.idFac = idFac;
        this.dateFac = dateFac;
        this.codePaiement = codePaiement;
        this.remise = remise;
        this.montant = montant;
        this.modePaiement = modePaiement;
    }

    public Integer getIdFac() {
        return idFac;
    }

    public void setIdFac(Integer idFac) {
        this.idFac = idFac;
    }

    public Date getDateFac() {
        return dateFac;
    }

    public void setDateFac(Date dateFac) {
        this.dateFac = dateFac;
    }

    public String getCodePaiement() {
        return codePaiement;
    }

    public void setCodePaiement(String codePaiement) {
        this.codePaiement = codePaiement;
    }

    public BigDecimal getRemise() {
        return remise;
    }

    public void setRemise(BigDecimal remise) {
        this.remise = remise;
    }

    public BigDecimal getMontant() {
        return montant;
    }

    public void setMontant(BigDecimal montant) {
        this.montant = montant;
    }

    public boolean getModePaiement() {
        return modePaiement;
    }

    public void setModePaiement(boolean modePaiement) {
        this.modePaiement = modePaiement;
    }

    public Client getIdClient() {
        return idClient;
    }

    public void setIdClient(Client idClient) {
        this.idClient = idClient;
    }

    public Gestionnaire getIdCaissiere() {
        return idCaissiere;
    }

    public void setIdCaissiere(Gestionnaire idCaissiere) {
        this.idCaissiere = idCaissiere;
    }

    @XmlTransient
    public List<Lignefacture> getLignefactureList() {
        return lignefactureList;
    }

    public void setLignefactureList(List<Lignefacture> lignefactureList) {
        this.lignefactureList = lignefactureList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idFac != null ? idFac.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Facture)) {
            return false;
        }
        Facture other = (Facture) object;
        if ((this.idFac == null && other.idFac != null) || (this.idFac != null && !this.idFac.equals(other.idFac))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.Facture[ idFac=" + idFac + " ]";
    }

    public String getClientName() {
        return idClient.getNom();
    }
    
}
