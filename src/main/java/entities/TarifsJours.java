package entities;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Objects;

@Entity
@Table(name = "tarifs_jours", schema = "bibliotheque", catalog = "")
public class TarifsJours {
    private int tarifsIdTarifs;
    private int joursIdJours;
    private int idTarifsJours;
    private double prix;
    private Timestamp dateDebut;
    private Timestamp dateFin;
    private Tarifs tarifsByTarifsIdTarifs;
    private Jours joursByJoursIdJours;

    @Basic
    @Column(name = "TarifsIdTarifs", nullable = false)
    public int getTarifsIdTarifs() {
        return tarifsIdTarifs;
    }

    public void setTarifsIdTarifs(int tarifsIdTarifs) {
        this.tarifsIdTarifs = tarifsIdTarifs;
    }

    @Basic
    @Column(name = "JoursIdJours", nullable = false)
    public int getJoursIdJours() {
        return joursIdJours;
    }

    public void setJoursIdJours(int joursIdJours) {
        this.joursIdJours = joursIdJours;
    }

    @Id
    @Column(name = "IdTarifsJours", nullable = false)
    public int getIdTarifsJours() {
        return idTarifsJours;
    }

    public void setIdTarifsJours(int idTarifsJours) {
        this.idTarifsJours = idTarifsJours;
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
        TarifsJours that = (TarifsJours) o;
        return tarifsIdTarifs == that.tarifsIdTarifs &&
                joursIdJours == that.joursIdJours &&
                idTarifsJours == that.idTarifsJours &&
                Double.compare(that.prix, prix) == 0 &&
                Objects.equals(dateDebut, that.dateDebut) &&
                Objects.equals(dateFin, that.dateFin);
    }

    @Override
    public int hashCode() {
        return Objects.hash(tarifsIdTarifs, joursIdJours, idTarifsJours, prix, dateDebut, dateFin);
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
    @JoinColumn(name = "JoursIdJours", referencedColumnName = "IdJours", nullable = false)
    public Jours getJoursByJoursIdJours() {
        return joursByJoursIdJours;
    }

    public void setJoursByJoursIdJours(Jours joursByJoursIdJours) {
        this.joursByJoursIdJours = joursByJoursIdJours;
    }
}
