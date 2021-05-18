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
        //Todo mettre/faire une verification de l'objet bibliotheque,
        save();
        return "/tableBibliotheques.xhtml?faces-redirect=true";
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
            fc.addMessage("ModifRe", new FacesMessage("Modification réussie"));
        } finally {
            if (transaction.isActive()) {
                transaction.rollback();
                FacesContext fc = FacesContext.getCurrentInstance();
                fc.addMessage("Erreur", new FacesMessage("une erreur est survenue"));
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
        return "tableBibliotheques?faces-redirect=true";
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
