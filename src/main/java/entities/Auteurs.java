package entities;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Collection;
import java.util.Objects;

@Entity
@Table(name="auteurs")
@NamedQueries
        ({
                @NamedQuery(name = "Auteurs.findAll", query = "SELECT a FROM Auteurs a"),
                @NamedQuery(name = "Auteurs.findOne", query = "SELECT a FROM Auteurs a WHERE a.nom=:nom AND a.prenom=:prenom "),
                @NamedQuery(name = "Auteurs.findAllTri", query="SELECT a FROM Auteurs a ORDER BY a.nom ASC"),
                @NamedQuery(name = "Auteurs.findAllActiv", query = "SELECT a FROM Auteurs a WHERE a.actif=TRUE"),
                @NamedQuery(name = "Auteurs.findAllInactiv", query = "SELECT a FROM Auteurs a WHERE a.actif=FALSE"),
                @NamedQuery(name="Auteurs.searchName", query="SELECT a FROM Auteurs a WHERE a.nom=:nom"),
        })
public class Auteurs implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idAuteurs;
    private String nom;
    private String prenom;
    private boolean actif = true;
    @OneToMany(mappedBy = "auteur")
    private Collection<LivresAuteurs> livresAuteur;

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
                Objects.equals(prenom, auteurs.prenom);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idAuteurs, nom, prenom, actif);
    }


    public Collection<LivresAuteurs> getLivresAuteur() {
        return livresAuteur;
    }

    public void setLivresAuteur(Collection<LivresAuteurs> livresAuteursByIdAuteurs) {
        this.livresAuteur = livresAuteursByIdAuteurs;
    }

}
