package entities;

import javax.persistence.*;
import java.util.Collection;
import java.util.Objects;

@Entity
public class Jours {
    private int idJours;
    private int nbrJour;
    private Collection<TarifsJours> tarifsJoursByIdJours;

    @Id
    @Column(name = "IdJours")
    public int getIdJours() {
        return idJours;
    }

    public void setIdJours(int idJours) {
        this.idJours = idJours;
    }

    
    @Column(name = "NbrJour")
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
