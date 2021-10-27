package services;

import entities.*;
import org.apache.log4j.Logger;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SvcUtilisateursBibliotheques extends Service<UtilisateursBibliotheques> implements Serializable
{
        private static final Logger log = Logger.getLogger(SvcUtilisateursBibliotheques.class);
        private static final long serialVersionUID = 1L;
        Map<String, Object> params = new HashMap<String, Object>();

        public SvcUtilisateursBibliotheques() {
            super();
        }

        @Override
        public UtilisateursBibliotheques save(UtilisateursBibliotheques utilisateursBibliotheques) {
            if (utilisateursBibliotheques.getIdUtilisateursBibliotheques() == 0) {
                em.persist(utilisateursBibliotheques);
            } else {
                utilisateursBibliotheques = em.merge(utilisateursBibliotheques);
            }

            return utilisateursBibliotheques;
        }

        public UtilisateursBibliotheques createUtilisateursBibliotheques(Utilisateurs u, Bibliotheques b)
        {
            UtilisateursBibliotheques ub = new UtilisateursBibliotheques();
            ub.setBibliotheque(b);
            ub.setUtilisateur(u);

            return ub;
        }
    public List<UtilisateursBibliotheques> findOneUtilisateurBibliotheque (Bibliotheques bib, Utilisateurs util) {
        Map<String, Object> param = new HashMap<>();
        param.put("bibliotheque", bib);
        param.put("utilisateur", util);
        return finder.findByNamedQuery("UtilisateursBibliotheques.findOne", param);
    }
}
