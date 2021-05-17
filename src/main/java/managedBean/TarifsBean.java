package managedBean;

import entities.Tarifs;
import org.apache.log4j.Logger;
import services.SvcPermissionRoles;
import services.SvcTarifs;

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
public class TarifsBean implements Serializable {
    // Déclaration des variables globales
    private static final long serialVersionUID = 1L;
    private Tarifs tarif;
    private static final Logger log = Logger.getLogger(TarifsBean.class);

    public void save()
    {
        SvcTarifs service = new SvcTarifs();
        EntityTransaction transaction = service.getTransaction();
        transaction.begin();
        try {
            service.save(tarif);
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
     * Méthode qui permet via le service de retourner la liste de toutes les grilles tarifaires
     */

    public List<Tarifs> getReadAll()
    {
        SvcTarifs service = new SvcTarifs();
        List<Tarifs> listTarifs = new ArrayList<Tarifs>();
        listTarifs = service.findAllTarifs();

        service.close();
        return listTarifs;
    }

    //-------------------------------Getter & Setter--------------------------------------------

    public Tarifs getTarif() {
        return tarif;
    }

    public void setTarif(Tarifs tarif) {
        this.tarif = tarif;
    }
}
