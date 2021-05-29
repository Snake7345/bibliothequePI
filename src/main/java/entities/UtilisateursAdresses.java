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
    @ManyToOne
    @JoinColumn(name = "AdressesIdAdresses", nullable = false)
    private Adresses adresse;
    @ManyToOne
    @JoinColumn(name = "UtilisateursIdUtilisateurs", nullable = false)
    private Utilisateurs utilisateur;

    @Id
    @Column(name = "IdUtilisateursAdresses", nullable = false)
    public int getIdUtilisateursAdresses() {
        return idUtilisateursAdresses;
    }

    public void setIdUtilisateursAdresses(int idUtilisateursAdresses) {
        this.idUtilisateursAdresses = idUtilisateursAdresses;
    }
    @Basic
    @Column(name = "Actif", nullable = false)
    public boolean isActif() {
        return actif;
    }

    public void setActif(boolean actif) {
        this.actif = actif;
    }

    public Utilisateurs getUtilisateur() {
        return utilisateur;
    }

    public void setUtilisateur(Utilisateurs utilisateur) {
        this.utilisateur = utilisateur;
    }

    public Adresses getAdresse() {
        return adresse;
    }

    public void setAdresse(Adresses adresse) {
        this.adresse = adresse;
    }

}
