package entities;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Collection;
import java.util.Objects;

@Entity
@Table(name="livres")
@NamedQueries
        ({
                @NamedQuery(name = "Livres.findAll", query = "SELECT l FROM Livres l"),
                @NamedQuery(name = "Livres.findOne", query ="SELECT l FROM Livres l WHERE l.titre=:titre AND l.annee=:annee AND l.isbn=:isbn AND l.livresAuteurs=:livreAuteurs AND l.livresGenres=:livresGenres AND l.editeurs=:editeurs"),
                @NamedQuery(name = "Livres.findAllTri", query="SELECT l FROM Livres l ORDER BY l.titre ASC"),
                @NamedQuery(name = "Livres.findActiv", query = "SELECT l FROM Livres l WHERE l.actif=TRUE"),
                @NamedQuery(name = "Livres.findInactiv", query = "SELECT l FROM Livres l WHERE l.actif=FALSE"),
                @NamedQuery(name = "Livres.findOneByIsbn", query="SELECT l FROM Livres l WHERE l.isbn=:isbn"),
                @NamedQuery(name = "Livres.findAllByTitre", query="SELECT l FROM Livres l WHERE l.titre=:titre ORDER BY l.titre ASC"),//A verifier
                @NamedQuery(name = "Livres.findLivreByAuteurs", query = "SELECT la.livre FROM LivresAuteurs la WHERE la.auteur=:auteur"),
                @NamedQuery(name = "Livres.findByEditeurs", query = "SELECT l FROM Livres l WHERE l.editeurs=:editeur"),
                @NamedQuery(name = "Livres.findByIsbn", query = "SELECT l FROM Livres l WHERE l.isbn=:isbn"),
                @NamedQuery(name = "Livres.findLivreByGenres", query = "SELECT lg.livre FROM LivresGenres lg WHERE lg.genre=:genre"),
                @NamedQuery(name=  "Livres.searchLivre", query="SELECT l FROM Livres l WHERE l.titre=:titre"),
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
    @OneToMany(mappedBy = "livres")
    private Collection<ExemplairesLivres> exemplairesLivres;
    @OneToMany(mappedBy = "livre")
    private Collection<LivresAuteurs> livresAuteurs;
    @OneToMany(mappedBy = "livre")
    private Collection<LivresGenres> livresGenres;
    @OneToMany(mappedBy = "livre")
    private Collection<Reservation> reservations;

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


    public Collection<ExemplairesLivres> getExemplairesLivres() {
        return exemplairesLivres;
    }

    public void setExemplairesLivres(Collection<ExemplairesLivres> exemplairesLivresByIdLivres) {
        this.exemplairesLivres = exemplairesLivresByIdLivres;
    }


    public Collection<LivresAuteurs> getLivresAuteurs() {
        return livresAuteurs;
    }

    public void setLivresAuteurs(Collection<LivresAuteurs> livresAuteurs) {
        this.livresAuteurs = livresAuteurs;
    }


    public Collection<LivresGenres> getLivresGenres()
    {
        return livresGenres;
    }

    public void setLivresGenres(Collection<LivresGenres> livresGenres) {
        this.livresGenres = livresGenres;
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

    public Collection<Reservation> getReservations() {
        return reservations;
    }

    public void setReservations(Collection<Reservation> reservations) {
        this.reservations = reservations;
    }
}