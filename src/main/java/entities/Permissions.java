package entities;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "permissions")
public class Permissions implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "IdPermissions", nullable = false)
    private Integer idPermissions;

    @Column(name = "Denomination", nullable = false)
    private String denomination;

    public void setIdPermissions(Integer idPermissions) {
        this.idPermissions = idPermissions;
    }

    public Integer getIdPermissions() {
        return idPermissions;
    }

    public void setDenomination(String denomination) {
        this.denomination = denomination;
    }

    public String getDenomination() {
        return denomination;
    }

    @Override
    public String toString() {
        return "Permissions{" +
                "idPermissions=" + idPermissions + '\'' +
                "denomination=" + denomination + '\'' +
                '}';
    }
}
