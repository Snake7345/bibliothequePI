package entities;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "penalites")
public class Penalites implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "IdPenalites", nullable = false)
    private Integer idPenalites;

    @Column(name = "Denomination", nullable = false)
    private String denomination;

    public void setIdPenalites(Integer idPenalites) {
        this.idPenalites = idPenalites;
    }

    public Integer getIdPenalites() {
        return idPenalites;
    }

    public void setDenomination(String denomination) {
        this.denomination = denomination;
    }

    public String getDenomination() {
        return denomination;
    }

    @Override
    public String toString() {
        return "Penalites{" +
                "idPenalites=" + idPenalites + '\'' +
                "denomination=" + denomination + '\'' +
                '}';
    }
}
