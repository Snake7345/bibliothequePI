package managedBean;

import enumeration.FactureEtatEnum;

import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
import java.io.Serializable;

public class FactureEnumBean implements Serializable{

        private static final long serialVersionUID = 1L;

        public FactureEtatEnum[] getEtatEnum()
        {
            return FactureEtatEnum.values();
        }

}
