package managedBean;

import entities.Genres;
import org.apache.log4j.Logger;
import services.SvcGenres;

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
    // Déclaration des variables globales
    private static final long serialVersionUID = 1L;
    private Genres genre;
    private static final Logger log = Logger.getLogger(GenresBean.class);

    @PostConstruct
    public void init()
    {
        genre = new Genres();
    }

    public String newGenres()
    {
        SvcGenres service = new SvcGenres();
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
                fc.addMessage("erreur", new FacesMessage("le rollback a pris le relais"));

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

    public void save()
    {
        SvcGenres service = new SvcGenres();
        EntityTransaction transaction = service.getTransaction();
        transaction.begin();
        try {
            service.save(genre);
            transaction.commit();
            FacesContext fc = FacesContext.getCurrentInstance();
            fc.addMessage("ModifRe", new FacesMessage("Modification réussie"));
        } finally {
            if (transaction.isActive()) {
                transaction.rollback();
                FacesContext fc = FacesContext.getCurrentInstance();
                fc.addMessage("Erreur", new FacesMessage("le rollback a pris le relais"));
            }
            service.close();
        }

    }

    public String flushGen()
    {
        init();
        return "tableGenres?faces-redirect=true";
    }

    public List<Genres> getReadAll()
    {
        SvcGenres service = new SvcGenres();
        List<Genres> listGenres = new ArrayList<Genres>();
        listGenres= service.findAllGenres();

        service.close();
        return listGenres;
    }

    //-------------------------------Getter & Setter--------------------------------------------

    public Genres getGenre() {
        return genre;
    }

    public void setGenre(Genres genre) {
        this.genre = genre;
    }

}
