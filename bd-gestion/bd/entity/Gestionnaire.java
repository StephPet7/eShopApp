package bd.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

@Entity
@Table(name = "gestionnaire")
public class Gestionnaire implements Serializable {
    private static final long serialVersionUID = 1L;

    @Column(name = "actif", nullable = false)
    private Integer actif = 0;

    @Id
    @Column(name = "idGest", insertable = false, nullable = false)
    private Integer idGest;

    @Column(name = "login", nullable = false)
    private String login;

    @Column(name = "nomGest", nullable = false)
    private String nomGest;

    @Column(name = "pwd", nullable = false)
    private String pwd;

    @Column(name = "typeGest", nullable = false)
    private Integer typeGest;

    public Gestionnaire() {
    }

    public Integer getActif() {
        return actif;
    }

    public void setActif(Integer actif) {
        this.actif = actif;
    }

    public Integer getIdGest() {
        return idGest;
    }

    public void setIdGest(Integer idGest) {
        this.idGest = idGest;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getNomGest() {
        return nomGest;
    }

    public void setNomGest(String nomGest) {
        this.nomGest = nomGest;
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    public Integer getTypeGest() {
        return typeGest;
    }

    public void setTypeGest(Integer typeGest) {
        this.typeGest = typeGest;
    }

    public String toString() {
      return "Gestionnaire{actif=" + actif + 
        ", idGest=" + idGest + 
        ", login=" + login + 
        ", nomGest=" + nomGest + 
        ", pwd=" + pwd + 
        ", typeGest=" + typeGest + 
        "}";
    }
}