package managedBean;

import entities.*;
import org.apache.log4j.Logger;
import services.*;

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
    private List<Utilisateurs> listUtil = new ArrayList<>();
    private List<Utilisateurs> listCli = new ArrayList<>();
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
        service.close();
    }

    public String redirectModifUtil(){
        for (UtilisateursAdresses ua: utilisateur.getUtilisateursAdresses()) {
            if(ua.isActif()){
                adresses=ua.getAdresse();
            }
        }
        return "/formEditUtilisateur.xhtml?faces-redirect=true";
    }

    public void saveActif() {
        SvcUtilisateurs service = new SvcUtilisateurs();
        EntityTransaction transaction = service.getTransaction();
        transaction.begin();
        try {
            service.save(utilisateur);
            transaction.commit();
            FacesContext fc = FacesContext.getCurrentInstance();
            fc.addMessage("messageGenre", new FacesMessage("Modification réussie"));
        } finally {
            if (transaction.isActive()) {
                transaction.rollback();
                FacesContext fc = FacesContext.getCurrentInstance();
                fc.addMessage("messageGenre", new FacesMessage("le rollback a pris le relais"));
            }
            else {
                init();
            }
            service.close();
        }

    }

    public void saveUtilisateur() {
        SvcUtilisateurs service = new SvcUtilisateurs();
        SvcUtilisateursAdresses serviceUA = new SvcUtilisateursAdresses();
        serviceUA.setEm(service.getEm());
        EntityTransaction transaction = service.getTransaction();
        transaction.begin();
        try {
            service.save(utilisateur);
            if(utilisateur.getIdUtilisateurs()!=0) {
                for (UtilisateursAdresses utiladress : utilisateur.getUtilisateursAdresses()) {
                    if (!utiladress.equals(UA) && utiladress.isActif()) {
                        utiladress.setActif(false);
                        serviceUA.save(utiladress);
                    }
                }
            }

            serviceUA.save(UA);
            transaction.commit();
            FacesContext fc = FacesContext.getCurrentInstance();
            fc.getExternalContext().getFlash().setKeepMessages(true);
            fc.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,"L'operation a reussie",null));
        } finally {
            if (transaction.isActive()) {
                transaction.rollback();
                FacesContext fc = FacesContext.getCurrentInstance();
                fc.getExternalContext().getFlash().setKeepMessages(true);
                fc.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,"L'operation n'a pas reussie",null));
            }

            service.close();
        }

    }

    public String newUtil() {
        boolean flag = false;
        SvcUtilisateursAdresses serviceUA = new SvcUtilisateursAdresses();
        utilisateur.setNom(utilisateur.getNom().substring(0,1).toUpperCase() + utilisateur.getNom().substring(1));
        utilisateur.setPrenom(utilisateur.getPrenom().substring(0,1).toUpperCase() + utilisateur.getPrenom().substring(1));

        //todo à vérifier que ça fonctionne...
        if (utilisateur.getIdUtilisateurs()!=0) {
            for (UtilisateursAdresses ua : utilisateur.getUtilisateursAdresses()) {
                if (ua.getAdresse().equals(adresses)) {
                    flag = true;
                    UA = ua;
                    break;
                }
            }
        }
        if (!flag){
            UA = serviceUA.createUtilisateursAdresses(utilisateur, adresses);
        }
        if(verifUtilExist(utilisateur)) {
            UA.setActif(true);
            saveUtilisateur();
        }else {

            FacesContext fc = FacesContext.getCurrentInstance();
            fc.getExternalContext().getFlash().setKeepMessages(true);
            fc.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,"L'utilisateur existe déjà tel quel en DB; opération échouée",null));
        }
        if(utilisateur.getRoles().getDenomination().equals("Client"))
        {
            init();
            return "/tableUtilisateursCli.xhtml?faces-redirect=true";
        }

        else
        {
            init();
            return "/tableUtilisateurs.xhtml?faces-redirect=true";
        }
    }

    public boolean verifUtilExist(Utilisateurs util)
    {
        SvcUtilisateurs serviceU = new SvcUtilisateurs();
        boolean flag= false;
        if (util.getIdUtilisateurs()!=0) {
            for (UtilisateursAdresses ua : util.getUtilisateursAdresses()) {
                if (ua.getAdresse().equals(adresses) && ua.isActif()) {
                    flag = true;
                    break;
                }
            }
            if (serviceU.findOneUtilisateur(util).size() > 0 && flag) {
                serviceU.close();
                return false;
            } else {
                serviceU.close();
                return true;
            }
        }
        else {
            if (serviceU.findOneUtilisateur(util).size() > 0) {
                serviceU.close();
                return false;
            } else {
                serviceU.close();
                return true;
            }
        }
    }

    public String newUtilCli() {
        boolean flag = false;
        SvcUtilisateursAdresses serviceUA = new SvcUtilisateursAdresses();
        SvcRoles serviceR = new SvcRoles();
        utilisateur.setNom(utilisateur.getNom().substring(0,1).toUpperCase() + utilisateur.getNom().substring(1));
        utilisateur.setPrenom(utilisateur.getPrenom().substring(0,1).toUpperCase() + utilisateur.getPrenom().substring(1));
        utilisateur.setRoles(serviceR.findRole("Client").get(0));
        utilisateur.setNumMembre(createNumMembre());
        if (utilisateur.getIdUtilisateurs()!=0) {
            for (UtilisateursAdresses ua : utilisateur.getUtilisateursAdresses()) {
                if (ua.getAdresse().equals(adresses)) {
                    flag = true;
                    UA = ua;
                    break;
                }
            }
        }
        if (!flag){
            UA = serviceUA.createUtilisateursAdresses(utilisateur, adresses);
        }
        if(!verifUtilExist(utilisateur)) {
            FacesContext fc = FacesContext.getCurrentInstance();
            fc.getExternalContext().getFlash().setKeepMessages(true);
            fc.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,"Le client existe déjà tel quel en DB; opération échouée",null));
        }
        else if(utilisateur.getNumMembre().equals("999999999")){
            FacesContext fc = FacesContext.getCurrentInstance();
            fc.getExternalContext().getFlash().setKeepMessages(true);
            fc.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,"Le nombre de client maximal a été atteint; opération échouée",null));
        }
        else {
            UA.setActif(true);
            saveUtilisateur();
        }
        init();
        return "/tableUtilisateursCli.xhtml?faces-redirect=true";
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
            utilisateur.setActif(false);
            saveActif();
        } else {
            if ((!utilisateur.isActif()) && (!utilisateur.getRoles().isActif())) {
                FacesContext fc = FacesContext.getCurrentInstance();
                fc.getExternalContext().getFlash().setKeepMessages(true);
                fc.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,"L'utilisateur ne peut pas être réactivé tant que le rôle est désactivé",null));

            } else {
                utilisateur.setActif(true);
                saveActif();
            }
        }
        return "/tableUtilisateurs.xhtml?faces-redirect=true";
    }

    public String searchUtilisateur() {

        SvcUtilisateurs service = new SvcUtilisateurs();

        if (service.getByName(utilisateur.getNom()).isEmpty()) {
            FacesContext fc = FacesContext.getCurrentInstance();
            fc.addMessage("utilRech", new FacesMessage("l'utilisateur n'a pas été trouvé"));
            return null;
        } else {
            searchResults = service.getByName(utilisateur.getNom());
        }

        return "/formSearchUtilisateur?faces-redirect=true";
    }

    public String flushUtil() {
        init();
        if (searchResults != null) {
            searchResults.clear();
        }
        return "/tableUtilisateurs?faces-redirect=true";
    }

    public String flushUtilCli() {
        init();
        if (searchResults != null) {
            searchResults.clear();
        }
        return "/tableUtilisateursCli?faces-redirect=true";
    }

    public String flushBienv()
    {
        //ToDO a optimiser
        init();
        return "/bienvenue?faces-redirect=true";
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

