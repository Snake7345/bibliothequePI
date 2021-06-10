package managedBean;

import entities.TarifsJours;
import org.apache.log4j.Logger;
import services.SvcTarifsJours;

import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.persistence.EntityTransaction;
import java.io.Serializable;
import java.util.List;

@Named
@SessionScoped

public class TarifsJoursBean implements Serializable {

    // Déclaration des variables globales
    private static final long serialVersionUID = 1L;
    private TarifsJours tarifsJours;


    private static final Logger log = Logger.getLogger(TarifsJoursBean.class);

    public void save()
    {
        SvcTarifsJours service = new SvcTarifsJours();
        EntityTransaction transaction = service.getTransaction();
        transaction.begin();
        try {
            service.save(tarifsJours);
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

    /*
     * Méthode qui permet via le service de retourner la liste de toutes les tarifs journalier
     */

    public List<TarifsJours> getReadAll()
    {
        SvcTarifsJours service = new SvcTarifsJours();
        List<TarifsJours> listTarifsJours = service.findAllTarifsJours();

        service.close();
        return listTarifsJours;
    }


    //-------------------------------Getter & Setter--------------------------------------------


    public TarifsJours getTarifsJours() {
        return tarifsJours;
    }

    public void setTarifsJours(TarifsJours tarifsJours) {
        this.tarifsJours = tarifsJours;
    }
}
