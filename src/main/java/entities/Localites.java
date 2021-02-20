package entities;

import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
import javax.persistence.*;
import java.io.Serializable;

@Entity
@Named
@SessionScoped
public class Localites implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "IdLocalites", nullable = false)
    private Integer idLocalites;

    @Column(name = "CP", nullable = false)
    private Integer CP;

    @Column(name = "Ville", nullable = false)
    private String ville;

    @Column(name = "PaysIdPays", nullable = false)
    private Integer paysIdPays;

    public void setIdLocalites(Integer idLocalites) {
        this.idLocalites = idLocalites;
    }

    public Integer getIdLocalites() {
        return idLocalites;
    }

    public void setCP(Integer CP) {
        this.CP = CP;
    }

    public Integer getCP() {
        return CP;
    }

    public void setVille(String ville) {
        this.ville = ville;
    }

    public String getVille() {
        return ville;
    }

    public void setPaysIdPays(Integer paysIdPays) {
        this.paysIdPays = paysIdPays;
    }

    public Integer getPaysIdPays() {
        return paysIdPays;
    }

    @Override
    public String toString() {
        return "Localites{" +
                "idLocalites=" + idLocalites + '\'' +
                "CP=" + CP + '\'' +
                "ville=" + ville + '\'' +
                "paysIdPays=" + paysIdPays + '\'' +
                '}';
    }
}
