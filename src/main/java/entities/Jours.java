package entities;

import javax.persistence.*;
import java.util.Collection;
import java.util.Objects;

@NamedQueries
        ({
                @NamedQuery(name = "Jours.findAll", query = "SELECT j FROM Jours j"),
        })
@Entity
public class Jours {
    private int idJours;
    private int nbrJour;
    private Collection<TarifsJours> tarifsJoursByIdJours;

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

    @OneToMany(mappedBy = "joursByJoursIdJours")
    public Collection<TarifsJours> getTarifsJoursByIdJours() {
        return tarifsJoursByIdJours;
    }

    public void setTarifsJoursByIdJours(Collection<TarifsJours> tarifsJoursByIdJours) {
        this.tarifsJoursByIdJours = tarifsJoursByIdJours;
    }
}
