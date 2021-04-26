package entities;

import enumeration.ExemplairesLivresEtatEnum;

import javax.persistence.*;
import java.util.Collection;
import java.util.Objects;

@Entity
@Table(name = "exemplaires_livres", schema = "bibliotheque")
public class ExemplairesLivres {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idExemplairesLivres;
    private String codeBarre;
    private int bibliothequesIdBibliotheques;
    private int livresIdLivres;
    private boolean actif;
    private ExemplairesLivresEtatEnum etat;
    private String commentaireEtat;
    private boolean loue;
    private Bibliotheques bibliothequesByBibliothequesIdBibliotheques;
    private Livres livresByLivresIdLivres;
    private Collection<FactureDetail> factureDetailsByIdExemplairesLivres;

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
                etat == that.etat &&
                Objects.equals(commentaireEtat, that.commentaireEtat) &&
                Objects.equals(bibliothequesByBibliothequesIdBibliotheques, that.bibliothequesByBibliothequesIdBibliotheques) &&
                Objects.equals(livresByLivresIdLivres, that.livresByLivresIdLivres) &&
                Objects.equals(factureDetailsByIdExemplairesLivres, that.factureDetailsByIdExemplairesLivres) &&
                Etat == that.Etat;
    }

    @Override
    public int hashCode() {
        return Objects.hash(idExemplairesLivres, codeBarre, bibliothequesIdBibliotheques, livresIdLivres, actif, etat, commentaireEtat, loue, bibliothequesByBibliothequesIdBibliotheques, livresByLivresIdLivres, factureDetailsByIdExemplairesLivres, Etat);
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
