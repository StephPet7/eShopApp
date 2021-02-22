package bd.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

@Table(name = "categorie")
@Entity
public class Categorie implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "idCat")
    private Integer idCat;

    @Column(name = "nomCat")
    private String nomCat;

    public Categorie() {
    }

    public Categorie(Integer idCat , String nomCat) {
        this.idCat = idCat ;
        this.nomCat = nomCat ;
    }

    public Integer getIdCat() {
        return idCat;
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

    public String toString() {
      return "Categorie{idCat=" + idCat + 
        ", nomCat=" + nomCat + 
        "}";
    }
}