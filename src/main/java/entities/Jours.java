package entities;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Collection;
import java.util.Objects;

@Entity
@Table(name="jours")
@NamedQueries
        ({
                @NamedQuery(name = "Jours.findAll", query = "SELECT j FROM Jours j"),
                @NamedQuery(name = "Jours.findByNbrJ", query = "SELECT j FROM Jours j WHERE j.nbrJour<=:nbrJour ORDER BY j.nbrJour DESC"),
                @NamedQuery(name = "Jours.findByNbrJExact", query = "SELECT j FROM Jours j WHERE j.nbrJour=:nbrJour"),
        })
public class Jours implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "IdJours", nullable = false)
    private int idJours;
    @Basic
    @Column(name = "NbrJour", nullable = false)
    private int nbrJour;
    @OneToMany(mappedBy = "jours")
    private Collection<TarifsJours> tarifsJours;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Jours jours = (Jours) o;
        return idJours == jours.idJours &&
                nbrJour == jours.nbrJour;
    }

    @Override
    public int hashCode() {
        return Objects.hash(idJours, nbrJour);
    }

    //-------------------------------Getter & Setter--------------------------------------------

    public int getIdJours() {
        return idJours;
    }

    public void setIdJours(int idJours) {
        this.idJours = idJours;
    }

    public int getNbrJour() {
        return nbrJour;
    }

    public void setNbrJour(int nbrJour) {
        this.nbrJour = nbrJour;
    }

    public Collection<TarifsJours> getTarifsJours() {
        return tarifsJours;
    }

    public void setTarifsJours(Collection<TarifsJours> tarifsJoursByIdJours) {
        this.tarifsJours = tarifsJoursByIdJours;
    }
}
