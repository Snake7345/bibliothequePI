package managedBean;

import enumeration.UtilisateurSexeEnum;

import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
import java.io.Serializable;

@Named
@SessionScoped
public class UtilisateursEnumBean implements Serializable
{
    private static final long serialVersionUID = 1L;

    public UtilisateurSexeEnum[] getSexeEnum()
    {
        return UtilisateurSexeEnum.values();
    }
}
