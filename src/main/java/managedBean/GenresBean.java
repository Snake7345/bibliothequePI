package managedBean;

import entities.Auteurs;
import entities.Facture;
import entities.FactureDetail;
import entities.Genres;
import org.apache.log4j.Logger;
import services.SvcFacture;
import services.SvcGenres;

import javax.annotation.ManagedBean;
import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.persistence.EntityTransaction;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Named
@SessionScoped
public class GenresBean implements Serializable {
    private static final long serialVersionUID = 1L;
    private Genres genre;
    private final SvcGenres service = new SvcGenres();
    private static final Logger log = Logger.getLogger(GenresBean.class);

    @PostConstruct
    public void init()
    {
        genre = new Genres();
    }

    public String newGenres()
    {
        EntityTransaction transaction = service.getTransaction();
        //Todo mettre/faire une verification de l'objet utilisateur,
        log.debug("J'vais essayer d'sauver le genre");
        transaction.begin();

        try {

            service.save(genre);

            transaction.commit();
            log.debug("J'ai sauvé le genre");
            return "/tableGenres.xhtml?faces-redirect=true";
        } finally {
            if (transaction.isActive()) {
                transaction.rollback();
                log.debug("J'ai fait une erreur et je suis con");
                FacesContext fc = FacesContext.getCurrentInstance();
                fc.addMessage("erreur", new FacesMessage("Erreur inconnue"));

                return "";
            }
            else
            {
                log.debug("je suis censé avoir réussi");
                init();
            }

            service.close();
        }

    }

    public List<Genres> getReadAll()
    {
        List<Genres> listGenres = new ArrayList<Genres>();
        listGenres= service.findAllGenres();


        return listGenres;
    }

    public Genres getGenre() {
        return genre;
    }

    public void setGenre(Genres genre) {
        this.genre = genre;
    }

}
