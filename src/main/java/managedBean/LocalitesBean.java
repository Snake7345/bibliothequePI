package managedBean;

import entities.Localites;
import org.apache.log4j.Logger;
import services.SvcLocalites;

import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Named
@SessionScoped
public class LocalitesBean implements Serializable {
    // Déclaration des variables globales
    private static final long serialVersionUID = 1L;
    private Localites localite;
    private static final Logger log = Logger.getLogger(LocalitesBean.class);

    /*
     * Méthode qui permet via le service de retourner la liste de toutes les localités
     */
    public List<Localites> getReadAll()
    {
        SvcLocalites service = new SvcLocalites();
        List<Localites> listLoca = new ArrayList<Localites>();
        listLoca = service.findAllLocalites();


        return listLoca;
    }


    //-------------------------------Getter & Setter--------------------------------------------

    public Localites getLocalite() {
        return localite;
    }

    public void setLocalite(Localites localite) {
        this.localite = localite;
    }

}
