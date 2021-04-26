package entities;

import javax.persistence.*;
import java.util.Collection;
import java.util.Objects;

@Entity
@Table(name = "pays")
@NamedQueries
        ({
                @NamedQuery(name = "Pays.findAll", query = "SELECT p FROM Pays p"),
                @NamedQuery(name = "Pays.findAllTri", query="SELECT p FROM Pays p ORDER BY p.nom ASC"),
        })

public class Pays {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idPays;
    private String nom;
    private Collection<Localites> localitesByIdPays;

    @Id
    @Column(name = "IdPays", nullable = false)
    public int getIdPays() {
        return idPays;
    }

    public void setIdPays(int idPays) {
        this.idPays = idPays;
    }

    @Basic
    @Column(name = "Nom", nullable = false, length = 150)
    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

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

    @OneToMany(mappedBy = "paysByPaysIdPays")
    public Collection<Localites> getLocalitesByIdPays() {
        return localitesByIdPays;
    }

    public void setLocalitesByIdPays(Collection<Localites> localitesByIdPays) {
        this.localitesByIdPays = localitesByIdPays;
    }
}
