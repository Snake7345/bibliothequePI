package services;

import entities.Adresses;
import entities.Utilisateurs;
import entities.UtilisateursAdresses;
import org.apache.log4j.Logger;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class SvcUtilisateursAdresses extends Service<UtilisateursAdresses> implements Serializable
{
        private static final Logger log = Logger.getLogger(services.SvcUtilisateursAdresses.class);
        private static final long serialVersionUID = 1L;
        Map<String, Object> params = new HashMap<String, Object>();

        public SvcUtilisateursAdresses() {
            super();
        }

        @Override
        public UtilisateursAdresses save(UtilisateursAdresses utilisateursAdresses) {
            if (utilisateursAdresses.getIdUtilisateursAdresses() == 0) {
                em.persist(utilisateursAdresses);
            } else {
                utilisateursAdresses = em.merge(utilisateursAdresses);
            }

            return utilisateursAdresses;
        }



        public UtilisateursAdresses createUtilisateursAdresses(Utilisateurs u, Adresses a)
        {
            UtilisateursAdresses ua = new UtilisateursAdresses();
            ua.setAdresse(a);
            ua.setUtilisateur(u);

            return ua;
        }
}
