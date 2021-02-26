package entities;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "bibliotheques")
public class Bibliotheques implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "IdBibliotheques", nullable = false)
    private Integer idBibliotheques;

    @Column(name = "Nom", nullable = false)
    private String nom;

    public void setIdBibliotheques(Integer idBibliotheques) {
        this.idBibliotheques = idBibliotheques;
    }

    public Integer getIdBibliotheques() {
        return idBibliotheques;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getNom() {
        return nom;
    }

    @Override
    public String toString() {
        return "Bibliotheques{" +
                "idBibliotheques=" + idBibliotheques + '\'' +
                "nom=" + nom + '\'' +
                '}';
    }
}
