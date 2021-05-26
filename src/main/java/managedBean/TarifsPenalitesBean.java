package managedBean;

import entities.TarifsPenalites;
import org.apache.log4j.Logger;
import services.SvcTarifsPenalites;

import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.persistence.EntityTransaction;
import java.io.Serializable;
import java.util.List;

@Named
@SessionScoped

/*TODO :
 *
 * -Verifier les dates au niveau des tarifs pénalités / tarifs jours car aucune ligne ou date ne doivent se chevaucher
 * - Et si sa se passe, vérifier les dates pour pas qu'il y ait de chevauchement
 *
 * */
public class TarifsPenalitesBean implements Serializable {

    // Déclaration des variables globales
    private static final long serialVersionUID = 1L;
    private TarifsPenalites tarifsPenalite;
    private static final Logger log = Logger.getLogger(TarifsPenalites.class);

        public void save()
    {
        SvcTarifsPenalites service = new SvcTarifsPenalites();
        EntityTransaction transaction = service.getTransaction();
        transaction.begin();
        try {
            service.save(tarifsPenalite);
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
     * Méthode qui permet via le service de retourner la liste de toutes les pénalités
     */
    public List<TarifsPenalites> getReadAll()
    {
        SvcTarifsPenalites service = new SvcTarifsPenalites();
        List<TarifsPenalites> listTarifsPenalites = service.findAllTarifsPenalites();
        service.close();

        return listTarifsPenalites;
    }


    //-------------------------------Getter & Setter--------------------------------------------

    public TarifsPenalites getTarifsPenalite() {
        return tarifsPenalite;
    }

    public void setTarifsPenalite(TarifsPenalites tarifsPenalite) {
        this.tarifsPenalite = tarifsPenalite;
    }
}
