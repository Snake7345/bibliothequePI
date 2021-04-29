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

    private UtilisateurSexeEnum sexe;

    public UtilisateurSexeEnum[] getSexeEnum()
    {
        return UtilisateurSexeEnum.values();
    }

    @PostConstruct
    public void init()
    {
        sexe = UtilisateurSexeEnum.A;
    }

    public UtilisateurSexeEnum getSexe() {
        return sexe;
    }

    public void setSexe(UtilisateurSexeEnum sexe) {
        this.sexe = sexe;
    }
}
