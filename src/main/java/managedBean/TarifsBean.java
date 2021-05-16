package managedBean;

import entities.Tarifs;
import org.apache.log4j.Logger;
import services.SvcTarifs;

import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Named
@SessionScoped
public class TarifsBean implements Serializable {
    // Déclaration des variables globales
    private static final long serialVersionUID = 1L;
    private Tarifs tarif;
    private static final Logger log = Logger.getLogger(TarifsBean.class);


    /*
     * Méthode qui permet via le service de retourner la liste de toutes les grilles tarifaires
     */

    public List<Tarifs> getReadAll()
    {
        SvcTarifs service = new SvcTarifs();
        List<Tarifs> listTarifs = new ArrayList<Tarifs>();
        listTarifs = service.findAllTarifs();

        service.close();
        return listTarifs;
    }

    //-------------------------------Getter & Setter--------------------------------------------

    public Tarifs getTarif() {
        return tarif;
    }

    public void setTarif(Tarifs tarif) {
        this.tarif = tarif;
    }
}
