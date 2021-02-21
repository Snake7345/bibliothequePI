package entities;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Collection;
import java.util.Objects;

@Entity
public class Facture {
    private int idLocations;
    private Timestamp dateDebut;
    private Double prixTvac;
    private String numeroFacture;
    private Object etat;
    private int utilisateursIdUtilisateurs;
    private Utilisateurs utilisateursByUtilisateursIdUtilisateurs;
    private Collection<FactureDetail> factureDetailsByIdLocations;

    @Id
    @Column(name = "IdLocations")
    public int getIdLocations() {
        return idLocations;
    }

    public void setIdLocations(int idLocations) {
        this.idLocations = idLocations;
    }

    @Basic
    @Column(name = "DateDebut")
    public Timestamp getDateDebut() {
        return dateDebut;
    }

    public void setDateDebut(Timestamp dateDebut) {
        this.dateDebut = dateDebut;
    }

    @Basic
    @Column(name = "PrixTVAC")
    public Double getPrixTvac() {
        return prixTvac;
    }

    public void setPrixTvac(Double prixTvac) {
        this.prixTvac = prixTvac;
    }

    @Basic
    @Column(name = "NumeroFacture")
    public String getNumeroFacture() {
        return numeroFacture;
    }

    public void setNumeroFacture(String numeroFacture) {
        this.numeroFacture = numeroFacture;
    }

    @Basic
    @Column(name = "Etat")
    public Object getEtat() {
        return etat;
    }

    public void setEtat(Object etat) {
        this.etat = etat;
    }

    @Basic
    @Column(name = "UtilisateursIdUtilisateurs")
    public int getUtilisateursIdUtilisateurs() {
        return utilisateursIdUtilisateurs;
    }

    public void setUtilisateursIdUtilisateurs(int utilisateursIdUtilisateurs) {
        this.utilisateursIdUtilisateurs = utilisateursIdUtilisateurs;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Facture facture = (Facture) o;
        return idLocations == facture.idLocations &&
                utilisateursIdUtilisateurs == facture.utilisateursIdUtilisateurs &&
                Objects.equals(dateDebut, facture.dateDebut) &&
                Objects.equals(prixTvac, facture.prixTvac) &&
                Objects.equals(numeroFacture, facture.numeroFacture) &&
                Objects.equals(etat, facture.etat);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idLocations, dateDebut, prixTvac, numeroFacture, etat, utilisateursIdUtilisateurs);
    }

    @ManyToOne
    @JoinColumn(name = "UtilisateursIdUtilisateurs", referencedColumnName = "IdUtilisateurs", nullable = false)
    public Utilisateurs getUtilisateursByUtilisateursIdUtilisateurs() {
        return utilisateursByUtilisateursIdUtilisateurs;
    }

    public void setUtilisateursByUtilisateursIdUtilisateurs(Utilisateurs utilisateursByUtilisateursIdUtilisateurs) {
        this.utilisateursByUtilisateursIdUtilisateurs = utilisateursByUtilisateursIdUtilisateurs;
    }

    @OneToMany(mappedBy = "factureByLocationsIdLocations")
    public Collection<FactureDetail> getFactureDetailsByIdLocations() {
        return factureDetailsByIdLocations;
    }

    public void setFactureDetailsByIdLocations(Collection<FactureDetail> factureDetailsByIdLocations) {
        this.factureDetailsByIdLocations = factureDetailsByIdLocations;
    }
}
