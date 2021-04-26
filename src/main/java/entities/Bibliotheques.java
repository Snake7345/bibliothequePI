package entities;

import javax.persistence.*;
import java.util.Collection;
import java.util.Objects;

@Entity
public class Bibliotheques {
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
}
