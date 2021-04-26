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




    @Basic
    @Column(name = "TarifsIdTarifs", nullable = false)
    private int tarifsIdTarifs;
    public int getTarifsIdTarifs() {
        return tarifsIdTarifs;
    }

    public void setTarifsIdTarifs(int tarifsIdTarifs) {
        this.tarifsIdTarifs = tarifsIdTarifs;
    }

    @Basic
    @Column(name = "PenalitesIdPenalites", nullable = false)
    private int penalitesIdPenalites;
    public int getPenalitesIdPenalites() {
        return penalitesIdPenalites;
    }

    public void setPenalitesIdPenalites(int penalitesIdPenalites) {
        this.penalitesIdPenalites = penalitesIdPenalites;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "IdTarifsPenalites", nullable = false)
    private int idTarifsPenalites;
    public int getIdTarifsPenalites() {
        return idTarifsPenalites;
    }

    public void setIdTarifsPenalites(int idTarifsPenalites) {
        this.idTarifsPenalites = idTarifsPenalites;
    }

    @Basic
    @Column(name = "Prix", nullable = false, precision = 0)
    private double prix;
    public double getPrix() {
        return prix;
    }

    public void setPrix(double prix) {
        this.prix = prix;
    }

    @Basic
    @Column(name = "DateDebut", nullable = false)
    private Timestamp dateDebut;
    public Timestamp getDateDebut() {
        return dateDebut;
    }

    public void setDateDebut(Timestamp dateDebut) {
        this.dateDebut = dateDebut;
    }

    @Basic
    @Column(name = "DateFin", nullable = false)
    private Timestamp dateFin;
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
    private Tarifs tarifsByTarifsIdTarifs;
    public Tarifs getTarifsByTarifsIdTarifs() {
        return tarifsByTarifsIdTarifs;
    }

    public void setTarifsByTarifsIdTarifs(Tarifs tarifsByTarifsIdTarifs) {
        this.tarifsByTarifsIdTarifs = tarifsByTarifsIdTarifs;
    }

    @ManyToOne
    @JoinColumn(name = "PenalitesIdPenalites", referencedColumnName = "IdPenalites", nullable = false)
    private Penalites penalitesByPenalitesIdPenalites;
    public Penalites getPenalitesByPenalitesIdPenalites() {
        return penalitesByPenalitesIdPenalites;
    }

    public void setPenalitesByPenalitesIdPenalites(Penalites penalitesByPenalitesIdPenalites) {
        this.penalitesByPenalitesIdPenalites = penalitesByPenalitesIdPenalites;
    }
}
