package services;

import entities.Penalites;
import org.apache.log4j.Logger;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SvcPenalites extends Service<Penalites> implements Serializable {
	private static final Logger log = Logger.getLogger(SvcPenalites.class);
	private static final long serialVersionUID = 1L;
	Map<String, Object> params = new HashMap<String, Object>();

	public SvcPenalites() {
		super();
	}

	@Override
	public Penalites save(Penalites penalites) {
		if (penalites.getIdPenalites() == 0) {
			em.persist(penalites);
		} else {
			penalites = em.merge(penalites);
		}

		return penalites;
	}


	public List<Penalites> findAllPenalites() {
		return finder.findByNamedQuery("Penalites.findAll", null);
	}


}

