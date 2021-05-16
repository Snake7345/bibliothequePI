package managedBean;

import entities.Facture;
import org.apache.log4j.Logger;
import services.SvcFacture;

import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Named
@SessionScoped
public class FactureBean implements Serializable {
    private static final long serialVersionUID = 1L;

    private Facture facture;
    private static final Logger log = Logger.getLogger(FactureBean.class);

    public List<Facture> getReadAll()
    {
        SvcFacture service = new SvcFacture();
        List<Facture> listFact = new ArrayList<Facture>();
        listFact= service.findAllFacture();

        service.close();
        return listFact;
    }

    public Facture getFacture() {
        return facture;
    }

    public void setFacture(Facture facture) {
        this.facture = facture;
    }

}
