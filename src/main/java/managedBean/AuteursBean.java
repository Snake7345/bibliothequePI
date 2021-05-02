package managedBean;

import entities.Auteurs;
import org.apache.log4j.Logger;
import services.SvcAdresses;
import services.SvcAuteurs;

import javax.annotation.ManagedBean;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
import java.io.Serializable;

@Named
@SessionScoped
public class AuteursBean implements Serializable {
    private static final long serialVersionUID = 1L;
    private Auteurs auteur;
    private final SvcAuteurs service = new SvcAuteurs();
    private static final Logger log = Logger.getLogger(AuteursBean.class);

    public Auteurs getAuteur() {
        return auteur;
    }

    public void setAuteur(Auteurs auteur) {
        this.auteur = auteur;
    }

}
