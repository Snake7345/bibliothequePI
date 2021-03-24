package services;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceUnit;

import entities.Livres;
import entities.LivresAuteurs;
import entities.LivresGenres;
import org.apache.log4j.Logger;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SvcLivresAuteurs extends Service<LivresAuteurs> implements Serializable {
	private static final Logger log = Logger.getLogger(SvcLivresAuteurs.class);
	private static final long serialVersionUID = 1L;
	Map<String, Object> params = new HashMap<String, Object>();

	public SvcLivresAuteurs() {
		super();
	}

	@Override
	public LivresAuteurs save(LivresAuteurs livresAuteurs) {
		return null;
	}

	public List<LivresAuteurs> findAllLivresAuteurs() {
		return finder.findByNamedQuery("LivresAuteurs.findAll", null);
	}



}

