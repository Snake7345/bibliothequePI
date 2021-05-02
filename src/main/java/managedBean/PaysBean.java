package managedBean;

import entities.Localites;
import entities.Pays;
import org.apache.log4j.Logger;
import services.SvcLocalites;
import services.SvcPays;

import javax.annotation.ManagedBean;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
import java.io.Serializable;

@Named
@SessionScoped
public class PaysBean implements Serializable {
    private static final long serialVersionUID = 1L;

    private Pays pays;
    private final SvcPays service = new SvcPays();
    private static final Logger log = Logger.getLogger(PaysBean.class);


    public Pays getPays() {
        return pays;
    }

    public void setPays(Pays pays) {
        this.pays = pays;
    }


}
