package managedBean;

import entities.Bibliotheques;
import entities.ExemplairesLivres;
import entities.Livres;
import enumeration.ExemplairesLivresEtatEnum;
import org.apache.log4j.Logger;
import org.apache.shiro.SecurityUtils;
import services.SvcBibliotheques;
import services.SvcExemplairesLivres;
import services.SvcLivres;

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
public class ExemplairesLivresBean implements Serializable {

    // Déclaration des variables globales
    private static final long serialVersionUID = 1L;
    private ExemplairesLivres exemplairesLivre;
    private static final Logger log = Logger.getLogger(ExemplairesLivresBean.class);
    private int nombreExemplaire;
    private Bibliotheques bibli;
    private Livres livre;
    private String LastBarCode;
    private final Bibliotheques bibliothequeActuelle = (Bibliotheques) SecurityUtils.getSubject().getSession().getAttribute("biblio");
    private List<ExemplairesLivres> listCB;

    /*Permet d'attribuer et/ou vider les variables au démarrage du bean*/
    @PostConstruct
    public void init()
    {
        listCB = new ArrayList<>();
        addNewListRow();
        exemplairesLivre = new ExemplairesLivres();
        SvcExemplairesLivres service = new SvcExemplairesLivres();
        SvcBibliotheques serviceB = new SvcBibliotheques();
        if (service.findlastExemplairesLivres().size()==0){
            LastBarCode = "0";
        }
        else {
            LastBarCode=service.findlastExemplairesLivres().get(0).getCodeBarre();
        }
        service.close();
        serviceB.close();
    }
    /*Cette méthode permet d'ajouter une nouvelle ligne dans un formulaire concernant le code barre d'un exemplaire livre*/

    public void addNewListRow() {
        listCB.add(new ExemplairesLivres());
    }

    /*Cette méthode permet de supprimer une ligne dans un formulaire concernant le code barre d'un exemplaire livre
    * Si jamais on veut supprimer la première ligne, le programme nous le refusera*/
    public void delListRow() {
        if (listCB.size() >1)
        {
            listCB.remove(listCB.size()-1);
        }
    }
    /*
     * Méthode qui permet de vider les variables et de revenir sur la table des livres
     */
    public String flushLivres()
    {
        init();
        return "/tableLivres.xhtml?faces-redirect=true";
    }

