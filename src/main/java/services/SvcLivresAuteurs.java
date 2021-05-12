package services;

import entities.*;

public class SvcLivresAuteurs extends Service<LivresAuteurs>{

    public SvcLivresAuteurs()
    {
        super();
    }

    @Override
    public LivresAuteurs save(LivresAuteurs livresAuteurs)
    {
        if (livresAuteurs.getIdLivresAuteurs() == 0) {
            em.persist(livresAuteurs);
        } else {
            livresAuteurs = em.merge(livresAuteurs);
        }

        return livresAuteurs;
    }

    public LivresAuteurs createLivresAuteurs(Livres l, Auteurs a)
    {
        LivresAuteurs la = new LivresAuteurs();
        la.setLivre(l);
        la.setAuteur(a);

        return la;
    }
}
