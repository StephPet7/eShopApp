package bd.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "gestionstock")
public class Gestionstock implements Serializable {
    private static final long serialVersionUID = 1L;

    @Column(name = "codePro", nullable = false)
    private Integer codePro;

    @Column(name = "dateStock", nullable = false)
    private LocalDateTime dateStock;

    @Column(name = "idGest", nullable = false)
    private Integer idGest;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(insertable = false, name = "idStock", nullable = false)
    private Integer idStock;

    @Column(name = "operation", nullable = false)
    private Integer operation = 0;

    @Column(name = "qte", nullable = false)
    private BigDecimal qte;

    public Gestionstock() {
    }

    public Integer getCodePro() {
        return codePro;
    }

    public void setCodePro(Integer codePro) {
        this.codePro = codePro;
    }

    public LocalDateTime getDateStock() {
        return dateStock;
    }

    public void setDateStock(LocalDateTime dateStock) {
        this.dateStock = dateStock;
    }

    public Integer getIdGest() {
        return idGest;
    }

    public void setIdGest(Integer idGest) {
        this.idGest = idGest;
    }

    public Integer getIdStock() {
        return idStock;
    }

    public void setIdStock(Integer idStock) {
        this.idStock = idStock;
    }

    public Integer getOperation() {
        return operation;
    }

    public void setOperation(Integer operation) {
        this.operation = operation;
    }

    public BigDecimal getQte() {
        return qte;
    }

    public void setQte(BigDecimal qte) {
        this.qte = qte;
    }

    public String toString() {
      return "Gestionstock{codePro=" + codePro + 
        ", dateStock=" + dateStock + 
        ", idGest=" + idGest + 
        ", idStock=" + idStock + 
        ", operation=" + operation + 
        ", qte=" + qte + 
        "}";
    }
}