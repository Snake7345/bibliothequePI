package entities;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;
import java.util.Objects;

@Entity
@Table(name = "tarifs_jours", schema = "bibliotheque")
@NamedQueries
        ({
                @NamedQuery(name = "TarifsJours.findAll", query = "SELECT tj FROM TarifsJours tj"),
        })
public class TarifsJours implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idTarifsJours;
    private double prix;
    private Date dateDebut;
    private Date dateFin;

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
        TarifsJours that = (TarifsJours) o;
        return  idTarifsJours == that.idTarifsJours &&
                Double.compare(that.prix, prix) == 0 &&
                Objects.equals(dateDebut, that.dateDebut) &&
                Objects.equals(dateFin, that.dateFin);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idTarifsJours, prix, dateDebut, dateFin);
    }

    @ManyToOne
    @JoinColumn(name = "TarifsIdTarifs", nullable = false)
    private Tarifs tarif;
    public Tarifs getTarif() {
        return tarif;
    }

    public void setTarif(Tarifs tarif1) {
        this.tarif = tarif1;
    }

    @ManyToOne
    @JoinColumn(name = "JoursIdJours", nullable = false)
    private Jours jours;
    public Jours getJours() {
        return jours;
    }

    public void setJours(Jours jours1) {
        this.jours = jours1;
    }
}
