package managedBean;

import entities.Pays;
import entities.TarifsPenalites;
import entities.UtilisateursAdresses;
import org.apache.log4j.Logger;
import services.SvcPays;
import services.SvcUtilisateursAdresses;

import javax.annotation.ManagedBean;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Named
@SessionScoped
public class UtilisateursAdressesBean implements Serializable {
    private static final long serialVersionUID = 1L;
    private UtilisateursAdresses UtilisateursAdresse;
    private final SvcUtilisateursAdresses service = new SvcUtilisateursAdresses();
    private static final Logger log = Logger.getLogger(UtilisateursAdressesBean.class);


    public UtilisateursAdresses getUtilisateursAdresse() {
        return UtilisateursAdresse;
    }

    public void setUtilisateursAdresse(UtilisateursAdresses utilisateursAdresse) {
        UtilisateursAdresse = utilisateursAdresse;
    }
}
