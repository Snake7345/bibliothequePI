package entities;

import javax.persistence.*;
import java.util.Objects;
@Entity
@Table(name = "livres_auteurs", schema = "bibliotheque")
@NamedQueries
        ({
                @NamedQuery(name = "LivresAuteurs.findAll", query = "SELECT la FROM LivresAuteurs la"),
        })

public class LivresAuteurs {

    @Basic
    @Column(name = "LivresIdLivres", nullable = false)
    private int livresIdLivres;
    public int getLivresIdLivres() {
        return livresIdLivres;
    }

    public void setLivresIdLivres(int livresIdLivres) {
        this.livresIdLivres = livresIdLivres;
    }

    @Basic
    @Column(name = "AuteursIdAuteurs", nullable = false)
    private int auteursIdAuteurs;
    public int getAuteursIdAuteurs() {
        return auteursIdAuteurs;
    }

    public void setAuteursIdAuteurs(int auteursIdAuteurs) {
        this.auteursIdAuteurs = auteursIdAuteurs;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "IdLivresAuteurs", nullable = false)
    private int idLivresAuteurs;
    public int getIdLivresAuteurs() {
        return idLivresAuteurs;
    }

    public void setIdLivresAuteurs(int idLivresAuteurs) {
        this.idLivresAuteurs = idLivresAuteurs;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        LivresAuteurs that = (LivresAuteurs) o;
        return livresIdLivres == that.livresIdLivres &&
                auteursIdAuteurs == that.auteursIdAuteurs &&
                idLivresAuteurs == that.idLivresAuteurs;
    }

    @Override
    public int hashCode() {
        return Objects.hash(livresIdLivres, auteursIdAuteurs, idLivresAuteurs);
    }

    @ManyToOne
    @JoinColumn(name = "LivresIdLivres", referencedColumnName = "IdLivres", nullable = false)
    private Livres livresByLivresIdLivres;
    public Livres getLivresByLivresIdLivres() {
        return livresByLivresIdLivres;
    }

    public void setLivresByLivresIdLivres(Livres livresByLivresIdLivres) {
        this.livresByLivresIdLivres = livresByLivresIdLivres;
    }

    @ManyToOne
    @JoinColumn(name = "AuteursIdAuteurs", referencedColumnName = "IdAuteurs", nullable = false)
    private Auteurs auteursByAuteursIdAuteurs;
    public Auteurs getAuteursByAuteursIdAuteurs() {
        return auteursByAuteursIdAuteurs;
    }

    public void setAuteursByAuteursIdAuteurs(Auteurs auteursByAuteursIdAuteurs) {
        this.auteursByAuteursIdAuteurs = auteursByAuteursIdAuteurs;
    }
}
