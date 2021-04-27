package entities;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Objects;

@Entity
@Table(name = "tarifs_penalites", schema = "bibliotheque")
public class TarifsPenalites implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idTarifsPenalites;

    private double prix;
    private Timestamp dateDebut;
    private Timestamp dateFin;

    @Id
    @Column(name = "IdTarifsPenalites", nullable = false)
    public int getIdTarifsPenalites() {
        return idTarifsPenalites;
    }

    public void setIdTarifsPenalites(int idTarifsPenalites) {
        this.idTarifsPenalites = idTarifsPenalites;
    }

    @ManyToOne
    @JoinColumn(name = "TarifsIdTarifs", nullable = false)
    private Tarifs tarif;

    public Tarifs getTarif() {
        return tarif;
    }

    public void setTarif(Tarifs tarif) {
        this.tarif = tarif;
    }

    @ManyToOne
    @JoinColumn(name = "PenalitesIdPenalites", nullable = false)
    private Penalites penalite;

    public Penalites getPenalite() {
        return penalite;
    }

    public void setPenalite(Penalites penalite) {
        this.penalite = penalite;
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
        return idTarifsPenalites == that.idTarifsPenalites &&
                Double.compare(that.prix, prix) == 0 &&
                Objects.equals(dateDebut, that.dateDebut) &&
                Objects.equals(dateFin, that.dateFin) &&
                Objects.equals(tarif, that.tarif) &&
                Objects.equals(penalite, that.penalite);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idTarifsPenalites, prix, dateDebut, dateFin, tarif, penalite);
    }
}
