package entities;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Objects;

@Entity
@Table(name = "facture_detail", schema = "bibliotheque")
@NamedQueries
        ({
                @NamedQuery(name = "FactureDetail.findAll", query = "SELECT fd FROM FactureDetail fd"),
        })
public class FactureDetail implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idFactureDetail;
    private Timestamp dateFin;
    private Timestamp dateRetour;
    private String etatRendu;
    private double prix;


    @Id
    @Column(name = "idFactureDetail", nullable = false)

    public int getIdFactureDetail() {
        return idFactureDetail;
    }

    public void setIdFactureDetail(int idFactureDetail) {
        this.idFactureDetail = idFactureDetail;
    }

    @Basic
    @Column(name = "DateFin", nullable = false)
    public Timestamp getDateFin() {
        return dateFin;
    }

    public void setDateFin(Timestamp dateFin) {
        this.dateFin = dateFin;
    }

    @Basic
    @Column(name = "DateRetour", nullable = true)
    public Timestamp getDateRetour() {
        return dateRetour;
    }

    public void setDateRetour(Timestamp dateRetour) {
        this.dateRetour = dateRetour;
    }

    @Basic
    @Column(name = "EtatRendu", nullable = false, length = 500)
    public String getEtatRendu() {
        return etatRendu;
    }

    public void setEtatRendu(String etatRendu) {
        this.etatRendu = etatRendu;
    }

    @Basic
    @Column(name = "Prix", nullable = false, precision = 0)
    public double getPrix() {
        return prix;
    }

    public void setPrix(double prix) {
        this.prix = prix;
    }

    @ManyToOne
    @JoinColumn(name = "FacturesIdFactures",  nullable = false)
    private Facture facture;

    public Facture getFacture() {
        return facture;
    }

    public void setFacture(Facture facture) {
        this.facture = facture;
    }


    @ManyToOne
    @JoinColumn(name = "ExemplairesLivresIdExemplairesLivres", nullable = false)
    private ExemplairesLivres exemplairesLivre;

    public ExemplairesLivres getExemplairesLivre() {
        return exemplairesLivre;
    }

    public void setExemplairesLivre(ExemplairesLivres exemplairesLivre) {
        this.exemplairesLivre = exemplairesLivre;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FactureDetail that = (FactureDetail) o;
        return idFactureDetail == that.idFactureDetail &&
                Double.compare(that.prix, prix) == 0 &&
                Objects.equals(dateFin, that.dateFin) &&
                Objects.equals(dateRetour, that.dateRetour) &&
                Objects.equals(etatRendu, that.etatRendu) &&
                Objects.equals(facture, that.facture) &&
                Objects.equals(exemplairesLivre, that.exemplairesLivre);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idFactureDetail, dateFin, dateRetour, etatRendu, prix, facture, exemplairesLivre);
    }
}
