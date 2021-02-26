package entities;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "roles")
public class Roles implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "IdRoles", nullable = false)
    private Integer idRoles;

    @Column(name = "Denomination", nullable = false)
    private String denomination;

    @Column(name = "Actif", nullable = false)
    private Integer actif = 1;

    public void setIdRoles(Integer idRoles) {
        this.idRoles = idRoles;
    }

    public Integer getIdRoles() {
        return idRoles;
    }

    public void setDenomination(String denomination) {
        this.denomination = denomination;
    }

    public String getDenomination() {
        return denomination;
    }

    public void setActif(Integer actif) {
        this.actif = actif;
    }

    public Integer getActif() {
        return actif;
    }

    @Override
    public String toString() {
        return "Roles{" +
                "idRoles=" + idRoles + '\'' +
                "denomination=" + denomination + '\'' +
                "actif=" + actif + '\'' +
                '}';
    }
}
