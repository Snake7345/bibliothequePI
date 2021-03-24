package services;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceUnit;

import entities.Jours;
import entities.Livres;
import org.apache.log4j.Logger;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SvcLivres extends Service<Livres> implements Serializable {
	private static final Logger log = Logger.getLogger(SvcLivres.class);
	private static final long serialVersionUID = 1L;
	Map<String, Object> params = new HashMap<String, Object>();

	public SvcLivres() {
		super();
	}

	@Override
	public Livres save(Livres livres) {
		return null;
	}


	public List<Livres> findAllLivres() {
		return finder.findByNamedQuery("Livres.findAll", null);
	}


}

