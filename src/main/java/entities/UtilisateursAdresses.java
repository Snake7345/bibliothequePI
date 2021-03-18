package entities;

import javax.persistence.*;
import java.util.Objects;
@Entity
@Table(name = "utilisateurs_adresses", schema = "bibliotheque")
@NamedQueries
        ({
                @NamedQuery(name = "UtilisateursAdresses.findAll", query = "SELECT u FROM UtilisateursAdresses u"),
        })

public class UtilisateursAdresses {
    private int utilisateursIdUtilisateurs;
    private int adressesIdAdresses;
    private int idUtilisateursAdresses;
    private Utilisateurs utilisateursByUtilisateursIdUtilisateurs;
    private Adresses adressesByAdressesIdAdresses;

    @Basic
    @Column(name = "UtilisateursIdUtilisateurs", nullable = false)
    public int getUtilisateursIdUtilisateurs() {
        return utilisateursIdUtilisateurs;
    }

    public void setUtilisateursIdUtilisateurs(int utilisateursIdUtilisateurs) {
        this.utilisateursIdUtilisateurs = utilisateursIdUtilisateurs;
    }

    @Basic
    @Column(name = "AdressesIdAdresses", nullable = false)
    public int getAdressesIdAdresses() {
        return adressesIdAdresses;
    }

    public void setAdressesIdAdresses(int adressesIdAdresses) {
        this.adressesIdAdresses = adressesIdAdresses;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "IdUtilisateursAdresses", nullable = false)
    public int getIdUtilisateursAdresses() {
        return idUtilisateursAdresses;
    }

    public void setIdUtilisateursAdresses(int idUtilisateursAdresses) {
        this.idUtilisateursAdresses = idUtilisateursAdresses;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UtilisateursAdresses that = (UtilisateursAdresses) o;
        return utilisateursIdUtilisateurs == that.utilisateursIdUtilisateurs &&
                adressesIdAdresses == that.adressesIdAdresses &&
                idUtilisateursAdresses == that.idUtilisateursAdresses;
    }

    @Override
    public int hashCode() {
        return Objects.hash(utilisateursIdUtilisateurs, adressesIdAdresses, idUtilisateursAdresses);
    }

    @ManyToOne
    @JoinColumn(name = "UtilisateursIdUtilisateurs", referencedColumnName = "IdUtilisateurs", nullable = false)
    public Utilisateurs getUtilisateursByUtilisateursIdUtilisateurs() {
        return utilisateursByUtilisateursIdUtilisateurs;
    }

    public void setUtilisateursByUtilisateursIdUtilisateurs(Utilisateurs utilisateursByUtilisateursIdUtilisateurs) {
        this.utilisateursByUtilisateursIdUtilisateurs = utilisateursByUtilisateursIdUtilisateurs;
    }

    @ManyToOne
    @JoinColumn(name = "AdressesIdAdresses", referencedColumnName = "IdAdresses", nullable = false)
    public Adresses getAdressesByAdressesIdAdresses() {
        return adressesByAdressesIdAdresses;
    }

    public void setAdressesByAdressesIdAdresses(Adresses adressesByAdressesIdAdresses) {
        this.adressesByAdressesIdAdresses = adressesByAdressesIdAdresses;
    }
}
