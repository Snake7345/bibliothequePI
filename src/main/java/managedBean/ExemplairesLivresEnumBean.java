package managedBean;

import enumeration.ExemplairesLivresEtatEnum;

import java.io.Serializable;

public class ExemplairesLivresEnumBean implements Serializable{
        private static final long serialVersionUID = 1L;

        public ExemplairesLivresEtatEnum[] getSexeEnum()
        {
            return ExemplairesLivresEtatEnum.values();
        }

}
