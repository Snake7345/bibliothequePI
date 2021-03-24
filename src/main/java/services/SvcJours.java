package services;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceUnit;

import entities.Genres;
import entities.Jours;
import org.apache.log4j.Logger;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SvcJours extends Service<Jours> implements Serializable {
	private static final Logger log = Logger.getLogger(SvcJours.class);
	private static final long serialVersionUID = 1L;
	Map<String, Object> params = new HashMap<String, Object>();

	public SvcJours() {
		super();
	}

	@Override
	public Jours save(Jours jours) {
		return null;
	}


	public List<Jours> findAllJours() {
		return finder.findByNamedQuery("Jours.findAll", null);
	}


}
