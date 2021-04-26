package entities;

import javax.persistence.*;
import java.util.Collection;
import java.util.Objects;
@Entity
@Table(name="auteurs")
@NamedQueries
        ({
                @NamedQuery(name = "Auteurs.findAll", query = "SELECT a FROM Auteurs a"),
                @NamedQuery(name = "Auteurs.findOne", query = "SELECT a FROM Auteurs a WHERE a.nom=:nom AND a.prenom=:prenom "),
                @NamedQuery(name = "Auteurs.findAllTri", query="SELECT a FROM Auteurs a ORDER BY a.nom ASC"),
                @NamedQuery(name = "Auteurs.findActiv", query = "SELECT a FROM Auteurs a WHERE a.actif=TRUE"),
                @NamedQuery(name = "Auteurs.findInactiv", query = "SELECT a FROM Auteurs a WHERE a.actif=FALSE"),
        })
public class Auteurs {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idAuteurs;
    @Column(name = "IdAuteurs", nullable = false)
    public int getIdAuteurs() {
        return idAuteurs;
    }

    public void setIdAuteurs(int idAuteurs) {
        this.idAuteurs = idAuteurs;
    }

    @Basic
    @Column(name = "Nom", nullable = false, length = 200)
    private String nom;
    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    @Basic
    @Column(name = "Prenom", nullable = false, length = 200)
    private String prenom;
    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    @Basic
    @Column(name = "Actif", nullable = false)
    private boolean actif;
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
                Objects.equals(prenom, auteurs.prenom);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idAuteurs, nom, prenom);
    }

    @OneToMany(mappedBy = "auteursByAuteursIdAuteurs")
    private Collection<LivresAuteurs> livresAuteursByIdAuteurs;
    public Collection<LivresAuteurs> getLivresAuteursByIdAuteurs() {
        return livresAuteursByIdAuteurs;
    }

    public void setLivresAuteursByIdAuteurs(Collection<LivresAuteurs> livresAuteursByIdAuteurs) {
        this.livresAuteursByIdAuteurs = livresAuteursByIdAuteurs;
    }
}
