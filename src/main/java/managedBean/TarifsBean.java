package managedBean;

import entities.Pays;
import entities.Tarifs;
import org.apache.log4j.Logger;
import services.SvcPays;
import services.SvcTarifs;

import javax.annotation.ManagedBean;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
import java.io.Serializable;

@Named
@SessionScoped
public class TarifsBean implements Serializable {
    private static final long serialVersionUID = 1L;
    private Tarifs tarif;
    private final SvcTarifs service = new SvcTarifs();
    private static final Logger log = Logger.getLogger(TarifsBean.class);

    public Tarifs getTarif() {
        return tarif;
    }

    public void setTarif(Tarifs tarif) {
        this.tarif = tarif;
    }
}
