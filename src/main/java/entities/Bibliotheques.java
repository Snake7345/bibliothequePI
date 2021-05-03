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
    private Collection<Adresses> adressesByIdBibliotheques;
    private Collection<ExemplairesLivres> exemplairesLivresByIdBibliotheques;
    private Collection<Tarifs> tarifsByIdBibliotheques;

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

    @OneToMany(mappedBy = "bibliothequesByBibliothequesIdBibliotheques")
    public Collection<Adresses> getAdressesByIdBibliotheques() {
        return adressesByIdBibliotheques;
    }

    public void setAdressesByIdBibliotheques(Collection<Adresses> adressesByIdBibliotheques) {
        this.adressesByIdBibliotheques = adressesByIdBibliotheques;
    }

    @OneToMany(mappedBy = "bibliothequesByBibliothequesIdBibliotheques")
    public Collection<ExemplairesLivres> getExemplairesLivresByIdBibliotheques() {
        return exemplairesLivresByIdBibliotheques;
    }

    public void setExemplairesLivresByIdBibliotheques(Collection<ExemplairesLivres> exemplairesLivresByIdBibliotheques) {
        this.exemplairesLivresByIdBibliotheques = exemplairesLivresByIdBibliotheques;
    }

    @OneToMany(mappedBy = "bibliothequesByBibliothequesIdBibliotheques")
    public Collection<Tarifs> getTarifsByIdBibliotheques() {
        return tarifsByIdBibliotheques;
    }

    public void setTarifsByIdBibliotheques(Collection<Tarifs> tarifsByIdBibliotheques) {
        this.tarifsByIdBibliotheques = tarifsByIdBibliotheques;
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
        this.adressesByIdBibliotheques = biblio.adressesByIdBibliotheques;
        this.exemplairesLivresByIdBibliotheques = biblio.exemplairesLivresByIdBibliotheques;
        this.tarifsByIdBibliotheques = biblio.tarifsByIdBibliotheques;
    }
}
