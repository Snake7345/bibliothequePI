package entities;

import javax.persistence.*;
import java.util.Collection;
import java.util.Objects;

@Entity
public class Auteurs {
    private int idAuteurs;
    private String nom;
    private String prenom;
    private Collection<LivresAuteurs> livresAuteursByIdAuteurs;

    @Id
    @Column(name = "IdAuteurs")
    public int getIdAuteurs() {
        return idAuteurs;
    }

    public void setIdAuteurs(int idAuteurs) {
        this.idAuteurs = idAuteurs;
    }

    @Basic
    @Column(name = "Nom")
    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    @Basic
    @Column(name = "Prenom")
    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Auteurs auteurs = (Auteurs) o;
        return idAuteurs == auteurs.idAuteurs &&
                Objects.equals(nom, auteurs.nom) &&
                Objects.equals(prenom, auteurs.prenom);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idAuteurs, nom, prenom);
    }

    @OneToMany(mappedBy = "auteursByAuteursIdAuteurs")
    public Collection<LivresAuteurs> getLivresAuteursByIdAuteurs() {
        return livresAuteursByIdAuteurs;
    }

    public void setLivresAuteursByIdAuteurs(Collection<LivresAuteurs> livresAuteursByIdAuteurs) {
        this.livresAuteursByIdAuteurs = livresAuteursByIdAuteurs;
    }
}
