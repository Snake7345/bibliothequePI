package managedBean;

import entities.Pays;
import entities.Penalites;
import org.apache.log4j.Logger;
import services.SvcPays;
import services.SvcPenalites;

import javax.annotation.ManagedBean;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Named
@SessionScoped
public class PenalitesBean implements Serializable {
    private static final long serialVersionUID = 1L;

    private Penalites penalite;
    private final SvcPenalites service = new SvcPenalites();
    private static final Logger log = Logger.getLogger(PenalitesBean.class);

    public List<Penalites> getReadAll()
    {
        List<Penalites> listPenalites = new ArrayList<Penalites>();
        listPenalites = service.findAllPenalites();


        return listPenalites;
    }

    public Penalites getPenalite() {
        return penalite;
    }

    public void setPenalite(Penalites penalite) {
        this.penalite = penalite;
    }
}
