package managedBean;

import entities.Adresses;
import entities.Utilisateurs;
import entities.UtilisateursAdresses;
import org.apache.log4j.Logger;
import services.SvcRoles;
import services.SvcUtilisateurs;
import services.SvcUtilisateursAdresses;

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


public class UtilisateursBean implements Serializable {
    // Déclaration des variables globales
    private static final long serialVersionUID = 1L;

    private Utilisateurs utilisateur;
    private static final Logger log = Logger.getLogger(UtilisateursBean.class);
    private List<Utilisateurs> listUtil = new ArrayList<Utilisateurs>();
    private List<Utilisateurs> listCli = new ArrayList<Utilisateurs>();
    private List<Utilisateurs> searchResults;
    private String numMembre;
    private Adresses adresses;
    private UtilisateursAdresses UA;

    public UtilisateursBean() {
        super();
    }

    @PostConstruct
    public void init() {
        listUtil = getReadAllUtil();
        listCli = getReadAllCli();
        utilisateur = new Utilisateurs();
        UA = new UtilisateursAdresses();
        adresses = new Adresses();
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
    public void saveUA() {
        SvcUtilisateurs service = new SvcUtilisateurs();
        SvcUtilisateursAdresses serviceUA = new SvcUtilisateursAdresses();
        serviceUA.setEm(service.getEm());
        EntityTransaction transaction = service.getTransaction();
        transaction.begin();
        try {
            service.save(utilisateur);
            serviceUA.save(UA);
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
        SvcUtilisateursAdresses serviceUA = new SvcUtilisateursAdresses();
        utilisateur.setNom(utilisateur.getNom().substring(0,1).toUpperCase() + utilisateur.getNom().substring(1));
        utilisateur.setPrenom(utilisateur.getPrenom().substring(0,1).toUpperCase() + utilisateur.getPrenom().substring(1));
        UA = serviceUA.createUtilisateursAdresses(utilisateur, adresses);
        saveUA();

        return "/tableUtilisateurs.xhtml?faces-redirect=true";

    }

    public String newUtilCli() {
        SvcRoles serviceR = new SvcRoles();
        utilisateur.setNom(utilisateur.getNom().substring(0,1).toUpperCase() + utilisateur.getNom().substring(1));
        utilisateur.setPrenom(utilisateur.getPrenom().substring(0,1).toUpperCase() + utilisateur.getPrenom().substring(1));
        utilisateur.setRoles(serviceR.findRole("Client").get(0));
        utilisateur.setNumMembre(createNumMembre());
        saveUA();
        return "/tableUtilisateurs.xhtml?faces-redirect=true";

    }

    public String createNumMembre()
    {
        if (numMembre.equals("0")){
            numMembre="400000000";
            return numMembre;
        }
        else{
            numMembre=String.valueOf(Integer.parseInt(numMembre)+1);
            if (!numMembre.equals("500000000")){
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

    public List<Utilisateurs> getReadUtilActiv()
    {
        SvcUtilisateurs service = new SvcUtilisateurs();
        listUtil = service.findAllUtilisateursActiv();

        service.close();
        return listUtil;
    }
    /*
     * Méthode qui permet via le service de retourner la liste de tous les utilisateurs inactifs
     */
    public List<Utilisateurs> getReadUtilInactiv()
    {
        SvcUtilisateurs service = new SvcUtilisateurs();
        listUtil = service.findAllUtilisateursInactiv();

        service.close();
        return listUtil;
    }
    public List<Utilisateurs> getReadCliInactiv()
    {
        SvcUtilisateurs service = new SvcUtilisateurs();
        listCli = service.findAllUtilisateursCliInactiv();

        service.close();
        return listCli;
    }
    public List<Utilisateurs> getReadCliActiv()
    {
        SvcUtilisateurs service = new SvcUtilisateurs();
        listCli = service.findAllUtilisateursCliActiv();

        service.close();
        return listCli;
    }

    /*
     * Méthode qui permet via le service de retourner la liste de tous les utilisateurs
     */
    public List<Utilisateurs> getReadAllUtil()
    {
        SvcUtilisateurs service = new SvcUtilisateurs();
        listUtil = service.findAllUtilisateursUtil();

        service.close();
        return listUtil;
    }
    public List<Utilisateurs> getReadAllCli()
    {
        SvcUtilisateurs service = new SvcUtilisateurs();
        listCli = service.findAllUtilisateursCli();

        service.close();
        return listCli;
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

    public Adresses getAdresses() {
        return adresses;
    }

    public void setAdresses(Adresses adresses) {
        this.adresses = adresses;
    }

    public UtilisateursAdresses getUA() {
        return UA;
    }

    public void setUA(UtilisateursAdresses UA) {
        this.UA = UA;
    }

    public List<Utilisateurs> getListCli() {
        return listCli;
    }

    public void setListCli(List<Utilisateurs> listCli) {
        this.listCli = listCli;
    }
}

