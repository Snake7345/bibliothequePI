package services;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceUnit;

import entities.Livres;
import entities.Pays;
import org.apache.log4j.Logger;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SvcPays extends Service<Pays> implements Serializable {
	private static final Logger log = Logger.getLogger(SvcPays.class);
	private static final long serialVersionUID = 1L;
	Map<String, Object> params = new HashMap<String, Object>();

	public SvcPays() {
		super();
	}

	@Override
	public Pays save(Pays pays) {
		if (pays.getIdPays() == 0) {
			em.persist(pays);
		} else {
			pays = em.merge(pays);
		}

		return pays;
	}


	public List<Pays> findAllPays() {
		return finder.findByNamedQuery("Pays.findAll", null);
	}


}

