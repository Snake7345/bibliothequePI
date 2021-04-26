package entities;

import javax.persistence.*;
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
                @NamedQuery(name = "Livres.findByAuteurs", query = "SELECT l FROM Livres l WHERE l.livresAuteursByIdLivres=:auteur"), // Join a verifier
                @NamedQuery(name = "Livres.findByEditeurs", query = "SELECT l FROM Livres l WHERE l.editeursByEditeursIdEditeurs=:editeur"),
                @NamedQuery(name = "Livres.findByGenres", query = "SELECT l FROM Livres l WHERE l.livresGenresByIdLivres=:genre"),
        })

public class Livres {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "IdLivres", nullable = false)
    private int idLivres;
    public int getIdLivres() {
        return idLivres;
    }

    public void setIdLivres(int idLivres) {
        this.idLivres = idLivres;
    }

    @Basic
    @Column(name = "Titre", nullable = false, length = 255)
    private String titre;
    public String getTitre() {
        return titre;
    }

    public void setTitre(String titre) {
        this.titre = titre;
    }

    @Basic
    @Column(name = "Annee", nullable = false)
    private int annee;
    public int getAnnee() {
        return annee;
    }

    public void setAnnee(int annee) {
        this.annee = annee;
    }

    @Basic
    @Column(name = "ISBN", nullable = false)
    private int isbn;
    public int getIsbn() {
        return isbn;
    }

    public void setIsbn(int isbn) {
        this.isbn = isbn;
    }

    @Basic
    @Column(name = "EditeursIdEditeurs", nullable = false)
    private int editeursIdEditeurs;
    public int getEditeursIdEditeurs() {
        return editeursIdEditeurs;
    }

    public void setEditeursIdEditeurs(int editeursIdEditeurs) {
        this.editeursIdEditeurs = editeursIdEditeurs;
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
        Livres livres = (Livres) o;
        return idLivres == livres.idLivres &&
                actif == livres.actif &&
                annee == livres.annee &&
                isbn == livres.isbn &&
                editeursIdEditeurs == livres.editeursIdEditeurs &&
                Objects.equals(titre, livres.titre);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idLivres, titre, annee, isbn, editeursIdEditeurs);
    }

    @OneToMany(mappedBy = "livresByLivresIdLivres")
    private Collection<ExemplairesLivres> exemplairesLivresByIdLivres;
    public Collection<ExemplairesLivres> getExemplairesLivresByIdLivres() {
        return exemplairesLivresByIdLivres;
    }

    public void setExemplairesLivresByIdLivres(Collection<ExemplairesLivres> exemplairesLivresByIdLivres) {
        this.exemplairesLivresByIdLivres = exemplairesLivresByIdLivres;
    }

    @ManyToOne
    @JoinColumn(name = "EditeursIdEditeurs", referencedColumnName = "IdEditeurs", nullable = false)
    private Editeurs editeursByEditeursIdEditeurs;
    public Editeurs getEditeursByEditeursIdEditeurs() {
        return editeursByEditeursIdEditeurs;
    }

    public void setEditeursByEditeursIdEditeurs(Editeurs editeursByEditeursIdEditeurs) {
        this.editeursByEditeursIdEditeurs = editeursByEditeursIdEditeurs;
    }

    @OneToMany(mappedBy = "livresByLivresIdLivres")
    private Collection<LivresAuteurs> livresAuteursByIdLivres;
    public Collection<LivresAuteurs> getLivresAuteursByIdLivres() {
        return livresAuteursByIdLivres;
    }

    public void setLivresAuteursByIdLivres(Collection<LivresAuteurs> livresAuteursByIdLivres) {
        this.livresAuteursByIdLivres = livresAuteursByIdLivres;
    }

    @OneToMany(mappedBy = "livresByLivresIdLivres")
    private Collection<LivresGenres> livresGenresByIdLivres;
    public Collection<LivresGenres> getLivresGenresByIdLivres() {
        return livresGenresByIdLivres;
    }

    public void setLivresGenresByIdLivres(Collection<LivresGenres> livresGenresByIdLivres) {
        this.livresGenresByIdLivres = livresGenresByIdLivres;
    }
}
