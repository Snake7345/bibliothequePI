package entities;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Collection;
import java.util.Objects;

@Entity
@Table(name="genres")
@NamedQueries
        ({
                @NamedQuery(name = "Genres.findAll", query = "SELECT g FROM Genres g"),
                @NamedQuery(name = "Genres.findOne", query ="SELECT g FROM Genres g WHERE g.denomination=:denomination"),
                @NamedQuery(name = "Genres.findAllTri", query = "SELECT g FROM Genres g ORDER BY g.denomination ASC"),
        })
public class Genres implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idGenres;
    private String denomination;
    @OneToMany(mappedBy = "genre")
    private Collection<LivresGenres> livresGenres;

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


    public Collection<LivresGenres> getLivresGenres() {
        return livresGenres;
    }

    public void setLivresGenres(Collection<LivresGenres> livresGenres) {
        this.livresGenres = livresGenres;
    }

    @Override
    public Genres clone(){
        Genres gen = null;
        try{
            gen = (Genres) super.clone();
        }catch (CloneNotSupportedException e) {
            e.printStackTrace(System.err);
        }
        return gen;
    }

    public void setFields(Genres gen) {
        this.denomination = gen.denomination;
        this.livresGenres = gen.livresGenres;
    }
}
