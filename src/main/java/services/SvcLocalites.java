package services;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceUnit;

import entities.Livres;
import entities.Localites;
import org.apache.log4j.Logger;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SvcLocalites extends Service<Localites> implements Serializable {
	private static final Logger log = Logger.getLogger(SvcLocalites.class);
	private static final long serialVersionUID = 1L;
	Map<String, Object> params = new HashMap<String, Object>();

	public SvcLocalites() {
		super();
	}

	@Override
	public Localites save(Localites localites) {
		if (localites.getIdLocalites() == 0) {
			em.persist(localites);
		} else {
			localites = em.merge(localites);
		}

		return localites;
	}


	public List<Localites> findAllLocalites() {
		return finder.findByNamedQuery("Localites.findAll", null);
	}


}