    // Méthode qui permet d'ajouter autant d'exemplaire de livre que demande l'utilisateur en fonction du livre qu'il a choisit
    // et nous renvoi sur la table des exemplaires
    public String addExemplaireLivre(){
        init();
        SvcExemplairesLivres service = new SvcExemplairesLivres();
        EntityTransaction transaction = service.getTransaction();
        transaction.begin();
        try {
            for (int i = 0; i < nombreExemplaire; i++) {

                exemplairesLivre.setBibliotheques(bibliothequeActuelle);
                exemplairesLivre.setEtat(ExemplairesLivresEtatEnum.Bon);
                exemplairesLivre.setLivres(livre);
                exemplairesLivre.setCodeBarre(generateBarCode());
                service.save(exemplairesLivre);
                exemplairesLivre = new ExemplairesLivres();
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
                fc.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,"Erreur fatale",null));
            }
            else{
                init();
            }
            service.close();
        }

        return "/tableExemplaireLivres.xhtml?faces-redirect=true";
    }

    // Méthode qui désactive un objet "livre", si jamais son état est "mauvais" et renvoi vers la table des exemplaires de livres.
    public String editExemplaireLivre()
    {
        if (exemplairesLivre.getEtat()==ExemplairesLivresEtatEnum.Mauvais)
        {
            exemplairesLivre.setActif(false);
        }
        save();
        return "/tableExemplaireLivres.xhtml?faces-redirect=true";
    }

    // Méthode qui permet de générer un code barre pour l'exemplaire de livre
    public String generateBarCode(){

        if (LastBarCode.equals("0")){
            LastBarCode="100000001";
        }
        else{
            LastBarCode=String.valueOf(Integer.parseInt(LastBarCode)+1);
            if (LastBarCode.equals("400000000")){
                LastBarCode="500000000";
            }
        }
        return LastBarCode;
    }
    // Méthode qui permet de confirmer la réception d'un objet "exemplaire livre" via le code barre de cet exemplaire SAUF si il est
    // déjà loué ou n'est pas reservé, ou n'est pas en transfert un message s'affiche
    public String reception()
    {
        boolean flag = false;
        List<ExemplairesLivres> listEL= new ArrayList<>();
        SvcExemplairesLivres serviceEL = new SvcExemplairesLivres();
        EntityTransaction transaction = serviceEL.getTransaction();
        transaction.begin();
        try {
            for(ExemplairesLivres CB:listCB)
            {
                listEL.add(serviceEL.findOneByCodeBarre(CB.getCodeBarre()).get(0));
            }
            for(ExemplairesLivres EL : listEL){
                /*On vérifiera que l'exemplaire n'est pas en transfert, ni reservé, ni loué si un de ces critères est vrai -> Message d'erreur*/
                if(!EL.isTransfert() || EL.isReserve() || EL.isLoue())
                {
                    transaction.rollback();
                    FacesContext fc = FacesContext.getCurrentInstance();
                    fc.getExternalContext().getFlash().setKeepMessages(true);
                    fc.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,"l'exemplaire num "+EL.getCodeBarre()+" n'est pas en transfert",null));
                    return "/tableLivres.xhtml?faces-redirect=true";
                }
                /*Si le livre est actif, alors on passera le transfert a faux et on l'attribuera a l'objet "bibliotheque" connectée et on sauvegarde en DB*/
                if(EL.isActif())
                {
                    EL.setTransfert(false);
                    EL.setBibliotheques(bibliothequeActuelle);
                    serviceEL.save(EL);
                }
            }

            transaction.commit();
            FacesContext fc = FacesContext.getCurrentInstance();
            fc.getExternalContext().getFlash().setKeepMessages(true);
            fc.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,"L'operation a reussie",null));
        }
        catch (NullPointerException npe){
            npe.printStackTrace();
        }
        finally {
            if (transaction.isActive()) {
                transaction.rollback();
                FacesContext fc = FacesContext.getCurrentInstance();
                fc.getExternalContext().getFlash().setKeepMessages(true);
                fc.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,"l'operation a échoué",null));
            }
            init();
            serviceEL.close();
        }
        return "/tableLivres.xhtml?faces-redirect=true";
    }
    // Méthode qui permet de transférer un objet "exemplaire de livre" via le code barre de cet exemplaire livre SAUF si l'exemplaire est
    // déjà reservé ou n'est pas dans la bibliothèque actuellement connecté
    public String transfer()
    {
        boolean flag = false;
        List<ExemplairesLivres> listEL= new ArrayList<>();
        SvcExemplairesLivres serviceEL = new SvcExemplairesLivres();
        EntityTransaction transaction = serviceEL.getTransaction();
        transaction.begin();
        try {
            for(ExemplairesLivres CB:listCB)
            {
               listEL.add(serviceEL.findOneByCodeBarre(CB.getCodeBarre()).get(0));
            }
            for(ExemplairesLivres EL : listEL){
                /*On vérifie si l'exemplaire est déjà reservé, si oui -> Message d'erreur*/
                if(EL.isReserve())
                {
                    transaction.rollback();
                    FacesContext fc = FacesContext.getCurrentInstance();
                    fc.getExternalContext().getFlash().setKeepMessages(true);
                    fc.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,"l'exemplaire num "+EL.getCodeBarre()+" est déjà réservé",null));
                    return "/tableLivres.xhtml?faces-redirect=true";
                }
                /*Si l'exemplaire de livre est pas dans la bibliothèque -> Message d'erreur*/
                if(!EL.getBibliotheques().equals(bibliothequeActuelle))
                {
                    transaction.rollback();
                    FacesContext fc = FacesContext.getCurrentInstance();
                    fc.getExternalContext().getFlash().setKeepMessages(true);
                    fc.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,"l'exemplaire num "+EL.getCodeBarre()+" n'est pas censé être dans votre bibliotheque",null));
                    return "/tableLivres.xhtml?faces-redirect=true";
                }
                /*Si l'exemplaire de livre est actif, alors on passe le transfert a true et on sauvegarde en DB*/
                if(EL.isActif())
                {
                    EL.setTransfert(true);
                    serviceEL.save(EL);
                }
            }

            transaction.commit();
            FacesContext fc = FacesContext.getCurrentInstance();
            fc.getExternalContext().getFlash().setKeepMessages(true);
            fc.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,"L'operation a reussie",null));
        }
        catch (Error e){
            e.printStackTrace();
        }
        finally {
            if (transaction.isActive()) {
                transaction.rollback();
                FacesContext fc = FacesContext.getCurrentInstance();
                fc.getExternalContext().getFlash().setKeepMessages(true);
                fc.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,"l'operation a échoué",null));
            }
            init();
            serviceEL.close();
        }
        return "/tableLivres.xhtml?faces-redirect=true";
    }

    // Méthode qui permet de sauvegarder un objet "exemplaire livre" en DB dans la bibliothèque auquel le programme est connecté
    public void save()
    {
        SvcExemplairesLivres service = new SvcExemplairesLivres();
        EntityTransaction transaction = service.getTransaction();
        transaction.begin();
        try {
            exemplairesLivre.setBibliotheques(bibliothequeActuelle);
            service.save(exemplairesLivre);
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

    public void deactivateAllExLiv()
    {
        log.debug("entree desactive ex liv");
        log.debug("titre livre : "+livre.getTitre());
        SvcExemplairesLivres service = new SvcExemplairesLivres();
        SvcLivres serviceL = new SvcLivres();
        livre= serviceL.findByIsbn(livre.getIsbn()).get(0);
        serviceL.close();
        EntityTransaction transaction = service.getTransaction();
        transaction.begin();
        try {
            for(ExemplairesLivres el : livre.getExemplairesLivres())
            {
                if(el.getBibliotheques().equals(bibliothequeActuelle)){
                    log.debug("Code bare trouve : "+el.getCodeBarre());
                    el.setActif(false);
                    service.save(el);
                }
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

    // Méthode qui permet la désactivation de l'exemplaire livre et renvoi vers la table des livres
    public String activdesactivExLiv()
    {
        /*Si l'exemplaire est actif alors on le désactive;*/
        if(exemplairesLivre.isActif())
        {
            exemplairesLivre.setActif(false);
        }

        save();
        return "/tableExemplaireLivres.xhtml?faces-redirect=true";
        }
    /*
     * Méthode qui permet via le service de retourner la liste de tous les exemplaires livres
     */
    public List<ExemplairesLivres> getReadAll()
    {
        SvcExemplairesLivres service = new SvcExemplairesLivres();
        List<ExemplairesLivres> listExemplaires = new ArrayList<ExemplairesLivres>();
        listExemplaires= service.findExemplairesLivresByLivre(livre);
        service.close();
        return listExemplaires;
    }

    /*
     * Méthode qui permet via le service de retourner la liste de tous les exemplaires livres
     */
    public List<ExemplairesLivres> getReadAllByBiblio()
    {
        SvcExemplairesLivres service = new SvcExemplairesLivres();
        List<ExemplairesLivres> listExemplaires = new ArrayList<ExemplairesLivres>();
        listExemplaires= service.findAllByLivreByBibliotheque(livre,bibliothequeActuelle);
        service.close();
        return listExemplaires;
    }

    //-------------------------------Getter & Setter--------------------------------------------

    public ExemplairesLivres getExemplairesLivre() {
        return exemplairesLivre;
    }

    public void setExemplairesLivre(ExemplairesLivres exemplairesLivre) {
        this.exemplairesLivre = exemplairesLivre;
    }

    public int getNombreExemplaire() {
        return nombreExemplaire;
    }

    public void setNombreExemplaire(int nombreExemplaire) {
        this.nombreExemplaire = nombreExemplaire;
    }

    public Livres getLivre() {
        return livre;
    }

    public void setLivre(Livres livre) {
        this.livre = livre;
    }

    public Bibliotheques getBibli() {
        return bibli;
    }

    public void setBibli(Bibliotheques bibli) {
        this.bibli = bibli;
    }

    public Bibliotheques getBib() {
        return bibliothequeActuelle;
    }

    public List<ExemplairesLivres> getListCB() {
        return listCB;
    }

    public void setListCB(List<ExemplairesLivres> listCB) {
        this.listCB = listCB;
    }
}
