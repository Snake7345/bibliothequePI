package entities;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

@Entity
@Table(name = "tarifs_jours", schema = "bibliotheque")
@NamedQueries
        ({
                @NamedQuery(name = "TarifsJours.findAll", query = "SELECT tj FROM TarifsJours tj"),
                // Trouver la date début la plus proche qui est déja passé, un join a jour car j'ai mon nombre de jour dedans
                @NamedQuery(name = "TarifsJours.findByJours", query = "SELECT tj FROM TarifsJours tj WHERE tj.dateDebut<=:dateDebut AND tj.dateFin>=:dateFin AND tj.jours.nbrJour<=:jour AND tj.tarif=:tarif ORDER BY tj.dateDebut DESC, tj.jours.nbrJour DESC "),
        })
public class TarifsJours implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "IdTarifsJours", nullable = false)
    private int idTarifsJours;
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
    @JoinColumn(name = "JoursIdJours", nullable = false)
    private Jours jours;

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

    //-------------------------------Getter & Setter--------------------------------------------

    public int getIdTarifsJours() {
        return idTarifsJours;
    }

    public void setIdTarifsJours(int idTarifsJours) {
        this.idTarifsJours = idTarifsJours;
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

    public Tarifs getTarif() {
        return tarif;
    }

    public void setTarif(Tarifs tarif1) {
        this.tarif = tarif1;
    }

    public Jours getJours() {
        return jours;
    }

    public void setJours(Jours jours1) {
        this.jours = jours1;
    }
}
