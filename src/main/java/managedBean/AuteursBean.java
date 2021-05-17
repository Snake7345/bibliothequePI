package managedBean;

import entities.Auteurs;
import org.apache.log4j.Logger;
import services.SvcAuteurs;

import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.persistence.EntityTransaction;
import java.io.Serializable;
import java.util.List;

@Named
@SessionScoped
public class AuteursBean implements Serializable {
    // Déclaration des variables globales
    private static final long serialVersionUID = 1L;
    private Auteurs auteur;
    private static final Logger log = Logger.getLogger(AuteursBean.class);
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
        //Todo mettre/faire une verification de l'objet auteur,
        save();
        return "/tableAuteurs.xhtml?faces-redirect=true";
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
                fc.addMessage("Erreur", new FacesMessage("une erreur est survenue"));
            }
            else
            {
                init();
            }
            service.close();
        }
    }

    /*Méthode qui permet d'activer et de désactiver un auteur*/
    public String activdesactivAut()
    {
        SvcAuteurs service = new SvcAuteurs();
        EntityTransaction transaction = service.getTransaction();
        log.debug("je débute la méthode activdésactive");

        if(auteur.isActif())
            {
            auteur.setActif(false);
            }
        else
            {
            auteur.setActif(true);
            }
        save();
        return "/tableAuteurs.xhtml?faces-redirect=true";

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

    /*
     * Méthode qui permet via le service de retourner
     * la liste de tous les auteurs
     */
    public List<Auteurs> getReadAll()
    {
        SvcAuteurs service = new SvcAuteurs();

        listAut= service.findAllAuteurs();

        service.close();
        return listAut;
    }

    /*
     * Méthode qui permet de vider les variables et de les mettres a leur valeur initiale avant de revenir sur la page web Bienvenue .
     * */
    public String flushBienv()
    {
        init();
        return "bienvenue?faces-redirect=true";
    }
    /*
     * Méthode qui permet via le service de retourner
     * la liste de tous les auteurs actifs
     */
    public List<Auteurs> getReadActiv()
    {
        SvcAuteurs service = new SvcAuteurs();
        listAut = service.findAllAuteursActiv();

        service.close();
        return listAut;
    }
    /*
     * Méthode qui permet via le service de retourner
     * la liste de tous les auteurs inactifs
     */
    public List<Auteurs> getReadInactiv()
    {
        SvcAuteurs service = new SvcAuteurs();
        listAut = service.findAllAuteursInactiv();

        service.close();
        return listAut;
    }
    //-------------------------------Getter & Setter--------------------------------------------

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
