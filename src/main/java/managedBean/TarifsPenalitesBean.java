package managedBean;

import entities.Pays;
import entities.TarifsPenalites;
import org.apache.log4j.Logger;
import services.SvcPays;
import services.SvcTarifsPenalites;

import javax.annotation.ManagedBean;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
import java.io.Serializable;

@Named
@SessionScoped
public class TarifsPenalitesBean implements Serializable {
    private static final long serialVersionUID = 1L;
    private TarifsPenalites tarifsPenalite;
    private final SvcTarifsPenalites service = new SvcTarifsPenalites();
    private static final Logger log = Logger.getLogger(TarifsPenalites.class);


    public TarifsPenalites getTarifsPenalite() {
        return tarifsPenalite;
    }

    public void setTarifsPenalite(TarifsPenalites tarifsPenalite) {
        this.tarifsPenalite = tarifsPenalite;
    }
}
