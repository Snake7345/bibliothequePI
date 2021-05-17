package managedBean;

import entities.UtilisateursAdresses;
import org.apache.log4j.Logger;
import services.SvcUtilisateursAdresses;

import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.persistence.EntityTransaction;
import java.io.Serializable;

@Named
@SessionScoped
public class UtilisateursAdressesBean implements Serializable {
    // Déclaration des variables globales
    private static final long serialVersionUID = 1L;
    private UtilisateursAdresses UtilisateursAdresse;
    private final SvcUtilisateursAdresses service = new SvcUtilisateursAdresses();
    private static final Logger log = Logger.getLogger(UtilisateursAdressesBean.class);

    public void save()
    {
        SvcUtilisateursAdresses service = new SvcUtilisateursAdresses();
        EntityTransaction transaction = service.getTransaction();
        transaction.begin();
        try {
            service.save(UtilisateursAdresse);
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
    //-------------------------------Getter & Setter--------------------------------------------

    public UtilisateursAdresses getUtilisateursAdresse() {
        return UtilisateursAdresse;
    }

    public void setUtilisateursAdresse(UtilisateursAdresses utilisateursAdresse) {
        UtilisateursAdresse = utilisateursAdresse;
    }
}
