package entities;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "tarifs_jours")
public class TarifsJours implements Serializable {

    private static final long serialVersionUID = 1L;

    @Column(name = "TarifsIdTarifs", nullable = false)
    private Integer tarifsIdTarifs;

    @Column(name = "JoursIdJours", nullable = false)
    private Integer joursIdJours;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "IdTarifsJours", nullable = false)
    private Integer idTarifsJours;

    @Column(name = "Prix", nullable = false)
    private Double prix;

    @Column(name = "DateDebut", nullable = false)
    private Date dateDebut;

    @Column(name = "DateFin", nullable = false)
    private Date dateFin;

    public void setTarifsIdTarifs(Integer tarifsIdTarifs) {
        this.tarifsIdTarifs = tarifsIdTarifs;
    }

    public Integer getTarifsIdTarifs() {
        return tarifsIdTarifs;
    }

    public void setJoursIdJours(Integer joursIdJours) {
        this.joursIdJours = joursIdJours;
    }

    public Integer getJoursIdJours() {
        return joursIdJours;
    }

    public void setIdTarifsJours(Integer idTarifsJours) {
        this.idTarifsJours = idTarifsJours;
    }

    public Integer getIdTarifsJours() {
        return idTarifsJours;
    }

    public void setPrix(Double prix) {
        this.prix = prix;
    }

    public Double getPrix() {
        return prix;
    }

    public void setDateDebut(Date dateDebut) {
        this.dateDebut = dateDebut;
    }

    public Date getDateDebut() {
        return dateDebut;
    }

    public void setDateFin(Date dateFin) {
        this.dateFin = dateFin;
    }

    public Date getDateFin() {
        return dateFin;
    }

    @Override
    public String toString() {
        return "TarifsJours{" +
                "tarifsIdTarifs=" + tarifsIdTarifs + '\'' +
                "joursIdJours=" + joursIdJours + '\'' +
                "idTarifsJours=" + idTarifsJours + '\'' +
                "prix=" + prix + '\'' +
                "dateDebut=" + dateDebut + '\'' +
                "dateFin=" + dateFin + '\'' +
                '}';
    }
}
