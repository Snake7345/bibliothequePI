package entities;

import javax.persistence.*;
import java.util.Collection;
import java.util.Objects;

@Entity
@Table(name = "localites")
@NamedQueries
        ({
                @NamedQuery(name = "Localites.findAll", query = "SELECT l FROM Localites l"),
                @NamedQuery(name= " Localites.findAllTri", query="SELECT l FROM Localites l ORDER BY l.ville ASC"),
        })
public class Localites {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "IdLocalites", nullable = false)
    private int idLocalites;
    public int getIdLocalites() {
        return idLocalites;
    }

    public void setIdLocalites(int idLocalites) {
        this.idLocalites = idLocalites;
    }

    @Basic
    @Column(name = "CP", nullable = false)
    private int cp;
    public int getCp() {
        return cp;
    }

    public void setCp(int cp) {
        this.cp = cp;
    }

    @Basic
    @Column(name = "Ville", nullable = false, length = 255)
    private String ville;
    public String getVille() {
        return ville;
    }

    public void setVille(String ville) {
        this.ville = ville;
    }

    @Basic
    @Column(name = "PaysIdPays", nullable = false)
    private int paysIdPays;
    public int getPaysIdPays() {
        return paysIdPays;
    }

    public void setPaysIdPays(int paysIdPays) {
        this.paysIdPays = paysIdPays;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Localites localites = (Localites) o;
        return idLocalites == localites.idLocalites &&
                cp == localites.cp &&
                paysIdPays == localites.paysIdPays &&
                Objects.equals(ville, localites.ville);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idLocalites, cp, ville, paysIdPays);
    }

    @OneToMany(mappedBy = "localitesByLocalitesIdLocalites")
    private Collection<Adresses> adressesByIdLocalites;
    public Collection<Adresses> getAdressesByIdLocalites() {
        return adressesByIdLocalites;
    }

    public void setAdressesByIdLocalites(Collection<Adresses> adressesByIdLocalites) {
        this.adressesByIdLocalites = adressesByIdLocalites;
    }

    @ManyToOne
    @JoinColumn(name = "PaysIdPays", referencedColumnName = "IdPays", nullable = false)
    private Pays paysByPaysIdPays;
    public Pays getPaysByPaysIdPays() {
        return paysByPaysIdPays;
    }

    public void setPaysByPaysIdPays(Pays paysByPaysIdPays) {
        this.paysByPaysIdPays = paysByPaysIdPays;
    }
}
