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
                @NamedQuery(name = "ExemplairesLivres.findAllByLivresByBibliotheque", query = "SELECT el FROM ExemplairesLivres el WHERE el.livres=:livre AND el.bibliotheques=:bibliotheque"),
        })
public class ExemplairesLivres implements Serializable {
    private static final long serialVersionUID = 1L;
    private static final Logger log = Logger.getLogger(ExemplairesLivres.class);
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "IdExemplairesLivres", nullable = false)
    private int idExemplairesLivres;
    @Basic
    @Column(name = "CodeBarre", nullable = false, length = 45)
    private String codeBarre;
    @Basic
    @Column(name = "Actif", nullable = false)
    private boolean actif = true;
    @Enumerated(EnumType.STRING)
    @Column(nullable = false, columnDefinition = "varchar(2) default 'FR'", name = "etat")
    private ExemplairesLivresEtatEnum etat;
    @Basic
    @Column(name = "CommentaireEtat", nullable = false, length = 500)
    private String commentaireEtat="Neuf";
    @Basic
    @Column(name = "Loue", nullable = false)
    private boolean loue=false;
    @Basic
    @Column(name = "Reserve", nullable = false)
    private boolean reserve=false;
    @Basic
    @Column(name = "Transfert", nullable = false)
    private boolean transfert=false;
    @OneToMany(mappedBy = "exemplairesLivre")
    private Collection<FacturesDetail> facturesDetails;
    @ManyToOne
    @JoinColumn(name = "BibliothequesIdBibliotheques", nullable = false)
    private Bibliotheques bibliotheques;
    @ManyToOne
    @JoinColumn(name = "LivresIdLivres", nullable = false)
    private Livres livres;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ExemplairesLivres that = (ExemplairesLivres) o;
        return idExemplairesLivres == that.idExemplairesLivres &&
                actif == that.actif &&
                reserve == that.reserve &&
                loue == that.loue &&
                transfert == that.transfert &&
                Objects.equals(codeBarre, that.codeBarre) &&
                Objects.equals(commentaireEtat, that.commentaireEtat);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idExemplairesLivres, codeBarre, actif, reserve, etat, commentaireEtat, loue, transfert);
    }

    //-------------------------------Getter & Setter--------------------------------------------

    public int getIdExemplairesLivres() {
        return idExemplairesLivres;
    }

    public void setIdExemplairesLivres(int idExemplairesLivres) {
        this.idExemplairesLivres = idExemplairesLivres;
    }

    public String getCodeBarre() {
        return codeBarre;
    }

    public void setCodeBarre(String codeBarre) {
        this.codeBarre = codeBarre;
    }

    public boolean isActif() {
        return actif;
    }

    public void setActif(boolean actif) {
        this.actif = actif;
    }

    public boolean isTransfert() {
        return transfert;
    }

    public void setTransfert(boolean transfert) {
        this.transfert = transfert;
    }

    public ExemplairesLivresEtatEnum getEtat() {
        return etat;
    }

    public void setEtat(ExemplairesLivresEtatEnum etat) {
        this.etat = etat;
    }

    public String getCommentaireEtat() {
        return commentaireEtat;
    }

    public void setCommentaireEtat(String commentaireEtat) {
        this.commentaireEtat = commentaireEtat;
    }

    public boolean isLoue() {
        return loue;
    }

    public void setLoue(boolean loue) {
        this.loue = loue;
    }


    public boolean isReserve() {
        return reserve;
    }

    public void setReserve(boolean reserve) {
        this.reserve = reserve;
    }

    public Bibliotheques getBibliotheques() {
        return bibliotheques;
    }

    public void setBibliotheques(Bibliotheques bibliotheques1) {
        this.bibliotheques = bibliotheques1;
    }


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
