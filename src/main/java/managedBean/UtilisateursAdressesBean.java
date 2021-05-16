package managedBean;

import entities.UtilisateursAdresses;
import org.apache.log4j.Logger;
import services.SvcUtilisateursAdresses;

import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
import java.io.Serializable;

@Named
@SessionScoped
public class UtilisateursAdressesBean implements Serializable {
    // Déclaration des variables globales
    private static final long serialVersionUID = 1L;
    private UtilisateursAdresses UtilisateursAdresse;
    private final SvcUtilisateursAdresses service = new SvcUtilisateursAdresses();
    private static final Logger log = Logger.getLogger(UtilisateursAdressesBean.class);


    //-------------------------------Getter & Setter--------------------------------------------

    public UtilisateursAdresses getUtilisateursAdresse() {
        return UtilisateursAdresse;
    }

    public void setUtilisateursAdresse(UtilisateursAdresses utilisateursAdresse) {
        UtilisateursAdresse = utilisateursAdresse;
    }
}
