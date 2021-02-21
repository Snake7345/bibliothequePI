package entities;

import javax.persistence.*;
import java.util.Collection;
import java.util.Objects;

@Entity
public class Editeurs {
    private int idEditeurs;
    private String nom;
    private Collection<Livres> livresByIdEditeurs;

    @Id
    @Column(name = "IdEditeurs")
    public int getIdEditeurs() {
        return idEditeurs;
    }

    public void setIdEditeurs(int idEditeurs) {
        this.idEditeurs = idEditeurs;
    }

    @Basic
    @Column(name = "Nom")
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
        Editeurs editeurs = (Editeurs) o;
        return idEditeurs == editeurs.idEditeurs &&
                Objects.equals(nom, editeurs.nom);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idEditeurs, nom);
    }

    @OneToMany(mappedBy = "editeursByEditeursIdEditeurs")
    public Collection<Livres> getLivresByIdEditeurs() {
        return livresByIdEditeurs;
    }

    public void setLivresByIdEditeurs(Collection<Livres> livresByIdEditeurs) {
        this.livresByIdEditeurs = livresByIdEditeurs;
    }
}
