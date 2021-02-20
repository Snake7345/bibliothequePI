package entities;

import javax.persistence.*;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
import java.io.Serializable;

@Entity
@Named
@SessionScoped
public class Adresses implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    //@Column(name = "IdAdresses", nullable = false)
    private int idAdresses;

    //@Column(name = "Rue", nullable = false)
    private String rue;

    //@Column(name = "Boite")
    private String boite;

    //@Column(name = "Numero", nullable = false)

    private String numero;

    //@Column(name = "LocalitesIdLocalites", nullable = false)
    @ManyToOne
    private Localites localitesIdLocalites;

    //@Column(name = "BibliothequesIdBibliotheques")
    @OneToOne
    private Bibliotheques bibliothequesIdBibliotheques;

    public void setIdAdresses(int idAdresses) {
        this.idAdresses = idAdresses;
    }

    public int getIdAdresses() {
        return idAdresses;
    }

    public void setRue(String rue) {
        this.rue = rue;
    }

    public String getRue() {
        return rue;
    }

    public void setBoite(String boite) {
        this.boite = boite;
    }

    public String getBoite() {
        return boite;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public String getNumero() {
        return numero;
    }

    public void setLocalitesIdLocalites(Localites localitesIdLocalites) {
        this.localitesIdLocalites = localitesIdLocalites;
    }

    public int getLocalitesIdLocalites() {
        return localitesIdLocalites;
    }

    public void setBibliothequesIdBibliotheques(Bibliotheques bibliothequesIdBibliotheques) {
        this.bibliothequesIdBibliotheques = bibliothequesIdBibliotheques;
    }

    public int getBibliothequesIdBibliotheques() {
        return bibliothequesIdBibliotheques;
    }

    @Override
    public String toString() {
        return "Adresses{" +
                "idAdresses=" + idAdresses + '\'' +
                "rue=" + rue + '\'' +
                "boite=" + boite + '\'' +
                "numero=" + numero + '\'' +
                "localitesIdLocalites=" + localitesIdLocalites + '\'' +
                "bibliothequesIdBibliotheques=" + bibliothequesIdBibliotheques + '\'' +
                '}';
    }
}
