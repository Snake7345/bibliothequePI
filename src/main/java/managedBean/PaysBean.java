package managedBean;

import entities.Pays;
import org.apache.log4j.Logger;
import services.SvcPays;

import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Named
@SessionScoped
public class PaysBean implements Serializable {
    // Déclaration des variables globales
    private static final long serialVersionUID = 1L;

    private Pays pays;
    private final SvcPays service = new SvcPays();
    private static final Logger log = Logger.getLogger(PaysBean.class);

    /*
     * Méthode qui permet via le service de retourner la liste de tous les pays
     */
    public List<Pays> getReadAll()
    {
        List<Pays> listPays = new ArrayList<Pays>();
        listPays = service.findAllPays();


        return listPays;
    }


    //-------------------------------Getter & Setter--------------------------------------------

    public Pays getPays() {
        return pays;
    }

    public void setPays(Pays pays) {
        this.pays = pays;
    }


}
