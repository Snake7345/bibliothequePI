package entities;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "utilisateurs_bibliotheques", schema = "bibliotheque")
@NamedQueries
        ({
                @NamedQuery(name = "UtilisateursAdresses.findAll", query = "SELECT u FROM UtilisateursAdresses u"),
        })
public class UtilisateursBibliotheques implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idUtilisateursBibliotheques;
    @ManyToOne
    @JoinColumn(name = "BibliothequesIdBibliotheques", nullable = false)
    private Bibliotheques bibliotheque;
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
        return bibliotheque;
    }

    public void setBibliotheque(Bibliotheques bibliotheque) {
        this.bibliotheque = bibliotheque;
    }

    public Utilisateurs getUtilisateur() {
        return utilisateur;
    }

    public void setUtilisateur(Utilisateurs utilisateur) {
        this.utilisateur = utilisateur;
    }


}
