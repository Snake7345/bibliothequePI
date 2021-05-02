package managedBean;

import entities.Adresses;
import entities.Utilisateurs;
import org.apache.log4j.Logger;
import services.SvcAdresses;
import services.SvcUtilisateurs;

import javax.annotation.ManagedBean;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Named
@SessionScoped
public class AdressesBean implements Serializable {
    private static final long serialVersionUID = 1L;
    private Adresses adresse;
    private final SvcAdresses service = new SvcAdresses();
    private static final Logger log = Logger.getLogger(AdressesBean.class);


    public List<Adresses> getReadAll()
    {
        List<Adresses> listAd = new ArrayList<Adresses>();
        listAd= service.findAllAdresses();


        return listAd;
    }


    public Adresses getAdresse() {
        return adresse;
    }

    public void setAdresse(Adresses adresse) {
        this.adresse = adresse;
    }


}
