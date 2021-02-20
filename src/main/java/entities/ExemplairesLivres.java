package entities;

import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
import javax.persistence.*;
import java.io.Serializable;

@Entity
@Named
@SessionScoped
public class ExemplairesLivres implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    //@Column(name = "IdExemplairesLivres", nullable = false)
    private Integer idExemplairesLivres;

    //@Column(name = "CodeBarre", nullable = false)
    private String codeBarre;

    //@Column(name = "BibliothequesIdBibliotheques", nullable = false)
    private Integer bibliothequesIdBibliotheques;

    //@Column(name = "LivresIdLivres", nullable = false)
    private Integer livresIdLivres;

    //@Column(name = "Actif", nullable = false)
    private Integer actif;

    //@Column(name = "Etat", nullable = false)
    private String etat;

    //@Column(name = "CommentaireEtat", nullable = false)
    private String commentaireEtat;

    public void setIdExemplairesLivres(Integer idExemplairesLivres) {
        this.idExemplairesLivres = idExemplairesLivres;
    }

    public Integer getIdExemplairesLivres() {
        return idExemplairesLivres;
    }

    public void setCodeBarre(String codeBarre) {
        this.codeBarre = codeBarre;
    }

    public String getCodeBarre() {
        return codeBarre;
    }

    public void setBibliothequesIdBibliotheques(Integer bibliothequesIdBibliotheques) {
        this.bibliothequesIdBibliotheques = bibliothequesIdBibliotheques;
    }

    public Integer getBibliothequesIdBibliotheques() {
        return bibliothequesIdBibliotheques;
    }

    public void setLivresIdLivres(Integer livresIdLivres) {
        this.livresIdLivres = livresIdLivres;
    }

    public Integer getLivresIdLivres() {
        return livresIdLivres;
    }

    public void setActif(Integer actif) {
        this.actif = actif;
    }

    public Integer getActif() {
        return actif;
    }

    public void setEtat(String etat) {
        this.etat = etat;
    }

    public String getEtat() {
        return etat;
    }

    public void setCommentaireEtat(String commentaireEtat) {
        this.commentaireEtat = commentaireEtat;
    }

    public String getCommentaireEtat() {
        return commentaireEtat;
    }

    @Override
    public String toString() {
        return "ExemplairesLivres{" +
                "idExemplairesLivres=" + idExemplairesLivres + '\'' +
                "codeBarre=" + codeBarre + '\'' +
                "bibliothequesIdBibliotheques=" + bibliothequesIdBibliotheques + '\'' +
                "livresIdLivres=" + livresIdLivres + '\'' +
                "actif=" + actif + '\'' +
                "etat=" + etat + '\'' +
                "commentaireEtat=" + commentaireEtat + '\'' +
                '}';
    }
}
