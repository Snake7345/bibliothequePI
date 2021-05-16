package managedBean;

import entities.*;
import org.apache.log4j.Logger;
import services.SvcLivres;
import services.SvcLivresAuteurs;

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

    private LivresAuteurs livresAuteur;
    private List<Auteurs> auteur;
    private LivresGenres livresGenre;
    private Genres genre;

    private List<Livres> listLiv = new ArrayList<Livres>();


    private List<Livres> searchResults;

    @PostConstruct
    public void init()
    {
        listLiv = getReadAll();
        livre = new Livres();
    }

    public String newLivre()
    {
        SvcLivres service = new SvcLivres();
        //SvcLivresGenres serviceLG = new SvcLivresGenres();
        EntityTransaction transaction = service.getTransaction();
        //Todo mettre/faire une verification de l'objet Livre,
        log.debug("J'vais essayer d'sauver le livre");

        transaction.begin();
        try {

            livre = service.save(livre);
            transaction.commit();
            log.debug("J'ai sauvé le livre");
            return "/tableLivres.xhtml?faces-redirect=true";
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
                newLivreAuteur();
                log.debug("je suis censé avoir réussi");
                init();
            }

            service.close();
        }
    }

    public void newLivreAuteur()
    {
        SvcLivresAuteurs serviceLA = new SvcLivresAuteurs();
        //SvcLivresGenres serviceLG = new SvcLivresGenres();
        EntityTransaction transaction = serviceLA.getTransaction();
        //Todo mettre/faire une verification de l'objet livreAuteur,
        log.debug("J'vais essayer d'sauver le bidule");

        transaction.begin();
        try {

            for (Auteurs auteurs : auteur) {
                livresAuteur = serviceLA.createLivresAuteurs(livre, auteurs);
                serviceLA.save(livresAuteur);
            }
            transaction.commit();
            log.debug("J'ai sauvé le bidule");
        } finally {
            if (transaction.isActive()) {
                transaction.rollback();
                log.debug("J'ai fait une erreur et je suis con");
                FacesContext fc = FacesContext.getCurrentInstance();
                fc.addMessage("erreur", new FacesMessage("le rollback a pris le relais"));
            }
            else
            {
                log.debug("je suis censé avoir réussi");
                init();
            }
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
                fc.addMessage("Erreur", new FacesMessage("le rollback a pris le relais"));
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

        listLiv= service.findAllLivres();


        return listLiv;
    }

    public List<Livres> getReadActiv()
    {
        SvcLivres service = new SvcLivres();
        listLiv = service.findAllLivresActiv();

        service.close();
        return listLiv;
    }
    public List<Livres> getReadInactiv()
    {
        SvcLivres service = new SvcLivres();
        listLiv = service.findAllLivresInactiv();

        service.close();
        return listLiv;
    }

    public String flushBienv()
    {
        init();
        return "bienvenue?faces-redirect=true";
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

    public LivresAuteurs getLivresAuteur() {
        return livresAuteur;
    }

    public void setLivresAuteur(LivresAuteurs livresAuteur) {
        this.livresAuteur = livresAuteur;
    }

    public LivresGenres getLivresGenre() {
        return livresGenre;
    }

    public void setLivresGenre(LivresGenres livresGenre) {
        this.livresGenre = livresGenre;
    }

    public Genres getGenre() {
        return genre;
    }

    public void setGenre(Genres genre) {
        this.genre = genre;
    }

    public List<Auteurs> getAuteur() {
        return auteur;
    }

    public void setAuteur(List<Auteurs> auteur) {
        this.auteur = auteur;
    }

    public List<Livres> getListLiv() {
        return listLiv;
    }

    public void setListLiv(List<Livres> listLiv) {
        this.listLiv = listLiv;
    }
}
