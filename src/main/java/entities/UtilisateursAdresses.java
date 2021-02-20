package entities;

import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
import javax.persistence.*;
import java.io.Serializable;

@Entity
@Named
@SessionScoped
public class UtilisateursAdresses implements Serializable {

    private static final long serialVersionUID = 1L;

    //@Column(name = "UtilisateursIdUtilisateurs", nullable = false)
    private Integer utilisateursIdUtilisateurs;

    //@Column(name = "AdressesIdAdresses", nullable = false)
    private Integer adressesIdAdresses;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    //@Column(name = "IdUtilisateursAdresses", nullable = false)
    private Integer idUtilisateursAdresses;

    public void setUtilisateursIdUtilisateurs(Integer utilisateursIdUtilisateurs) {
        this.utilisateursIdUtilisateurs = utilisateursIdUtilisateurs;
    }

    public Integer getUtilisateursIdUtilisateurs() {
        return utilisateursIdUtilisateurs;
    }

    public void setAdressesIdAdresses(Integer adressesIdAdresses) {
        this.adressesIdAdresses = adressesIdAdresses;
    }

    public Integer getAdressesIdAdresses() {
        return adressesIdAdresses;
    }

    public void setIdUtilisateursAdresses(Integer idUtilisateursAdresses) {
        this.idUtilisateursAdresses = idUtilisateursAdresses;
    }

    public Integer getIdUtilisateursAdresses() {
        return idUtilisateursAdresses;
    }

    @Override
    public String toString() {
        return "UtilisateursAdresses{" +
                "utilisateursIdUtilisateurs=" + utilisateursIdUtilisateurs + '\'' +
                "adressesIdAdresses=" + adressesIdAdresses + '\'' +
                "idUtilisateursAdresses=" + idUtilisateursAdresses + '\'' +
                '}';
    }
}
