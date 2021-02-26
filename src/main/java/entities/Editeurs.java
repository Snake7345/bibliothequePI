package entities;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "editeurs")
public class Editeurs implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "IdEditeurs", nullable = false)
    private Integer idEditeurs;

    @Column(name = "Nom", nullable = false)
    private String nom;

    public void setIdEditeurs(Integer idEditeurs) {
        this.idEditeurs = idEditeurs;
    }

    public Integer getIdEditeurs() {
        return idEditeurs;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getNom() {
        return nom;
    }

    @Override
    public String toString() {
        return "Editeurs{" +
                "idEditeurs=" + idEditeurs + '\'' +
                "nom=" + nom + '\'' +
                '}';
    }
}
