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
import java.io.IOException;
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

    public String  newGenres()
    {
        save();
        init();
        return "/tableGenres?faces-redirect=true";
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
            fc.getExternalContext().getFlash().setKeepMessages(true);
            fc.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,"L'operation a reussie",null));
        } finally {
            if (transaction.isActive()) {
                transaction.rollback();
                FacesContext fc = FacesContext.getCurrentInstance();
                fc.getExternalContext().getFlash().setKeepMessages(true);
                fc.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,"l'operation n'est pas reussie",null));
            }
            service.close();
        }

    }


    public String flushGen()
    {
        init();
        return "/tableGenres?faces-redirect=true";
    }

    /*
     * Méthode qui permet via le service de retourner la liste de tous les genres
     */
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
