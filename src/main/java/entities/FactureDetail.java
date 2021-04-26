package entities;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Objects;
@Entity
@Table(name = "facture_detail", schema = "bibliotheque")
@NamedQueries
        ({
                @NamedQuery(name = "FactureDetail.findAll", query = "SELECT fd FROM FactureDetail fd"),
        })

public class FactureDetail {










    @Basic
    @Column(name = "LocationsIdLocations", nullable = false)
    private int locationsIdLocations;
    public int getLocationsIdLocations() {
        return locationsIdLocations;
    }

    public void setLocationsIdLocations(int locationsIdLocations) {
        this.locationsIdLocations = locationsIdLocations;
    }

    @Basic
    @Column(name = "ExemplairesLivresIdExemplairesLivres", nullable = false)
    private int exemplairesLivresIdExemplairesLivres;
    public int getExemplairesLivresIdExemplairesLivres() {
        return exemplairesLivresIdExemplairesLivres;
    }

    public void setExemplairesLivresIdExemplairesLivres(int exemplairesLivresIdExemplairesLivres) {
        this.exemplairesLivresIdExemplairesLivres = exemplairesLivresIdExemplairesLivres;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "IdLocationsHistorique", nullable = false)
    private int idLocationsHistorique;
    public int getIdLocationsHistorique() {
        return idLocationsHistorique;
    }

    public void setIdLocationsHistorique(int idLocationsHistorique) {
        this.idLocationsHistorique = idLocationsHistorique;
    }

    @Basic
    @Column(name = "DateFin", nullable = false)
    private Timestamp dateFin;
    public Timestamp getDateFin() {
        return dateFin;
    }

    public void setDateFin(Timestamp dateFin) {
        this.dateFin = dateFin;
    }

    @Basic
    @Column(name = "DateRetour", nullable = true)
    private Timestamp dateRetour;
    public Timestamp getDateRetour() {
        return dateRetour;
    }

    public void setDateRetour(Timestamp dateRetour) {
        this.dateRetour = dateRetour;
    }

    @Basic
    @Column(name = "EtatRendu", nullable = false, length = 500)
    private String etatRendu;
    public String getEtatRendu() {
        return etatRendu;
    }

    public void setEtatRendu(String etatRendu) {
        this.etatRendu = etatRendu;
    }

    @Basic
    @Column(name = "Prix", nullable = false, precision = 0)
    private double prix;
    public double getPrix() {
        return prix;
    }

    public void setPrix(double prix) {
        this.prix = prix;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FactureDetail that = (FactureDetail) o;
        return locationsIdLocations == that.locationsIdLocations &&
                exemplairesLivresIdExemplairesLivres == that.exemplairesLivresIdExemplairesLivres &&
                idLocationsHistorique == that.idLocationsHistorique &&
                Double.compare(that.prix, prix) == 0 &&
                Objects.equals(dateFin, that.dateFin) &&
                Objects.equals(dateRetour, that.dateRetour) &&
                Objects.equals(etatRendu, that.etatRendu);
    }

    @Override
    public int hashCode() {
        return Objects.hash(locationsIdLocations, exemplairesLivresIdExemplairesLivres, idLocationsHistorique, dateFin, dateRetour, etatRendu, prix);
    }

    @ManyToOne
    @JoinColumn(name = "LocationsIdLocations", referencedColumnName = "IdLocations", nullable = false)
    private Facture factureByLocationsIdLocations;
    public Facture getFactureByLocationsIdLocations() {
        return factureByLocationsIdLocations;
    }

    public void setFactureByLocationsIdLocations(Facture factureByLocationsIdLocations) {
        this.factureByLocationsIdLocations = factureByLocationsIdLocations;
    }

    @ManyToOne
    @JoinColumn(name = "ExemplairesLivresIdExemplairesLivres", referencedColumnName = "IdExemplairesLivres", nullable = false)
    private ExemplairesLivres exemplairesLivresByExemplairesLivresIdExemplairesLivres;
    public ExemplairesLivres getExemplairesLivresByExemplairesLivresIdExemplairesLivres() {
        return exemplairesLivresByExemplairesLivresIdExemplairesLivres;
    }

    public void setExemplairesLivresByExemplairesLivresIdExemplairesLivres(ExemplairesLivres exemplairesLivresByExemplairesLivresIdExemplairesLivres) {
        this.exemplairesLivresByExemplairesLivresIdExemplairesLivres = exemplairesLivresByExemplairesLivresIdExemplairesLivres;
    }
}
