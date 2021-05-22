package entities;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

@Entity
@Table(name = "tarifs_penalites", schema = "bibliotheque")
@NamedQueries
        ({
                @NamedQuery(name = "TarifsPenalites.findAll", query = "SELECT tp FROM TarifsPenalites tp"),
                // Trouver la date début la plus proche qui est déja passé, un join a pena car j'ai ma dénomination dedans
                @NamedQuery(name = "TarifsPenalites.findByPenalites", query = "SELECT tp FROM TarifsPenalites tp WHERE tp.dateDebut<=:dateDebut AND tp.dateFin>=:dateFin AND tp.penalite IN (SELECT p FROM Penalites p where p.denomination=:denominationPena)  AND tp.tarif IN (SELECT t FROM Tarifs t where t.denomination=:denominationTarif) ORDER BY tp.dateDebut DESC"),
        })
public class TarifsPenalites implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idTarifsPenalites;
    private double prix;
    private Date dateDebut;
    private Date dateFin;

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
    public Date getDateDebut() {
        return dateDebut;
    }

    public void setDateDebut(Date dateDebut) {
        this.dateDebut = dateDebut;
    }


    @Basic
    @Column(name = "DateFin", nullable = false)

    public Date getDateFin() {
        return dateFin;
    }

    public void setDateFin(Date dateFin) {
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
