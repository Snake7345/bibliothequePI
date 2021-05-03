package managedBean;

import entities.Facture;
import entities.Jours;
import entities.Livres;
import org.apache.log4j.Logger;
import services.SvcFacture;
import services.SvcLivres;

import javax.annotation.ManagedBean;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Named
@SessionScoped
public class LivresBean implements Serializable {
    private static final long serialVersionUID = 1L;
    private Livres livre;
    private final SvcLivres service = new SvcLivres();
    private static final Logger log = Logger.getLogger(LivresBean.class);


    public List<Livres> getReadAll()
    {
        List<Livres> listLiv = new ArrayList<Livres>();
        listLiv= service.findAllLivres();


        return listLiv;
    }

    public Livres getLivre() {
        return livre;
    }

    public void setLivre(Livres livre) {
        this.livre = livre;
    }




}
