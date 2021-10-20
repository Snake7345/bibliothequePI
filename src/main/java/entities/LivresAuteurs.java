package entities;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "livres_auteurs", schema = "bibliotheque")
@NamedQueries
        ({
                @NamedQuery(name = "LivresAuteurs.findAll", query = "SELECT la FROM LivresAuteurs la"),
                @NamedQuery(name = "LivresAuteurs.findBylivre", query = "SELECT la FROM LivresAuteurs la where la.livre=:livre"),
        })
public class LivresAuteurs implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "IdLivresAuteurs", nullable = false)
    private int IdLivresAuteurs;
    @ManyToOne
    @JoinColumn(name = "AuteursIdAuteurs", nullable = false)
    private Auteurs auteur;
    @ManyToOne
    @JoinColumn(name = "LivresIdLivres", nullable = false)
    private Livres livre;

    //-------------------------------Getter & Setter--------------------------------------------

    public int getIdLivresAuteurs() {
        return IdLivresAuteurs;
    }

    public void setIdLivresAuteurs(int idLivresAuteurs) {
        IdLivresAuteurs = idLivresAuteurs;
    }

    public Auteurs getAuteur() {
        return auteur;
    }

    public void setAuteur(Auteurs auteur) {
        this.auteur = auteur;
    }

    public Livres getLivre() {
        return livre;
    }

    public void setLivre(Livres livre) {
        this.livre = livre;
    }
}
