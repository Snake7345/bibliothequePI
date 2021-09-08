package managedBean;

import entities.Adresses;
import entities.Utilisateurs;
import entities.UtilisateursAdresses;
import org.apache.log4j.Logger;
import security.SecurityManager;
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
    private List<Utilisateurs> listUtil = new ArrayList<>();
    private List<Utilisateurs> listCli = new ArrayList<>();
    private List<Utilisateurs> searchResults;
    private String numMembre;
    private Adresses adresses;
    private UtilisateursAdresses UA;

    private String mdpNouveau;


    private String mdpNouveau2;

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
    /*TODO : Scinder la fonction : Les clients d'un coté et les utilisateurs de l'autre
    *
    *       C'EST UN MINIMUM
    * */

    public String modifMdp()
    {
        SvcUtilisateurs serviceU = new SvcUtilisateurs();
        EntityTransaction transaction = serviceU.getTransaction();
        log.debug("utilisateur objet : " + utilisateur);
        log.debug("utilisateur findbyLogin : " + serviceU.findByLogin(utilisateur.getLogin()).get(0).getMdp());
        log.debug("utilisateur getmdp : " + utilisateur.getMdp());

        try
        {
            if(!mdpNouveau.equals(mdpNouveau2))
            {
                FacesContext fc = FacesContext.getCurrentInstance();
                fc.getExternalContext().getFlash().setKeepMessages(true);
                fc.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Le nouveau mot de passe et la confirmation ne correspondent pas", null));
            }
            else
            {
                if (utilisateur.getMdp().equals(serviceU.findByLogin(utilisateur.getLogin()).get(0).getMdp())) {
                    if (SecurityManager.PasswordMatch(mdpNouveau, utilisateur.getMdp())) {
                        FacesContext fc = FacesContext.getCurrentInstance();
                        fc.getExternalContext().getFlash().setKeepMessages(true);
                        fc.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "le mot de passe est le même que précédemment", null));
                    } else {

                        transaction.begin();
                        utilisateur.setMdp(mdpNouveau);
                        serviceU.save(utilisateur);
                        transaction.commit();
                        FacesContext fc = FacesContext.getCurrentInstance();
                        fc.getExternalContext().getFlash().setKeepMessages(true);
                        fc.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "L'operation a reussie", null));

                    }

                }
            }
        }
        catch (NullPointerException npe)
        {
            FacesContext fc = FacesContext.getCurrentInstance();
            fc.getExternalContext().getFlash().setKeepMessages(true);
            fc.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Une erreur est survenue(erreur 101), veuillez contacter le support technique", null));
        }
        finally {
            if (transaction.isActive()) {
                transaction.rollback();
                FacesContext fc = FacesContext.getCurrentInstance();
                fc.getExternalContext().getFlash().setKeepMessages(true);
                fc.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "L'operation n'a pas reussie", null));
            }
            serviceU.close();
        }

        init();
        return "/tableUtilisateurs.xhtml?faces-redirect=true";
    }

    public String newUtil() {
        boolean flag = false;
        SvcUtilisateursAdresses serviceUA = new SvcUtilisateursAdresses();
        utilisateur.setNom(utilisateur.getNom().substring(0,1).toUpperCase() + utilisateur.getNom().substring(1));
        utilisateur.setPrenom(utilisateur.getPrenom().substring(0,1).toUpperCase() + utilisateur.getPrenom().substring(1));
        /*

        log.debug((SecurityManager.encryptPassword(utilisateur.getMdp())));

        PasswordMatcher matcher = new PasswordMatcher();
        log.debug(matcher.getPasswordService().passwordsMatch(utilisateur.getMdp(),SecurityManager.encryptPassword(utilisateur.getMdp())));

        */
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

    /*TODO : Faire une méthode concernant la modification du mot de passe et uniquement de celui-ci via un pop-up*/

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

    //Méthode qui va créer un nouveau membre en commencant par le nombre 400000000
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




    /*Méthode qui permet de désactiver un utilisateur et de le réactiver en verifiant si son rôle est actif ou pas.
    * Si on desactiver/active un client il nous renverra sur la table des clients sinon il nous renverra sur la table des utilisateurs*/
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
        if (utilisateur.getRoles().getDenomination().equals("Client"))
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
    // Méthode qui permet en fonction de la donnée de l'utilisateur de rechercher un nom parmi les utilisateurs(Client) et nous renvoi sur le formulaire de recherche des utilisateurs(Client)
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
    //Méthode qui permet de vider les variables et de revenir sur le table des utilisateurs
    public String flushUtil() {
        init();
        if (searchResults != null) {
            searchResults.clear();
        }
        return "/tableUtilisateurs?faces-redirect=true";
    }
    //Méthode qui permet de vider les variables et de revenir sur le table des utilisateurs(Client)
    public String flushUtilCli() {
        init();
        if (searchResults != null) {
            searchResults.clear();
        }
        return "/tableUtilisateursCli?faces-redirect=true";
    }
    //Méthode qui permet de vider les variables et de revenir sur la page de bienvenue
    public String flushBienv()
    {
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
    /*
     * Méthode qui permet via le service de retourner la liste de tous les utilisateurs(Client) inactifs
     */
    public List<Utilisateurs> getReadCliInactiv()
    {
        SvcUtilisateurs service = new SvcUtilisateurs();
        listCli = service.findAllUtilisateursCliInactiv();

        service.close();
        return listCli;
    }

    /*
     * Méthode qui permet via le service de retourner la liste de tous les utilisateurs(Client) actifs
     */
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
    /*
     * Méthode qui permet via le service de retourner la liste de tous les utilisateurs(Client)
     */
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

    public String getMdpNouveau() {
        return mdpNouveau;
    }

    public void setMdpNouveau(String mdpNouveau) {
        this.mdpNouveau = mdpNouveau;
    }

    public String getMdpNouveau2() {
        return mdpNouveau2;
    }

    public void setMdpNouveau2(String mdpNouveau2) {
        this.mdpNouveau2 = mdpNouveau2;
    }
}

