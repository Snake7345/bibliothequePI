package entities;

import javax.persistence.*;
import java.util.Collection;
import java.util.Objects;

@Entity
public class Livres {
    private int idLivres;
    private String titre;
    private int annee;
    private int isbn;
    private int editeursIdEditeurs;
    private Collection<ExemplairesLivres> exemplairesLivresByIdLivres;
    private Editeurs editeursByEditeursIdEditeurs;
    private Collection<LivresAuteurs> livresAuteursByIdLivres;
    private Collection<LivresGenres> livresGenresByIdLivres;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Livres livres = (Livres) o;
        return idLivres == livres.idLivres &&
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
