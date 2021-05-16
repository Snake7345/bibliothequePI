package entities;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Objects;

@Entity
@Table(name = "factures_detail", schema = "bibliotheque")
public class FacturesDetail {
    private int idFactureDetail;
    private Timestamp dateFin;
    private Timestamp dateRetour;
    private String etatRendu;
    private double prix;

    @Id
    @Column(name = "IdFactureDetail", nullable = false)
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


}
