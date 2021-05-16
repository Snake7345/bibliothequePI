package services;

import entities.Auteurs;
import entities.Livres;
import entities.LivresAuteurs;
import org.apache.log4j.Logger;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SvcLivresAuteurs extends Service<LivresAuteurs>{

    public SvcLivresAuteurs()
    {
        super();
    }
    private static final Logger log = Logger.getLogger(SvcLivresAuteurs.class);

    public List<LivresAuteurs> GetByLivre(Livres livres) {
        Map<String, Livres> param = new HashMap<>();
        param.put("livre", livres);

        return finder.findByNamedQuery("LivresAuteurs.findBylivre", param);
    }
    @Override
    public LivresAuteurs save(LivresAuteurs livresAuteurs)
    {
        log.debug("test a la con" + livresAuteurs.getLivre().getIdLivres() + "  " + livresAuteurs.getAuteur().getIdAuteurs());
        if (livresAuteurs.getIdLivresAuteurs() == 0) {
            em.persist(livresAuteurs);
            log.debug("1");
        } else {
            livresAuteurs = em.merge(livresAuteurs);
            log.debug("2");
        }
        log.debug("truc a la con");
        return livresAuteurs;
    }

    public LivresAuteurs createLivresAuteurs(Livres l, Auteurs a)
    {
        log.debug("truc a la con numeroshlag");
        LivresAuteurs la = new LivresAuteurs();
        la.setLivre(l);
        la.setAuteur(a);

        return la;
    }
}
