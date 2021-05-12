package services;

import entities.*;
import managedBean.LivresBean;
import org.apache.log4j.Logger;

public class SvcLivresAuteurs extends Service<LivresAuteurs>{

    public SvcLivresAuteurs()
    {
        super();
    }
    private static final Logger log = Logger.getLogger(SvcLivresAuteurs.class);

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
