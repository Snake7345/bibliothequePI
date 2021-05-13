package entities;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Collection;
import java.util.Objects;

@Entity
@Table(name = "localites")
@NamedQueries
        ({
                @NamedQuery(name = "Localites.findAll", query = "SELECT l FROM Localites l"),
                @NamedQuery(name= " Localites.findAllTri", query="SELECT l FROM Localites l ORDER BY l.ville ASC"),
        })
public class Localites implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idLocalites;
    private int cp;
    private String ville;
    @OneToMany(mappedBy = "localites")
    private Collection<Adresses> adresses;


    @Id
    @Column(name = "IdLocalites", nullable = false)
    public int getIdLocalites() {
        return idLocalites;
    }

    public void setIdLocalites(int idLocalites) {
        this.idLocalites = idLocalites;
    }

    @Basic
    @Column(name = "CP", nullable = false)
    public int getCp() {
        return cp;
    }

    public void setCp(int cp) {
        this.cp = cp;
    }

    @Basic
    @Column(name = "Ville", nullable = false, length = 255)
    public String getVille() {
        return ville;
    }

    public void setVille(String ville) {
        this.ville = ville;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Localites localites = (Localites) o;
        return idLocalites == localites.idLocalites &&
                cp == localites.cp &&
                Objects.equals(ville, localites.ville);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idLocalites, cp, ville);
    }


    public Collection<Adresses> getAdresses() {
        return adresses;
    }

    public void setAdresses(Collection<Adresses> adresses1) {
        this.adresses = adresses1;
    }

    @ManyToOne
    @JoinColumn(name = "PaysIdPays", referencedColumnName = "IdPays", nullable = false)
    private Pays pays;
    public Pays getPays() {
        return pays;
    }

    public void setPays(Pays paysByPaysIdPays) {
        this.pays = paysByPaysIdPays;
    }
}