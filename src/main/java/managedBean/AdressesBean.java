package managedBean;

import entities.Adresses;
import entities.Bibliotheques;
import org.apache.log4j.Logger;
import services.SvcAdresses;

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
public class AdressesBean implements Serializable {
    // Déclaration des variables globales
    private static final long serialVersionUID = 1L;
    private Adresses adresse;
    private static final Logger log = Logger.getLogger(AdressesBean.class);
    private Bibliotheques bibliotheque;

    @PostConstruct
    public void init()
    {
        bibliotheque = new Bibliotheques();
        adresse = new Adresses();
    }



    // Méthode qui permet l'appel de save() qui créée une nouvelle adresse et envoi un message si jamais
    // l'adresse se trouve déjà en base de donnée et nous renvoi sur la table des adresses
    public String newAdress()
    {
        if(verifAdresseExist(adresse))
        {
            save();
        }
        else{
            FacesContext fc = FacesContext.getCurrentInstance();
            fc.getExternalContext().getFlash().setKeepMessages(true);
            fc.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,"La donnée est déjà existante en DB",null));
            init();
        }
        return "/tableAdresses.xhtml?faces-redirect=true";

    }


    // Méthode qui permet l'appel de save() qui créée une nouvelle adresse et envoi un message si jamais
    // l'adresse se trouve déjà en base de donnée. L'appel se fait sur un popup.

    public void newAdresspopup()
    {
        if(verifAdresseExist(adresse))
        {
            save();
        }
        else{
            FacesContext fc = FacesContext.getCurrentInstance();
            fc.getExternalContext().getFlash().setKeepMessages(true);
            fc.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,"La donnée est déjà existante en DB",null));
            init();
        }

    }

    // Méthode qui permet la sauvegarde d'une adresse en base de donnée
    public void save()
    {
        SvcAdresses service = new SvcAdresses();
        EntityTransaction transaction = service.getTransaction();
        transaction.begin();
        try {
            service.save(adresse);
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

    // Méthode qui vérifie qu'une adresse se trouve déjà ou pas dans la base de donnée
    public boolean verifAdresseExist(Adresses ad)
    {
        SvcAdresses serviceA = new SvcAdresses();
        if(serviceA.findOneAdresse(ad).size() > 0)
        {
            serviceA.close();
            return false;
        }
        else {
            serviceA.close();
            return true;
        }

    }
    /*
     * Méthode qui permet de vider les variables avec init() et de revenir sur le table des Adresses .
     * */
    public String flushAdd()
    {
        init();
        return "/tableAdresses?faces-redirect=true";
    }
    /*
     * Méthode qui permet de vider les variables avec init() et de revenir sur le formulaire d'une nouvelle adresse .
     * */
    public String flushAddNew()
    {
        init();
        return "/formNewAdress?faces-redirect=true";
    }


    /*
     * Méthode qui permet via le service de retourner
     * la liste de toutes les adresses
     */
    public List<Adresses> getReadAll()
    {
        SvcAdresses service = new SvcAdresses();
        List<Adresses> listAd = new ArrayList<Adresses>();
        listAd= service.findAllAdresses();

        service.close();
        return listAd;
    }

    /*
     * Méthode qui permet via le service de retourner
     * la liste de toutes les adresses qui n'est pas aloué a une bibliothèque
     */
    public List<Adresses> getReadAllNotBibli()
    {
        SvcAdresses service = new SvcAdresses();
        List<Adresses> listAd = new ArrayList<Adresses>();
        listAd= service.getfindAllNotAdBibli();

        service.close();
        return listAd;
    }
    /*
     * Méthode qui permet via le service de retourner
     * la liste de toutes les adresses qui n'est pas aloué ni a une bibliothèque ni à un utilisateur
     */
    public List<Adresses> getReadAllNotBibliNotUti()
    {
        SvcAdresses service = new SvcAdresses();
        List<Adresses> listAd = new ArrayList<Adresses>();
        listAd= service.getfindAllNotAdBibliNotUti();

        service.close();
        return listAd;
    }

    /*
     * Méthode pas encore utilisé
     */
    public List<Adresses> getReadAllNotBibliNotUtiForModif()
    {
        SvcAdresses service = new SvcAdresses();
        List<Adresses> listAd = new ArrayList<Adresses>();
        listAd= service.getfindAllNotAdBibliNotUtiForMofif(bibliotheque);

        service.close();
        return listAd;
    }


//-------------------------------Getter & Setter--------------------------------------------

    public Adresses getAdresse() {
        return adresse;
    }

    public void setAdresse(Adresses adresse) {
        this.adresse = adresse;
    }

    public Bibliotheques getBibliotheque() {
        return bibliotheque;
    }

    public void setBibliotheque(Bibliotheques bibliotheque) {
        this.bibliotheque = bibliotheque;
    }
}
