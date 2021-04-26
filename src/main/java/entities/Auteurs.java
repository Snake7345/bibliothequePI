package entities;

import javax.persistence.*;
import java.util.Collection;
import java.util.Objects;

@Entity
public class Auteurs {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idAuteurs;
    private String nom;
    private String prenom;
    private boolean actif;
    private Collection<LivresAuteurs> livresAuteursByIdAuteurs;

    @Id
    @Column(name = "IdAuteurs", nullable = false)
    public int getIdAuteurs() {
        return idAuteurs;
    }

    public void setIdAuteurs(int idAuteurs) {
        this.idAuteurs = idAuteurs;
    }

    @Basic
    @Column(name = "Nom", nullable = false, length = 200)
    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    @Basic
    @Column(name = "Prenom", nullable = false, length = 200)
    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }


    @Basic
    @Column(name = "Actif", nullable = false)

    public boolean isActif() {
        return actif;
    }

    public void setActif(boolean actif) {
        this.actif = actif;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Auteurs auteurs = (Auteurs) o;
        return idAuteurs == auteurs.idAuteurs &&
                actif == auteurs.actif &&
                Objects.equals(nom, auteurs.nom) &&
                Objects.equals(prenom, auteurs.prenom) &&
                Objects.equals(livresAuteursByIdAuteurs, auteurs.livresAuteursByIdAuteurs);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idAuteurs, nom, prenom, actif, livresAuteursByIdAuteurs);
    }

    @OneToMany(mappedBy = "auteursByAuteursIdAuteurs")
    public Collection<LivresAuteurs> getLivresAuteursByIdAuteurs() {
        return livresAuteursByIdAuteurs;
    }

    public void setLivresAuteursByIdAuteurs(Collection<LivresAuteurs> livresAuteursByIdAuteurs) {
        this.livresAuteursByIdAuteurs = livresAuteursByIdAuteurs;
    }
}
