package entities;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "livres_genres", schema = "bibliotheque")
@NamedQueries
        ({
                @NamedQuery(name = "LivresGenres.findAll", query = "SELECT lg FROM LivresGenres lg"),
                @NamedQuery(name = "LivresGenres.findBylivre", query = "SELECT lg FROM LivresGenres lg where lg.livre=:livre"),

        })
public class LivresGenres implements Serializable {
    private static final long serialVersionUID = 1L;


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "IdLivresGenres", nullable = false)
    private int idLivresGenres;
    @ManyToOne
    @JoinColumn(name = "LivresIdLivres", nullable = false)
    private Livres livre;
    @ManyToOne
    @JoinColumn(name = "GenresIdGenres", nullable = false)
    private Genres genre;

    public int getIdLivresGenres() {
        return idLivresGenres;
    }

    public void setIdLivresGenres(int idLivresGenres) {
        this.idLivresGenres = idLivresGenres;
    }

    public Livres getLivre() {
        return livre;
    }

    public void setLivre(Livres livre) {
        this.livre = livre;
    }

    public Genres getGenre() {
        return genre;
    }

    public void setGenre(Genres genre) {
        this.genre = genre;
    }


}
