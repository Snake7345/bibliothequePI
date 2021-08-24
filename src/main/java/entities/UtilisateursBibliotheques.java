package entities;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "utilisateurs_bibliotheques", schema = "bibliotheque")
@NamedQueries
        ({
                @NamedQuery(name = "UtilisateursBibliotheques.findAll", query = "SELECT u FROM UtilisateursBibliotheques u"),
        })
public class UtilisateursBibliotheques implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idUtilisateursBibliotheques;
    @ManyToOne
    @JoinColumn(name = "BibliothequesIdBibliotheques", nullable = false)
    private Bibliotheques bibliotheques;
    @ManyToOne
    @JoinColumn(name = "UtilisateursIdUtilisateurs", nullable = false)
    private Utilisateurs utilisateur;

    @Id
    @Column(name = "IdUtilisateursBibliotheques", nullable = false)

    public int getIdUtilisateursBibliotheques() {
        return idUtilisateursBibliotheques;
    }

    public void setIdUtilisateursBibliotheques(int idUtilisateursBibliotheques) {
        this.idUtilisateursBibliotheques = idUtilisateursBibliotheques;
    }

    public Bibliotheques getBibliotheque() {
        return bibliotheques;
    }

    public void setBibliotheque(Bibliotheques bibliotheque) {
        this.bibliotheques = bibliotheque;
    }

    public Utilisateurs getUtilisateur() {
        return utilisateur;
    }

    public void setUtilisateur(Utilisateurs utilisateur) {
        this.utilisateur = utilisateur;
    }


}
