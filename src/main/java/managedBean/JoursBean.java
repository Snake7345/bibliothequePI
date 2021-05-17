package managedBean;

import entities.Jours;
import org.apache.log4j.Logger;
import services.SvcFacture;
import services.SvcJours;

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
public class JoursBean implements Serializable {
    // Déclaration des variables globales
    private static final long serialVersionUID = 1L;
    private Jours jour;
    private static final Logger log = Logger.getLogger(JoursBean.class);

    public void save()
    {
        SvcJours service = new SvcJours();
        EntityTransaction transaction = service.getTransaction();
        transaction.begin();
        try {
            service.save(jour);
            transaction.commit();
            FacesContext fc = FacesContext.getCurrentInstance();
            fc.addMessage("ModifEd", new FacesMessage("Modification réussie"));
        } finally {
            if (transaction.isActive()) {
                transaction.rollback();
                FacesContext fc = FacesContext.getCurrentInstance();
                fc.addMessage("Erreur", new FacesMessage("une erreur est survenue"));
            }
            service.close();
        }

    }
    /*
     * Méthode qui permet via le service de retourner la liste de tous les jours
     */
    public List<Jours> getReadAll()
    {
        SvcJours service = new SvcJours();
        List<Jours> listJours = new ArrayList<Jours>();
        listJours= service.findAllJours();

        service.close();
        return listJours;
    }


    //-------------------------------Getter & Setter--------------------------------------------

    public Jours getJour() {
        return jour;
    }

    public void setJour(Jours jour) {
        this.jour = jour;
    }


}
