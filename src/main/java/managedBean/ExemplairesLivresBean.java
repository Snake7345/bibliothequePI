package managedBean;

import entities.Auteurs;
import entities.Editeurs;
import entities.ExemplairesLivres;
import org.apache.log4j.Logger;
import services.SvcBibliotheques;
import services.SvcEditeurs;
import services.SvcExemplairesLivres;

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
public class ExemplairesLivresBean implements Serializable {
    private static final long serialVersionUID = 1L;
    private ExemplairesLivres exemplairesLivre;
    SvcExemplairesLivres service = new SvcExemplairesLivres();
    EntityTransaction transaction = service.getTransaction();
    private static final Logger log = Logger.getLogger(ExemplairesLivresBean.class);


    public List<ExemplairesLivres> getReadAll()
    {
        List<ExemplairesLivres> listExemplaires = new ArrayList<ExemplairesLivres>();
        listExemplaires= service.findAllExemplairesLivres();

        service.close();
        return listExemplaires;
    }

    public ExemplairesLivres getExemplairesLivre() {
        return exemplairesLivre;
    }

    public void setExemplairesLivre(ExemplairesLivres exemplairesLivre) {
        this.exemplairesLivre = exemplairesLivre;
    }

}
