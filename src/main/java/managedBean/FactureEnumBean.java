package managedBean;

import enumeration.FactureEtatEnum;

import java.io.Serializable;

public class FactureEnumBean implements Serializable{

        private static final long serialVersionUID = 1L;

        public FactureEtatEnum[] getEtatEnum()
        {
            return FactureEtatEnum.values();
        }

}
