package managedBean;

import entities.Auteurs;
import entities.ExemplairesLivres;
import entities.Livres;
import entities.LivresAuteurs;
import org.apache.log4j.Logger;
import org.primefaces.event.UnselectEvent;
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

    /*Permet d'attribuer et/ou vider les variables au démarrage du bean*/
    @PostConstruct
    public void init()
    {
        listAut = getReadAll();
        auteur = new Auteurs();
    }

    // Méthode qui permet l'appel de save() qui créé un nouvel objet "auteur" et envoi un message si jamais l'auteur se trouve déjà en base de donnée
    // La méthode met également une majuscule pour la première lettre du nom et du prénom de l'auteur et nous renvoi sur la table des auteurs
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
    /*
    * Méthode qui permet de travailler sur le selectCheckboxMenu et de voir les éléments qui ne sont pas selectionné
    * */
    public void onItemUnselect(UnselectEvent event) {
        FacesContext context = FacesContext.getCurrentInstance();

        FacesMessage msg = new FacesMessage();
        msg.setSummary("Item unselected: " + event.getObject().toString());
        msg.setSeverity(FacesMessage.SEVERITY_INFO);

        context.addMessage(null, msg);
    }

    // Méthode qui permet la sauvegarde d'un objet "auteur" dans la base de donnée
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

    /* Méthode qui permet de désactiver un objet "auteur" ainsi que ces livres et exemplaires livres.
     * Cette méthode permet également d'activer un auteur et ces livres, il nous renvoi sur la table des auteurs
     * */
    public String activdesactivAut()
    {
        SvcAuteurs service = new SvcAuteurs();
        SvcExemplairesLivres serviceEL = new SvcExemplairesLivres();
        SvcLivres serviceL = new SvcLivres();
        serviceL.setEm(service.getEm());
        serviceEL.setEm(service.getEm());
        EntityTransaction transaction = service.getTransaction();

        transaction.begin();
        try {
            /*On vérifie si l'objet "auteur" est actif*/
            if(auteur.isActif())
            {

                if (auteur.getLivresAuteur().size()>=1){
                    /*Si oui alors on désactivera tous les livres ainsi que tous les exemplaires liées à cet auteur*/
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
                /*Sinon, alors l'auteur est désactivé, du coup on activera l'auteur ainsi que ces livres (les exemplaires de livres ne seront pas réactivés)*/
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

    //Méthode qui permet de vider les variables et de revenir sur la table des Auteurs
    public String flushAut()
    {
        init();
        if(searchResults!= null)
        {
            searchResults.clear();
        }
        return "/tableAuteurs?faces-redirect=true";
    }
    //Méthode qui permet de vider les variables et de revenir sur le formulaire de création d'un auteur
    public String flushAutNew()
    {
        init();
        if(searchResults!= null)
        {
            searchResults.clear();
        }
        return "/formNewAuteur?faces-redirect=true";
    }

    // Méthode qui permet en fonction de la donnée que l'utilisateur encode, de rechercher
    // un nom parmi les auteurs et nous renvoi le resultat sur le formulaire de recherche des auteurs
    public String searchAuteur()
    {

        SvcAuteurs service = new SvcAuteurs();
        if(searchResults!= null)
        {
            searchResults.clear();
        }
            if(service.getByName(auteur.getNom()).isEmpty())
            {
                FacesContext fc = FacesContext.getCurrentInstance();
                fc.getExternalContext().getFlash().setKeepMessages(true);
                fc.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,"l'auteur n'a pas été trouvé",null));
                return "/formSearchAuteur.xhtml?faces-redirect=true";
            }
            else
            {
                searchResults = service.getByName(auteur.getNom());
            }

        return "/formSearchAuteur?faces-redirect=true";
    }

    /*
     * Méthode qui permet de vérifier si un auteur existe ou non en DB
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

    // Méthode qui retourne la liste de tous les auteurs présent en DB
    public List<Auteurs> getReadAll()
    {
        SvcAuteurs service = new SvcAuteurs();

        listAut= service.findAllAuteurs();

        service.close();
        return listAut;
    }

    //Méthode qui permet de vider les variables et de revenir sur le formulaire de recherche d'auteur
    public String flushAutSearch() {
        init();
        if (searchResults != null) {
            searchResults.clear();
        }
        return "/formSearchAuteur?faces-redirect=true";
    }


    /*
     * Méthode qui permet de retourner
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
     * Méthode qui permet de retourner
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
