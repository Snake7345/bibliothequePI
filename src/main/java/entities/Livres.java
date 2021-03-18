package entities;

import javax.persistence.*;
import java.util.Collection;
import java.util.Objects;
@Entity
@Table(name="livres")
@NamedQueries
        ({
                @NamedQuery(name = "Livres.findAll", query = "SELECT l FROM Livres l"),
                @NamedQuery(name = "Livres.findOne", query ="SELECT l FROM Livres l WHERE l.titre=:id"),
                @NamedQuery(name = "Livres.findAllTri", query="SELECT l FROM Livres l ORDER BY l.titre ASC"),
                @NamedQuery(name = "Livres.findActiv", query = "SELECT l FROM Livres l WHERE l.actif=TRUE"),
                @NamedQuery(name = "Livres.findInactiv", query = "SELECT l FROM Livres l WHERE l.actif=FALSE"),
                @NamedQuery(name = "Livres.findOneByIsbn", query="SELECT l FROM Livres l WHERE l.isbn=:isbn"),
                @NamedQuery(name = "Livres.findAllByTitre", query="SELECT l FROM Livres l WHERE l.titre=:titre ORDER BY l.titre ASC"),//A verifier
        })
public class Livres {
    private int idLivres;
    private String titre;
    private int annee;
    private int isbn;
    private int editeursIdEditeurs;
    private boolean actif;
    private Collection<ExemplairesLivres> exemplairesLivresByIdLivres;
    private Editeurs editeursByEditeursIdEditeurs;
    private Collection<LivresAuteurs> livresAuteursByIdLivres;
    private Collection<LivresGenres> livresGenresByIdLivres;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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
    public int getIsbn() {
        return isbn;
    }

    public void setIsbn(int isbn) {
        this.isbn = isbn;
    }

    @Basic
    @Column(name = "EditeursIdEditeurs", nullable = false)
    public int getEditeursIdEditeurs() {
        return editeursIdEditeurs;
    }

    public void setEditeursIdEditeurs(int editeursIdEditeurs) {
        this.editeursIdEditeurs = editeursIdEditeurs;
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
    public Collection<ExemplairesLivres> getExemplairesLivresByIdLivres() {
        return exemplairesLivresByIdLivres;
    }

    public void setExemplairesLivresByIdLivres(Collection<ExemplairesLivres> exemplairesLivresByIdLivres) {
        this.exemplairesLivresByIdLivres = exemplairesLivresByIdLivres;
    }

    @ManyToOne
    @JoinColumn(name = "EditeursIdEditeurs", referencedColumnName = "IdEditeurs", nullable = false)
    public Editeurs getEditeursByEditeursIdEditeurs() {
        return editeursByEditeursIdEditeurs;
    }

    public void setEditeursByEditeursIdEditeurs(Editeurs editeursByEditeursIdEditeurs) {
        this.editeursByEditeursIdEditeurs = editeursByEditeursIdEditeurs;
    }

    @OneToMany(mappedBy = "livresByLivresIdLivres")
    public Collection<LivresAuteurs> getLivresAuteursByIdLivres() {
        return livresAuteursByIdLivres;
    }

    public void setLivresAuteursByIdLivres(Collection<LivresAuteurs> livresAuteursByIdLivres) {
        this.livresAuteursByIdLivres = livresAuteursByIdLivres;
    }

    @OneToMany(mappedBy = "livresByLivresIdLivres")
    public Collection<LivresGenres> getLivresGenresByIdLivres() {
        return livresGenresByIdLivres;
    }

    public void setLivresGenresByIdLivres(Collection<LivresGenres> livresGenresByIdLivres) {
        this.livresGenresByIdLivres = livresGenresByIdLivres;
    }
}
