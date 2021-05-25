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
                @NamedQuery(name="Facture.findLastId", query="SELECT f FROM Factures f ORDER BY f.idFactures DESC")
        })
public class Factures implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idFactures;
    private Timestamp dateDebut;
    private Double prixTvac;
    private String numeroFacture;
    @OneToMany(mappedBy = "factures")
    private Collection<FacturesDetail> facturesDetails;
    private String lienPdf;

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

    @Basic
    @Column(name = "LienPdf", nullable = false, length = 255)
    public String getLienPdf() {
        return lienPdf;
    }

    public void setLienPdf(String lienPdf) {
        this.lienPdf = lienPdf;
    }


    @Enumerated(EnumType.STRING)
    @Column(nullable = false,columnDefinition = "varchar(2) default 'FR'" ,name = "etat")
    private FactureEtatEnum etat;
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
                Objects.equals(facturesDetails, factures.facturesDetails);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idFactures, dateDebut, prixTvac, numeroFacture, etat, utilisateurs, facturesDetails);
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
