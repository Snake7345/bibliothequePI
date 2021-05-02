package managedBean;

import enumeration.ExemplairesLivresEtatEnum;

import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
import java.io.Serializable;

public class ExemplairesLivresEnumBean implements Serializable{
        private static final long serialVersionUID = 1L;

        public ExemplairesLivresEtatEnum[] getSexeEnum()
        {
            return ExemplairesLivresEtatEnum.values();
        }

}
