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
                @NamedQuery(name = "Adresses.findOne", query = "SELECT a FROM Adresses a WHERE a.boite=:boite AND a.numero=:numero AND a.localites=:localite AND a.rue=:rue"),


        })
public class Adresses implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idAdresses;
    private String rue;
    private String boite;
    private String numero;
    @OneToMany(mappedBy = "adresse")
    private Collection<UtilisateursAdresses> utilisateursAdresses;
    @ManyToOne
    @JoinColumn(name = "BibliothequesIdBibliotheques", referencedColumnName = "IdBibliotheques")
    private Bibliotheques bibliotheques;
    @ManyToOne
    @JoinColumn(name = "LocalitesIdLocalites", referencedColumnName = "IdLocalites", nullable = false)
    private Localites localites;

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

    public Localites getLocalites() {
        return localites;
    }

    public void setLocalites(Localites localitesByLocalitesIdLocalites) {
        this.localites = localitesByLocalitesIdLocalites;
    }

    public Bibliotheques getBibliotheques() {
        return bibliotheques;
    }

    public void setBibliotheques(Bibliotheques bibliothequesByBibliothequesIdBibliotheques) {
        this.bibliotheques = bibliothequesByBibliothequesIdBibliotheques;
    }

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
        return idAdresses == adresses.idAdresses && rue.equals(adresses.rue) && Objects.equals(boite, adresses.boite) && numero.equals(adresses.numero);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idAdresses, rue, boite, numero);
    }
}
