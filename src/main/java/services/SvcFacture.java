package services;

import entities.Facture;
import org.apache.log4j.Logger;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SvcFacture extends Service<Facture> implements Serializable {
	private static final Logger log = Logger.getLogger(SvcFacture.class);
	private static final long serialVersionUID = 1L;
	Map<String, Object> params = new HashMap<String, Object>();

	public SvcFacture() {
		super();
	}

	@Override
	public Facture save(Facture facture) {
		if (facture.getIdLocations() == 0) {
			em.persist(facture);
		} else {
			facture = em.merge(facture);
		}

		return facture;
	}


	public List<Facture> findAllFacture() {
		return finder.findByNamedQuery("Facture.findAll", null);
	}


}