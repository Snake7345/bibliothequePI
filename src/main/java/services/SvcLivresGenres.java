package services;


import entities.Genres;
import entities.Livres;
import entities.LivresGenres;

public class SvcLivresGenres extends Service<LivresGenres>

{
    public SvcLivresGenres()
        {
            super();
        }

    @Override
    public LivresGenres save(LivresGenres livresGenres)
    {
        if (livresGenres.getIdLivresGenres() == 0) {
            em.persist(livresGenres);
        } else {
            livresGenres = em.merge(livresGenres);
        }

        return livresGenres;
    }

    public LivresGenres createLivresGenres(Livres l, Genres g)
    {
        LivresGenres lg = new LivresGenres();
        lg.setLivre(l);
        lg.setGenre(g);

        return lg;
    }
}
