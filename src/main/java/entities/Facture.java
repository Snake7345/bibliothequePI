package entities;

import enumeration.FactureEtatEnum;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Collection;
import java.util.Objects;

@Entity
@Table(name="facture")
@NamedQueries
        ({
                @NamedQuery(name = "Facture.findAll", query = "SELECT f FROM Facture f"),
                @NamedQuery(name = "Facture.findAllByEtat", query = "SELECT f FROM Facture f WHERE f.etat=:etat"),
        })
public class Facture implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idFactures;
    private Timestamp dateDebut;
    private Double prixTvac;
    private String numeroFacture;
    private FactureEtatEnum etat;
    private Collection<FactureDetail> factureDetails;

    @Id
    @Column(name = "IdFactures", nullable = false)
    public int getIdFactures() {
        return idFactures;
    }

    public void setIdFactures(int idFactures) {
        this.idFactures = idFactures;
    }

    @Basic
    @Column(name = "DateDebut", nullable = false)
    public Timestamp getDateDebut() {
        return dateDebut;
    }

    public void setDateDebut(Timestamp dateDebut) {
        this.dateDebut = dateDebut;
    }

    @Basic
    @Column(name = "PrixTVAC", nullable = true, precision = 0)
    public Double getPrixTvac() {
        return prixTvac;
    }

    public void setPrixTvac(Double prixTvac) {
        this.prixTvac = prixTvac;
    }

    @Basic
    @Column(name = "NumeroFacture", nullable = true, length = 45)
    public String getNumeroFacture() {
        return numeroFacture;
    }

    public void setNumeroFacture(String numeroFacture) {
        this.numeroFacture = numeroFacture;
    }


    @Enumerated(EnumType.STRING)
    @Column(nullable = false, columnDefinition = "varchar(2) default 'FR'", name = "etat")

    public FactureEtatEnum getEtat() {
        return etat;
    }

    public void setEtat(FactureEtatEnum etat) {
        this.etat = etat;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Facture facture = (Facture) o;
        return idFactures == facture.idFactures &&
                Objects.equals(dateDebut, facture.dateDebut) &&
                Objects.equals(prixTvac, facture.prixTvac) &&
                Objects.equals(numeroFacture, facture.numeroFacture) &&
                etat == facture.etat &&
                Objects.equals(utilisateurs, facture.utilisateurs) &&
                Objects.equals(factureDetails, facture.factureDetails);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idFactures, dateDebut, prixTvac, numeroFacture, etat, utilisateurs, factureDetails);
    }

    @ManyToOne
    @JoinColumn(name = "UtilisateursIdUtilisateurs", referencedColumnName = "IdUtilisateurs", nullable = false)
    private Utilisateurs utilisateurs;
    public Utilisateurs getUtilisateurs() {
        return utilisateurs;
    }
    public void setUtilisateurs(Utilisateurs utilisateurs1) {
        this.utilisateurs = utilisateurs1;
    }

    @OneToMany(mappedBy = "factureByLocationsIdLocations")

    public Collection<FactureDetail> getFactureDetails() {
        return factureDetails;
    }

    public void setFactureDetails(Collection<FactureDetail> factureDetails1) {
        this.factureDetails = factureDetails1;
    }
}
