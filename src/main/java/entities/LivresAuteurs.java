package entities;

import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
import javax.persistence.*;
import java.io.Serializable;

@Entity
@Named
@SessionScoped
public class LivresAuteurs implements Serializable {

    private static final long serialVersionUID = 1L;

    @Column(name = "LivresIdLivres", nullable = false)
    private Integer livresIdLivres;

    @Column(name = "AuteursIdAuteurs", nullable = false)
    private Integer auteursIdAuteurs;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "IdLivresAuteurs", nullable = false)
    private Integer idLivresAuteurs;

    public void setLivresIdLivres(Integer livresIdLivres) {
        this.livresIdLivres = livresIdLivres;
    }

    public Integer getLivresIdLivres() {
        return livresIdLivres;
    }

    public void setAuteursIdAuteurs(Integer auteursIdAuteurs) {
        this.auteursIdAuteurs = auteursIdAuteurs;
    }

    public Integer getAuteursIdAuteurs() {
        return auteursIdAuteurs;
    }

    public void setIdLivresAuteurs(Integer idLivresAuteurs) {
        this.idLivresAuteurs = idLivresAuteurs;
    }

    public Integer getIdLivresAuteurs() {
        return idLivresAuteurs;
    }

    @Override
    public String toString() {
        return "LivresAuteurs{" +
                "livresIdLivres=" + livresIdLivres + '\'' +
                "auteursIdAuteurs=" + auteursIdAuteurs + '\'' +
                "idLivresAuteurs=" + idLivresAuteurs + '\'' +
                '}';
    }
}
