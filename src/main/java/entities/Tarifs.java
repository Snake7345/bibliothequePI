package entities;

import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;

@Entity
@Named
@SessionScoped
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
    private Timestamp dateDebut;

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

    public void setDateDebut(Timestamp dateDebut) {
        this.dateDebut = dateDebut;
    }

    public Timestamp getDateDebut() {
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
