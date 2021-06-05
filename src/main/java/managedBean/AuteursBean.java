package managedBean;

import entities.*;
import enumeration.ExemplairesLivresEtatEnum;
import org.apache.log4j.Logger;
import services.SvcAdresses;
import services.SvcAuteurs;
import services.SvcExemplairesLivres;
import services.SvcLivres;

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

        if(verifAuteurExist(auteur))
        {
            auteur.setNom(auteur.getNom().substring(0,1).toUpperCase() + auteur.getNom().substring(1));
            auteur.setPrenom(auteur.getPrenom().substring(0,1).toUpperCase() + auteur.getPrenom().substring(1));
            save();
        }
        else{
            FacesContext fc = FacesContext.getCurrentInstance();
            fc.getExternalContext().getFlash().setKeepMessages(true);
            fc.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,"La donnée est déjà existante en DB",null));
            init();
        }
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
            fc.getExternalContext().getFlash().setKeepMessages(true);
            fc.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,"L'operation a reussie",null));
        } finally {
            if (transaction.isActive()) {
                transaction.rollback();
                FacesContext fc = FacesContext.getCurrentInstance();
                fc.getExternalContext().getFlash().setKeepMessages(true);
                fc.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,"l'operation n'a pas reussie",null));
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
        SvcExemplairesLivres serviceEL = new SvcExemplairesLivres();
        SvcLivres serviceL = new SvcLivres();
        serviceL.setEm(service.getEm());
        serviceEL.setEm(service.getEm());
        EntityTransaction transaction = service.getTransaction();

        log.debug("je débute la méthode activdésactive");

        transaction.begin();
        try {
            if(auteur.isActif())
            {

                if (auteur.getLivresAuteur().size()>=1){
                    for (LivresAuteurs LA: auteur.getLivresAuteur()){
                        Livres livres = LA.getLivre();
                        if (livres.getExemplairesLivres().size()>=1){
                            for (ExemplairesLivres EL:livres.getExemplairesLivres()) {
                                EL.setActif(false);
                                serviceEL.save(EL);

                            }
                        }
                        livres.setActif(false);
                        serviceL.save(livres);
                    }
                }
                auteur.setActif(false);
            }
            else
            {
                if (auteur.getLivresAuteur().size()>=1){
                    for (LivresAuteurs LA: auteur.getLivresAuteur()){
                        Livres livres = LA.getLivre();
                        livres.setActif(true);
                        serviceL.save(livres);
                    }
                }
                auteur.setActif(true);
            }
            service.save(auteur);
            transaction.commit();
            FacesContext fc = FacesContext.getCurrentInstance();
            fc.getExternalContext().getFlash().setKeepMessages(true);
            fc.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,"L'operation a reussie",null));
        } finally {
            if (transaction.isActive()) {
                transaction.rollback();
                FacesContext fc = FacesContext.getCurrentInstance();
                fc.getExternalContext().getFlash().setKeepMessages(true);
                fc.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,"L'operation n'est pas reussie",null));
            }
            else
            {
                init();
            }
            service.close();
        }
        return "/tableAuteurs.xhtml?faces-redirect=true";

    }

    public String flushAut()
    {
        init();
        if(searchResults!= null)
        {
            searchResults.clear();
        }
        return "/tableAuteurs?faces-redirect=true";
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
        return "/formSearchAuteur?faces-redirect=true";
    }

    //public void edit()
    //{

    //}

    /*
     * Méthode qui permet via le service de retourner
     * la liste de tous les auteurs
     */

    public boolean verifAuteurExist(Auteurs aut)
    {
        SvcAuteurs serviceA = new SvcAuteurs();
        if(serviceA.findOneAuteur(aut).size() > 0)
        {
            serviceA.close();
            return false;
        }
        else {
            serviceA.close();
            return true;
        }

    }

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
        return "/bienvenue?faces-redirect=true";
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
