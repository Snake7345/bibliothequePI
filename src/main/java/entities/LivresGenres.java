package entities;

import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
import javax.persistence.*;
import java.io.Serializable;

@Entity
@Named
@SessionScoped
public class LivresGenres implements Serializable {

    private static final long serialVersionUID = 1L;

    @Column(name = "LivresIdLivres", nullable = false)
    private Integer livresIdLivres;

    @Column(name = "GenresIdGenres", nullable = false)
    private Integer genresIdGenres;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "IdLivresGenres", nullable = false)
    private Integer idLivresGenres;

    public void setLivresIdLivres(Integer livresIdLivres) {
        this.livresIdLivres = livresIdLivres;
    }

    public Integer getLivresIdLivres() {
        return livresIdLivres;
    }

    public void setGenresIdGenres(Integer genresIdGenres) {
        this.genresIdGenres = genresIdGenres;
    }

    public Integer getGenresIdGenres() {
        return genresIdGenres;
    }

    public void setIdLivresGenres(Integer idLivresGenres) {
        this.idLivresGenres = idLivresGenres;
    }

    public Integer getIdLivresGenres() {
        return idLivresGenres;
    }

    @Override
    public String toString() {
        return "LivresGenres{" +
                "livresIdLivres=" + livresIdLivres + '\'' +
                "genresIdGenres=" + genresIdGenres + '\'' +
                "idLivresGenres=" + idLivresGenres + '\'' +
                '}';
    }
}
