package entities;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Collection;
import java.util.Objects;

@Entity
@Table(name="bibliotheques")
@NamedQueries
        ({
                @NamedQuery(name = "Bibliotheques.findAll", query = "SELECT b FROM Bibliotheques b"),

        })
public class Bibliotheques implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idBibliotheques;
    private String nom;
    @OneToMany(mappedBy = "bibliotheques")
    private Collection<Adresses> adresses;
    @OneToMany(mappedBy = "bibliotheques")
    private Collection<ExemplairesLivres> exemplairesLivres;
    @OneToMany(mappedBy = "bibliotheques")
    private Collection<Tarifs> tarifs;

    @Id
    @Column(name = "IdBibliotheques", nullable = false)
    public int getIdBibliotheques() {
        return idBibliotheques;
    }

    public void setIdBibliotheques(int idBibliotheques) {
        this.idBibliotheques = idBibliotheques;
    }

    @Basic
    @Column(name = "Nom", nullable = false, length = 255)
    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Bibliotheques that = (Bibliotheques) o;
        return idBibliotheques == that.idBibliotheques &&
                Objects.equals(nom, that.nom);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idBibliotheques, nom);
    }


    public Collection<Adresses> getAdresses() {
        return adresses;
    }

    public void setAdresses(Collection<Adresses> adresses) {
        this.adresses = adresses;
    }

    public Collection<ExemplairesLivres> getExemplairesLivres() {
        return exemplairesLivres;
    }

    public void setExemplairesLivres(Collection<ExemplairesLivres> exemplairesLivres) {
        this.exemplairesLivres = exemplairesLivres;
    }

    public Collection<Tarifs> getTarifs() {
        return tarifs;
    }

    public void setTarifs(Collection<Tarifs> tarifs) {
        this.tarifs = tarifs;
    }


}
