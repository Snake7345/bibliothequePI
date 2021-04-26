package entities;

import javax.persistence.*;
import java.util.Collection;
import java.util.Objects;

@Entity
public class Adresses {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idAdresses;
    private String rue;
    private String boite;
    private String numero;
    private int localitesIdLocalites;
    private int bibliothequesIdBibliotheques;
    private Localites localitesByLocalitesIdLocalites;
    private Bibliotheques bibliothequesByBibliothequesIdBibliotheques;
    private Collection<UtilisateursAdresses> utilisateursAdressesByIdAdresses;

    @Id
    @Column(name = "IdAdresses", nullable = false)
    public int getIdAdresses() {
        return idAdresses;
    }

    public void setIdAdresses(int idAdresses) {
        this.idAdresses = idAdresses;
    }

    @Basic
    @Column(name = "Rue", nullable = false, length = 255)
    public String getRue() {
        return rue;
    }

    public void setRue(String rue) {
        this.rue = rue;
    }

    @Basic
    @Column(name = "Boite", nullable = true, length = 4)
    public String getBoite() {
        return boite;
    }

    public void setBoite(String boite) {
        this.boite = boite;
    }

    @Basic
    @Column(name = "Numero", nullable = false, length = 6)
    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    @Basic
    @Column(name = "LocalitesIdLocalites", nullable = false)
    public int getLocalitesIdLocalites() {
        return localitesIdLocalites;
    }

    public void setLocalitesIdLocalites(int localitesIdLocalites) {
        this.localitesIdLocalites = localitesIdLocalites;
    }

    @Basic
    @Column(name = "BibliothequesIdBibliotheques", nullable = true)

    public int getBibliothequesIdBibliotheques() {
        return bibliothequesIdBibliotheques;
    }

    public void setBibliothequesIdBibliotheques(int bibliothequesIdBibliotheques) {
        this.bibliothequesIdBibliotheques = bibliothequesIdBibliotheques;
    }


    @ManyToOne
    @JoinColumn(name = "LocalitesIdLocalites", referencedColumnName = "IdLocalites", nullable = false)
    public Localites getLocalitesByLocalitesIdLocalites() {
        return localitesByLocalitesIdLocalites;
    }

    public void setLocalitesByLocalitesIdLocalites(Localites localitesByLocalitesIdLocalites) {
        this.localitesByLocalitesIdLocalites = localitesByLocalitesIdLocalites;
    }

    @ManyToOne
    @JoinColumn(name = "BibliothequesIdBibliotheques", referencedColumnName = "IdBibliotheques")
    public Bibliotheques getBibliothequesByBibliothequesIdBibliotheques() {
        return bibliothequesByBibliothequesIdBibliotheques;
    }

    public void setBibliothequesByBibliothequesIdBibliotheques(Bibliotheques bibliothequesByBibliothequesIdBibliotheques) {
        this.bibliothequesByBibliothequesIdBibliotheques = bibliothequesByBibliothequesIdBibliotheques;
    }

    @OneToMany(mappedBy = "adressesByAdressesIdAdresses")
    public Collection<UtilisateursAdresses> getUtilisateursAdressesByIdAdresses() {
        return utilisateursAdressesByIdAdresses;
    }

    public void setUtilisateursAdressesByIdAdresses(Collection<UtilisateursAdresses> utilisateursAdressesByIdAdresses) {
        this.utilisateursAdressesByIdAdresses = utilisateursAdressesByIdAdresses;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Adresses adresses = (Adresses) o;
        return idAdresses == adresses.idAdresses &&
                localitesIdLocalites == adresses.localitesIdLocalites &&
                bibliothequesIdBibliotheques == adresses.bibliothequesIdBibliotheques &&
                Objects.equals(rue, adresses.rue) &&
                Objects.equals(boite, adresses.boite) &&
                Objects.equals(numero, adresses.numero) &&
                Objects.equals(localitesByLocalitesIdLocalites, adresses.localitesByLocalitesIdLocalites) &&
                Objects.equals(bibliothequesByBibliothequesIdBibliotheques, adresses.bibliothequesByBibliothequesIdBibliotheques) &&
                Objects.equals(utilisateursAdressesByIdAdresses, adresses.utilisateursAdressesByIdAdresses);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idAdresses, rue, boite, numero, localitesIdLocalites, bibliothequesIdBibliotheques, localitesByLocalitesIdLocalites, bibliothequesByBibliothequesIdBibliotheques, utilisateursAdressesByIdAdresses);
    }
}
