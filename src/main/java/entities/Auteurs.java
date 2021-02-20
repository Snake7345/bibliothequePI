package entities;

import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
import javax.persistence.*;
import java.io.Serializable;

@Entity
@Named
@SessionScoped
public class Auteurs implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    //@Column(name = "IdAuteurs", nullable = false)
    private Integer idAuteurs;

    //@Column(name = "Nom", nullable = false)
    private String nom;

    //@Column(name = "Prenom", nullable = false)
    private String prenom;

    public void setIdAuteurs(Integer idAuteurs) {
        this.idAuteurs = idAuteurs;
    }

    public Integer getIdAuteurs() {
        return idAuteurs;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getNom() {
        return nom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public String getPrenom() {
        return prenom;
    }

    @Override
    public String toString() {
        return "Auteurs{" +
                "idAuteurs=" + idAuteurs + '\'' +
                "nom=" + nom + '\'' +
                "prenom=" + prenom + '\'' +
                '}';
    }
}
