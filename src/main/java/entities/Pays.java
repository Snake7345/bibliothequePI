package entities;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Collection;
import java.util.Objects;

@Entity
@Table(name = "pays")
@NamedQueries
        ({
                @NamedQuery(name = "Pays.findAll", query = "SELECT p FROM Pays p"),
                @NamedQuery(name = "Pays.findAllTri", query="SELECT p FROM Pays p ORDER BY p.nom ASC"),
        })

public class Pays implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "IdPays", nullable = false)
    private int idPays;
    @Basic
    @Column(name = "Nom", nullable = false, length = 150)
    private String nom;
    @OneToMany(mappedBy = "pays")
    private Collection<Localites> localites;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Pays pays = (Pays) o;
        return idPays == pays.idPays &&
                Objects.equals(nom, pays.nom);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idPays, nom);
    }

    //-------------------------------Getter & Setter--------------------------------------------

    public Collection<Localites> getLocalites() {
        return localites;
    }

    public void setLocalites(Collection<Localites> localites) {
        this.localites = localites;
    }

    public int getIdPays() {
        return idPays;
    }

    public void setIdPays(int idPays) {
        this.idPays = idPays;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }
}
