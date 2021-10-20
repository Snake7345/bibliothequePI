package entities;

import javax.persistence.*;
import java.io.Serializable;
@Entity
@Table(name = "reservation", schema = "bibliotheque")
@NamedQueries
        ({
                @NamedQuery(name = "Reservation.findAll", query = "SELECT r FROM Reservation r"),
                @NamedQuery(name = "Reservation.findOneActiv", query = "SELECT r FROM Reservation r where r.actif=TRUE and r.utilisateur=:utilisateur " +
                        "and r.livre=:livre"),
                @NamedQuery(name = "Reservation.findAllActivByLivre", query = "SELECT r FROM Reservation r where r.actif=TRUE and r.livre=:livre and r.bibliotheques=:bibliotheque"),
                @NamedQuery(name = "Reservation.findAllActivByClient", query = "SELECT r FROM Reservation r where r.actif=TRUE and r.utilisateur=:utilisateur and r.bibliotheques=:bibliotheque"),

        })

public class Reservation implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idReservations;
    @Basic
    @Column(name = "Actif", nullable = false)
    private boolean actif = true;
    @Basic
    @Column(name = "MailEnvoye", nullable = false)
    private boolean mailEnvoye = false;
    @ManyToOne
    @JoinColumn(name = "LivresIdLivres", nullable = false)
    private Livres livre;
    @ManyToOne
    @JoinColumn(name = "UtilisateursIdUtilisateurs", nullable = false)
    private Utilisateurs utilisateur;
    @ManyToOne
    @JoinColumn(name = "BibliothequesIdBibliotheques", nullable = false)
    private Bibliotheques bibliotheques;

    @Id
    @Column(name = "idReservations", nullable = false)
    public int getIdReservation() {
        return idReservations;
    }

    public void setIdReservation(int idReservation) {
        this.idReservations = idReservation;
    }

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

    public Livres getLivre() {
        return livre;
    }

    public void setLivre(Livres livre) {
        this.livre = livre;
    }

    public Bibliotheques getBibliotheques() {
        return bibliotheques;
    }

    public void setBibliotheques(Bibliotheques bibliotheques) {
        this.bibliotheques = bibliotheques;
    }

    public boolean isMailEnvoye() {
        return mailEnvoye;
    }

    public void setMailEnvoye(boolean mailEnvoye) {
        this.mailEnvoye = mailEnvoye;
    }
}
