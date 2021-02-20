package entities;

import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;

@Entity
@Named
@SessionScoped
public class TarifsPenalites implements Serializable {

    private static final long serialVersionUID = 1L;

    @Column(name = "TarifsIdTarifs", nullable = false)
    private Integer tarifsIdTarifs;

    @Column(name = "PenalitesIdPenalites", nullable = false)
    private Integer penalitesIdPenalites;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "IdTarifsPenalites", nullable = false)
    private Integer idTarifsPenalites;

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

    public void setPenalitesIdPenalites(Integer penalitesIdPenalites) {
        this.penalitesIdPenalites = penalitesIdPenalites;
    }

    public Integer getPenalitesIdPenalites() {
        return penalitesIdPenalites;
    }

    public void setIdTarifsPenalites(Integer idTarifsPenalites) {
        this.idTarifsPenalites = idTarifsPenalites;
    }

    public Integer getIdTarifsPenalites() {
        return idTarifsPenalites;
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
        return "TarifsPenalites{" +
                "tarifsIdTarifs=" + tarifsIdTarifs + '\'' +
                "penalitesIdPenalites=" + penalitesIdPenalites + '\'' +
                "idTarifsPenalites=" + idTarifsPenalites + '\'' +
                "prix=" + prix + '\'' +
                "dateDebut=" + dateDebut + '\'' +
                "dateFin=" + dateFin + '\'' +
                '}';
    }
}
