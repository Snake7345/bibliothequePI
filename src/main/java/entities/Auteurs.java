package entities;

import javax.persistence.*;
import java.util.Collection;
import java.util.Objects;

@NamedQueries
        ({
                @NamedQuery(name = "Auteurs.findAll", query = "SELECT a FROM Auteurs a"),
                @NamedQuery(name = "Auteurs.findOne", query = "SELECT a FROM Auteurs a WHERE a.nom=:nom AND a.prenom=:prenom "),
                @NamedQuery(name = "Auteurs.findAllTri", query="SELECT a FROM Auteurs a ORDER BY a.nom ASC"),
        })
@Entity
public class Auteurs {
    private int idAuteurs;
    private String nom;
    private String prenom;
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
