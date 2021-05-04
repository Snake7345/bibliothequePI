package managedBean;

import entities.Pays;
import entities.Roles;
import entities.Tarifs;
import org.apache.log4j.Logger;
import services.SvcPays;
import services.SvcRoles;
import services.SvcTarifs;

import javax.annotation.ManagedBean;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
import javax.persistence.EntityTransaction;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Named
@SessionScoped
public class TarifsBean implements Serializable {
    private static final long serialVersionUID = 1L;
    private Tarifs tarif;
    private static final Logger log = Logger.getLogger(TarifsBean.class);

    public List<Tarifs> getReadAll()
    {
        SvcTarifs service = new SvcTarifs();
        List<Tarifs> listTarifs = new ArrayList<Tarifs>();
        listTarifs = service.findAllTarifs();

        service.close();
        return listTarifs;
    }

    public Tarifs getTarif() {
        return tarif;
    }

    public void setTarif(Tarifs tarif) {
        this.tarif = tarif;
    }
}
