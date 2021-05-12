package entities;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Collection;
import java.util.Objects;

@Entity
@Table(name="livres")
//TODO Regarder les requetes
@NamedQueries
        ({
                @NamedQuery(name = "Livres.findAll", query = "SELECT l FROM Livres l"),
                @NamedQuery(name = "Livres.findOne", query ="SELECT l FROM Livres l WHERE l.titre=:id"),
                @NamedQuery(name = "Livres.findAllTri", query="SELECT l FROM Livres l ORDER BY l.titre ASC"),
                @NamedQuery(name = "Livres.findActiv", query = "SELECT l FROM Livres l WHERE l.actif=TRUE"),
                @NamedQuery(name = "Livres.findInactiv", query = "SELECT l FROM Livres l WHERE l.actif=FALSE"),
                @NamedQuery(name = "Livres.findOneByIsbn", query="SELECT l FROM Livres l WHERE l.isbn=:isbn"),
                @NamedQuery(name = "Livres.findAllByTitre", query="SELECT l FROM Livres l WHERE l.titre=:titre ORDER BY l.titre ASC"),//A verifier
                @NamedQuery(name = "Livres.findByAuteurs", query = "SELECT l FROM Livres l WHERE l.livresAuteurs=:auteur"), // Join a verifier
                @NamedQuery(name = "Livres.findByEditeurs", query = "SELECT l FROM Livres l WHERE l.editeurs=:editeur"),
                @NamedQuery(name = "Livres.findByGenres", query = "SELECT l FROM Livres l WHERE l.livresGenres=:genre"),
                @NamedQuery(name="Livres.searchLivre", query="SELECT l FROM Livres l WHERE l.titre=:titre"),
        })
public class Livres implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idLivres;
    private String titre;
    private int annee;
    private String isbn;
    private boolean actif = true;
    private Collection<ExemplairesLivres> exemplairesLivres;
    private Collection<LivresAuteurs> livresAuteurs;
    private Collection<LivresGenres> livresGenres;

    @Id
    @Column(name = "IdLivres", nullable = false)
    public int getIdLivres() {
        return idLivres;
    }

    public void setIdLivres(int idLivres) {
        this.idLivres = idLivres;
    }

    @Basic
    @Column(name = "Titre", nullable = false, length = 255)
    public String getTitre() {
        return titre;
    }

    public void setTitre(String titre) {
        this.titre = titre;
    }

    @Basic
    @Column(name = "Annee", nullable = false)
    public int getAnnee() {
        return annee;
    }

    public void setAnnee(int annee) {
        this.annee = annee;
    }


    @Basic
    @Column(name = "ISBN", nullable = false)

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
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
        Livres livres = (Livres) o;
        return idLivres == livres.idLivres &&
                annee == livres.annee &&
                isbn.equals(livres.isbn) &&
                actif == livres.actif &&
                Objects.equals(titre, livres.titre) &&
                Objects.equals(editeurs, livres.editeurs);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idLivres, titre, annee, isbn, actif, editeurs);
    }

    @OneToMany(mappedBy = "livresByLivresIdLivres")
    public Collection<ExemplairesLivres> getExemplairesLivres() {
        return exemplairesLivres;
    }

    public void setExemplairesLivres(Collection<ExemplairesLivres> exemplairesLivresByIdLivres) {
        this.exemplairesLivres = exemplairesLivresByIdLivres;
    }

    @ManyToOne
    @JoinColumn(name = "EditeursIdEditeurs", referencedColumnName = "IdEditeurs", nullable = false)
    private Editeurs editeurs;
    public Editeurs getEditeurs() {
        return editeurs;
    }

    public void setEditeurs(Editeurs editeurs) {
        this.editeurs = editeurs;
    }

    @OneToMany(mappedBy = "livre")
    public Collection<LivresAuteurs> getLivresAuteurs() {
        return livresAuteurs;
    }

    public void setLivresAuteurs(Collection<LivresAuteurs> livresAuteurs) {
        this.livresAuteurs = livresAuteurs;
    }

    @OneToMany(mappedBy = "livresByLivresIdLivres")
    public Collection<LivresGenres> getLivresGenres() {
        return livresGenres;
    }

    public void setLivresGenres(Collection<LivresGenres> livresGenres) {
        this.livresGenres = livresGenres;
    }

    @Override
    public Livres clone(){
        Livres liv = null;
        try{
            liv = (Livres) super.clone();
        }catch (CloneNotSupportedException e) {
            e.printStackTrace(System.err);
        }
        return liv;
    }

    public void setFields(Livres liv) {
        this.titre = liv.titre;
        this.annee = liv.annee;
        this.isbn = liv.isbn;
        this.actif = liv.actif;
        this.exemplairesLivres = liv.exemplairesLivres;
        this.editeurs = liv.editeurs;
        this.livresAuteurs = liv.livresAuteurs;
        this.livresGenres = liv.livresGenres;


    }
}
