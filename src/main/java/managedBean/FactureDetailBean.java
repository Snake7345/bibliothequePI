package managedBean;

import entities.FacturesDetail;
import org.apache.log4j.Logger;
import services.SvcFactureDetail;

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
public class FactureDetailBean implements Serializable {
    // Déclaration des variables globales
    private static final long serialVersionUID = 1L;
    private FacturesDetail facturesDetail;

    private static final Logger log = Logger.getLogger(FactureDetailBean.class);



    public void save()
    {
        SvcFactureDetail service = new SvcFactureDetail();
        EntityTransaction transaction = service.getTransaction();
        transaction.begin();
        try {
            service.save(facturesDetail);
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

    public List<FacturesDetail> getReadAll()
    {
        SvcFactureDetail service = new SvcFactureDetail();
        List<FacturesDetail> listFactD = new ArrayList<FacturesDetail>();
        listFactD= service.findAllFactureDetail();

        service.close();
        return listFactD;
    }

    //-------------------------------Getter & Setter--------------------------------------------

    public FacturesDetail getFactureDetail() {
        return facturesDetail;
    }

    public void setFactureDetail(FacturesDetail facturesDetail) {
        this.facturesDetail = facturesDetail;
    }







}
