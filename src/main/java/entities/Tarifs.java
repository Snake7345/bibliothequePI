package entities;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "tarifs")
public class Tarifs implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "IdTarifs", nullable = false)
    private Integer idTarifs;

    @Column(name = "Denomination", nullable = false)
    private String denomination;

    @Column(name = "BibliothequesIdBibliotheques", nullable = false)
    private Integer bibliothequesIdBibliotheques;

    @Column(name = "DateDebut", nullable = false)
    private Date dateDebut;

    public void setIdTarifs(Integer idTarifs) {
        this.idTarifs = idTarifs;
    }

    public Integer getIdTarifs() {
        return idTarifs;
    }

    public void setDenomination(String denomination) {
        this.denomination = denomination;
    }

    public String getDenomination() {
        return denomination;
    }

    public void setBibliothequesIdBibliotheques(Integer bibliothequesIdBibliotheques) {
        this.bibliothequesIdBibliotheques = bibliothequesIdBibliotheques;
    }

    public Integer getBibliothequesIdBibliotheques() {
        return bibliothequesIdBibliotheques;
    }

    public void setDateDebut(Date dateDebut) {
        this.dateDebut = dateDebut;
    }

    public Date getDateDebut() {
        return dateDebut;
    }

    @Override
    public String toString() {
        return "Tarifs{" +
                "idTarifs=" + idTarifs + '\'' +
                "denomination=" + denomination + '\'' +
                "bibliothequesIdBibliotheques=" + bibliothequesIdBibliotheques + '\'' +
                "dateDebut=" + dateDebut + '\'' +
                '}';
    }
}
