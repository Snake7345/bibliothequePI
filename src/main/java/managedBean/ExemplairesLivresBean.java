package managedBean;

import entities.ExemplairesLivres;
import org.apache.log4j.Logger;
import services.SvcEditeurs;
import services.SvcExemplairesLivres;

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
public class ExemplairesLivresBean implements Serializable {
    // Déclaration des variables globales
    private static final long serialVersionUID = 1L;
    private ExemplairesLivres exemplairesLivre;
    private static final Logger log = Logger.getLogger(ExemplairesLivresBean.class);


    public void save()
    {
        SvcExemplairesLivres service = new SvcExemplairesLivres();
        EntityTransaction transaction = service.getTransaction();
        transaction.begin();
        try {
            service.save(exemplairesLivre);
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
     * Méthode qui permet via le service de retourner la liste de tous les exemplaires livres
     */
    public List<ExemplairesLivres> getReadAll()
    {
        SvcExemplairesLivres service = new SvcExemplairesLivres();
        List<ExemplairesLivres> listExemplaires = new ArrayList<ExemplairesLivres>();
        listExemplaires= service.findAllExemplairesLivres();

        service.close();
        return listExemplaires;
    }

    //-------------------------------Getter & Setter--------------------------------------------

    public ExemplairesLivres getExemplairesLivre() {
        return exemplairesLivre;
    }

    public void setExemplairesLivre(ExemplairesLivres exemplairesLivre) {
        this.exemplairesLivre = exemplairesLivre;
    }

}
