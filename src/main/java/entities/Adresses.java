package entities;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Collection;
import java.util.Objects;

@Entity
@Table(name="Adresses")
@NamedQueries
        ({
                @NamedQuery(name = "Adresses.findAll", query = "SELECT a FROM Adresses a"),
                @NamedQuery(name = "Adresses.findOne", query = "SELECT a FROM Adresses a")


        })
public class Adresses implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idAdresses;
    private String rue;
    private String boite;
    private String numero;

    private Collection<UtilisateursAdresses> utilisateursAdresses;

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


    @ManyToOne
    @JoinColumn(name = "LocalitesIdLocalites", referencedColumnName = "IdLocalites", nullable = false)
    private Localites localites;
    public Localites getLocalites() {
        return localites;
    }

    public void setLocalites(Localites localitesByLocalitesIdLocalites) {
        this.localites = localitesByLocalitesIdLocalites;
    }

    @ManyToOne
    @JoinColumn(name = "BibliothequesIdBibliotheques", referencedColumnName = "IdBibliotheques")
    private Bibliotheques bibliotheques;
    public Bibliotheques getBibliotheques() {
        return bibliotheques;
    }

    public void setBibliotheques(Bibliotheques bibliothequesByBibliothequesIdBibliotheques) {
        this.bibliotheques = bibliothequesByBibliothequesIdBibliotheques;
    }

    @OneToMany(mappedBy = "adressesByAdressesIdAdresses")
    public Collection<UtilisateursAdresses> getUtilisateursAdresses() {
        return utilisateursAdresses;
    }

    public void setUtilisateursAdresses(Collection<UtilisateursAdresses> utilisateursAdressesByIdAdresses) {
        this.utilisateursAdresses = utilisateursAdressesByIdAdresses;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Adresses adresses = (Adresses) o;
        return idAdresses == adresses.idAdresses &&
                Objects.equals(rue, adresses.rue) &&
                Objects.equals(boite, adresses.boite) &&
                Objects.equals(numero, adresses.numero) &&
                Objects.equals(localites, adresses.localites) &&
                Objects.equals(bibliotheques, adresses.bibliotheques) &&
                Objects.equals(utilisateursAdresses, adresses.utilisateursAdresses);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idAdresses, rue, boite, numero, localites, bibliotheques, utilisateursAdresses);
    }
}
