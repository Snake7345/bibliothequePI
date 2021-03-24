package services;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceUnit;

import entities.Facture;
import entities.Genres;
import org.apache.log4j.Logger;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SvcGenres extends Service<Genres> implements Serializable {
	private static final Logger log = Logger.getLogger(SvcGenres.class);
	private static final long serialVersionUID = 1L;
	Map<String, Object> params = new HashMap<String, Object>();

	public SvcGenres() {
		super();
	}

	@Override
	public Genres save(Genres genres) {
		return null;
	}


	public List<Genres> findAllGenres() {
		return finder.findByNamedQuery("Genres.findAll", null);
	}


}
