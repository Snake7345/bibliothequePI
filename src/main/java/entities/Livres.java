package entities;

import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

@Entity
@Named
@SessionScoped
public class Livres implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "IdLivres", nullable = false)
    private Integer idLivres;

    @Column(name = "Titre", nullable = false)
    private String titre;

    @Column(name = "Annee", nullable = false)
    private Integer annee;

    @Column(name = "ISBN", nullable = false)
    private Integer ISBN;

    @Column(name = "EditeursIdEditeurs", nullable = false)
    private Integer editeursIdEditeurs;

    public void setIdLivres(Integer idLivres) {
        this.idLivres = idLivres;
    }

    public Integer getIdLivres() {
        return idLivres;
    }

    public void setTitre(String titre) {
        this.titre = titre;
    }

    public String getTitre() {
        return titre;
    }

    public void setAnnee(Integer annee) {
        this.annee = annee;
    }

    public Integer getAnnee() {
        return annee;
    }

    public void setISBN(Integer ISBN) {
        this.ISBN = ISBN;
    }

    public Integer getISBN() {
        return ISBN;
    }

    public void setEditeursIdEditeurs(Integer editeursIdEditeurs) {
        this.editeursIdEditeurs = editeursIdEditeurs;
    }

    public Integer getEditeursIdEditeurs() {
        return editeursIdEditeurs;
    }

    @Override
    public String toString() {
        return "Livres{" +
                "idLivres=" + idLivres + '\'' +
                "titre=" + titre + '\'' +
                "annee=" + annee + '\'' +
                "ISBN=" + ISBN + '\'' +
                "editeursIdEditeurs=" + editeursIdEditeurs + '\'' +
                '}';
    }
}
