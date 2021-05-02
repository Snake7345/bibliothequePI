package managedBean;

import entities.Editeurs;
import org.apache.log4j.Logger;
import services.SvcBibliotheques;
import services.SvcEditeurs;

import javax.annotation.ManagedBean;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
import java.io.Serializable;

@Named
@SessionScoped
public class EditeursBean implements Serializable {
    private static final long serialVersionUID = 1L;
    private Editeurs editeur;
    private final SvcEditeurs service = new SvcEditeurs();
    private static final Logger log = Logger.getLogger(EditeursBean.class);


    public Editeurs getEditeur() {
        return editeur;
    }

    public void setEditeur(Editeurs editeur) {
        this.editeur = editeur;
    }

}
