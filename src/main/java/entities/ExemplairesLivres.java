package entities;

import javax.persistence.*;
import java.util.Collection;
import java.util.Objects;

@Entity
@Table(name = "exemplaires_livres", schema = "bibliotheque", catalog = "")
public class ExemplairesLivres {
    private int idExemplairesLivres;
    private String codeBarre;
    private int bibliothequesIdBibliotheques;
    private int livresIdLivres;
    private byte actif;
    private Object etat;
    private String commentaireEtat;
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
    public byte getActif() {
        return actif;
    }

    public void setActif(byte actif) {
        this.actif = actif;
    }

    @Basic
    @Column(name = "Etat", nullable = false)
    public Object getEtat() {
        return etat;
    }

    public void setEtat(Object etat) {
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ExemplairesLivres that = (ExemplairesLivres) o;
        return idExemplairesLivres == that.idExemplairesLivres &&
                bibliothequesIdBibliotheques == that.bibliothequesIdBibliotheques &&
                livresIdLivres == that.livresIdLivres &&
                actif == that.actif &&
                Objects.equals(codeBarre, that.codeBarre) &&
                Objects.equals(etat, that.etat) &&
                Objects.equals(commentaireEtat, that.commentaireEtat);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idExemplairesLivres, codeBarre, bibliothequesIdBibliotheques, livresIdLivres, actif, etat, commentaireEtat);
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
