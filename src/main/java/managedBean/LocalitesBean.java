package managedBean;

import entities.Facture;
import entities.Localites;
import entities.Utilisateurs;
import org.apache.log4j.Logger;
import services.SvcFacture;
import services.SvcLivres;
import services.SvcLocalites;

import javax.annotation.ManagedBean;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
import javax.persistence.EntityTransaction;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Named
@SessionScoped
public class LocalitesBean implements Serializable {
    private static final long serialVersionUID = 1L;
    private Localites localite;
    private static final Logger log = Logger.getLogger(LocalitesBean.class);

    public List<Localites> getReadAll()
    {
        SvcLocalites service = new SvcLocalites();
        List<Localites> listLoca = new ArrayList<Localites>();
        listLoca = service.findAllLocalites();


        return listLoca;
    }

    public Localites getLocalite() {
        return localite;
    }

    public void setLocalite(Localites localite) {
        this.localite = localite;
    }

}
