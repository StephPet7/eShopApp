package bd.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "facture")
public class Facture implements Serializable {
    private static final long serialVersionUID = 1L;

    @Column(name = "codePaiement", nullable = false)
    private String codePaiement;

    @Column(name = "dateFac", nullable = false)
    private LocalDateTime dateFac;

    @Column(name = "idCaissiere", nullable = false)
    private Integer idCaissiere;

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idClient", nullable = false)
    private Integer idClient;

    @Id
    @Column(name = "idFac", insertable = false, nullable = false)
    private Integer idFac;

    @Column(name = "modePaiement", nullable = false)
    private Integer modePaiement;

    @Column(name = "montant", nullable = false)
    private BigDecimal montant;

    @Column(name = "remise", nullable = false)
    private BigDecimal remise;

    public Facture() {
    }

    public String getCodePaiement() {
        return codePaiement;
    }

    public void setCodePaiement(String codePaiement) {
        this.codePaiement = codePaiement;
    }

    public LocalDateTime getDateFac() {
        return dateFac;
    }

    public void setDateFac(LocalDateTime dateFac) {
        this.dateFac = dateFac;
    }

    public Integer getIdCaissiere() {
        return idCaissiere;
    }

    public void setIdCaissiere(Integer idCaissiere) {
        this.idCaissiere = idCaissiere;
    }

    public Integer getIdClient() {
        return idClient;
    }

    public void setIdClient(Integer idClient) {
        this.idClient = idClient;
    }

    public Integer getIdFac() {
        return idFac;
    }

    public void setIdFac(Integer idFac) {
        this.idFac = idFac;
    }

    public Integer getModePaiement() {
        return modePaiement;
    }

    public void setModePaiement(Integer modePaiement) {
        this.modePaiement = modePaiement;
    }

    public BigDecimal getMontant() {
        return montant;
    }

    public void setMontant(BigDecimal montant) {
        this.montant = montant;
    }

    public BigDecimal getRemise() {
        return remise;
    }

    public void setRemise(BigDecimal remise) {
        this.remise = remise;
    }

    public String toString() {
      return "Facture{codePaiement=" + codePaiement + 
        ", dateFac=" + dateFac + 
        ", idCaissiere=" + idCaissiere + 
        ", idClient=" + idClient + 
        ", idFac=" + idFac + 
        ", modePaiement=" + modePaiement + 
        ", montant=" + montant + 
        ", remise=" + remise + 
        "}";
    }
}