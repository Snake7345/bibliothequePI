package entities;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Collection;
import java.util.Objects;

@Entity
@Table(name="editeurs")
@NamedQueries
        ({
                @NamedQuery(name = "Editeurs.findAll", query = "SELECT e FROM Editeurs e"),
                @NamedQuery(name = "Editeurs.findOne", query ="SELECT e FROM Editeurs e WHERE e.nom=:id"),
                @NamedQuery(name = "Editeurs.findAllTri", query="SELECT e FROM Editeurs e ORDER BY e.nom ASC"),
        })
public class Editeurs implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idEditeurs;
    private String nom;
    private Collection<Livres> livresByIdEditeurs;

    @Id
    @Column(name = "IdEditeurs", nullable = false)
    public int getIdEditeurs() {
        return idEditeurs;
    }

    public void setIdEditeurs(int idEditeurs) {
        this.idEditeurs = idEditeurs;
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

    @Override
    public Editeurs clone(){
        Editeurs edit = null;
        try{
            edit = (Editeurs) super.clone();
        }catch (CloneNotSupportedException e) {
            e.printStackTrace(System.err);
        }
        return edit;
    }

    public void setFields(Editeurs edit) {
        this.nom = edit.nom;
        this.livresByIdEditeurs = edit.livresByIdEditeurs;
    }
}
