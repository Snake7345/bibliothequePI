package entities;

import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
import javax.persistence.*;
import java.io.Serializable;

@Entity
@Named
@SessionScoped
public class Jours implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "IdJours", nullable = false)
    private Integer idJours;

    @Column(name = "NbrJour", nullable = false)
    private Integer nbrJour;

    public void setIdJours(Integer idJours) {
        this.idJours = idJours;
    }

    public Integer getIdJours() {
        return idJours;
    }

    public void setNbrJour(Integer nbrJour) {
        this.nbrJour = nbrJour;
    }

    public Integer getNbrJour() {
        return nbrJour;
    }

    @Override
    public String toString() {
        return "Jours{" +
                "idJours=" + idJours + '\'' +
                "nbrJour=" + nbrJour + '\'' +
                '}';
    }
}
