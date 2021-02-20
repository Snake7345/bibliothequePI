package entities;

import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;

@Entity
@Named
@SessionScoped
public class FactureDetail implements Serializable {

    private static final long serialVersionUID = 1L;

    @Column(name = "LocationsIdLocations", nullable = false)
    private Integer locationsIdLocations;

    @Column(name = "ExemplairesLivresIdExemplairesLivres", nullable = false)
    private Integer exemplairesLivresIdExemplairesLivres;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "IdLocationsHistorique", nullable = false)
    private Integer idLocationsHistorique;

    @Column(name = "DateFin", nullable = false)
    private Timestamp dateFin;

    @Column(name = "DateRetour")
    private Timestamp dateRetour;

    @Column(name = "EtatRendu", nullable = false)
    private String etatRendu;

    @Column(name = "Prix", nullable = false)
    private Double prix;

    public void setLocationsIdLocations(Integer locationsIdLocations) {
        this.locationsIdLocations = locationsIdLocations;
    }

    public Integer getLocationsIdLocations() {
        return locationsIdLocations;
    }

    public void setExemplairesLivresIdExemplairesLivres(Integer exemplairesLivresIdExemplairesLivres) {
        this.exemplairesLivresIdExemplairesLivres = exemplairesLivresIdExemplairesLivres;
    }

    public Integer getExemplairesLivresIdExemplairesLivres() {
        return exemplairesLivresIdExemplairesLivres;
    }

    public void setIdLocationsHistorique(Integer idLocationsHistorique) {
        this.idLocationsHistorique = idLocationsHistorique;
    }

    public Integer getIdLocationsHistorique() {
        return idLocationsHistorique;
    }

    public void setDateFin(Timestamp dateFin) {
        this.dateFin = dateFin;
    }

    public Timestamp getDateFin() {
        return dateFin;
    }

    public void setDateRetour(Timestamp dateRetour) {
        this.dateRetour = dateRetour;
    }

    public Timestamp getDateRetour() {
        return dateRetour;
    }

    public void setEtatRendu(String etatRendu) {
        this.etatRendu = etatRendu;
    }

    public String getEtatRendu() {
        return etatRendu;
    }

    public void setPrix(Double prix) {
        this.prix = prix;
    }

    public Double getPrix() {
        return prix;
    }

    @Override
    public String toString() {
        return "FactureDetail{" +
                "locationsIdLocations=" + locationsIdLocations + '\'' +
                "exemplairesLivresIdExemplairesLivres=" + exemplairesLivresIdExemplairesLivres + '\'' +
                "idLocationsHistorique=" + idLocationsHistorique + '\'' +
                "dateFin=" + dateFin + '\'' +
                "dateRetour=" + dateRetour + '\'' +
                "etatRendu=" + etatRendu + '\'' +
                "prix=" + prix + '\'' +
                '}';
    }
}
