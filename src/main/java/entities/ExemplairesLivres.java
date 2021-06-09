package entities;

import enumeration.ExemplairesLivresEtatEnum;
import org.apache.log4j.Logger;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Collection;
import java.util.Objects;

@Entity
@Table(name = "exemplaires_livres", schema = "bibliotheque")
@NamedQueries
        ({
                @NamedQuery(name = "ExemplairesLivres.findAllByLivre", query = "SELECT el FROM ExemplairesLivres el WHERE el.livres=:livre"),
                @NamedQuery(name = "ExemplairesLivres.findOneCB", query = "SELECT el FROM ExemplairesLivres el WHERE el.codeBarre=:codeBarre"),
                @NamedQuery(name = "ExemplairesLivres.findActiv", query = "SELECT el FROM ExemplairesLivres el WHERE el.actif=TRUE"),
                @NamedQuery(name = "ExemplairesLivres.findInactiv", query = "SELECT el FROM ExemplairesLivres el WHERE el.actif=FALSE"),
                @NamedQuery(name = "ExemplairesLivres.findLoue", query = "SELECT el FROM ExemplairesLivres el WHERE el.loue=TRUE"),
                @NamedQuery(name = "ExemplairesLivres.findNotLoue", query = "SELECT el FROM ExemplairesLivres el WHERE el.loue=FALSE"),
                @NamedQuery(name = "ExemplairesLivres.findLastExemplaire", query = "SELECT el FROM ExemplairesLivres el ORDER BY el.codeBarre DESC"),
        })
public class ExemplairesLivres implements Serializable {
    private static final long serialVersionUID = 1L;
    private static final Logger log = Logger.getLogger(ExemplairesLivres.class);
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idExemplairesLivres;
    private String codeBarre;
    private boolean actif = true;
    private String commentaireEtat="Neuf";
    private boolean loue=false;
    @OneToMany(mappedBy = "exemplairesLivre")
    private Collection<FacturesDetail> facturesDetails;

    @Id
    @Column(name = "IdExemplairesLivres", nullable = false)
    public int getIdExemplairesLivres() {
        return idExemplairesLivres;
    }

    public void setIdExemplairesLivres(int idExemplairesLivres) {
        this.idExemplairesLivres = idExemplairesLivres;
    }

    @Basic
    @Column(name = "CodeBarre", nullable = false, length = 45)
    public String getCodeBarre() {
        return codeBarre;
    }

    public void setCodeBarre(String codeBarre) {
        this.codeBarre = codeBarre;
    }

    @Basic
    @Column(name = "Actif", nullable = false)
    public boolean isActif() {
        return actif;
    }

    public void setActif(boolean actif) {
        this.actif = actif;
    }

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, columnDefinition = "varchar(2) default 'FR'", name = "etat")
    private ExemplairesLivresEtatEnum etat;

    public ExemplairesLivresEtatEnum getEtat() {
        return etat;
    }

    public void setEtat(ExemplairesLivresEtatEnum etat) {
        this.etat = etat;
    }

    @Basic
    @Column(name = "CommentaireEtat", nullable = false, length = 500)
    public String getCommentaireEtat() {
        return commentaireEtat;
    }

    public void setCommentaireEtat(String commentaireEtat) {
        this.commentaireEtat = commentaireEtat;
    }


    @Basic
    @Column(name = "Loue", nullable = false)
    public boolean isLoue() {
        return loue;
    }

    public void setLoue(boolean loue) {
        this.loue = loue;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ExemplairesLivres that = (ExemplairesLivres) o;
        return idExemplairesLivres == that.idExemplairesLivres &&
                actif == that.actif &&
                loue == that.loue &&
                Objects.equals(codeBarre, that.codeBarre) &&
                Objects.equals(commentaireEtat, that.commentaireEtat);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idExemplairesLivres, codeBarre, actif, etat, commentaireEtat, loue);
    }

    @ManyToOne
    @JoinColumn(name = "BibliothequesIdBibliotheques", nullable = false)
    private Bibliotheques bibliotheques;
    public Bibliotheques getBibliotheques() {
        return bibliotheques;
    }

    public void setBibliotheques(Bibliotheques bibliotheques1) {
        this.bibliotheques = bibliotheques1;
    }

    @ManyToOne
    @JoinColumn(name = "LivresIdLivres", nullable = false)
    private Livres livres;
    public Livres getLivres() {
        return livres;
    }

    public void setLivres(Livres livresByLivresIdLivres) {
        this.livres = livresByLivresIdLivres;
    }


    public Collection<FacturesDetail> getFactureDetails() {
        return facturesDetails;
    }

    public void setFactureDetails(Collection<FacturesDetail> facturesDetails1) {
        this.facturesDetails = facturesDetails1;
    }
}
