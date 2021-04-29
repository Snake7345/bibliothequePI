package managedBean;

import javax.annotation.ManagedBean;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
import java.io.Serializable;

@Named
@SessionScoped
public class LivresBean implements Serializable {
    private static final long serialVersionUID = 1L;

}
