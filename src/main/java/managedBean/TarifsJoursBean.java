package managedBean;

import entities.TarifsJours;
import entities.TarifsPenalites;
import org.apache.log4j.Logger;
import services.SvcTarifs;
import services.SvcTarifsJours;
import services.SvcTarifsPenalites;

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
            fc.addMessage("ModifRe", new FacesMessage("Modification réussie"));
        } finally {
            if (transaction.isActive()) {
                transaction.rollback();
                FacesContext fc = FacesContext.getCurrentInstance();
                fc.addMessage("Erreur", new FacesMessage("le rollback a pris le relais"));
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
