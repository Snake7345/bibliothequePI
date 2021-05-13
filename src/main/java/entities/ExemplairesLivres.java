package entities;

import enumeration.ExemplairesLivresEtatEnum;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Collection;
import java.util.Objects;

@Entity
@Table(name = "exemplaires_livres", schema = "bibliotheque")
@NamedQueries
        ({
                @NamedQuery(name = "ExemplairesLivres.findAll", query = "SELECT el FROM ExemplairesLivres el"),
                @NamedQuery(name = "ExemplairesLivres.findOneCB", query = "SELECT el FROM ExemplairesLivres el WHERE el.codeBarre=:codeBarre"),
                @NamedQuery(name = "ExemplairesLivres.findActiv", query = "SELECT el FROM ExemplairesLivres el WHERE el.actif=TRUE"),
                @NamedQuery(name = "ExemplairesLivres.findInactiv", query = "SELECT el FROM ExemplairesLivres el WHERE el.actif=FALSE"),
                @NamedQuery(name = "ExemplairesLivres.findLoue", query = "SELECT el FROM ExemplairesLivres el WHERE el.loue=TRUE"),
                @NamedQuery(name = "ExemplairesLivres.findNotLoue", query = "SELECT el FROM ExemplairesLivres el WHERE el.loue=FALSE"),
        })
public class ExemplairesLivres implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idExemplairesLivres;
    private String codeBarre;
    private int bibliothequesIdBibliotheques;
    private int livresIdLivres;
    private boolean actif = true;
    private String commentaireEtat;
    private boolean loue;
    private Bibliotheques bibliotheques;
    private Livres livres;
    @OneToMany(mappedBy = "exemplairesLivre")
    private Collection<FactureDetail> factureDetails;

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
    @Column(name = "BibliothequesIdBibliotheques", nullable = false)
    public int getBibliothequesIdBibliotheques() {
        return bibliothequesIdBibliotheques;
    }

    public void setBibliothequesIdBibliotheques(int bibliothequesIdBibliotheques) {
        this.bibliothequesIdBibliotheques = bibliothequesIdBibliotheques;
    }

    @Basic
    @Column(name = "LivresIdLivres", nullable = false)
    public int getLivresIdLivres() {
        return livresIdLivres;
    }

    public void setLivresIdLivres(int livresIdLivres) {
        this.livresIdLivres = livresIdLivres;
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
                bibliothequesIdBibliotheques == that.bibliothequesIdBibliotheques &&
                livresIdLivres == that.livresIdLivres &&
                actif == that.actif &&
                loue == that.loue &&
                Objects.equals(codeBarre, that.codeBarre) &&
                Objects.equals(commentaireEtat, that.commentaireEtat) &&
                Objects.equals(bibliotheques, that.bibliotheques) &&
                Objects.equals(livres, that.livres) &&
                Objects.equals(factureDetails, that.factureDetails) &&
                etat == that.etat;
    }

    @Override
    public int hashCode() {
        return Objects.hash(idExemplairesLivres, codeBarre, bibliothequesIdBibliotheques, livresIdLivres, actif, etat, commentaireEtat, loue, bibliotheques, livres, factureDetails, etat);
    }

    @ManyToOne
    @JoinColumn(name = "BibliothequesIdBibliotheques", referencedColumnName = "IdBibliotheques", nullable = false)
    public Bibliotheques getBibliotheques() {
        return bibliotheques;
    }

    public void setBibliotheques(Bibliotheques bibliothequesByBibliothequesIdBibliotheques) {
        this.bibliotheques = bibliothequesByBibliothequesIdBibliotheques;
    }

    @ManyToOne
    @JoinColumn(name = "LivresIdLivres", referencedColumnName = "IdLivres", nullable = false)
    public Livres getLivres() {
        return livres;
    }

    public void setLivres(Livres livresByLivresIdLivres) {
        this.livres = livresByLivresIdLivres;
    }


    public Collection<FactureDetail> getFactureDetails() {
        return factureDetails;
    }

    public void setFactureDetails(Collection<FactureDetail> factureDetailsByIdExemplairesLivres) {
        this.factureDetails = factureDetailsByIdExemplairesLivres;
    }
}
