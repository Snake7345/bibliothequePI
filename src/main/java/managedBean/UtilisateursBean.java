package managedBean;

import entities.*;
import org.apache.log4j.Logger;
import org.apache.shiro.SecurityUtils;
import security.SecurityManager;
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
    private List<Utilisateurs> listUtilBib = new ArrayList<>();
    private List<Utilisateurs> listCli = new ArrayList<>();
    private List<Utilisateurs> searchResults;
    private String numMembre;
    private Adresses adresses;
    private UtilisateursAdresses UA;
    private final Bibliotheques bibliothequeActuelle = (Bibliotheques) SecurityUtils.getSubject().getSession().getAttribute("biblio");
    private Bibliotheques bibli;
    private UtilisateursBibliotheques UB;
    private List<Bibliotheques> tabbibli = new ArrayList<>();
    private List<UtilisateursBibliotheques> listUB = new ArrayList<>();
    private String mdpNouveau;
    private String mdpNouveau2;
    private List<Reservation> reservationClient = new ArrayList<>();

    public UtilisateursBean() {
        super();
    }

    /*Permet d'attribuer et/ou vider les variables au démarrage du bean*/
    @PostConstruct
    public void init() {
        listUtil = getReadAllUtil();
        listUtilBib = getReadAllUtilBib();
        listCli = getReadAllCli();
        reservationClient = new ArrayList<>();
        bibli = new Bibliotheques();
        utilisateur = new Utilisateurs();
        listUB = new ArrayList<>();
        UA = new UtilisateursAdresses();
        UB = new UtilisateursBibliotheques();
        adresses = new Adresses();
        tabbibli = new ArrayList<>();
        SvcUtilisateurs service = new SvcUtilisateurs();
        if (service.findlastMembre().size()==0){
            numMembre = "0";
        }
        else {
            numMembre=service.findlastMembre().get(0).getNumMembre();
        }
        service.close();
    }
    /*Cette méthode permet d'ajouter une nouvelle ligne concernant la bibliothèque auquel appartient l'utilisateur*/
    public void addBibliothequeRow(){
        tabbibli.add(new Bibliotheques());
    }
    /*Cette méthode permet de retirer une ligne concernant la bibliothèque */
    public void supBibliothequeRow(){
        if (tabbibli.size() >1)
        {
            tabbibli.remove(tabbibli.size()-1);
        }
    }

    /*Cette méthode permet de récupérer les données liées a l'utilisateur et nous renvoit sur le formulaire d'édition de l'utilisateur*/
    public String redirectModifUtil(){
        for (UtilisateursAdresses ua: utilisateur.getUtilisateursAdresses()) {
            if(ua.isActif()){
                adresses=ua.getAdresse();
            }
        }
        for (UtilisateursBibliotheques ub : utilisateur.getUtilisateursBibliotheques())
        {
            tabbibli.add(ub.getBibliotheque());
        }
        return "/formEditUtilisateur.xhtml?faces-redirect=true";
    }
    /*Méthode qui permet la sauvegarde de la modification d'un objet "utilisateur"*/
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

    public void afficheReservations()
    {

        SvcReservations service = new SvcReservations();
        reservationClient = service.findAllActivbyClient(bibliothequeActuelle,utilisateur);
        service.close();
        FacesContext fc = FacesContext.getCurrentInstance();
        fc.getExternalContext().getFlash().setKeepMessages(true);
        fc.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,"la réservation a bien été désactivé",null));
    }

    /*Cette méthode permet de sauvegarder un objet "utilisateur", l'adresse ainsi que les bibliothèques auquel il travaille*/
    public void saveUtilisateur() {
        SvcUtilisateurs service = new SvcUtilisateurs();
        SvcUtilisateursAdresses serviceUA = new SvcUtilisateursAdresses();
        SvcUtilisateursBibliotheques serviceUB = new SvcUtilisateursBibliotheques();
        serviceUA.setEm(service.getEm());
        serviceUB.setEm(service.getEm());
        EntityTransaction transaction = service.getTransaction();
        transaction.begin();
        try {
            service.save(utilisateur);
            if(!(null == utilisateur.getUtilisateursBibliotheques())){
                for(UtilisateursBibliotheques ub : utilisateur.getUtilisateursBibliotheques()){
                    serviceUB.delete(ub.getIdUtilisateursBibliotheques());
                }
            }
            if(utilisateur.getIdUtilisateurs()!=0) {
                for (UtilisateursAdresses utiladress : utilisateur.getUtilisateursAdresses()) {
                    if (!utiladress.equals(UA) && utiladress.isActif()) {
                        utiladress.setActif(false);
                        serviceUA.save(utiladress);
                    }
                }
            }
            else{
                serviceUA.save(UA);
            }
            if(listUB.size() > 0)
            {
                for(UtilisateursBibliotheques ub : listUB)
                {
                    serviceUB.save(ub);
                }
            }
            else
            {
                serviceUB.save(UB);
            }

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
    /*Cette méthode permet de sauvegarder un objet "utilisateur" en db, c-a-d le client ainsi que son adresse*/
    public void saveUtilisateurCli() {
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
    /*Cette méthode permet de modifier un mot de passe d'un utilisateur*/
    public String modifMdp()
    {
        SvcUtilisateurs serviceU = new SvcUtilisateurs();
        EntityTransaction transaction = serviceU.getTransaction();

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
        mdpNouveau="";
        mdpNouveau2="";
        return "/tableUtilisateurs.xhtml?faces-redirect=true";
    }
    /*Cette méthode permet la création de l'objet utilisateur ainsi que la vérification si un utilisateur est similaire*/
    public String newUtil() {
        boolean flag = false;
        SvcUtilisateursAdresses serviceUA = new SvcUtilisateursAdresses();
        SvcUtilisateursBibliotheques serviceUB = new SvcUtilisateursBibliotheques();
        utilisateur.setNom(utilisateur.getNom().substring(0,1).toUpperCase() + utilisateur.getNom().substring(1));
        utilisateur.setPrenom(utilisateur.getPrenom().substring(0,1).toUpperCase() + utilisateur.getPrenom().substring(1));
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
            UB = serviceUB.createUtilisateursBibliotheques(utilisateur,bibli);
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
    /*Cette méthode permet de modifier l'objet utilisateur et vérifie si un utilisateur existe*/
    public String modifUtil() {
        boolean flag = false;

        SvcUtilisateursAdresses serviceUA = new SvcUtilisateursAdresses();
        SvcUtilisateursBibliotheques serviceUB = new SvcUtilisateursBibliotheques();
        utilisateur.setNom(utilisateur.getNom().substring(0,1).toUpperCase() + utilisateur.getNom().substring(1));
        utilisateur.setPrenom(utilisateur.getPrenom().substring(0,1).toUpperCase() + utilisateur.getPrenom().substring(1));
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
        if(verifUtilExist(utilisateur))
        {
            UA.setActif(true);
            for (Bibliotheques bib : tabbibli)
            {
                if(listUB.size()==0) {
                    listUB.add(serviceUB.createUtilisateursBibliotheques(utilisateur, bib));
                }
                else{
                    boolean fl = false;
                    for (UtilisateursBibliotheques ub : listUB){
                        if (ub.getBibliotheque().getIdBibliotheques() == bib.getIdBibliotheques()) {
                            fl = true;
                            break;
                        }
                    }
                    if(fl){
                        listUB=new ArrayList<>();
                        FacesContext fc = FacesContext.getCurrentInstance();
                        fc.getExternalContext().getFlash().setKeepMessages(true);
                        fc.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,"Vous ne pouvez pas choisir deux fois les mêmes bibliothèques",null));
                        return "/formEditUtilisateur.xhtml?faces-redirect=true";}
                    else {
                        listUB.add(serviceUB.createUtilisateursBibliotheques(utilisateur, bib));
                    }
                }
            }

            saveUtilisateur();
        }else {

            FacesContext fc = FacesContext.getCurrentInstance();
            fc.getExternalContext().getFlash().setKeepMessages(true);
            fc.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,"L'utilisateur existe déjà tel quel en DB; opération échouée",null));
        }

            init();
            return "/tableUtilisateurs.xhtml?faces-redirect=true";
    }

    /*TODO : Essayer de corriger la faille concernant le changement de mail : Si je met le mail de quelqu'un d'autre mais que je mets pas le même rôle ça passe MAIS CA NE DEVRAIT PAS*/
    /*Cette méthode verifie si un utilisateur existe déjà*/
    public boolean verifUtilExist(Utilisateurs util)
    {
        SvcUtilisateurs serviceU = new SvcUtilisateurs();

        List<Utilisateurs> listUtil = serviceU.findOneUtilisateur(util);
        if (util.getIdUtilisateurs()!=0)
        {

            if (listUtil.size() > 0 ) {
                if(listUtil.get(0).getIdUtilisateurs() == util.getIdUtilisateurs()){
                    serviceU.close();
                    return true;
                }
                else
                {
                    serviceU.close();
                    return false;
                }
            }
            else {
                serviceU.close();
                return true;
            }
        }
        else
        {
            if (listUtil.size() > 0) {
                serviceU.close();
                return false;
            } else {
                serviceU.close();
                return true;
            }
        }
    }
    /*Cette méthode permet la création de l'objet utilisateur(client) ainsi que la vérification si un client est similaire*/
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
            saveUtilisateurCli();
        }
        init();
        return "/tableUtilisateursCli.xhtml?faces-redirect=true";
    }

    //Méthode qui va créer un nouveau membre en commencant par le nombre 400000000 (cette méthode concerne uniquement les clients)
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
        }
        else {
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
        if(searchResults!= null)
        {
            searchResults.clear();
        }
        if (service.getByName(utilisateur.getNom()).isEmpty()) {
            FacesContext fc = FacesContext.getCurrentInstance();
            fc.getExternalContext().getFlash().setKeepMessages(true);
            fc.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,"l'utilisateur n'a pas été trouvé",null));
            return "/formSearchUtilisateur?faces-redirect=true";
        } else {
            searchResults = service.getByName(utilisateur.getNom());
        }

        return "/formSearchUtilisateur?faces-redirect=true";
    }

    public String searchUtilisateurNumMembre() {

        SvcUtilisateurs service = new SvcUtilisateurs();
        if(searchResults!= null)
        {
            searchResults.clear();
        }
        if (service.getByNumMembre(utilisateur.getNumMembre()).isEmpty()) {
            FacesContext fc = FacesContext.getCurrentInstance();
            fc.getExternalContext().getFlash().setKeepMessages(true);
            fc.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,"l'utilisateur n'a pas été trouvé",null));
            return "/formSearchUtilisateurNumMembre?faces-redirect=true";
        } else {
            searchResults = service.getByNumMembre(utilisateur.getNumMembre());
        }

        return "/formSearchUtilisateurNumMembre?faces-redirect=true";
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
    //Méthode qui permet de vider les variables et de revenir sur le formulaire de création d'un client
    public String flushUtilCliNew() {
        init();
        if (searchResults != null) {
            searchResults.clear();
        }
        return "/formNewUtilisateurCli?faces-redirect=true";
    }
    //Méthode qui permet de vider les variables et de revenir sur le formulaire de création d'un utilisateur
    public String flushUtilNew() {
        init();
        if (searchResults != null) {
            searchResults.clear();
        }
        return "/formNewUtilisateur?faces-redirect=true";
    }

    //Méthode qui permet de vider les variables et de revenir sur le formulaire de recherche d'utilisateur
    public String flushUtilSearch() {
        init();
        if (searchResults != null) {
            searchResults.clear();
        }
        return "/formSearchUtilisateur?faces-redirect=true";
    }
    public String flushUtilSearchNum() {
        init();
        if (searchResults != null) {
            searchResults.clear();
        }
        return "/formSearchUtilisateurNumMembre?faces-redirect=true";
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
    /*Méthode qui permet via le service de retournerl la liste de tous lees utilisateurs d'une bibliothèque*/
    public List<Utilisateurs> getReadAllUtilBib()
    {
        SvcUtilisateurs service = new SvcUtilisateurs();
        listUtilBib = service.findAllUtilisateursUtilBib(bibliothequeActuelle);

        service.close();
        return listUtilBib;
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

    public List<Utilisateurs> getListUtilBib() {
        return listUtilBib;
    }

    public void setListUtilBib(List<Utilisateurs> listUtilBib) {
        this.listUtilBib = listUtilBib;
    }

    public Bibliotheques getBibliactuel() {
        return bibliothequeActuelle;
    }

    public Bibliotheques getBibli() {
        return bibli;
    }

    public void setBibli(Bibliotheques bibli) {
        this.bibli = bibli;
    }

    public UtilisateursBibliotheques getUB() {
        return UB;
    }

    public void setUB(UtilisateursBibliotheques UB) {
        this.UB = UB;
    }

    public List<Bibliotheques> getTabbibli() {
        return tabbibli;
    }

    public void setTabbibli(List<Bibliotheques> tabbibli) {
        this.tabbibli = tabbibli;
    }

    public List<UtilisateursBibliotheques> getListUB() {
        return listUB;
    }

    public void setListUB(List<UtilisateursBibliotheques> listUB) {
        this.listUB = listUB;
    }

    public List<Reservation> getReservationClient() {
        return reservationClient;
    }

    public void setReservationClient(List<Reservation> reservationClient) {
        this.reservationClient = reservationClient;
    }
}

