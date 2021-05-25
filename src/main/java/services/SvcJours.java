package services;

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
		if (jours.getIdJours() == 0) {
			em.persist(jours);
		} else {
			jours = em.merge(jours);
		}

		return jours;
	}

	public Jours addJours(int j) {
		if (findByNbrJ(j).size() == 1) {
			return findByNbrJ(j).get(0);
		} else {
			Jours jours = new Jours();
			jours.setNbrJour(j);
			save(jours);
			return jours;
		}
	}

	public List<Jours> findByNbrJ(int nbrJ) {
		Map<String, Integer> param = new HashMap<>();
		param.put("nbrJour", nbrJ);
		log.debug("nbr jour demandé: "+nbrJ);
		return finder.findByNamedQuery("Jours.findByNbrJ", param);
	}


	public List<Jours> findAllJours() {
		return finder.findByNamedQuery("Jours.findAll", null);
	}


}
