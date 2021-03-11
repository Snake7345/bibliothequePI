package entities;

import javax.persistence.*;
import java.util.Collection;
import java.util.Objects;

@Entity
public class Genres {
    private int idGenres;
    private String denomination;
    private Collection<LivresGenres> livresGenresByIdGenres;

    @Id
    @Column(name = "IdGenres", nullable = false)
    public int getIdGenres() {
        return idGenres;
    }

    public void setIdGenres(int idGenres) {
        this.idGenres = idGenres;
    }

    @Basic
    @Column(name = "Denomination", nullable = false, length = 255)
    public String getDenomination() {
        return denomination;
    }

    public void setDenomination(String denomination) {
        this.denomination = denomination;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Genres genres = (Genres) o;
        return idGenres == genres.idGenres &&
                Objects.equals(denomination, genres.denomination);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idGenres, denomination);
    }

    @OneToMany(mappedBy = "genresByGenresIdGenres")
    public Collection<LivresGenres> getLivresGenresByIdGenres() {
        return livresGenresByIdGenres;
    }

    public void setLivresGenresByIdGenres(Collection<LivresGenres> livresGenresByIdGenres) {
        this.livresGenresByIdGenres = livresGenresByIdGenres;
    }
}
