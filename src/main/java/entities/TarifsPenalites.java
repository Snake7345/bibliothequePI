package entities;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Objects;

@Entity
@Table(name = "tarifs_penalites", schema = "bibliotheque")
@NamedQueries
        ({
                @NamedQuery(name = "TarifsPenalites.findAll", query = "SELECT tp FROM TarifsPenalites tp"),
        })
public class TarifsPenalites {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int tarifsIdTarifs;
    private int penalitesIdPenalites;
    private int idTarifsPenalites;
    private double prix;
    private Timestamp dateDebut;
    private Timestamp dateFin;
    private Tarifs tarifsByTarifsIdTarifs;
    private Penalites penalitesByPenalitesIdPenalites;

    @Basic
    @Column(name = "TarifsIdTarifs", nullable = false)
    public int getTarifsIdTarifs() {
        return tarifsIdTarifs;
    }

    public void setTarifsIdTarifs(int tarifsIdTarifs) {
        this.tarifsIdTarifs = tarifsIdTarifs;
    }

    @Basic
    @Column(name = "PenalitesIdPenalites", nullable = false)
    public int getPenalitesIdPenalites() {
        return penalitesIdPenalites;
    }

    public void setPenalitesIdPenalites(int penalitesIdPenalites) {
        this.penalitesIdPenalites = penalitesIdPenalites;
    }

    @Id
    @Column(name = "IdTarifsPenalites", nullable = false)
    public int getIdTarifsPenalites() {
        return idTarifsPenalites;
    }

    public void setIdTarifsPenalites(int idTarifsPenalites) {
        this.idTarifsPenalites = idTarifsPenalites;
    }

    @Basic
    @Column(name = "Prix", nullable = false, precision = 0)
    public double getPrix() {
        return prix;
    }

    public void setPrix(double prix) {
        this.prix = prix;
    }

    @Basic
    @Column(name = "DateDebut", nullable = false)
    public Timestamp getDateDebut() {
        return dateDebut;
    }

    public void setDateDebut(Timestamp dateDebut) {
        this.dateDebut = dateDebut;
    }

    @Basic
    @Column(name = "DateFin", nullable = false)
    public Timestamp getDateFin() {
        return dateFin;
    }

    public void setDateFin(Timestamp dateFin) {
        this.dateFin = dateFin;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TarifsPenalites that = (TarifsPenalites) o;
        return tarifsIdTarifs == that.tarifsIdTarifs &&
                penalitesIdPenalites == that.penalitesIdPenalites &&
                idTarifsPenalites == that.idTarifsPenalites &&
                Double.compare(that.prix, prix) == 0 &&
                Objects.equals(dateDebut, that.dateDebut) &&
                Objects.equals(dateFin, that.dateFin);
    }

    @Override
    public int hashCode() {
        return Objects.hash(tarifsIdTarifs, penalitesIdPenalites, idTarifsPenalites, prix, dateDebut, dateFin);
    }

    @ManyToOne
    @JoinColumn(name = "TarifsIdTarifs", referencedColumnName = "IdTarifs", nullable = false)
    public Tarifs getTarifsByTarifsIdTarifs() {
        return tarifsByTarifsIdTarifs;
    }

    public void setTarifsByTarifsIdTarifs(Tarifs tarifsByTarifsIdTarifs) {
        this.tarifsByTarifsIdTarifs = tarifsByTarifsIdTarifs;
    }

    @ManyToOne
    @JoinColumn(name = "PenalitesIdPenalites", referencedColumnName = "IdPenalites", nullable = false)
    public Penalites getPenalitesByPenalitesIdPenalites() {
        return penalitesByPenalitesIdPenalites;
    }

    public void setPenalitesByPenalitesIdPenalites(Penalites penalitesByPenalitesIdPenalites) {
        this.penalitesByPenalitesIdPenalites = penalitesByPenalitesIdPenalites;
    }
}
