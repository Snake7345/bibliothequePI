package entities;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "utilisateurs_adresses", schema = "bibliotheque")
@NamedQueries
        ({
                @NamedQuery(name = "UtilisateursAdresses.findAll", query = "SELECT u FROM UtilisateursAdresses u"),
        })
public class UtilisateursAdresses implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idUtilisateursAdresses;
    private boolean actif = true;

    @Id
    @Column(name = "IdUtilisateursAdresses", nullable = false)
    public int getIdUtilisateursAdresses() {
        return idUtilisateursAdresses;
    }

    public void setIdUtilisateursAdresses(int idUtilisateursAdresses) {
        this.idUtilisateursAdresses = idUtilisateursAdresses;
    }

    @ManyToOne
    @JoinColumn(name = "UtilisateursIdUtilisateurs", nullable = false)
    private Utilisateurs utilisateur;
    public Utilisateurs getUtilisateur() {
        return utilisateur;
    }

    public void setUtilisateur(Utilisateurs utilisateur) {
        this.utilisateur = utilisateur;
    }

    @ManyToOne
    @JoinColumn(name = "AdressesIdAdresses", referencedColumnName = "IdAdresses", nullable = false)
    private Adresses adresse;
    public Adresses getAdresse() {
        return adresse;
    }

    public void setAdresse(Adresses adresse) {
        this.adresse = adresse;
    }

    @Basic
    @Column(name = "Actif", nullable = false)
    public boolean isActif() {
        return actif;
    }

    public void setActif(boolean actif) {
        this.actif = actif;
    }
}
