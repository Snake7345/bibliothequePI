package managedBean;

import entities.Facture;
import entities.LivresAuteurs;
import org.apache.log4j.Logger;
import services.SvcFacture;

import javax.annotation.ManagedBean;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
import java.io.Serializable;

@Named
@SessionScoped
public class LivresAuteursBean implements Serializable {
    private static final long serialVersionUID = 1L;


}
