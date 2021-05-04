package managedBean;

import entities.Facture;
import entities.FactureDetail;
import entities.Jours;
import org.apache.log4j.Logger;
import services.SvcFacture;
import services.SvcGenres;
import services.SvcJours;

import javax.annotation.ManagedBean;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
import javax.persistence.EntityTransaction;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Named
@SessionScoped
public class JoursBean implements Serializable {
    private static final long serialVersionUID = 1L;
    private Jours jour;
    SvcJours service = new SvcJours();
    EntityTransaction transaction = service.getTransaction();
    private static final Logger log = Logger.getLogger(JoursBean.class);

    public List<Jours> getReadAll()
    {
        List<Jours> listJours = new ArrayList<Jours>();
        listJours= service.findAllJours();

        service.close();
        return listJours;
    }

    public Jours getJour() {
        return jour;
    }

    public void setJour(Jours jour) {
        this.jour = jour;
    }


}
