package managedBean;

import entities.Jours;
import org.apache.log4j.Logger;
import services.SvcJours;

import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Named
@SessionScoped
public class JoursBean implements Serializable {
    private static final long serialVersionUID = 1L;
    private Jours jour;
    private static final Logger log = Logger.getLogger(JoursBean.class);

    public List<Jours> getReadAll()
    {
        SvcJours service = new SvcJours();
        List<Jours> listJours = new ArrayList<Jours>();
        listJours= service.findAllJours();

        service.close();
        return listJours;
    }

    public Jours getJour() {
        return jour;
    }

    public void setJour(Jours jour) {
        this.jour = jour;
    }


}
