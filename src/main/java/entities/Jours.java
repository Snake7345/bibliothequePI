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
    private int idJours;
    private int nbrJour;
    @OneToMany(mappedBy = "jours")
    private Collection<TarifsJours> tarifsJours;

    @Id
    @Column(name = "IdJours", nullable = false)
    public int getIdJours() {
        return idJours;
    }

    public void setIdJours(int idJours) {
        this.idJours = idJours;
    }

    @Basic
    @Column(name = "NbrJour", nullable = false)
    public int getNbrJour() {
        return nbrJour;
    }

    public void setNbrJour(int nbrJour) {
        this.nbrJour = nbrJour;
    }

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


    public Collection<TarifsJours> getTarifsJours() {
        return tarifsJours;
    }

    public void setTarifsJours(Collection<TarifsJours> tarifsJoursByIdJours) {
        this.tarifsJours = tarifsJoursByIdJours;
    }
}
