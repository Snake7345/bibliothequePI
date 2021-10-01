package managedBean;

import entities.Bibliotheques;
import entities.ExemplairesLivres;
import entities.Livres;
import enumeration.ExemplairesLivresEtatEnum;
import org.apache.log4j.Logger;
import org.apache.shiro.SecurityUtils;
import services.SvcBibliotheques;
import services.SvcExemplairesLivres;

import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.persistence.EntityTransaction;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
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
    private final Bibliotheques bib = (Bibliotheques) SecurityUtils.getSubject().getSession().getAttribute("biblio");
    private List<ExemplairesLivres> listCB;


    @PostConstruct
    public void init()
    {
        log.debug("init exemplaire livre lancé");
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

    public void addNewListRow() {
        listCB.add(new ExemplairesLivres());
        log.debug(listCB.size());
    }

    public void delListRow() {
        if (listCB.size() >1)
        {
            listCB.remove(listCB.size()-1);
        }
    }

    public String flush()
    {
        init();
        return "/tableLivres.xhtml?faces-redirect=true";
    }

    // Méthode qui permet d'ajouter autant d'exemplaire livre que demande l'utilisateur
    public String addExemplaireLivre(){
        init();
        SvcExemplairesLivres service = new SvcExemplairesLivres();
        EntityTransaction transaction = service.getTransaction();
        transaction.begin();
        try {
            for (int i = 0; i < nombreExemplaire; i++) {

                exemplairesLivre.setBibliotheques(bib);
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

    // Méthode qui désactive le livre, si jamais son état est "mauvais" et renvoi vers tableExemplaireLivres
    public String editExemplaireLivre()
    {
        if (exemplairesLivre.getEtat()==ExemplairesLivresEtatEnum.Mauvais)
        {
            exemplairesLivre.setActif(false);
        }
        save();
        return "/tableExemplaireLivres.xhtml?faces-redirect=true";
    }

    // Méthode qui permet de générer un code barre pour l'exemplaire livre
    public String generateBarCode(){

        if (LastBarCode.equals("0")){
            LastBarCode="100000001";
            return LastBarCode;
        }
        else{
            LastBarCode=String.valueOf(Integer.parseInt(LastBarCode)+1);
            if (LastBarCode.equals("400000000")){
                LastBarCode="500000000";
            }
            return LastBarCode;
        }
    }

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
                if(!EL.isReserve() || EL.isLoue())
                {
                    transaction.rollback();
                    FacesContext fc = FacesContext.getCurrentInstance();
                    fc.getExternalContext().getFlash().setKeepMessages(true);
                    fc.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,"l'exemplaire num "+EL.getCodeBarre()+" n'est pas en transfert",null));
                    return "/tableLivres.xhtml?faces-redirect=true";
                }
                if(EL.isActif())
                {
                    EL.setReserve(false);
                    EL.setBibliotheques(bib);
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

    public String transfer()
    {
        log.debug("transfert demare");
        log.debug(listCB.size());
        boolean flag = false;
        List<ExemplairesLivres> listEL= new ArrayList<>();
        SvcExemplairesLivres serviceEL = new SvcExemplairesLivres();
        EntityTransaction transaction = serviceEL.getTransaction();
        transaction.begin();
        try {
            for(ExemplairesLivres CB:listCB)
            {
                log.debug(CB);
               listEL.add(serviceEL.findOneByCodeBarre(CB.getCodeBarre()).get(0));
            }
            for(ExemplairesLivres EL : listEL){
                if(EL.isReserve())
                {
                    transaction.rollback();
                    FacesContext fc = FacesContext.getCurrentInstance();
                    fc.getExternalContext().getFlash().setKeepMessages(true);
                    fc.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,"l'exemplaire num "+EL.getCodeBarre()+" est déjà réservé",null));
                    return "/tableLivres.xhtml?faces-redirect=true";
                }
                if(!EL.getBibliotheques().equals(bib))
                {
                    transaction.rollback();
                    FacesContext fc = FacesContext.getCurrentInstance();
                    fc.getExternalContext().getFlash().setKeepMessages(true);
                    fc.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,"l'exemplaire num "+EL.getCodeBarre()+" n'est pas censé être dans votre bibliotheque",null));
                    return "/tableLivres.xhtml?faces-redirect=true";
                }
                if(EL.isActif())
                {
                    EL.setReserve(true);
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

    // Méthode qui permet de sauvegarder un exemplaire livre en DB
    public void save()
    {
        SvcExemplairesLivres service = new SvcExemplairesLivres();
        EntityTransaction transaction = service.getTransaction();
        transaction.begin();
        try {
            exemplairesLivre.setBibliotheques(bib);
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

    // Méthode qui permet la désactivation de l'exemplaire livre et renvoi vers la table des livres
    public String activdesactivExLiv()
    {
        /*Si l'exemplaire est actif alors on le désactive;*/
        if(exemplairesLivre.isActif())
        {
            exemplairesLivre.setActif(false);
        }

        save();
        return "/tableLivres.xhtml?faces-redirect=true";
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
        return bib;
    }

    public List<ExemplairesLivres> getListCB() {
        log.debug(Arrays.toString(listCB.toArray()));
        return listCB;
    }

    public void setListCB(List<ExemplairesLivres> listCB) {
        log.debug(Arrays.toString(listCB.toArray()));
        this.listCB = listCB;
        log.debug(Arrays.toString(listCB.toArray()));
    }
}
