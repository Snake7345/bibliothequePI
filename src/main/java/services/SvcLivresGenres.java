package services;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceUnit;

import entities.Livres;
import entities.LivresGenres;
import org.apache.log4j.Logger;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SvcLivresGenres extends Service<LivresGenres> implements Serializable {
	private static final Logger log = Logger.getLogger(SvcLivresGenres.class);
	private static final long serialVersionUID = 1L;
	Map<String, Object> params = new HashMap<String, Object>();

	public SvcLivresGenres() {
		super();
	}

	@Override
	public LivresGenres save(LivresGenres livresGenres) {
		return null;
	}


	public List<LivresGenres> findAllLivresGenres() {
		return finder.findByNamedQuery("LivresGenres.findAll", null);
	}


}

