package entities;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

@Entity
@Table(name = "livres_genres", schema = "bibliotheque")
@NamedQueries
        ({
                @NamedQuery(name = "LivresGenres.findAll", query = "SELECT lg FROM LivresGenres lg"),
        })
public class LivresGenres implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int livresIdLivres;
    private int genresIdGenres;
    private int idLivresGenres;
    private Livres livresByLivresIdLivres;
    private Genres genresByGenresIdGenres;

    @Basic
    @Column(name = "LivresIdLivres", nullable = false)
    public int getLivresIdLivres() {
        return livresIdLivres;
    }

    public void setLivresIdLivres(int livresIdLivres) {
        this.livresIdLivres = livresIdLivres;
    }

    @Basic
    @Column(name = "GenresIdGenres", nullable = false)
    public int getGenresIdGenres() {
        return genresIdGenres;
    }

    public void setGenresIdGenres(int genresIdGenres) {
        this.genresIdGenres = genresIdGenres;
    }

    @Id
    @Column(name = "IdLivresGenres", nullable = false)
    public int getIdLivresGenres() {
        return idLivresGenres;
    }

    public void setIdLivresGenres(int idLivresGenres) {
        this.idLivresGenres = idLivresGenres;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        LivresGenres that = (LivresGenres) o;
        return livresIdLivres == that.livresIdLivres &&
                genresIdGenres == that.genresIdGenres &&
                idLivresGenres == that.idLivresGenres;
    }

    @Override
    public int hashCode() {
        return Objects.hash(livresIdLivres, genresIdGenres, idLivresGenres);
    }

    @ManyToOne
    @JoinColumn(name = "LivresIdLivres", referencedColumnName = "IdLivres", nullable = false)
    public Livres getLivresByLivresIdLivres() {
        return livresByLivresIdLivres;
    }

    public void setLivresByLivresIdLivres(Livres livresByLivresIdLivres) {
        this.livresByLivresIdLivres = livresByLivresIdLivres;
    }

    @ManyToOne
    @JoinColumn(name = "GenresIdGenres", referencedColumnName = "IdGenres", nullable = false)
    public Genres getGenresByGenresIdGenres() {
        return genresByGenresIdGenres;
    }

    public void setGenresByGenresIdGenres(Genres genresByGenresIdGenres) {
        this.genresByGenresIdGenres = genresByGenresIdGenres;
    }
}
