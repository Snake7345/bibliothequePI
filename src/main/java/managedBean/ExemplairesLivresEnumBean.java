package managedBean;

import enumeration.ExemplairesLivresEtatEnum;

import java.io.Serializable;

public class ExemplairesLivresEnumBean implements Serializable{
    // Déclaration des variables globales
        private static final long serialVersionUID = 1L;

        public ExemplairesLivresEtatEnum[] getSexeEnum()
        {
            return ExemplairesLivresEtatEnum.values();
        }

}
