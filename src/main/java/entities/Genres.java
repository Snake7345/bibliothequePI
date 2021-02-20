package entities;

import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
import javax.persistence.*;
import java.io.Serializable;

@Entity
@Named
@SessionScoped
public class Genres implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "IdGenres", nullable = false)
    private Integer idGenres;

    @Column(name = "Denomination", nullable = false)
    private String denomination;

    public void setIdGenres(Integer idGenres) {
        this.idGenres = idGenres;
    }

    public Integer getIdGenres() {
        return idGenres;
    }

    public void setDenomination(String denomination) {
        this.denomination = denomination;
    }

    public String getDenomination() {
        return denomination;
    }

    @Override
    public String toString() {
        return "Genres{" +
                "idGenres=" + idGenres + '\'' +
                "denomination=" + denomination + '\'' +
                '}';
    }
}
