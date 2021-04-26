package entities;

import javax.persistence.*;
import java.util.Collection;
import java.util.Objects;

@Entity
@Table(name = "penalites")
@NamedQueries
        ({
                @NamedQuery(name = "Penalites.findAllTri", query = "SELECT p FROM Penalites p ORDER BY p.denomination ASC"),
        })
public class Penalites {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idPenalites;
    private String denomination;
    private Collection<TarifsPenalites> tarifsPenalitesByIdPenalites;

    @Id
    @Column(name = "IdPenalites", nullable = false)
    public int getIdPenalites() {
        return idPenalites;
    }

    public void setIdPenalites(int idPenalites) {
        this.idPenalites = idPenalites;
    }

    @Basic
    @Column(name = "Denomination", nullable = false, length = 150)
    public String getDenomination() {
        return denomination;
    }

    public void setDenomination(String denomination) {
        this.denomination = denomination;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Penalites penalites = (Penalites) o;
        return idPenalites == penalites.idPenalites &&
                Objects.equals(denomination, penalites.denomination);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idPenalites, denomination);
    }

    @OneToMany(mappedBy = "penalitesByPenalitesIdPenalites")
    public Collection<TarifsPenalites> getTarifsPenalitesByIdPenalites() {
        return tarifsPenalitesByIdPenalites;
    }

    public void setTarifsPenalitesByIdPenalites(Collection<TarifsPenalites> tarifsPenalitesByIdPenalites) {
        this.tarifsPenalitesByIdPenalites = tarifsPenalitesByIdPenalites;
    }
}
