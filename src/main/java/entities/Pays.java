package entities;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "pays")
public class Pays implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "IdPays", nullable = false)
    private Integer idPays;

    @Column(name = "Nom", nullable = false)
    private String nom;

    public void setIdPays(Integer idPays) {
        this.idPays = idPays;
    }

    public Integer getIdPays() {
        return idPays;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getNom() {
        return nom;
    }

    @Override
    public String toString() {
        return "Pays{" +
                "idPays=" + idPays + '\'' +
                "nom=" + nom + '\'' +
                '}';
    }
}
