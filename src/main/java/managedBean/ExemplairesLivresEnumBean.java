package managedBean;

import enumeration.ExemplairesLivresEtatEnum;

import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
import java.io.Serializable;

@Named
@SessionScoped
public class ExemplairesLivresEnumBean implements Serializable{
    // Déclaration des variables globales
        private static final long serialVersionUID = 1L;

        public ExemplairesLivresEtatEnum[] getEtatEnum()
        {
            return ExemplairesLivresEtatEnum.values();
        }

}
