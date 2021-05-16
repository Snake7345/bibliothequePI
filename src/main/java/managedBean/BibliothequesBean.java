package managedBean;

import entities.Auteurs;
import entities.Bibliotheques;
import org.apache.log4j.Logger;
import services.SvcAuteurs;
import services.SvcBibliotheques;

import javax.annotation.ManagedBean;
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
        SvcBibliotheques service = new SvcBibliotheques();
        EntityTransaction transaction = service.getTransaction();
        //Todo mettre/faire une verification de l'objet utilisateur,
        log.debug("J'vais essayer d'sauver la bibliotheque");
        transaction.begin();

        try {

            service.save(bibliotheque);

            transaction.commit();
            log.debug("J'ai sauvé la bibliotheque");
            return "/tableBibliotheques.xhtml?faces-redirect=true";
        } finally {
            if (transaction.isActive()) {
                transaction.rollback();
                log.debug("J'ai fait une erreur et je suis con");
                FacesContext fc = FacesContext.getCurrentInstance();
                fc.addMessage("erreur", new FacesMessage("le rollback a pris le relais"));

                return "";
            }
            else
            {
                log.debug("je suis censé avoir réussi");
                init();
            }

            service.close();
        }

    }

    public String flushBiblio()
    {
        init();
        return "tableBibliotheques?faces-redirect=true";
    }

    public List<Bibliotheques> getReadAll()
    {
        SvcBibliotheques service = new SvcBibliotheques();
        List<Bibliotheques> listBib = new ArrayList<Bibliotheques>();
        listBib= service.findAllBibliotheques();

        service.close();
        return listBib;
    }

    public Bibliotheques getBibliotheque() {
        return bibliotheque;
    }

    public void setBibliotheque(Bibliotheques bibliotheque) {
        this.bibliotheque = bibliotheque;
    }
}
