package services;

import entities.Genres;
import org.apache.log4j.Logger;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SvcGenres extends Service<Genres> implements Serializable {
	//Déclaration des variables
	private static final Logger log = Logger.getLogger(SvcGenres.class);
	private static final long serialVersionUID = 1L;
	Map<String, Object> params = new HashMap<String, Object>();

	public SvcGenres() {
		super();
	}

	// Méthode qui permet de sauver un genre et de le mettre en DB
	@Override
	public Genres save(Genres genres) {
		if (genres.getIdGenres() == 0) {
			em.persist(genres);
		} else {
			genres = em.merge(genres);
		}

		return genres;
	}

	//Méthode qui permet via une requete de retourner la liste entière des genres
	public List<Genres> findAllGenres() {
		return finder.findByNamedQuery("Genres.findAllTri", null);
	}


}
