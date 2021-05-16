package managedBean;

import entities.Adresses;
import entities.Auteurs;
import entities.Utilisateurs;
import org.apache.log4j.Logger;
import services.SvcAdresses;
import services.SvcAuteurs;
import services.SvcUtilisateurs;

import javax.annotation.ManagedBean;
import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.persistence.EntityTransaction;
import java.io.Serializable;
import java.util.*;

@Named
@SessionScoped
public class AuteursBean implements Serializable {
    private static final long serialVersionUID = 1L;
    private Auteurs auteur;
    private static final Logger log = Logger.getLogger(AuteursBean.class);
    private Auteurs autTemp;
    private List<Auteurs> searchResults;
    private List<Auteurs> listAut;

    @PostConstruct
    public void init()
    {
        listAut = getReadAll();
        auteur = new Auteurs();
    }

    public String newAuteur()
    {
        SvcAuteurs service = new SvcAuteurs();
        EntityTransaction transaction = service.getTransaction();
        //Todo mettre/faire une verification de l'objet utilisateur,
        log.debug("J'vais essayer d'sauver l'auteur");
        transaction.begin();

        try {

            service.save(auteur);

            transaction.commit();
            log.debug("J'ai sauvé l'adresse");
            return "/tableAuteurs.xhtml?faces-redirect=true";
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
        SvcAuteurs service = new SvcAuteurs();
        EntityTransaction transaction = service.getTransaction();
        transaction.begin();
        try {
            service.save(auteur);
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

    public String activdesactivAut()
    {
        SvcAuteurs service = new SvcAuteurs();
        EntityTransaction transaction = service.getTransaction();
        log.debug("je débute la méthode activdésactive");
        try
        {
            transaction.begin();
            /*Si la voiture est active alors on la désactive*/
            if(auteur.isActif())
            {
                log.debug("je passe le if de désactive");
                auteur.setActif(false);
            }

            else
            {
                auteur.setActif(true);
            }


            service.save(auteur);

            transaction.commit();
            log.debug("J'ai modifié l'auteur");
            return "/tableAuteurs.xhtml?faces-redirect=true";
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

    public String flushAut()
    {
        init();
        if(searchResults!= null)
        {
            searchResults.clear();
        }
        return "tableAuteurs?faces-redirect=true";
    }

    public String searchAuteur()
    {

        SvcAuteurs service = new SvcAuteurs();
        //try
        //{

            log.debug("list auteur " + service.getByName(auteur.getNom()).size());
            if(service.getByName(auteur.getNom()).isEmpty())
            {
                FacesContext fc = FacesContext.getCurrentInstance();
                fc.addMessage("autRech", new FacesMessage("l'auteur n'a pas été trouvé"));
                return null;
            }
            else
            {
                searchResults = service.getByName(auteur.getNom());
            }

        //}
        //catch
        //{

        //}
        return "formSearchAuteur?faces-redirect=true";
    }

    //public void edit()
    //{

    //}


    public List<Auteurs> getReadAll()
    {
        SvcAuteurs service = new SvcAuteurs();

        listAut= service.findAllAuteurs();

        service.close();
        return listAut;
    }

    public String flushBienv()
    {
        init();
        return "bienvenue?faces-redirect=true";
    }

    public List<Auteurs> getReadActiv()
    {
        SvcAuteurs service = new SvcAuteurs();
        listAut = service.findAllAuteursActiv();

        service.close();
        return listAut;
    }
    public List<Auteurs> getReadInactiv()
    {
        SvcAuteurs service = new SvcAuteurs();
        listAut = service.findAllAuteursInactiv();

        service.close();
        return listAut;
    }

    public Auteurs getAuteur() {
        return auteur;
    }

    public void setAuteur(Auteurs auteur) {
        this.auteur = auteur;
    }

    public List<Auteurs> getSearchResults() {
        return searchResults;
    }

    public void setSearchResults(List<Auteurs> searchResults) {
        this.searchResults = searchResults;
    }


    public List<Auteurs> getListAut() {
        return listAut;
    }

    public void setListAut(List<Auteurs> listAut) {
        this.listAut = listAut;
    }


}
