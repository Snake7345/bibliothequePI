package entities;

import managedBean.TarifsBean;
import org.apache.log4j.Logger;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Collection;
import java.util.Objects;

@Entity
@Table(name = "tarifs")
@NamedQueries
        ({
                @NamedQuery(name = "Tarifs.findAll", query = "SELECT t FROM Tarifs t"),
        })
public class Tarifs implements Serializable {
    private static final long serialVersionUID = 1L;
    private static final Logger log = Logger.getLogger(Tarifs.class);
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idTarifs;
    private String denomination;
    private Timestamp dateDebut;
    @OneToMany(mappedBy = "tarif")
    private Collection<TarifsJours> tarifsJours;
    @OneToMany(mappedBy = "tarif")
    private Collection<TarifsPenalites> tarifsPenalites;

    @Id
    @Column(name = "IdTarifs", nullable = false)
    public int getIdTarifs() {
        return idTarifs;
    }

    public void setIdTarifs(int idTarifs) {
        this.idTarifs = idTarifs;
    }

    @Basic
    @Column(name = "Denomination", nullable = false, length = 255)
    public String getDenomination() {
        return denomination;
    }

    public void setDenomination(String denomination) {
        this.denomination = denomination;
    }



    @Basic
    @Column(name = "DateDebut", nullable = false)
    public Timestamp getDateDebut() {
        return dateDebut;
    }

    public void setDateDebut(Timestamp dateDebut) {
        this.dateDebut = dateDebut;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Tarifs tarifs = (Tarifs) o;
        return idTarifs == tarifs.idTarifs &&
                Objects.equals(denomination, tarifs.denomination) &&
                Objects.equals(dateDebut, tarifs.dateDebut);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idTarifs, denomination, dateDebut);
    }

    @ManyToOne
    @JoinColumn(name = "BibliothequesIdBibliotheques", nullable = false)
    private Bibliotheques bibliotheques;

    public Bibliotheques getBibliotheques() {
        return bibliotheques;
    }

    public void setBibliotheques(Bibliotheques bibliotheques1) {
        this.bibliotheques = bibliotheques1;
    }


    public Collection<TarifsJours> getTarifsJours() {
        return tarifsJours;
    }

    public void setTarifsJours(Collection<TarifsJours> tarifsJoursByIdTarifs) {
        this.tarifsJours = tarifsJoursByIdTarifs;
    }


    public Collection<TarifsPenalites> getTarifsPenalites() {
        return tarifsPenalites;
    }

    public void setTarifsPenalites(Collection<TarifsPenalites> tarifsPenalitesByIdTarifs) {
        this.tarifsPenalites = tarifsPenalitesByIdTarifs;
    }
}
