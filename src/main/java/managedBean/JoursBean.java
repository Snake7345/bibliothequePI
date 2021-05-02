package managedBean;

import entities.Facture;
import entities.Jours;
import org.apache.log4j.Logger;
import services.SvcFacture;
import services.SvcJours;

import javax.annotation.ManagedBean;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
import java.io.Serializable;

@Named
@SessionScoped
public class JoursBean implements Serializable {
    private static final long serialVersionUID = 1L;
    private Jours jour;
    private final SvcJours service = new SvcJours();
    private static final Logger log = Logger.getLogger(JoursBean.class);

}
