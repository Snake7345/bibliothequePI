package entities;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Collection;
import java.util.Objects;

@Entity
@Table(name = "tarifs")
@NamedQueries
        ({
                @NamedQuery(name = "Tarifs.findAll", query = "SELECT t FROM Tarifs t"),
        })
public class Tarifs {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idTarifs;
    private String denomination;
    private int bibliothequesIdBibliotheques;
    private Timestamp dateDebut;
    private Bibliotheques bibliothequesByBibliothequesIdBibliotheques;
    private Collection<TarifsJours> tarifsJoursByIdTarifs;
    private Collection<TarifsPenalites> tarifsPenalitesByIdTarifs;

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
    @Column(name = "BibliothequesIdBibliotheques", nullable = false)
    public int getBibliothequesIdBibliotheques() {
        return bibliothequesIdBibliotheques;
    }

    public void setBibliothequesIdBibliotheques(int bibliothequesIdBibliotheques) {
        this.bibliothequesIdBibliotheques = bibliothequesIdBibliotheques;
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
                bibliothequesIdBibliotheques == tarifs.bibliothequesIdBibliotheques &&
                Objects.equals(denomination, tarifs.denomination) &&
                Objects.equals(dateDebut, tarifs.dateDebut);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idTarifs, denomination, bibliothequesIdBibliotheques, dateDebut);
    }

    @ManyToOne
    @JoinColumn(name = "BibliothequesIdBibliotheques", referencedColumnName = "IdBibliotheques", nullable = false)
    public Bibliotheques getBibliothequesByBibliothequesIdBibliotheques() {
        return bibliothequesByBibliothequesIdBibliotheques;
    }

    public void setBibliothequesByBibliothequesIdBibliotheques(Bibliotheques bibliothequesByBibliothequesIdBibliotheques) {
        this.bibliothequesByBibliothequesIdBibliotheques = bibliothequesByBibliothequesIdBibliotheques;
    }

    @OneToMany(mappedBy = "tarifsByTarifsIdTarifs")
    public Collection<TarifsJours> getTarifsJoursByIdTarifs() {
        return tarifsJoursByIdTarifs;
    }

    public void setTarifsJoursByIdTarifs(Collection<TarifsJours> tarifsJoursByIdTarifs) {
        this.tarifsJoursByIdTarifs = tarifsJoursByIdTarifs;
    }

    @OneToMany(mappedBy = "tarifsByTarifsIdTarifs")
    public Collection<TarifsPenalites> getTarifsPenalitesByIdTarifs() {
        return tarifsPenalitesByIdTarifs;
    }

    public void setTarifsPenalitesByIdTarifs(Collection<TarifsPenalites> tarifsPenalitesByIdTarifs) {
        this.tarifsPenalitesByIdTarifs = tarifsPenalitesByIdTarifs;
    }
}
