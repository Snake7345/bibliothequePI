package managedBean;

import javax.annotation.ManagedBean;
import javax.inject.Named;
import java.io.Serializable;

@ManagedBean("factureBean")
public class FactureBean implements Serializable {
    private static final long serialVersionUID = 1L;

}
