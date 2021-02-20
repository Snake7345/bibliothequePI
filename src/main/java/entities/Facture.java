package entities;

import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;

@Entity
@Named
@SessionScoped
public class Facture implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    //@Column(name = "IdLocations", nullable = false)
    private Integer idLocations;

    //@Column(name = "DateDebut", nullable = false)
    private Timestamp dateDebut;

    //@Column(name = "PrixTVAC")
    private Double prixTVAC;

    //@Column(name = "NumeroFacture")
    private String numeroFacture;

    //@Column(name = "Etat", nullable = false)
    private String etat;

    //@Column(name = "UtilisateursIdUtilisateurs", nullable = false)
    private Integer utilisateursIdUtilisateurs;

    public void setIdLocations(Integer idLocations) {
        this.idLocations = idLocations;
    }

    public Integer getIdLocations() {
        return idLocations;
    }

    public void setDateDebut(Timestamp dateDebut) {
        this.dateDebut = dateDebut;
    }

    public Timestamp getDateDebut() {
        return dateDebut;
    }

    public void setPrixTVAC(Double prixTVAC) {
        this.prixTVAC = prixTVAC;
    }

    public Double getPrixTVAC() {
        return prixTVAC;
    }

    public void setNumeroFacture(String numeroFacture) {
        this.numeroFacture = numeroFacture;
    }

    public String getNumeroFacture() {
        return numeroFacture;
    }

    public void setEtat(String etat) {
        this.etat = etat;
    }

    public String getEtat() {
        return etat;
    }

    public void setUtilisateursIdUtilisateurs(Integer utilisateursIdUtilisateurs) {
        this.utilisateursIdUtilisateurs = utilisateursIdUtilisateurs;
    }

    public Integer getUtilisateursIdUtilisateurs() {
        return utilisateursIdUtilisateurs;
    }

    @Override
    public String toString() {
        return "Facture{" +
                "idLocations=" + idLocations + '\'' +
                "dateDebut=" + dateDebut + '\'' +
                "prixTVAC=" + prixTVAC + '\'' +
                "numeroFacture=" + numeroFacture + '\'' +
                "etat=" + etat + '\'' +
                "utilisateursIdUtilisateurs=" + utilisateursIdUtilisateurs + '\'' +
                '}';
    }
}
