package managedBean;

import entities.Genres;
import org.apache.log4j.Logger;
import org.primefaces.event.UnselectEvent;
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

    // Méthode qui va appellé la méthode save() pour créer/modifier un genre en DB et qui envoi un message si jamais le nom du genre existe en DB et nous renvoi sur la table des genres
    public String saveGenres()
    {
        if(verifGenreExist(genre))
        {
            save();
        }
        else{
            FacesContext fc = FacesContext.getCurrentInstance();
            fc.getExternalContext().getFlash().setKeepMessages(true);
            fc.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,"La donnée est déjà existante en DB",null));
        }
        init();
        return "/tableGenres?faces-redirect=true";
    }

    public void onItemUnselect(UnselectEvent event) {
        FacesContext context = FacesContext.getCurrentInstance();

        FacesMessage msg = new FacesMessage();
        msg.setSummary("Item unselected: " + event.getObject().toString());
        msg.setSeverity(FacesMessage.SEVERITY_INFO);

        context.addMessage(null, msg);
    }

    // Méthode qui permet la sauvegarde du genre dans la base de donnée.
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
            fc.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,"L'opération a réussie",null));
        } finally {
            if (transaction.isActive()) {
                transaction.rollback();
                FacesContext fc = FacesContext.getCurrentInstance();
                fc.getExternalContext().getFlash().setKeepMessages(true);
                fc.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,"l'opération n'est pas reussie",null));
            }
            service.close();
        }

    }

    public boolean verifGenreExist(Genres gen)
    {
        SvcGenres serviceG = new SvcGenres();
        if(serviceG.findOneGenre(gen).size() > 0)
        {
            serviceG.close();
            return false;
        }
        else {
            serviceG.close();
            return true;
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
