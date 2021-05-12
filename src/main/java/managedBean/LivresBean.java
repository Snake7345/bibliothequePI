package managedBean;

import entities.Auteurs;
import entities.Facture;
import entities.Jours;
import entities.Livres;
import org.apache.log4j.Logger;
import services.SvcAuteurs;
import services.SvcFacture;
import services.SvcLivres;

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
public class LivresBean implements Serializable {
    private static final long serialVersionUID = 1L;
    private Livres livre;
    private static final Logger log = Logger.getLogger(LivresBean.class);

    private List<Livres> searchResults;

    @PostConstruct
    public void init()
    {
        livre = new Livres();
    }

    public String newLivre()
    {
        SvcLivres service = new SvcLivres();
        EntityTransaction transaction = service.getTransaction();
        //Todo mettre/faire une verification de l'objet utilisateur,
        log.debug("J'vais essayer d'sauver le livre");
        transaction.begin();

        try {

            service.save(livre);

            transaction.commit();
            log.debug("J'ai sauvé l'adresse");
            return "/tableLivres.xhtml?faces-redirect=true";
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

    public void save()
    {
        SvcLivres service = new SvcLivres();
        EntityTransaction transaction = service.getTransaction();
        transaction.begin();
        try {
            service.save(livre);
            transaction.commit();
            FacesContext fc = FacesContext.getCurrentInstance();
            fc.addMessage("ModifRe", new FacesMessage("Modification réussie"));
        } finally {
            if (transaction.isActive()) {
                transaction.rollback();
                FacesContext fc = FacesContext.getCurrentInstance();
                fc.addMessage("Erreur", new FacesMessage("Erreur inconnue"));
            }
            service.close();
        }

    }

    public String activdesactivLiv()
    {
        SvcLivres service = new SvcLivres();
        EntityTransaction transaction = service.getTransaction();
        log.debug("je débute la méthode activdésactive");
        try
        {
            transaction.begin();
            /*Si la voiture est active alors on la désactive*/
            if(livre.isActif())
            {
                log.debug("je passe le if de désactive");
                livre.setActif(false);
            }

            else
            {
                livre.setActif(true);
            }


            service.save(livre);

            transaction.commit();
            log.debug("J'ai modifié le livre");
            return "/tableLivres.xhtml?faces-redirect=true";
        }
        finally {
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

    public String searchLivre()
    {

        SvcLivres service = new SvcLivres();
        //try
        //{

        log.debug("list livres " + service.getByTitre(livre.getTitre()).size());
        if(service.getByTitre(livre.getTitre()).isEmpty())
        {
            FacesContext fc = FacesContext.getCurrentInstance();
            fc.addMessage("livRech", new FacesMessage("le livre n'a pas été trouvé"));
            return null;
        }
        else
        {
            searchResults = service.getByTitre(livre.getTitre());
        }

        //}
        //catch
        //{

        //}
        return "formSearchLivre?faces-redirect=true";
    }

    public String flushLiv()
    {
        init();
        if(searchResults!= null)
        {
            searchResults.clear();
        }
        return "tableLivres?faces-redirect=true";
    }


    public List<Livres> getReadAll()
    {
        SvcLivres service = new SvcLivres();
        List<Livres> listLiv = new ArrayList<Livres>();
        listLiv= service.findAllLivres();


        return listLiv;
    }

    public Livres getLivre() {
        return livre;
    }

    public void setLivre(Livres livre) {
        this.livre = livre;
    }

    public List<Livres> getSearchResults() {
        return searchResults;
    }

    public void setSearchResults(List<Livres> searchResults) {
        this.searchResults = searchResults;
    }




}
