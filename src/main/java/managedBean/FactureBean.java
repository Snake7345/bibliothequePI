package managedBean;

import entities.Factures;
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
    // Déclaration des variables globales
    private static final long serialVersionUID = 1L;

    private Factures facture;
    private static final Logger log = Logger.getLogger(FactureBean.class);

    public List<Factures> getReadAll()
    {
        SvcFacture service = new SvcFacture();
        List<Factures> listFact = new ArrayList<Factures>();
        listFact= service.findAllFacture();

        service.close();
        return listFact;
    }

    //-------------------------------Getter & Setter--------------------------------------------

    public Factures getFacture() {
        return facture;
    }

    public void setFacture(Factures facture) {
        this.facture = facture;
    }

}
