package managedBean;

import entities.Penalites;
import org.apache.log4j.Logger;
import services.SvcPenalites;

import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Named
@SessionScoped
public class PenalitesBean implements Serializable {
    // Déclaration des variables globales
    private static final long serialVersionUID = 1L;
    private Penalites penalite;
    private static final Logger log = Logger.getLogger(PenalitesBean.class);

    public List<Penalites> getReadAll()
    {
        SvcPenalites service = new SvcPenalites();
        List<Penalites> listPenalites = new ArrayList<Penalites>();
        listPenalites = service.findAllPenalites();


        return listPenalites;
    }

    //-------------------------------Getter & Setter--------------------------------------------

    public Penalites getPenalite() {
        return penalite;
    }

    public void setPenalite(Penalites penalite) {
        this.penalite = penalite;
    }
}
