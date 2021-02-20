package entities;

import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;

@Entity
@Named
@SessionScoped
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
    private Timestamp dateDebut;

    @Column(name = "DateFin", nullable = false)
    private Timestamp dateFin;

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

    public void setDateDebut(Timestamp dateDebut) {
        this.dateDebut = dateDebut;
    }

    public Timestamp getDateDebut() {
        return dateDebut;
    }

    public void setDateFin(Timestamp dateFin) {
        this.dateFin = dateFin;
    }

    public Timestamp getDateFin() {
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
