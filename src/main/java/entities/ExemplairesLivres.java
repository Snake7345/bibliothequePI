package entities;

import enumeration.ExemplairesLivresEtatEnum;

import javax.persistence.*;
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

public class ExemplairesLivres {

    private String codeBarre;
    private int bibliothequesIdBibliotheques;
    private int livresIdLivres;
    private boolean actif;
    private boolean loue;
    private String commentaireEtat;
    private Bibliotheques bibliothequesByBibliothequesIdBibliotheques;
    private Livres livresByLivresIdLivres;
    private Collection<FactureDetail> factureDetailsByIdExemplairesLivres;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "IdExemplairesLivres", nullable = false)
    private int idExemplairesLivres;

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
        return this.actif;
    }

    public void setActif(boolean actif) {
        this.actif = actif;
    }

    @Basic
    @Column(name = "Loue", nullable = false)
    public boolean isLoue() {
        return loue;
    }

    public void setLoue(boolean loue) {
        this.loue = loue;
    }

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, columnDefinition = "varchar(2) default 'FR'", name = "Etat")
    private ExemplairesLivresEtatEnum Etat;

    public ExemplairesLivresEtatEnum getEtat() {
        return Etat;
    }

    public void setEtat(ExemplairesLivresEtatEnum etat) {
        Etat = etat;
    }

    @Basic
    @Column(name = "CommentaireEtat", nullable = false, length = 500)
    public String getCommentaireEtat() {
        return commentaireEtat;
    }

    public void setCommentaireEtat(String commentaireEtat) {
        this.commentaireEtat = commentaireEtat;
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
                Objects.equals(bibliothequesByBibliothequesIdBibliotheques, that.bibliothequesByBibliothequesIdBibliotheques);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idExemplairesLivres, codeBarre, bibliothequesIdBibliotheques, livresIdLivres, actif, loue, commentaireEtat, bibliothequesByBibliothequesIdBibliotheques);
    }

    @ManyToOne
    @JoinColumn(name = "BibliothequesIdBibliotheques", referencedColumnName = "IdBibliotheques", nullable = false)
    public Bibliotheques getBibliothequesByBibliothequesIdBibliotheques() {
        return bibliothequesByBibliothequesIdBibliotheques;
    }

    public void setBibliothequesByBibliothequesIdBibliotheques(Bibliotheques bibliothequesByBibliothequesIdBibliotheques) {
        this.bibliothequesByBibliothequesIdBibliotheques = bibliothequesByBibliothequesIdBibliotheques;
    }

    @ManyToOne
    @JoinColumn(name = "LivresIdLivres", referencedColumnName = "IdLivres", nullable = false)
    public Livres getLivresByLivresIdLivres() {
        return livresByLivresIdLivres;
    }

    public void setLivresByLivresIdLivres(Livres livresByLivresIdLivres) {
        this.livresByLivresIdLivres = livresByLivresIdLivres;
    }

    @OneToMany(mappedBy = "exemplairesLivresByExemplairesLivresIdExemplairesLivres")
    public Collection<FactureDetail> getFactureDetailsByIdExemplairesLivres() {
        return factureDetailsByIdExemplairesLivres;
    }

    public void setFactureDetailsByIdExemplairesLivres(Collection<FactureDetail> factureDetailsByIdExemplairesLivres) {
        this.factureDetailsByIdExemplairesLivres = factureDetailsByIdExemplairesLivres;
    }
}
