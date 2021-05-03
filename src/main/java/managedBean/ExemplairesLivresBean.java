package managedBean;

import entities.Editeurs;
import entities.ExemplairesLivres;
import org.apache.log4j.Logger;
import services.SvcBibliotheques;
import services.SvcExemplairesLivres;

import javax.annotation.ManagedBean;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Named
@SessionScoped
public class ExemplairesLivresBean implements Serializable {
    private static final long serialVersionUID = 1L;
    private ExemplairesLivres exemplairesLivre;
    private final SvcExemplairesLivres service = new SvcExemplairesLivres();
    private static final Logger log = Logger.getLogger(ExemplairesLivresBean.class);

    public List<ExemplairesLivres> getReadAll()
    {
        List<ExemplairesLivres> listExemplaires = new ArrayList<ExemplairesLivres>();
        listExemplaires= service.findAllExemplairesLivres();


        return listExemplaires;
    }

    public ExemplairesLivres getExemplairesLivre() {
        return exemplairesLivre;
    }

    public void setExemplairesLivre(ExemplairesLivres exemplairesLivre) {
        this.exemplairesLivre = exemplairesLivre;
    }

}
