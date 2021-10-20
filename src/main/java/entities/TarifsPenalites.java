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
                @NamedQuery(name = "TarifsPenalites.findByPenalites", query = "SELECT tp FROM TarifsPenalites tp WHERE tp.dateDebut<=:dateDebut AND tp.dateFin>=:dateFin AND tp.penalite=:penalite AND tp.tarif=:tarif ORDER BY tp.dateDebut DESC"),
        })
public class TarifsPenalites implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "IdTarifsPenalites", nullable = false)
    private int idTarifsPenalites;
    @Basic
    @Column(name = "Prix", nullable = false, precision = 0)
    private double prix;
    @Basic
    @Column(name = "DateDebut", nullable = false)
    private Date dateDebut;
    @Basic
    @Column(name = "DateFin", nullable = false)
    private Date dateFin;
    @ManyToOne
    @JoinColumn(name = "TarifsIdTarifs", nullable = false)
    private Tarifs tarif;
    @ManyToOne
    @JoinColumn(name = "PenalitesIdPenalites", nullable = false)
    private Penalites penalite;

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

    //-------------------------------Getter & Setter--------------------------------------------

    public int getIdTarifsPenalites() {
        return idTarifsPenalites;
    }

    public void setIdTarifsPenalites(int idTarifsPenalites) {
        this.idTarifsPenalites = idTarifsPenalites;
    }

    public Tarifs getTarif() {
        return tarif;
    }

    public void setTarif(Tarifs tarif) {
        this.tarif = tarif;
    }

    public Penalites getPenalite() {
        return penalite;
    }

    public void setPenalite(Penalites penalite) {
        this.penalite = penalite;
    }

    public double getPrix() {
        return prix;
    }

    public void setPrix(double prix) {
        this.prix = prix;
    }

    public Date getDateDebut() {
        return dateDebut;
    }

    public void setDateDebut(Date dateDebut) {
        this.dateDebut = dateDebut;
    }

    public Date getDateFin() {
        return dateFin;
    }

    public void setDateFin(Date dateFin) {
        this.dateFin = dateFin;
    }

}
