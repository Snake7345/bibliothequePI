package managedBean;

import entities.Pays;
import entities.Roles;
import entities.TarifsPenalites;
import org.apache.log4j.Logger;
import services.SvcPays;
import services.SvcTarifsPenalites;

import javax.annotation.ManagedBean;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
import javax.persistence.EntityTransaction;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Named
@SessionScoped
public class TarifsPenalitesBean implements Serializable {
    private static final long serialVersionUID = 1L;
    private TarifsPenalites tarifsPenalite;
    SvcTarifsPenalites service = new SvcTarifsPenalites();
    EntityTransaction transaction = service.getTransaction();
    private static final Logger log = Logger.getLogger(TarifsPenalites.class);

    public List<TarifsPenalites> getReadAll()
    {
        List<TarifsPenalites> listTarifsPenalites = new ArrayList<TarifsPenalites>();
        listTarifsPenalites = service.findAllTarifsPenalites();

        service.close();
        return listTarifsPenalites;
    }

    public TarifsPenalites getTarifsPenalite() {
        return tarifsPenalite;
    }

    public void setTarifsPenalite(TarifsPenalites tarifsPenalite) {
        this.tarifsPenalite = tarifsPenalite;
    }
}
