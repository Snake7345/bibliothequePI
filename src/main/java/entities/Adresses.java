package entities;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "adresses")
public class Adresses implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "IdAdresses", nullable = false)
    private Integer idAdresses;

    @Column(name = "Rue", nullable = false)
    private String rue;

    @Column(name = "Boite")
    private String boite;

    @Column(name = "Numero", nullable = false)
    private String numero;

    @Column(name = "LocalitesIdLocalites", nullable = false)
    private Integer localitesIdLocalites;

    @Column(name = "BibliothequesIdBibliotheques")
    private Integer bibliothequesIdBibliotheques;

    public void setIdAdresses(Integer idAdresses) {
        this.idAdresses = idAdresses;
    }

    public Integer getIdAdresses() {
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

    public void setLocalitesIdLocalites(Integer localitesIdLocalites) {
        this.localitesIdLocalites = localitesIdLocalites;
    }

    public Integer getLocalitesIdLocalites() {
        return localitesIdLocalites;
    }

    public void setBibliothequesIdBibliotheques(Integer bibliothequesIdBibliotheques) {
        this.bibliothequesIdBibliotheques = bibliothequesIdBibliotheques;
    }

    public Integer getBibliothequesIdBibliotheques() {
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
