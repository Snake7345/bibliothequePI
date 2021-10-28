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
                @NamedQuery(name = "Facture.findAll", query = "SELECT f FROM Factures f"),
                @NamedQuery(name = "Facture.findAllByEtat", query = "SELECT f FROM Factures f WHERE f.etat=:etat"),
                @NamedQuery(name=  "Facture.findLastId", query="SELECT f FROM Factures f ORDER BY f.idFactures DESC"),
                @NamedQuery(name = "Facture.findByBiblio", query = "SELECT f FROM Factures f WHERE f.bibliotheques.idBibliotheques=:bibliotheque"),
        })
public class Factures implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "IdFactures", nullable = false)
    private int idFactures;
    @Basic
    @Column(name = "DateDebut", nullable = false)
    private Timestamp dateDebut;
    @Basic
    @Column(name = "PrixTVAC", nullable = true, precision = 0)
    private Double prixTvac;
    @Basic
    @Column(name = "NumeroFacture", nullable = true, length = 45)
    private String numeroFacture;
    @Basic
    @Column(name = "LienPdf", nullable = false, length = 255)
    private String lienPdf;
    @OneToMany(mappedBy = "factures")
    private Collection<FacturesDetail> facturesDetails;
    @ManyToOne
    @JoinColumn(name = "UtilisateursIdUtilisateurs", nullable = false)
    private Utilisateurs utilisateurs;
    @ManyToOne
    @JoinColumn(name = "Bibliotheques_IdBibliotheques", nullable = false)
    private Bibliotheques bibliotheques;
    @Enumerated(EnumType.STRING)
    @Column(nullable = false,columnDefinition = "varchar(2) default 'FR'" ,name = "etat")
    private FactureEtatEnum etat;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Factures factures = (Factures) o;
        return idFactures == factures.idFactures && dateDebut.equals(factures.dateDebut) && Objects.equals(prixTvac, factures.prixTvac) && Objects.equals(numeroFacture, factures.numeroFacture) && lienPdf.equals(factures.lienPdf) && facturesDetails.equals(factures.facturesDetails) && utilisateurs.equals(factures.utilisateurs) && etat == factures.etat;
    }

    @Override
    public int hashCode() {
        return Objects.hash(idFactures, dateDebut, prixTvac, numeroFacture, lienPdf, facturesDetails, utilisateurs, etat);
    }

    //-------------------------------Getter & Setter--------------------------------------------

    public int getIdFactures() {
        return idFactures;
    }

    public void setIdFactures(int idFactures) {
        this.idFactures = idFactures;
    }

    public Timestamp getDateDebut() {
        return dateDebut;
    }

    public void setDateDebut(Timestamp dateDebut) {
        this.dateDebut = dateDebut;
    }

    public Double getPrixTvac() {
        return prixTvac;
    }

    public void setPrixTvac(Double prixTvac) {
        this.prixTvac = prixTvac;
    }

    public String getNumeroFacture() {
        return numeroFacture;
    }

    public void setNumeroFacture(String numeroFacture) {
        this.numeroFacture = numeroFacture;
    }

    public String getLienPdf() {
        return lienPdf;
    }

    public void setLienPdf(String lienPdf) {
        this.lienPdf = lienPdf;
    }

    public FactureEtatEnum getEtat() {
        return etat;
    }

    public void setEtat(FactureEtatEnum etat) {
        this.etat = etat;
    }

    public Collection<FacturesDetail> getFactureDetails() {
        return facturesDetails;
    }

    public void setFactureDetails(Collection<FacturesDetail> facturesDetails1) {
        this.facturesDetails = facturesDetails1;
    }

    public Bibliotheques getBibliotheques() {
        return bibliotheques;
    }

    public void setBibliotheques(Bibliotheques bibliotheques) {
        this.bibliotheques = bibliotheques;
    }

    public Utilisateurs getUtilisateurs() {
        return utilisateurs;
    }
    public void setUtilisateurs(Utilisateurs utilisateurs1) {
        this.utilisateurs = utilisateurs1;
    }




}
