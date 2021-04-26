package entities;

import javax.persistence.*;
import java.util.Collection;
import java.util.Objects;

@Entity
@Table(name="Adresses")
@NamedQueries
({
        @NamedQuery(name = "Adresses.findAll", query = "SELECT a FROM Adresses a"),
        
})
public class Adresses {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "IdAdresses", nullable = false)
    private int idAdresses;
    public int getIdAdresses() {
        return idAdresses;
    }

    public void setIdAdresses(int idAdresses) {
        this.idAdresses = idAdresses;
    }

    @Basic
    @Column(name = "Rue", nullable = false, length = 255)
    private String rue;
    public String getRue() {
        return rue;
    }

    public void setRue(String rue) {
        this.rue = rue;
    }

    @Basic
    @Column(name = "Boite", nullable = true, length = 4)
    private String boite;
    public String getBoite() {
        return boite;
    }

    public void setBoite(String boite) {
        this.boite = boite;
    }

    @Basic
    @Column(name = "Numero", nullable = false, length = 6)
    private String numero;
    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    @Basic
    @Column(name = "LocalitesIdLocalites", nullable = false)
    private int localitesIdLocalites;
    public int getLocalitesIdLocalites() {
        return localitesIdLocalites;
    }

    public void setLocalitesIdLocalites(int localitesIdLocalites) {
        this.localitesIdLocalites = localitesIdLocalites;
    }

    @Basic
    @Column(name = "BibliothequesIdBibliotheques", nullable = true)
    private int bibliothequesIdBibliotheques;
    public int getBibliothequesIdBibliotheques() {
        return bibliothequesIdBibliotheques;
    }

    public void setBibliothequesIdBibliotheques(int bibliothequesIdBibliotheques) {
        this.bibliothequesIdBibliotheques = bibliothequesIdBibliotheques;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Adresses adresses = (Adresses) o;
        return idAdresses == adresses.idAdresses &&
                localitesIdLocalites == adresses.localitesIdLocalites &&
                Objects.equals(rue, adresses.rue) &&
                Objects.equals(boite, adresses.boite) &&
                Objects.equals(numero, adresses.numero) &&
                Objects.equals(bibliothequesIdBibliotheques, adresses.bibliothequesIdBibliotheques);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idAdresses, rue, boite, numero, localitesIdLocalites, bibliothequesIdBibliotheques);
    }

    @ManyToOne
    @JoinColumn(name = "LocalitesIdLocalites", referencedColumnName = "IdLocalites", nullable = false)
    private Localites localitesByLocalitesIdLocalites;
    public Localites getLocalitesByLocalitesIdLocalites() {
        return localitesByLocalitesIdLocalites;
    }

    public void setLocalitesByLocalitesIdLocalites(Localites localitesByLocalitesIdLocalites) {
        this.localitesByLocalitesIdLocalites = localitesByLocalitesIdLocalites;
    }

    @ManyToOne
    @JoinColumn(name = "BibliothequesIdBibliotheques", referencedColumnName = "IdBibliotheques")
    private Bibliotheques bibliothequesByBibliothequesIdBibliotheques;
    public Bibliotheques getBibliothequesByBibliothequesIdBibliotheques() {
        return bibliothequesByBibliothequesIdBibliotheques;
    }

    public void setBibliothequesByBibliothequesIdBibliotheques(Bibliotheques bibliothequesByBibliothequesIdBibliotheques) {
        this.bibliothequesByBibliothequesIdBibliotheques = bibliothequesByBibliothequesIdBibliotheques;
    }

    @OneToMany(mappedBy = "adressesByAdressesIdAdresses")
    private Collection<UtilisateursAdresses> utilisateursAdressesByIdAdresses;
    public Collection<UtilisateursAdresses> getUtilisateursAdressesByIdAdresses() {
        return utilisateursAdressesByIdAdresses;
    }

    public void setUtilisateursAdressesByIdAdresses(Collection<UtilisateursAdresses> utilisateursAdressesByIdAdresses) {
        this.utilisateursAdressesByIdAdresses = utilisateursAdressesByIdAdresses;
    }
}
