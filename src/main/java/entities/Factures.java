package entities;

import enumeration.FactureEtatEnum;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Collection;
import java.util.Objects;

@Entity
@Table(name="factures")
@NamedQueries
        ({
                @NamedQuery(name = "Factures.findAll", query = "SELECT f FROM Factures f"),
                @NamedQuery(name = "Factures.findAllByEtat", query = "SELECT f FROM Factures f WHERE f.etat=:etat"),
        })
public class Factures implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idFactures;
    private Timestamp dateDebut;
    private Double prixTvac;
    private String numeroFacture;
    private FactureEtatEnum etat;
    @OneToMany(mappedBy = "factures")
    private Collection<FacturesDetail> factureDetails;

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

    public Collection<FacturesDetail> getFactureDetails() {
        return factureDetails;
    }

    public void setFactureDetails(Collection<FacturesDetail> factureDetails1) {
        this.factureDetails = factureDetails1;
    }
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Factures factures = (Factures) o;
        return idFactures == factures.idFactures &&
                Objects.equals(dateDebut, factures.dateDebut) &&
                Objects.equals(prixTvac, factures.prixTvac) &&
                Objects.equals(numeroFacture, factures.numeroFacture) &&
                etat == factures.etat &&
                Objects.equals(utilisateurs, factures.utilisateurs) &&
                Objects.equals(factureDetails, factures.factureDetails);
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




}
