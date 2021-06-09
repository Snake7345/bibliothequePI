package managedBean;

import entities.Bibliotheques;
import org.apache.log4j.Logger;
import services.SvcBibliotheques;

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
public class BibliothequesBean implements Serializable {
    // Déclaration des variables globales
    private static final long serialVersionUID = 1L;
    private Bibliotheques bibliotheque;
    private static final Logger log = Logger.getLogger(BibliothequesBean.class);

    @PostConstruct
    public void init()
    {
        bibliotheque = new Bibliotheques();
    }

    public String newBiblio()
    {
        SvcBibliotheques serviceB = new SvcBibliotheques();
        boolean flag = getCheckbibli();
        boolean flagbib =false;
        if (!flag)
        {
            flagbib = serviceB.getById(bibliotheque.getIdBibliotheques()).equals(bibliotheque);
        }
        if(getCheckbibli() || !flagbib)
        {
            save();
        }
        else if (flagbib)
        {
            FacesContext fc = FacesContext.getCurrentInstance();
            fc.getExternalContext().getFlash().setKeepMessages(true);
            fc.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,"Le nom de la bibliotheque n'a pas été changé",null));
        }
        else
        {
            FacesContext fc = FacesContext.getCurrentInstance();
            fc.getExternalContext().getFlash().setKeepMessages(true);
            fc.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,"une seule bibliotheque autorisé",null));
        }
        serviceB.close();
        return "/tableBibliotheques.xhtml?faces-redirect=true";
    }

    public boolean getCheckbibli(){
        SvcBibliotheques serviceB = new SvcBibliotheques();
        if(serviceB.findAllBibliotheques().size()==0)
        {
            serviceB.close();
            return true;
        }
        else
        {
            serviceB.close();
            return false;
        }
    }

    public void save()
    {
        SvcBibliotheques service = new SvcBibliotheques();
        EntityTransaction transaction = service.getTransaction();
        transaction.begin();
        try {
            service.save(bibliotheque);
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

    /*
     * Méthode qui permet de vider les variables et de revenir sur le table Bibliothèques .
     *
     */
    public String flushBiblio()
    {
        init();
        return "/tableBibliotheques?faces-redirect=true";
    }
    /*
     * Méthode qui permet via le service de retourner
     * la liste de toutes les bibliothèques
     */
    public List<Bibliotheques> getReadAll()
    {
        SvcBibliotheques service = new SvcBibliotheques();
        List<Bibliotheques> listBib = new ArrayList<Bibliotheques>();
        listBib= service.findAllBibliotheques();

        service.close();
        return listBib;
    }
    //-------------------------------Getter & Setter--------------------------------------------

    public Bibliotheques getBibliotheque() {
        return bibliotheque;
    }

    public void setBibliotheque(Bibliotheques bibliotheque) {
        this.bibliotheque = bibliotheque;
    }
}
