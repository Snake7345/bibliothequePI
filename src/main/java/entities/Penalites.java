package entities;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Collection;
import java.util.Objects;

@Entity
@Table(name = "penalites")
@NamedQueries
        ({
                @NamedQuery(name = "Penalites.findAllTri", query = "SELECT p FROM Penalites p ORDER BY p.denomination ASC"),
                @NamedQuery(name = "Penalites.findByName", query = "SELECT p FROM Penalites p WHERE p.denomination=:denomination"),
        })
public class Penalites implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "IdPenalites", nullable = false)
    private int idPenalites;
    @Basic
    @Column(name = "Denomination", nullable = false, length = 150)
    private String denomination;
    @OneToMany(mappedBy = "penalite")
    private Collection<TarifsPenalites> tarifsPenalites;

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

    //-------------------------------Getter & Setter--------------------------------------------

    public int getIdPenalites() {
        return idPenalites;
    }

    public void setIdPenalites(int idPenalites) {
        this.idPenalites = idPenalites;
    }

    public String getDenomination() {
        return denomination;
    }

    public void setDenomination(String denomination) {
        this.denomination = denomination;
    }

    public Collection<TarifsPenalites> getTarifsPenalites() {
        return tarifsPenalites;
    }

    public void setTarifsPenalites(Collection<TarifsPenalites> tarifsPenalites) {
        this.tarifsPenalites = tarifsPenalites;
    }

}
