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
    @OneToMany(mappedBy = "bibliotheques")
    private Collection<Factures> factures;

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

    public Collection<Factures> getFactures() {
        return factures;
    }

    public void setFactures(Collection<Factures> factures) {
        this.factures = factures;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Bibliotheques that = (Bibliotheques) o;
        return idBibliotheques == that.idBibliotheques && nom.equals(that.nom) && adresses.equals(that.adresses) && Objects.equals(exemplairesLivres, that.exemplairesLivres) && Objects.equals(tarifs, that.tarifs) && Objects.equals(factures, that.factures);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idBibliotheques, nom, adresses, exemplairesLivres, tarifs, factures);
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

    @Override
    public Bibliotheques clone(){
        Bibliotheques biblio = null;
        try{
            biblio = (Bibliotheques) super.clone();
        }catch (CloneNotSupportedException e) {
            e.printStackTrace(System.err);
        }
        return biblio;
    }

    public void setFields(Bibliotheques biblio) {
        this.nom = biblio.nom;
        this.adresses = biblio.adresses;
        this.exemplairesLivres = biblio.exemplairesLivres;
        this.tarifs = biblio.tarifs;
    }
}
