package entities;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Objects;

@Entity
@Table(name = "factures_detail", schema = "bibliotheque")
@NamedQueries
        ({
                @NamedQuery(name = "FacturesDetail.findAll", query = "SELECT fd FROM FacturesDetail fd"),
        })
public class FacturesDetail implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idFactureDetail", nullable = false)
    private int idFactureDetail;
    @Basic
    @Column(name = "DateFin", nullable = false)
    private Timestamp dateFin;
    @Basic
    @Column(name = "DateRetour", nullable = true)
    private Timestamp dateRetour;
    @Basic
    @Column(name = "EtatRendu", nullable = true, length = 500)
    private String etatRendu;
    @Basic
    @Column(name = "Prix", nullable = false, precision = 0)
    private double prix;
    @ManyToOne
    @JoinColumn(name = "ExemplairesLivresIdEL", nullable = false)
    private ExemplairesLivres exemplairesLivre;
    @ManyToOne
    @JoinColumn(name = "FacturesIdFactures",  nullable = false)
    private Factures factures;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FacturesDetail that = (FacturesDetail) o;
        return idFactureDetail == that.idFactureDetail &&
                Double.compare(that.prix, prix) == 0 &&
                Objects.equals(dateFin, that.dateFin) &&
                Objects.equals(dateRetour, that.dateRetour) &&
                Objects.equals(etatRendu, that.etatRendu) &&
                Objects.equals(exemplairesLivre, that.exemplairesLivre);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idFactureDetail, dateFin, dateRetour, etatRendu, prix, exemplairesLivre);
    }

    //-------------------------------Getter & Setter--------------------------------------------

    public int getIdFactureDetail() {
        return idFactureDetail;
    }

    public void setIdFactureDetail(int idFactureDetail) {
        this.idFactureDetail = idFactureDetail;
    }

    public Timestamp getDateFin() {
        return dateFin;
    }

    public void setDateFin(Timestamp dateFin) {
        this.dateFin = dateFin;
    }

    public Timestamp getDateRetour() {
        return dateRetour;
    }

    public void setDateRetour(Timestamp dateRetour) {
        this.dateRetour = dateRetour;
    }

    public String getEtatRendu() {
        return etatRendu;
    }

    public void setEtatRendu(String etatRendu) {
        this.etatRendu = etatRendu;
    }

    public double getPrix() {
        return prix;
    }

    public void setPrix(double prix) {
        this.prix = prix;
    }

    public Factures getFacture() {
        return factures;
    }

    public void setFacture(Factures factures) {
        this.factures = factures;
    }

    public ExemplairesLivres getExemplairesLivre() {
        return exemplairesLivre;
    }

    public void setExemplairesLivre(ExemplairesLivres exemplairesLivre) {
        this.exemplairesLivre = exemplairesLivre;
    }

}
