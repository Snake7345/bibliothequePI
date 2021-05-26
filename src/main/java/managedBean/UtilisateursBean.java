package managedBean;

import entities.Utilisateurs;
import org.apache.log4j.Logger;
import services.SvcRoles;
import services.SvcUtilisateurs;

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

/*TODO :
*
* -Verifier si un utilisateur n'existe pas déjà dans la base de données
* -Vu qu'on a deux formulaires pour les utilisateurs, il faut "supprimer de la liste" le rôle client pour par exemple qu'il ne soit pas disponible pour le formulaire de création d'utilisateur
*
*
* */
public class UtilisateursBean implements Serializable {
    // Déclaration des variables globales
    private static final long serialVersionUID = 1L;

    private Utilisateurs utilisateur;
    private static final Logger log = Logger.getLogger(UtilisateursBean.class);
    private List<Utilisateurs> listUtil = new ArrayList<Utilisateurs>();
    private List<Utilisateurs> searchResults;
    private String numMembre;

    public UtilisateursBean() {
        super();
    }

    @PostConstruct
    public void init() {
        listUtil = getReadAll();
        utilisateur = new Utilisateurs();
        SvcUtilisateurs service = new SvcUtilisateurs();
        if (service.findlastMembre().size()==0){
            numMembre = "0";
        }
        else {
            numMembre=service.findlastMembre().get(0).getNumMembre();
        }
    }



    public void save() {
        SvcUtilisateurs service = new SvcUtilisateurs();
        EntityTransaction transaction = service.getTransaction();
        transaction.begin();
        try {
            service.save(utilisateur);
            transaction.commit();
            FacesContext fc = FacesContext.getCurrentInstance();
            fc.addMessage("ModifRe", new FacesMessage("Modification réussie"));
        } finally {
            if (transaction.isActive()) {
                transaction.rollback();
                FacesContext fc = FacesContext.getCurrentInstance();
                fc.addMessage("Erreur", new FacesMessage("le rollback a pris le relais"));
            }
            else {
                init();
            }
            service.close();
        }

    }

    public String newUtil() {
        utilisateur.setNom(utilisateur.getNom().substring(0,0).toUpperCase() + utilisateur.getNom().substring(1));
        utilisateur.setPrenom(utilisateur.getPrenom().substring(0,0).toUpperCase() + utilisateur.getPrenom().substring(1));
        save();
        return "/tableUtilisateurs.xhtml?faces-redirect=true";

    }

    public String newUtilCli() {
        SvcRoles serviceR = new SvcRoles();
        utilisateur.setNom(utilisateur.getNom().substring(0,0).toUpperCase() + utilisateur.getNom().substring(1));
        utilisateur.setPrenom(utilisateur.getPrenom().substring(0,0).toUpperCase() + utilisateur.getPrenom().substring(1));
        utilisateur.setRoles(serviceR.findRole("Client").get(0));
        utilisateur.setNumMembre(createNumMembre());
        save();
        return "/tableUtilisateurs.xhtml?faces-redirect=true";

    }

    public String createNumMembre()
    {
        if (numMembre=="0"){
            numMembre="400000000";
            return numMembre;
        }
        else{
            numMembre=String.valueOf(Integer.parseInt(numMembre)+1);
            if (numMembre!="500000000"){
                return numMembre;
            }
            else {
                return "999999999";
            }
        }
    }

    public String activdesactivUtil() {
        if (utilisateur.isActif()) {
            log.debug("je passe le if de désactive");
            utilisateur.setActif(false);
            save();
            return "/tableUtilisateurs.xhtml?faces-redirect=true";
        } else {
            if ((!utilisateur.isActif()) && (!utilisateur.getRoles().isActif())) {
                FacesContext fc = FacesContext.getCurrentInstance();
                fc.addMessage("erreurId", new FacesMessage("L'utilisateur ne peut pas être réactivé tant que le rôle est désactivé"));

                return "/tableUtilisateurs.xhtml?faces-redirect=true";
            } else {
                utilisateur.setActif(true);
                save();
                return "/tableUtilisateurs.xhtml?faces-redirect=true";
            }
        }
    }

    public String searchUtilisateur() {

        SvcUtilisateurs service = new SvcUtilisateurs();
        //try
        //{

        log.debug("list auteur " + service.getByName(utilisateur.getNom()).size());
        if (service.getByName(utilisateur.getNom()).isEmpty()) {
            FacesContext fc = FacesContext.getCurrentInstance();
            fc.addMessage("utilRech", new FacesMessage("l'utilisateur n'a pas été trouvé"));
            return null;
        } else {
            searchResults = service.getByName(utilisateur.getNom());
        }

        //}
        //catch
        //{

        //}
        return "formSearchUtilisateur?faces-redirect=true";
    }

    public String flushUtil() {
        init();
        if (searchResults != null) {
            searchResults.clear();
        }
        return "tableUtilisateurs?faces-redirect=true";
    }

    public String flushBienv()
    {
        //ToDO a optimiser
        init();
        return "bienvenue?faces-redirect=true";
    }
    /*
     * Méthode qui permet via le service de retourner la liste de tous les utilisateurs actifs
     */

    public List<Utilisateurs> getReadActiv()
    {
        SvcUtilisateurs service = new SvcUtilisateurs();
        listUtil = service.findAllUtilisateursActiv();

        service.close();
        return listUtil;
    }
    /*
     * Méthode qui permet via le service de retourner la liste de tous les utilisateurs inactifs
     */
    public List<Utilisateurs> getReadInactiv()
    {
        SvcUtilisateurs service = new SvcUtilisateurs();
        listUtil = service.findAllUtilisateursInactiv();

        service.close();
        return listUtil;
    }
    /*
     * Méthode qui permet via le service de retourner la liste de tous les utilisateurs
     */
    public List<Utilisateurs> getReadAll()
    {
        SvcUtilisateurs service = new SvcUtilisateurs();
        listUtil = service.findAllUtilisateurs();

        service.close();
        return listUtil;
    }


//-------------------------------Getter & Setter--------------------------------------------
    public Utilisateurs getUtilisateur() {
        return utilisateur;
    }

    public void setUtilisateur(Utilisateurs utilisateur) {
        this.utilisateur = utilisateur;
    }

    public List<Utilisateurs> getSearchResults() {
        return searchResults;
    }

    public void setSearchResults(List<Utilisateurs> searchResults) {
        this.searchResults = searchResults;
    }


    public List<Utilisateurs> getListUtil() {
        return listUtil;
    }

    public void setListUtil(List<Utilisateurs> listUtil) {
        this.listUtil = listUtil;
    }

    public String getNumMembre() {
        return numMembre;
    }

    public void setNumMembre(String numMembre) {
        this.numMembre = numMembre;
    }
}

