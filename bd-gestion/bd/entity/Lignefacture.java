package bd.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;

@Table(name = "lignefacture")
@Entity
public class Lignefacture implements Serializable {
    private static final long serialVersionUID = 1L;

    @Column(name = "codePro", nullable = false)
    private Integer codePro;

    @Column(name = "idFac", nullable = false)
    private Integer idFac;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idLFac", insertable = false, nullable = false)
    private Integer idLFac;

    @Column(name = "prixAchat", nullable = false)
    private BigDecimal prixAchat;

    @Column(name = "prixVente", nullable = false)
    private BigDecimal prixVente;

    public Lignefacture() {
    }

    @Column(name = "qte", nullable = false)
    private BigDecimal qte;

    public Integer getCodePro() {
        return codePro;
    }

    public void setCodePro(Integer codePro) {
        this.codePro = codePro;
    }

    public Integer getIdFac() {
        return idFac;
    }

    public void setIdFac(Integer idFac) {
        this.idFac = idFac;
    }

    public Integer getIdLFac() {
        return idLFac;
    }

    public void setIdLFac(Integer idLFac) {
        this.idLFac = idLFac;
    }

    public BigDecimal getPrixAchat() {
        return prixAchat;
    }

    public void setPrixAchat(BigDecimal prixAchat) {
        this.prixAchat = prixAchat;
    }

    public BigDecimal getPrixVente() {
        return prixVente;
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

    public String toString() {
      return "Lignefacture{codePro=" + codePro + 
        ", idFac=" + idFac + 
        ", idLFac=" + idLFac + 
        ", prixAchat=" + prixAchat + 
        ", prixVente=" + prixVente + 
        ", qte=" + qte + 
        "}";
    }
}