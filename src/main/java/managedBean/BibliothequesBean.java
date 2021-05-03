package managedBean;

import entities.Auteurs;
import entities.Bibliotheques;
import org.apache.log4j.Logger;
import services.SvcAuteurs;
import services.SvcBibliotheques;

import javax.annotation.ManagedBean;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Named
@SessionScoped
public class BibliothequesBean implements Serializable {
    private static final long serialVersionUID = 1L;
    private Bibliotheques bibliotheque;
    private final SvcBibliotheques service = new SvcBibliotheques();
    private static final Logger log = Logger.getLogger(BibliothequesBean.class);

    public List<Bibliotheques> getReadAll()
    {
        List<Bibliotheques> listBib = new ArrayList<Bibliotheques>();
        listBib= service.findAllBibliotheques();


        return listBib;
    }

    public Bibliotheques getBibliotheque() {
        return bibliotheque;
    }

    public void setBibliotheque(Bibliotheques bibliotheque) {
        this.bibliotheque = bibliotheque;
    }
}
