package bd.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Table(name = "produit")
@Entity
public class Produit implements Serializable {
    private static final long serialVersionUID = 1L;

    @Column(name = "actif")
    private Integer actif = 1;

    @Column(name = "categorie_idCat")
    private Integer categorieIdcat;

    @Column(name = "codeFour")
    private String codeFour;

    @Id
    @Column(name = "codePro")
    private Integer codePro;

    @Column(name = "dateInsertion")
    private LocalDateTime dateInsertion;

    @Column(name = "description")
    private String description = "RAS";

    @Column(name = "nomPro")
    private String nomPro = "ND";

    @Column(name = "prixAchat")
    private BigDecimal prixAchat;

    @Column(name = "prixVente")
    private BigDecimal prixVente;

    @Column(name = "qte")
    private BigDecimal qte;

    public Produit() {
    }

    public Produit(Integer codePro, String nomPro, BigDecimal prixVente, BigDecimal prixAchat, BigDecimal qte , int categorieIdcat , String codeFour) {
        this.codePro = codePro;
        this.nomPro = nomPro;
        this.prixVente = prixVente;
        this.prixAchat = prixAchat;
        this.qte = qte;
        this.categorieIdcat = categorieIdcat;
        this.codeFour = codeFour ;

    }

    public Integer getActif() {
        return actif;
    }

    public void setActif(Integer actif) {
        this.actif = actif;
    }

    public Integer getCategorieIdcat() {
        return categorieIdcat;
    }

    public void setCategorieIdcat(Integer categorieIdcat) {
        this.categorieIdcat = categorieIdcat;
    }

    public String getCodeFour() {
        return codeFour;
    }

    public void setCodeFour(String codeFour) {
        this.codeFour = codeFour;
    }

    public Integer getCodePro() {
        return codePro;
    }

    public void setCodePro(Integer codePro) {
        this.codePro = codePro;
    }

    public LocalDateTime getDateInsertion() {
        return dateInsertion;
    }

    public void setDateInsertion(LocalDateTime dateInsertion) {
        this.dateInsertion = dateInsertion;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getNomPro() {
        return nomPro;
    }

    public void setNomPro(String nomPro) {
        this.nomPro = nomPro;
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
      return "Produit{actif=" + actif + 
        ", categorieIdcat=" + categorieIdcat + 
        ", codeFour=" + codeFour + 
        ", codePro=" + codePro + 
        ", dateInsertion=" + dateInsertion + 
        ", description=" + description + 
        ", nomPro=" + nomPro + 
        ", prixAchat=" + prixAchat + 
        ", prixVente=" + prixVente + 
        ", qte=" + qte + 
        "}";
    }
}