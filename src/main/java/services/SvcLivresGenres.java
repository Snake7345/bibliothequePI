package services;


import entities.Genres;
import entities.Livres;
import entities.LivresAuteurs;
import entities.LivresGenres;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    public List<LivresGenres> GetByLivre(Livres livres) {
        Map<String, Livres> param = new HashMap<>();
        param.put("livre", livres);

        return finder.findByNamedQuery("LivresGenres.findBylivre", param);
    }
}
