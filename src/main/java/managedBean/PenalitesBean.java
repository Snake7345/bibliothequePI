package managedBean;

import entities.Penalites;
import org.apache.log4j.Logger;
import services.SvcPenalites;

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

public class PenalitesBean implements Serializable {
    // Déclaration des variables globales
    private static final long serialVersionUID = 1L;
    private Penalites penalite;
    private static final Logger log = Logger.getLogger(PenalitesBean.class);

    /*
     * Méthode qui permet de sauver une pénalité
     */
    public void save()
    {
        SvcPenalites service = new SvcPenalites();
        EntityTransaction transaction = service.getTransaction();
        transaction.begin();
        try {
            service.save(penalite);
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
            else
            {
                //init();
            }
            service.close();
        }
    }
    /*
     * Méthode qui permet via le service de retourner la liste de toutes les pénalités
     */
    public List<Penalites> getReadAll()
    {
        SvcPenalites service = new SvcPenalites();
        List<Penalites> listPenalites = new ArrayList<Penalites>();
        listPenalites = service.findAllPenalites();


        return listPenalites;
    }

    //-------------------------------Getter & Setter--------------------------------------------

    public Penalites getPenalite() {
        return penalite;
    }

    public void setPenalite(Penalites penalite) {
        this.penalite = penalite;
    }
}
